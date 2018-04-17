package Levels;
import Sprites.Sprite;

/**The class in charge to hold all of the relevent data
 * for any level. This includes the two dimensional int
 * array that is the map tiles, the sprite array, the
 * number of relevent sprites, and the start and end points
 * for the level.
 * @author Michael Mueller, Paul Coen, Dakota Oria
 */
public interface LevelInterface {
	/**Returns the number of relevant sprites in the level
	 * @return The number of sprites
	 */
	public int getNumOfSprites();
	/**Returns the start point if this level was accessed
	 * from a forward direction in the list.
	 * @return start point of that level.
	 */
	public double getStartNextX();
	/**Returns the start point if this level was accessed
	 * from a forward direction in the list.
	 * @return start point of that level.
	 */
	public double getStartNextY();
	/**Returns the start point if this level was accessed
	 * from a backward direction in the list.
	 * @return start point of that level.
	 */
	public double getStartPrevY();
	/**Returns the start point if this level was accessed
	 * from a backward direction in the list.
	 * @return start point of that level.
	 */
	public double getStartPrevX();
	/**Returns the end point at the end of the level (forward
	 * direction)
	 * @return relevent end point
	 */
	public int getEndNextX();
	/**Returns the end point at the end of the level (forward
	 * direction)
	 * @return relevent end point
	 */
	public int getEndNextY();
	/**Returns the end point at the end of the level (backward
	 * direction)
	 * @return relevent end point
	 */
	public int getEndPrevX();
	/**Returns the end point at the end of the level (backward
	 * direction)
	 * @return relevent end point
	 */
	public int getEndPrevY();
	/**Returns the sprite array for this level
	 * @return The array of sprites
	 */
	public Sprite[] getSpriteArray();
	/**Returns the two dimensional array that serves as the tile
	 * map for the game
	 * @return the map for this level
	 */
	public int[][] getMap();
}
