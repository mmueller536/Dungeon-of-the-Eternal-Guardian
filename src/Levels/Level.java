package Levels;
import Game.RaycastEngine;
import Sprites.Bob;
import Sprites.Sprite;

/**The Object in charge of holding all of the relevent data
 * for any level. This includes the two dimensional int
 * array that is the map tiles, the sprite array, the
 * number of relevent sprites, and the start and end points
 * for the level.
 * @author Michael Mueller, Paul Coen, Dakota Oria
 */
public class Level implements LevelInterface {
	public int[][] map;
	public Sprite[] spriteArray = new Sprite[RaycastEngine.MAX_SPRITES];
	protected int numOfSprites;
	public double startNextX;
	public double startNextY;
	protected double startPrevX;
	protected double startPrevY;
	protected int endNextX, endNextY, endPrevX, endPrevY;
	/**
	 * Initializes the levels map, sprites, and endpoints.
	 */
	public Level(){
		int[][] tempMap = {
				{1,1,1,1,1,1,1},
				{1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1},
				{1,0,1,1,1,0,1},
				{1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1},
				{1,1,1,1,1,1,1},
			}; 
		map = tempMap;
		numOfSprites = 1;
		spriteArray[0] = new Bob(5, 3);
		
		startNextX = 2;
		startNextY = 2;
		startPrevX = 6;
		startPrevY = 6;
		endNextX = 7;
		endNextX = 6;
		endPrevX = 2;
		endPrevY = 2;
	}
	/**
	 * Returns the number of relevant sprites in the level
	 * 
	 * @return The number of sprites
	 */
	public int getNumOfSprites(){
		return numOfSprites;
	}
	/**
	 * Returns the start point if this level was accessed
	 * from a forward direction in the list.
	 * 
	 * @return start point of that level.
	 */
	public double getStartNextX(){
		return startNextX;
	}
	/**
	 * Returns the start point if this level was accessed
	 * from a forward direction in the list.
	 * 
	 * @return start point of that level.
	 */
	public double getStartNextY(){
		return startNextY;
	}
	/**
	 * Returns the start point if this level was accessed
	 * from a backward direction in the list.
	 * 
	 * @return start point of that level.
	 */
	public double getStartPrevX(){
		return startPrevX;
	}
	/**
	 * Returns the start point if this level was accessed
	 * from a backward direction in the list.
	 * 
	 * @return start point of that level.
	 */
	public double getStartPrevY(){
		return startPrevY;
	}
	/**
	 * Returns the start point if this level was accessed
	 * from a backward direction in the list.
	 * 
	 * @return start point of that level.
	 */
	public int getEndNextX(){
		return endNextX;
	}
	/**
	 * Returns the end point at the end of the level (forward
	 * direction)
	 * 
	 * @return relevent end point
	 */
	public int getEndNextY(){
		return endNextY;
	}
	/**
	 * Returns the end point at the end of the level (forward
	 * direction)
	 * 
	 * @return relevent end point
	 */
	public int getEndPrevX(){
		return endPrevX;
	}
	/**
	 * Returns the end point at the end of the level (backward
	 * direction)
	 * 
	 * @return relevent end point
	 */
	public int getEndPrevY(){
		return endPrevY;
	}
	/**
	 * Returns the sprite array for this level
	 * 
	 * @return The array of sprites
	 */
	public Sprite[] getSpriteArray(){
		return spriteArray;
	}
	/**
	 * Returns the two dimensional array that serves as the tile
	 * map for the game
	 * 
	 * @return the map for this level
	 */
	public int[][] getMap(){
		return map;
	}
}