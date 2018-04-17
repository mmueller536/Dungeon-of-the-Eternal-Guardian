package Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;

import Levels.Dungeon;
import Levels.Dungeon2;
import Levels.Dungeon3;
import Levels.Dungeon4;
import Levels.DungeonBoss;
import Levels.Level;
import Textures.Texture;

/**The game engine of the application. Handles collaborating with the various
 * components of the game and the player's input to successfully run the game.
 * Levels are stored via a double linked list and the current level moves among
 * the list, created more elements in the collection as needed. Furthermore, the
 * engine handles sound in the game, as well as storing textures, the player,
 * sprites, the screens images, and various other assets.
 *
 * @author Michael Mueller, Paul Coen, Dakota Oria
 *
 * The heart and soul of our game (<3 +1-up)
 */

public class RaycastEngine extends JFrame implements Runnable{
	//Level Variables
	private LevelNode firstLevel;
	private LevelNode lastLevel;
	public static LevelNode currentLevel;
	
	//Interface Overlay
	private ImageIcon I = new ImageIcon("res/Interface/Interface.png");//Creates the Interface Overlay
	private Image Interface = I.getImage();
	public static String message = null;
	
	//Health Bar Assets
	private ImageIcon HB = new ImageIcon("res/Interface/HealthBarBorder.png");//Creates the HealthBarBorder
	private Image HealthBarBorder = HB.getImage();
	
	//Death screen Assets
	private ImageIcon DS = new ImageIcon("res/Interface/deathScreen.png");
	private Image DeathScreen = DS.getImage();
	
	//Thread variables
	private Thread thread;	//used for starting the game thread
	public static boolean winCon = false;
	private boolean running;	//tells us if the game is running or not
	
	//Screen Variables
	public Screen screen;
	private BufferedImage Raycast; //The Image update by the screen's raycasting
	public int[] pixels; //Pixel array that will be fed to the Raycast Image
	public ArrayList<Texture> wallTextures; //Texture  array for the ways
	
	//The player and the maximum allowed spites
	public static Player player;
	public final static int MAX_SPRITES=30;
	
	//Variables for screen size
	static GraphicsDevice multiDisplayDefault = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int screenWidth = multiDisplayDefault.getDisplayMode().getWidth();
	public static int screenHeight = multiDisplayDefault.getDisplayMode().getHeight();
	
	//Loot Tree root
	public final static Tree<String> root = new Tree<String>(); //base of the tree;
	
	//Level counter
	private int levelCounter;
	
	/**Initializes the RaycastEngine and handles the initializing of the various variables
	 * Noteworthy operations include:
	 * The connection of the pixel array to the buffered image for the screen
	 * The addition of all the wall textures
	 * The creation of the game thread
	 * The creation of the loot tree
	 * The initialization of the levels system, setting up the initial level
	 * The creation of the player and screen
	 * The setup of the JFrame where the screen and various overlays are placed.
	 * @throws IOException 
	 * 
	 */
	private RaycastEngine() throws IOException{
		thread = new Thread(this);//creates the thread and raycasted image
		Raycast = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)Raycast.getRaster().getDataBuffer()).getData();
		
		//creates the wall texture list
		wallTextures = new ArrayList<Texture>();
		wallTextures.add(Texture.wood);//1
		wallTextures.add(Texture.woodTorch);//2
		wallTextures.add(Texture.woodDoor);//3
		wallTextures.add(Texture.brick);//4
		wallTextures.add(Texture.brickTorch);//5
		wallTextures.add(Texture.brickDoor);//6
		wallTextures.add(Texture.Steel);//7
		
		//level counter variable keeps track of level number
		levelCounter = 0;
		
		//creates the loot tree object used in the game
		createLootTree();
		
		//Creates the first level (random)
		addLevel(pickLevel());
		currentLevel=firstLevel;
		
		//creates the player and the screen
		player = new Player(currentLevel.level.startNextX, currentLevel.level.startNextY, 1, 0, 0, -.66, currentLevel.level.getMap(), screenWidth);
		screen = new Screen(wallTextures, screenWidth, screenHeight);
		
		
		addKeyListener(player);
		setSize(screenWidth, screenHeight);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		//Begins the game loop
		start();
	}
	/**
	 * When called plays the sound that is specified.
	 * 
	 * @param s	is the string of the location of the file. Currently can only take .wav files
	 */
	public static void playSound(String s){
		try{
			File soundFile = new File(s);
			AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip;
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * When called plays a sound as long as the game is running
	 * 
	 * @param s	is the string of the location of the file. Currently can only take .wav files
	 */
	public static void playLoop(String s){
		try{
			File soundFile = new File(s);
			AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip;
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * creates the tree for the stuff the player can be given
	 */
	private void createLootTree() {
		Tree<String> gold = new Tree<String>();
		Tree<String> gold10 = new Tree<String>("Giving Gold: +10");
		Tree<String> gold100 = new Tree<String>("Giving Gold: +100");
		Tree<String> gold1000 = new Tree<String>("Giving Gold: +1000");
		Tree<String> buffs = new Tree<String>();
		Tree<String> speedPlayer = new Tree<String>("Stats added: speed +5");
		Tree<String> health = new Tree<String>("Stats added: health +20");
		Tree<String> armor = new Tree<String>("Stats added: armor +50");
		Tree<String> wepAdd = new Tree<String>();
		Tree<String> ShortSword = new Tree<String>("ShortSword");
		Tree<String> Claymore = new Tree<String>("Claymore");
		Tree<String> Spear = new Tree<String>("Spear");
		Tree<String> CrossBow = new Tree<String>("CrossBow");
		Tree<String> Bow = new Tree<String>("Bow");
		
		gold.setParent(root);
		gold10.setParent(gold);
		gold100.setParent(gold);
		gold1000.setParent(gold);
		gold.setChild(gold10, 0);
		gold.setChild(gold100, 1);
		gold.setChild(gold1000, 2);
		buffs.setParent(root);
		speedPlayer.setParent(buffs);
		health.setParent(buffs);
		armor.setParent(buffs);
		buffs.setChild(speedPlayer, 0);
		buffs.setChild(health, 1);
		buffs.setChild(armor, 2);
		buffs.setParent(root);
		ShortSword.setParent(wepAdd);
		Claymore.setParent(wepAdd);
		Spear.setParent(wepAdd);
		CrossBow.setParent(wepAdd);
		Bow.setParent(wepAdd);
		wepAdd.setChild(ShortSword, 0);
		wepAdd.setChild(Claymore, 1);
		wepAdd.setChild(Spear, 2);
		wepAdd.setChild(CrossBow, 3);
		wepAdd.setChild(Bow, 4);
		root.setChild(gold, 0);
		root.setChild(buffs, 1);
		root.setChild(wepAdd, 2);
	}
	/**Starts the game loop and the game's thread
	 * 
	 */
	private synchronized void start() {	//starts the thread
		running = true;
		thread.start();
	}
	
	/**Ends the current game thread
	 * 
	 */
	private synchronized void stop() {	//ends the thread
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**Draws all the images associated with the game. This includes the
	 * handling of the pixel array and its bufferedimage as well as the
	 * various other overlays that are involved.
	 * 
	 */
	private void render() throws IOException {
		//Creates the triple buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		if(!winCon){
			if(player.getHealth() > 0.0){//draws the screen if the player is alive
				g.drawImage(Raycast, 0, 0, Raycast.getWidth(), Raycast.getHeight(), null);
				g.drawImage(player.currentWeapon.getImage(), 0, 0, Raycast.getWidth(), (int)(Raycast.getHeight()*.90), null);
				drawGUI(g);
			}else{//draws a special case death screen if the player has died
				g.drawImage(DeathScreen, 0, 22, screenWidth, screenHeight-22, this);
				Color c = new Color(255, 100, 0);
				g.setColor(c);
				g.setFont(new Font("default", Font.BOLD, 60));
				g.drawString("Game Over", (int) (Raycast.getWidth()*.25), (int) (Raycast.getHeight()*.30));
				g.drawString("Your Score was", (int) (Raycast.getWidth()*.15), (int) (Raycast.getHeight()*.50));
				g.drawString(player.getGold()+"", (int) (Raycast.getWidth()/2), (int) (Raycast.getHeight()*.70));
			}
		}else{
			g.drawImage(DeathScreen, 0, 22, screenWidth, screenHeight-22, this);
			Color c = new Color(0, 255, 255);
			g.setColor(c);
			g.setFont(new Font("default", Font.BOLD, 60));
			g.drawString("You Win!!!", (int) (Raycast.getWidth()*.25), (int) (Raycast.getHeight()*.30));
			g.drawString("Your Score was", (int) (Raycast.getWidth()*.15), (int) (Raycast.getHeight()*.50));
			g.drawString(player.getGold()+"", (int) (Raycast.getWidth()/2), (int) (Raycast.getHeight()*.70));
		}
		bs.show();
	}
	/**
	 * Draws the interface for the game. Begins by drawing an overlay and places
	 * individual images on top of such. This could include alerts, health, mana,
	 * score, etc. etc.
	 * 
	 * @param g graphics object used to draw the interface.
	 * @throws IOException
	 */
	private void drawGUI(Graphics2D g) throws IOException {
		g.drawImage(Interface, 0, 22, screenWidth, screenHeight-22, this);
		if(player.getHealth() <= 1){
			g.drawImage(getSizeOf(0,0,(int)(136*player.getHealth()), 36, "res/Interface/HealthBar.png"), (int)(screenWidth*.60), (int)(screenHeight*.80), (int)((screenWidth*.3445)*player.getHealth()), (int)(screenHeight*.075), this);
		}else if(player.getHealth() > 1 && player.getHealth() < 2){
			g.drawImage(getSizeOf(0,0,(int)(136*1), 36, "res/Interface/HealthBar.png"), (int)(screenWidth*.60), (int)(screenHeight*.80), (int)((screenWidth*.3445)*1), (int)(screenHeight*.075), this);
			g.drawImage(getSizeOf(0,0,(int)(136*(player.getHealth()-1)), 36, "res/Interface/Armor Bar.png"), (int)(screenWidth*.60), (int)(screenHeight*.80), (int)((screenWidth*.3445)*(player.getHealth()-1)), (int)(screenHeight*.075), this);
		}else{
			g.drawImage(getSizeOf(0,0,(int)(136*1), 36, "res/Interface/HealthBar.png"), (int)(screenWidth*.60), (int)(screenHeight*.80), (int)((screenWidth*.3445)*1), (int)(screenHeight*.075), this);
			g.drawImage(getSizeOf(0,0,(int)(136*1), 36, "res/Interface/Armor Bar.png"), (int)(screenWidth*.60), (int)(screenHeight*.80), (int)((screenWidth*.3445)*1), (int)(screenHeight*.075), this);
		}
		g.drawImage(HealthBarBorder, (int)(screenWidth*.60), (int)(screenHeight*.80), (int)(screenWidth*.3445), (int)(screenHeight*.075), this);
		Color c = new Color(255, 215, 0);
		g.setColor(c);
		g.setFont(new Font("default", Font.BOLD, 30));
		g.drawString("Gold: " + player.getGold(), (int) (Raycast.getWidth()*.055), (int) (Raycast.getHeight()*.85));
		if(message != null){
			g.setFont(new Font("default", Font.BOLD, 20));
			g.drawString(message, (int) (Raycast.getWidth()*.055), (int) (Raycast.getHeight()*.95));
		}
	}
	/**
	 * This is used to cut and/or grab images from a sheet image
	 * 
	 * @param startX	The starting x location to begin the cut 
	 * @param startY	The starting y location to begin the cut
	 * @param endX	The ending x location of the cut
	 * @param endY	The ending y location of the cut
	 * @param s		The location of the image to be used
	 * @return returns the cropped image that will be used
	 */
	public BufferedImage getSizeOf(int startX, int startY, int endX, int endY, String s) throws IOException{
		BufferedImage img = ImageIO.read(new File(s));
		BufferedImage copyOfImage = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
		BufferedImage toReturn = copyOfImage.getSubimage(startX, startY, endX, endY);
		Graphics g = toReturn.getGraphics();
		g.drawImage(img, 0, 0, null);
		return toReturn;
	}
	
	/**The Game Loop for the RaycastEngine
	 * Runs at 60 frames a second
	 * Updates the screen and handles the activities of the player and sprites
	 *  as well handling the addition of new levels
	 * 
	 */
	public void run() {	//runs the thread at a beautiful 60fps
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		int levelNum = 0;
		//Plays the game's sound
		playLoop("sound/main.wav");//TODO
		while(running) {//Runs the game loop while running is true
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				//updates all the relevent sprites
				for(int spriteNum=0;spriteNum<currentLevel.level.getNumOfSprites();spriteNum++){
					currentLevel.level.spriteArray[spriteNum].updateBehavior(player);
					currentLevel.level.spriteArray[spriteNum].updatePlayer(currentLevel.level.getMap());
				}
				//updates the screen
				screen.update(player, pixels, currentLevel.level.getSpriteArray(), currentLevel.level.getNumOfSprites(), currentLevel.level.getMap());
				//updates the player
				player.updatePlayer(currentLevel.level.getMap());
				//checks to see if the player has reached the end of the level
				if((int)Math.round(player.xPos) == currentLevel.level.getEndNextX() && (int)Math.round(player.yPos)==currentLevel.level.getEndNextY()){
					if(currentLevel.next==null)
						addLevel(pickLevel());
					currentLevel = currentLevel.next;
					player.xPos = currentLevel.level.getStartNextX();
					player.yPos = currentLevel.level.getStartNextY();
					levelNum++;
				}
				//checks to see if the player is returning to a previous level
				if((int)Math.round(player.xPos) == currentLevel.level.getEndPrevX() && (int)Math.round(player.yPos)==currentLevel.level.getEndPrevY()){
					if(currentLevel.prev!=null){
						currentLevel = currentLevel.prev;
						player.xPos = currentLevel.level.getStartPrevX();
						player.yPos = currentLevel.level.getStartPrevY();
						levelNum--;
					}
				}
				delta--;
				//System.out.println("X: " + player.xPos +" Y: " + player.yPos);
			} 
			try {
				render(); //draws images and screen
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**Randomly finds a level and returns it during the creation of the next
	 * Level. The Boss room is returned if the player has reached far enough 
	 * in the game.
	 * 
	 * @return The level that was randomly selected
	 */
	private Level pickLevel(){
		Level result;
		Random gen = new Random();
		switch(gen.nextInt(5)){ 
		case 0:
			result = new Dungeon();
			break;
		case 1:
			result = new Dungeon2();
			break;
		case 2:
			result = new Dungeon3();
			break;
		case 3:
			result = new Dungeon4();
			break;
		default:
			result = new Dungeon();//demoroom
		}
		//changes level to the boss room
		if(levelCounter >= 6){
			result = new DungeonBoss();
		}
		levelCounter++;
		return result;
	}
	/**The main method that begins the execution
	 * @throws IOException 
	 * 
	 */
	public static void main(String [] args) throws IOException {	//Let the Dungeon Crawl Begin, good luck 
		RaycastEngine game = new RaycastEngine();
	}
	/**Checks if the level list is empty
	 * @return True if the list is empty, false if not empty
	 */
	private boolean isEmpty(){
		return firstLevel==null;
	}
	/**Adds a new level when the current level is at the end and
	 * the player proceeds to the next area.
	 * 
	 * @param level The level that is to be added
	 */
	private void addLevel(Level level){
		LevelNode newLevel = new LevelNode(level);
		if(!isEmpty())
			lastLevel.next = newLevel;
		else
			firstLevel = newLevel;
		newLevel.prev = lastLevel;
		lastLevel = newLevel;
	}
	/**The private class LevelNode that contains all the relevent level
	 * data in a double linked list format
	 * 
	 */
	public class LevelNode{
		private LevelNode next; //next node in list
		private LevelNode prev; //previous node in list
		public Level level; //level data
		
		/**Passes the relevent data into the super constructor
		 * LevelNode(Level level, LevelNode next, LevelNode prev)
		 * to be process and successfully initialized
		 * 
		 * @param level
		 */
		private LevelNode(Level level){
			this(level, null, null);
		}
		/**The constructor that initializes the level data, the next level
		 * in the list, and the previous level in the list.
		 * 
		 * @param level The level data itself
		 * @param next The next LevelNode in the list
		 * @param prev The previous LevelNode in the list
		 */
		private LevelNode(Level level, LevelNode next, LevelNode prev){
			this.level = level;
			this.next = next;
			this.prev = prev;
		}
	}
}
