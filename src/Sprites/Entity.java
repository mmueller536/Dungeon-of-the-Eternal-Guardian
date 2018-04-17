package Sprites;

/**The Class that the sprite class implements and represents
 * all monsters and objects in the game besides walls and the
 * player
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 */
public interface Entity {
	
	/**
	 * Moves the entity to a point on the map, ensuring collision does
	 * not occur
	 * @param map The map that will be checked
	 */
	public void updatePlayer(int[][] map);
	
	/**The method used for the sprite to attack the player.
	 */
	public void attack();
	
	/**Method used to attack the sprite and do damage to its
	 * respective health.
	 * @param damageTaken the amount of damage that is taken
	 */
	public void damaged(int damageTaken);
	
	/**Checks if the sprite is currently alive
	 * @return returns true if the sprite is alive, false if dead
	 */
	public boolean alive();
}