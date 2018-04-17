package Game;

import Sprites.Sprite;

/**The Screen class handles various inputs and edits a one dimensional
 * array of inputs that stores all the pixels that will be used and converted
 * into an image in the RaycastEngine Class.
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 *
 */
public interface ScreenInterface {
	/**Screen's Update Method:
	 * The most important method of the class, in charge of handling the input of the player, sprites, etc.
	 * and updating the screen with this information. This is done via raycasting, which creates the pseudo
	 * 3D environment.
	 * 
	 * @param player: gives the screen access to the player and the player's information.
	 * @param pixels: gives access to the pixel array the screen is drawn from.
	 * @param spriteArray: the array of sprites in the current level.
	 * @param numOfSprites: the number of initialized sprites within the array.
	 * @param m: the current level's map tiles (numbers corresponding to wall texture.
	 * @return: returns the pixel array that will be used in the raycastEngine, post update.
	 */
	public int[] update(Player player, int[] pixels, Sprite[] spriteArray, int numOfSprites, int[][] m);
	
}