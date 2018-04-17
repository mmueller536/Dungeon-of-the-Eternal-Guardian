package Sprites;

import java.util.Random;

import javax.imageio.ImageIO;

import Game.Player;
import Game.RaycastEngine;
import Textures.Texture;
import Textures.TextureAnimation;
/**
 * Implements the Entity Interface. This is a outline for an object in the game. 
 * Contains all the necessary variables to move the sprite, determine if the sprite is alive, and the AI of the sprite.
 * The sprite moves based on a Cartesian coordinate plane.
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 */
public class Sprite implements Entity {
	
	//Data Variables for the sprite
	public String name;	//The name of the sprite
	protected int health;	//This is the amount of life the sprite has
	protected int damage; //The amount of damage the sprite does
	protected String loot = null; //What thing the sprite will drop from the loot tree
	protected boolean hasGivenLoot; //To determine if the sprite has given its loot to the player
	
	public double x;	//the x location of the sprite
	protected double dx;	//change in x location
	public double y; 	//the y location of the sprite
	protected double dy;	//change in y location
	protected double speed;//higher number means slower speed, 60 is 1 tile per second
	public double centerX;
	public double distance;
	protected double attackRange;	//The range that the sprite has to attack in tiles
			
	
	protected Behavior topBehavior;	//The Expressed AI behavior
	protected Random rand = new Random(System.nanoTime());		//A RNG
	public boolean seen;	//detects if the player has been seen
	protected double lastPlayerX, lastPlayerY;		//last seen place of the player, for "hunting"
	
	protected String spriteDeathSound;
	protected String spriteHitSound;

	public Texture currentTexture;
	protected TextureAnimation currentAnimation;
	protected TextureAnimation walkingAnimation;
	protected TextureAnimation attackAnimation;
	protected TextureAnimation dyingAnimation;
	
	/**
	 * Creates the sprite and calls its initialization function
	 * 
	 * @param x the x coordinate to spawn the sprite
	 * @param y	the y coordinate to spawn the sprite
	 */
	public Sprite(double x, double y){
		initializeSprite();
		this.x=x;
		this.y=y;
	}
	/**
	 * Initializes all the variables for the sprite to use. This
	 * includes data variables, position variables, and behavior
	 * & animation setup. Meant to be overrided by "unique"
	 * sprites.
	 */
	protected void initializeSprite(){
		//Data relevent for sprite
		name = "none";
		attackRange = .5;
		damage=2;
		health = 20;
		speed = 60;
		
		//Sprite in relation to player
		hasGivenLoot = false;
		distance=0;
		
		//Behaviors and sprites
		push("patrol");
		loadAnimations();//Loads animations for a given sprite
		currentAnimation = walkingAnimation;//default the first animation
	}
	
	/**
	 * Loads all animations that a sprite will use in its
	 * "lifespan".
	 */
	protected void loadAnimations(){
		walkingAnimation = new TextureAnimation("res/MechCon.png", 1);
		attackAnimation = new TextureAnimation("res/MechCon.png", 1);
		dyingAnimation = new TextureAnimation("res/MechCon.png",1);
	}
	
	/**
	 * Consults the animation and sets the next frame
	 * of the walking animation to advance by one. Is 
	 * designed to be called continuously every 1/60th 
	 * of a second.
	 */
	protected void animation(){
		currentTexture = currentAnimation.Animate();
	}
	
	/**
	 * In charge of moving the sprite and ensuring it
	 * obeys obstacles of the environment via collision
	 * testing. Receives the map as an input to perform
	 * testing.
	 */
	public void updatePlayer(int[][] map) {
		if(alive()){
			if(!collision(x+dx, y)){
				x+=dx;
			}
			if(!collision(x, y+dy)){
				y+=dy;
			}
		}else{
			dx = 0;
			dy = 0;
		}
	}
	/**
	 * Handles damaging the sprite. Simply takes in input
	 * of damage and removes said damage from health.
	 * 
	 * @param damageTaken The damage being removed from the health pool
	 */
	public void damaged(int damageTaken) {
		health-=damageTaken;
	}
	/**
	 * Tests to see if the sprite is still alive.
	 * 
	 * @return returns true if alive, false if dead
	 */
	public boolean alive() {
		boolean alive = true;
		if(health<=0)
			alive = false;
		return alive;
	}
	/**
	 * The basic AI core of the sprite. Handles behaviors
	 * via a stack. This creates a sense of development in
	 * the AI as methods are designed to try to "elevate"
	 * themselves to the next degree. (i.e patrol to persue
	 * to attack). This method acts as the operator that
	 * handles the highest level behavior (latest) that is 
	 * being pushed.
	 * 
	 * @param map takes the current map as a variable
	 * @param player takes the current player as a variable
	 */
	public void updateBehavior(Player player) {
		//Tests whether the sprite is alive or dead
		if(alive()){
			//Acts as the operator, directing the sprite
			//to the relevent method.
			switch(peek()){
			case "patrol":
				patrol();
				break;
			case "persue":
				persue();
				break;
			case "attack":
				attack();
				break;
			case "persuePath":
				persuePath();
				break;
			}
		}else{
			//Handles the death of the sprite
			dx=0;
			dy=0;
			currentAnimation = dyingAnimation;
			if(!hasGivenLoot){
				RaycastEngine.player.addLoot(getLoot());
				hasGivenLoot = true;
				RaycastEngine.playSound("sound/Hit.wav");
			}
		}
		//Continues the relevent animation
		animation();
	}
	
	/**
	 * *AI function*
	 * 
	 * Handles attacking for the sprite and ensures
	 * that the enemy of the sprite is within range
	 * to be attacked. In the even that the enemy is
	 * outside of attack range, the enemy will forfeit
	 * attack and regress to a previous action.
	 */
	public void attack() {
		currentAnimation = attackAnimation;
		if(currentAnimation.animationFinished()){
			RaycastEngine.player.damaged(damage);
			RaycastEngine.playSound("sound/Hit2.wav");
		}
		dx=0;
		dy=0;
		if(distance>attackRange)
			pop();
	}
	
	/**
	 * *AI function*
	 * 
	 * Will cause the sprite to chase after the player.
	 * Uses very basic path-finding by testing if it is 
	 * at the players location and will continuously move 
	 * toward the player via simple comparisons. Will make
	 * an attempt to persue "after-image" of the player if
	 * failed. Otherwise leads to an attack.
	 */
	protected void persue(){
		currentAnimation = walkingAnimation;
		if(distance>attackRange){
			if(RaycastEngine.player.xPos>x)
				dx=(1/speed);
			else if(RaycastEngine.player.xPos<x)
				dx=-(1/speed);
			else
				dx=0;
			if(RaycastEngine.player.yPos>y)
				dy=(1/speed);
			else if(RaycastEngine.player.yPos<y)
				dy=-(1/speed);
			else
				dy=0;
			if(seen){
				lastPlayerX = RaycastEngine.player.xPos;
				lastPlayerY = RaycastEngine.player.yPos;
			}else{
				push("persuePath");
			}
				
		}else{
			push("attack"); //if close enough it will cause the sprite to attack the player
		}
	}
	
	/**
	 * *AI function*
	 * 
	 * If the player is not seen, will go the players 
	 * last location in an attempt to continue pursuit.
	 * If the method fails, the pursuit is popped as
	 * well as its previous persue method. This returns
	 * the sprite to the original patrol method. If
	 * successful, the sprite will begin an attack or
	 * revert back to the simplier persue methond.
	 */
	protected void persuePath(){
		currentAnimation = walkingAnimation;
		if(lastPlayerX>x)
			dx=1/speed;
		else if(lastPlayerX<x)
			dx=-1/speed;
		else
			dx=0;
		if(lastPlayerY>y)
			dy=1/speed;
		else if(lastPlayerY<y)
			dy=-1/speed;
		else
			dy=0;
		if(seen==true){
			pop();
		}else if(x>lastPlayerX+.125&&x<lastPlayerX-1.25)
			if(y>lastPlayerY+.125&&y<lastPlayerY-1.25){
				pop();
				pop();
			}
	}
	
	/**
	 * *AI function*
	 * 
	 * Will cause the sprite to randomly move around 
	 * until it either dies or sees the player. SBase 
	 * Behavior that cannot be failed. Aims to get the
	 * sprite in an eventual pursuit of the player.
	 */
	protected void patrol() {
		currentAnimation = walkingAnimation;
		if(seen){
			push("persue");
		}else{
			if(currentAnimation.animationFinished()){
				int switchNum = rand.nextInt(6);
				switch(switchNum){
					case 0:
						dx = 1/speed;
						dy = 1/speed;
						break;
					case 1:
						dx = 1/-speed;
						dy = 1/speed;
						break;
					case 2:
						dx = 1/speed;
						dy = 1/-speed;
						break;
					case 3:
						dx = 1/-speed;
						dy = 1/speed;
						break;
					case 4:
						dx = 0;
						dy = 1/speed;
						break;
					case 5:
						dx = 1/speed;
						dy = 0;
						break;
				}
			}
		}
	}
	/**
	 * Detects if the sprite has collided with a wall.
	 * Used for moving the sprite and obeying the laws
	 * of physics.
	 * 
	 * @param x	tests the x location that is input
	 * @param y	tests the y location that is input
	 * @return returns true if it has hit something
	 */
	protected boolean collision(double x, double y){
		if(RaycastEngine.currentLevel.level.map[(int) x][(int) y] > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Used to add an operation to the AI stack. Each Behavior
	 * is designed to try and push another method that will
	 * continue to the sprites goal (ex. attacking the player).
	 * 
	 * @param behaviorName	indicates the top most behavior to be added
	 */
	protected void push(String behaviorName){
		Behavior newBehavior = new Behavior(behaviorName, topBehavior);
		topBehavior=newBehavior;
	}
	
	/**
	 * Removes the current action the AI is doing. This is normally
	 * done in response to a behavior "self-terminating" post-failure.
	 * 
	 * @return	returns the removed behavior
	 */
	protected String pop(){
		String behavior="dead";
		if(!noBehavior()){
			behavior=topBehavior.behaviorName;
			topBehavior=topBehavior.nextBehavior;
		}
		return behavior;
	}
	/**
	 * Returns highest behavior in the stack. This is
	 * normally meant to be called in determining what
	 * the sprite is supposed to do.
	 * 
	 * @return	Returns what the AI is supposed to do
	 */
	protected String peek(){
		String behavior="dead";
		if(!noBehavior())
			behavior=topBehavior.behaviorName;
		return behavior;
	}
	
	/**
	 * Detects if the stack is empty, done
	 * via mistake or if the sprite as been
	 * killed and behaviors emptied
	 * 
	 * @return	if it was successfully set
	 */
	protected boolean noBehavior(){
		return topBehavior==null;
	}
	
	/**
	 * A linked Stack of the behaviors the sprite will have.
	 * The height of the stack corresponds to the levels and
	 * steps that must be taken to reach that sprites
	 * programmed goal. Each behavior is meant to be a stepping
	 * stone to reach the next
	 *
	 */
	protected class Behavior{
		protected String behaviorName;
		protected Behavior nextBehavior;
		/**
		 * Constructs a new behavior with a desired behavior name
		 * @param behaviorName the behavior to be added
		 */
		protected Behavior(String behaviorName){
			this(behaviorName, null);
		}
		/**
		 * Constructs a new behavior with a desired behavior name
		 * and a desired next behavior.
		 * 
		 * @param behaviorName the behavior to be added
		 * @param next indicates the next behavior
		 */
		protected Behavior(String behaviorName, Behavior next){
			this.behaviorName=behaviorName;
			nextBehavior=next;
		}
	}
	
	/**
	 * Returns the string corresponding to the loot the
	 * sprite has.
	 * 
	 * @return	The loot that the sprite has
	 */
	public String getLoot(){
		return loot;
	}
}