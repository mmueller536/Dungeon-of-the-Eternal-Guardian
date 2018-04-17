package Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import Sprites.Entity;
/**The Player Class handles all input that the user inputs
 * during execution; This is done via the key listener.
 * It also stores all of the player's weapon objects and
 * the score (gold) of the player, among other important
 * pieces of data.
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 */
public class Player implements Entity, KeyListener {
	//All weapons
	Weapon currentWeapon;
	Weapon Fist;
	Weapon ShortSword;
	Weapon Spear;
	Weapon Claymore;
	Weapon CrossBow;
	Weapon Bow;
	
	//dev Weapon
	Weapon SpikeFist;
	boolean attacking;
	
	//Players health and gold
	private int health;
	private int gold;
	
	//Player's position data
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back;
	public int[][] currentMap;
	
	//Player speed values
	public double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;

	//Screenwidth (saved for later in constructor)
	int screenWidth;
	
	/**Initializes the player's data, position, and is responsible for calling the loading
	 * of Weapons.
	 * 
	 * @param x X coordinate of player
	 * @param y Y coordinate of player
	 * @param xd X coordinate for ray direction
	 * @param yd Y coordinate for ray direction
	 * @param xp X coordinate for the camera plane
	 * @param yp Y coordinate for the camera plane
	 * @param map The current map the player is in
	 * @param screenWidth The width of the screen
	 * @throws IOException 
	 */
	public Player(double x, double y, double xd, double yd, double xp, double yp, int[][]map, int screenWidth) throws IOException 
	{	
		this.screenWidth = screenWidth; //sets the known screen width
		//Data initialized for player
		health=100;
		gold = 0;
		attacking = false;
		
		//Current Position Data initialized
		currentMap= map;
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
		
		//Initializes arsenal for player
		loadWeapons();
		currentWeapon = Fist; //Sets the default weapon
		currentWeapon.animation();
	}
	/**
	 * Initializes the arsenal of weapons the player has and the data
	 * for each relevent weapon.
	 * @throws IOException 
	 */
	private void loadWeapons() throws IOException{
		//Initializes all the weapons below
		Fist = new Weapon("res/Weapons/fist.png", 2,(int)Math.round(.33*screenWidth), (int)Math.round(.66*screenWidth), 15, 2);
		Bow = new Weapon("res/Weapons/bow.png", 1,(int)Math.round(.20*screenWidth), (int)Math.round(.80*screenWidth), 30, 10.0);
		CrossBow = new Weapon("res/Weapons/crossbow.png", 1,(int)Math.round(.20*screenWidth), (int)Math.round(.80*screenWidth), 50, 5.0);
		ShortSword = new Weapon("res/Weapons/sword.png", 5,(int)Math.round(.40*screenWidth), (int)Math.round(.60*screenWidth), 30, 1.5);
		Spear = new Weapon("res/Weapons/spear.png", 5,(int)Math.round(.45*screenWidth), (int)Math.round(.55*screenWidth), 50, 3.0);
		Claymore = new Weapon("res/Weapons/claymore.png", 10,(int)Math.round(.20*screenWidth), (int)Math.round(.80*screenWidth), 40 ,2.0);
		SpikeFist = new Weapon("res/Weapons/spikeFist.png", 1,(int)Math.round(.20*screenWidth), (int)Math.round(.80*screenWidth), 100 , 20.0);
	}
	/**
	 * Called when the player is to be damaged. Damages the player
	 * by the amount specified by the input
	 * 
	 * @param damageTaken The amount of damage to be dealt to the player
	 */
	public void damaged(int damageTaken){
		health = health-damageTaken;
	}
	/**
	 * Causes the player to deal damage to sprites that are
	 * 1) Within Range
	 * 2) Within Target Reticle
	 * 3) In Front of Player
	 */
	public void attack(){
		if(!attacking){
			attacking = true;
			//Plays sounds dependent on weapon
			if(currentWeapon==CrossBow||currentWeapon==Bow)
				RaycastEngine.playSound("sound/Shoot.wav");
			else
				RaycastEngine.playSound("sound/SpriteDeath.wav");
			//loops to see if any sprites were damaged
			for(int i=0; i<RaycastEngine.currentLevel.level.getNumOfSprites(); i++){
					//Checks to make sure if they are opening a chest or not
					if(RaycastEngine.currentLevel.level.spriteArray[i].name == "chest"){
						//ensures the player is close enough
						if(RaycastEngine.currentLevel.level.spriteArray[i].distance<1){
							if((RaycastEngine.currentLevel.level.spriteArray[i].centerX>currentWeapon.getLeftRect()) && (RaycastEngine.currentLevel.level.spriteArray[i].centerX<currentWeapon.getRightRect())){
								RaycastEngine.currentLevel.level.spriteArray[i].damaged(currentWeapon.getDamage());
								RaycastEngine.playSound("sound/ExplosionWarning.wav");
							}
						}
					}else{
						//Checks range and attacks
						if(RaycastEngine.currentLevel.level.spriteArray[i].distance<currentWeapon.getWeaponRange()){
							if((RaycastEngine.currentLevel.level.spriteArray[i].centerX>currentWeapon.getLeftRect()) && (RaycastEngine.currentLevel.level.spriteArray[i].centerX<currentWeapon.getRightRect())){
								RaycastEngine.currentLevel.level.spriteArray[i].damaged(currentWeapon.getDamage());
							}
						}
					}
			}
		}
	}
	/**
	 * Tests to see if the player is alive
	 * 
	 * @return Returns whether or not the player is currently alive
	 */
	public boolean alive(){
		return health<=0;
	}
	
	/**
	 * The movement and attack keys for the game, as well as dev commands
	 * to ease in demo-ing the game. Detects when a key is pressed and does 
	 * the action that is specified via the key input.
	 *
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = true;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = true;
		if((key.getKeyCode() == KeyEvent.VK_UP))
			forward = true;
		if((key.getKeyCode() == KeyEvent.VK_DOWN))
			back = true;
		if((key.getKeyCode()== KeyEvent.VK_SPACE))
			attack();
		if((key.getKeyCode()== KeyEvent.VK_1)){
			switchWeapon(ShortSword);
		}
		if((key.getKeyCode()== KeyEvent.VK_2)){
			switchWeapon(Spear);
		}
		if((key.getKeyCode()== KeyEvent.VK_3)){
			switchWeapon(Claymore);
		}
		if((key.getKeyCode()== KeyEvent.VK_4)){
			switchWeapon(CrossBow);
		}
		if((key.getKeyCode()== KeyEvent.VK_5)){
			switchWeapon(Bow);
		}
		if((key.getKeyCode()== KeyEvent.VK_B)){
			//System.out.println("Boost!!!");
			if(MOVE_SPEED < .99){
				MOVE_SPEED += 0.01;
			}
		}
		if((key.getKeyCode()== KeyEvent.VK_V)){
			//System.out.println("Boost!!!");
			if(MOVE_SPEED > 0.01){
				MOVE_SPEED -= 0.01;
			}
		}
		if((key.getKeyCode()== KeyEvent.VK_H)){
			health++;
		}
		if((key.getKeyCode()== KeyEvent.VK_G)){
			switchWeapon(SpikeFist);
		}
	}
	/**
	 * Attempts to switch the weapon to a specified weapon
	 * of choice
	 * 
	 * @param w The weapon in question
	 */
	private void switchWeapon(Weapon w) {
		if(w.hasWeapon){
			currentWeapon = w;
			currentWeapon.animation();
		}else{
			RaycastEngine.message = "You don't have that weapon";
		}
	}
	/**
	 * Listens to detect if the keys for movement are still
	 * being pressed.
	 */
	@Override
	public void keyReleased(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = false;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = false;
		if((key.getKeyCode() == KeyEvent.VK_UP))
			forward = false;
		if((key.getKeyCode() == KeyEvent.VK_DOWN))
			back = false;
	}
	/**
	 * Handles updating the player and ensuring that the
	 * player does not collide or "clip through" walls.
	 */
	public void updatePlayer(int[][] map) {
		//checks which direction the player is moving and ensures that there
		//is no collision before moving
		if(forward) {
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
				xPos+=xDir*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
				yPos+=yDir*MOVE_SPEED;
		}
		if(back) {
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
				yPos-=yDir*MOVE_SPEED;
		}
		if(right) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
		}
		if(left) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
		}
		
		if(attacking){
			if(currentWeapon.animationFinished()){
				currentWeapon.resetAnimation();
				attacking = false;
			}else{
				currentWeapon.animation();
			}
		}
	}
	
	/**
	 * Unused and forced to be implemented
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Returns the current health of the player
	 * 
	 * @return Player's health
	 */
	public double getHealth() {
		double result = ((double)health)/100;
		if(result < 0){
			result = 0;
		}
		return result;
	}
	/**Gets the current amount of gold the player has
	 * 
	 * @return Player's amount of gold
	 */
	public int getGold(){
		return gold;
	}
	/**Takes in the name of the loot the player has received and adds
	 * it to their "inventory"
	 * @param s The name of the loot received
	 */
	public void addLoot(String s){
		switch(s){	//handles loot giving here based off of loot label
		case "Giving Gold: +10": gold += (int)Math.round(Math.random()*10);
			break;
		case "Giving Gold: +100": gold += (int)Math.round(Math.random()*100);
			break;
		case "Giving Gold: +1000": gold += (int)Math.round(Math.random()*1000);
			break;
		case "Stats added: speed +5": for(int i = 0; i < 5; i++){if(MOVE_SPEED < 0.99){MOVE_SPEED += 0.01;}}
			break;
		case "Stats added: health +20": health += 20;
			break;
		case "Stats added: armor +50": health += 50;
			break;
		case "ShortSword": ShortSword.hasWeapon = true;
			break;
		case "Claymore": Claymore.hasWeapon = true;
			break;
		case "Spear": Spear.hasWeapon = true;
			break;
		case "CrossBow": CrossBow.hasWeapon = true;
			break;
		case "Bow": Bow.hasWeapon = true;
			break;
		}
		RaycastEngine.message = s;
	}
	/**Method responsible if the player is poisoned
	 */
	public void poision(){
		health--;
	}
	/**Method responsible if the player is slowed
	 */
	public void slow(){
		if(MOVE_SPEED > 0.02){
			MOVE_SPEED -= 0.01;
		}
	}
}
