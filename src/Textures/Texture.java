package Textures;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 *
 * Loads all the textures for use in the game, this includes all: walls, floors, sprites, player, weapons, etc...
 */
public class Texture {
	public static Texture wood = new Texture("res/Wall_Textures/wood.png", 64);
	public static Texture woodDoor = new Texture("res/Wall_Textures/woodDoor.png", 64);
	public static Texture woodTorch = new Texture("res/Wall_Textures/woodTorch.png", 64);
	public static Texture brick = new Texture("res/Wall_Textures/brick.png", 64);
	public static Texture brickDoor = new Texture("res/Wall_Textures/brickDoor.png", 64);
	public static Texture brickTorch = new Texture("res/Wall_Textures/brickTorch.png", 64);
	public static Texture Steel = new Texture("res/Wall_Textures/Steel.png", 64);
	
	public int[] pixels;
	private String loc;
	public final int SIZE;
	/**
	 * creates a texture to use in the game
	 * @param location	The location the the texture on the computer
	 * @param size	The square size of the texture ie 64x64 pixel texture = 64 as size
	 */
	public Texture(String location, int size) {
		this(location, size, 0, 0);
	}
	
	/**
	 * creates a texture to use in the game
	 * @param location	The location the the texture on the computer
	 * @param size	The square size of the texture ie 64x64 pixel texture = 64 as size
	 * @param startX	The starting x location to crop the image
	 * @param startY	The starting y location to crop the image
	 */
	public Texture(String location, int size, int startX, int startY) {
		loc = location;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load(startX, startY);
	}
	
	/**
	 * 	Default constructor if no cropping is needed
	 * @param image	the image to me cropped
	 * @param size	The size of the image
	 */
	public Texture(BufferedImage image, int size){
		this(image, size, 0, 0);
	}
	/**
	 * 	For when the image is provided and cropping is needed
	 * @param image	the image to me cropped
	 * @param size	The size of the image
	 * @param startX	the starting x location to crop
	 * @param startY	the starting y location to crop
	 */
	public Texture(BufferedImage image, int size, int startX, int startY){
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		int w = SIZE;
		int h = SIZE;
		image.getRGB(startX, startY, w, h, pixels, 0, w);
	}
	/**
	 * loads the image into the game by converting it into many pixels
	 * @param startX	The starting x location to pixelate the image
	 * @param startY	The starting y location to pixelate the image
	 */
	private void load(int startX, int startY) {
		try {
			BufferedImage image = ImageIO.read(new File(loc));
			int w = SIZE;
			int h = SIZE;
			image.getRGB(startX, startY, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}