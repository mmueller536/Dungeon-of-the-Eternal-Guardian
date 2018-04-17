package Game;
import java.util.ArrayList;

import Sprites.Sprite;
import Textures.Texture;

import java.awt.Color;

/**The Screen class handles various inputs and edits a one dimensional
 * array of inputs that stores all the pixels that will be used and converted
 * into an image in the RaycastEngine Class.
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 * 
 */

public class Screen implements ScreenInterface {
	private int[][] map; //map point data
	private int mapWidth, mapHeight, width, height; //Map dimensions and screen size
	private ArrayList<Texture> wallTextures; //texture array for drawing walls
	
	private double[] ZBuffer; //ZBuffer that creates the allusion of depth among drawn sprites

	private Sprite[] spriteArray; //array of sprites that will be sprite-casted on the screen
	private int numOfSprites; //the number of initialized sprites in the spriteclass.
	
	/**Screen Constructor:
	 * Initializes the screen and passes some specific information that 
	 * will be constant throughout the program's execution
	 * 
	 * @param tex: imports the wall textures to be drawn
	 * @param w: alerts the screen about how wide the screen is
	 * @param h: alerts the screen about how tall the screen is
	 */
	public Screen(ArrayList<Texture> tex, int w, int h) {
		wallTextures = tex;
		width = w;
		height = h;
		ZBuffer= new double[width];
	}
	
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
	public int[] update(Player player, int[] pixels, Sprite[] spriteArray, int numOfSprites, int[][] m) {
		//assigns input to variables.
		map = m;
		this.spriteArray=spriteArray;
		this.numOfSprites = numOfSprites; //end assignments
		
		//Colors in and fills in the floors and Ceiling with solid colors
		for(int n=0; n<pixels.length/2; n++){ //top half of screen is filled with a dark grey.
			if(pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.DARK_GRAY.getRGB();
		}
		for(int i=pixels.length/2; i<pixels.length; i++){ //bottom half of screen is filled with a light grey
			if(pixels[i] != Color.gray.getRGB()) pixels[i] = Color.gray.getRGB();
		}//End of filling floor/ceiling
		
	    //Begin Raycasting of Walls
	    for(int x=0; x<width; x=x+1) { //for loop that cycles through each vertical "slice" of the screen
	    	
			double playerX = 2 * x / (double)(width) -1; //The current vertical slice.
			
			//Creates a ray vector that will be "casted" into the environment
		    double rayDirX = player.xDir + player.xPlane * playerX; //The ray vector's x direction
		    double rayDirY = player.yDir + player.yPlane * playerX; //The ray vector's y direction
		    
		    //Map position
		    int mapX = (int)player.xPos; //player's x position
		    int mapY = (int)player.yPos; //player's y position
		    
		    //Length of ray from current position to next the x or y-side
		    double sideDistX;
		    double sideDistY;
		    //Length of ray from one side to next in map
		    double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
		    double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
		    double perpWallDist;
		    //Direction for ray to go in x and y direction
		    int stepX, stepY;
		    boolean hit = false;//was a wall hit
		    int side=0;//was the wall vertical or horizontal
		    //Figure out the step direction and initial distance to a side
		    //Detects the direction the ray is being casted
		    //If the direction in either direction is negative,
		    //the step will be negative and the sideDistX is calculated.
		    //Else, the step will be positive and the sideDist is calculated.
		    if (rayDirX < 0){
		    	stepX = -1;
		    	sideDistX = (player.xPos - mapX) * deltaDistX;
		    }
		    else
		    {
		    	stepX = 1;
		    	sideDistX = (mapX + 1.0 - player.xPos) * deltaDistX;
		    }
		    if (rayDirY < 0)
		    {
		    	stepY = -1;
		        sideDistY = (player.yPos - mapY) * deltaDistY;
		    }
		    else
		    {
		    	stepY = 1;
		        sideDistY = (mapY + 1.0 - player.yPos) * deltaDistY;
		    }
		    //Knowing the information of the increments for the ray in the x and y
		    //direction, the ray is extended via the below loop until it collides 
		    //and detects a wall.
		    while(!hit){
		    	//Checks if the ray's vector should be incremented by its sideDist X or sideDistY
		    	//Depending on which is more dominant in the vector's growth.
		    	if (sideDistX < sideDistY)
		        {
		    		sideDistX += deltaDistX;
		    		mapX += stepX;
		    		side = 0;
		        }
		        else
		        {
		        	sideDistY += deltaDistY;
		        	mapY += stepY;
		        	side = 1;
		        }
		    	//Checks if ray has hit a wall
		    	if(RaycastEngine.currentLevel.level.map[mapX][mapY] > 0) hit = true;
		    }
		    //Calculate distance to the point of impact on wall.
		    if(side==0)
		    	perpWallDist = Math.abs((mapX - player.xPos + (1 - stepX) / 2) / rayDirX);
		    else
		    	perpWallDist = Math.abs((mapY - player.yPos + (1 - stepY) / 2) / rayDirY);
		    //Calculates the height of the wall based on the distance from the player
		    int lineHeight;
		    if(perpWallDist > 0) //if there is distance from the screen calculate height
		    	lineHeight = Math.abs((int)(height / perpWallDist));
		    else //else simply use the height of the screen
		    	lineHeight = height;
		    //calculate lowest and highest pixel to fill in current stripe
		    int drawStart = -lineHeight/2 + height/2;
		    if(drawStart < 0)
		    	drawStart = 0;
		    int drawEnd = lineHeight/2 + height/2;
		    if(drawEnd >= height) 
		    	drawEnd = height - 1;
		    //add a texture
		    int texNum = map[mapX][mapY] - 1;
		    double wallX; //Exact position of where wall was hit, used later to find the x coordinate on texture.
		    if(side==1) { //If its a y-axis wall
		    	wallX = (player.xPos + ((mapY - player.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
		    } else { //X-axis wall
		    	wallX = (player.yPos + ((mapX - player.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
		    }
		    wallX-=Math.floor(wallX);
		    
		    //x coordinate on the texture
		    int texX = (int)(wallX * (wallTextures.get(texNum).SIZE));
		    if(side == 0 && rayDirX > 0) texX = wallTextures.get(texNum).SIZE - texX - 1;
		    if(side == 1 && rayDirY < 0) texX = wallTextures.get(texNum).SIZE - texX - 1;
		    //calculate y coordinate on texture
		    for(int y=drawStart; y<drawEnd; y++) {
		    	int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2;
		    	int color; //color of pixel to be received
		    	if(texX + (texY * wallTextures.get(texNum).SIZE)>wallTextures.get(texNum).pixels.length-1 || texX + (texY * wallTextures.get(texNum).SIZE)<0)
	        		  break;
		    	if(side==0) color = wallTextures.get(texNum).pixels[texX + (texY * wallTextures.get(texNum).SIZE)];//retrieves the pixel at a specified point.
		    	else color = (wallTextures.get(texNum).pixels[texX + (texY * wallTextures.get(texNum).SIZE)]>>1) & 8355711;//Make y sides darker
		    	pixels[x + y*(width)] = color;
		    }
		    
		    //ZBuffer is primed for sprites
		    ZBuffer[x] = perpWallDist;
		}
	    //Calculates the distance that the sprites are from the player. Used for the Z-buffer
	    //and is saved and used later on in the sprite class.
	    for(int i=0;i<numOfSprites; i++){
	    	spriteArray[i].distance = Math.sqrt(((player.xPos-spriteArray[i].x)*(player.xPos-spriteArray[i].x)+(player.yPos-spriteArray[i].y)*(player.yPos-spriteArray[i].y)));
	    }
	    //Sprite-casting is done below
	    sortSprites(spriteArray); //Sorting of the sprites is done
	    for(int i=0; i<numOfSprites; i++){ //loops through each sprite
	    	//sprite's position in relation to player
		    double spriteX = spriteArray[i].x-player.xPos;
		    double spriteY = spriteArray[i].y-player.yPos;
		    	
		   	double invDet = 1.0/(player.xPlane*player.yDir-player.xDir*player.yPlane);
		    	
		    double transformX= invDet*(player.yDir*spriteX-player.xDir*spriteY);
		    double transformY= invDet*(-player.yPlane*spriteX+player.xPlane*spriteY);
		    
		    int spriteScreenX= (int)((width/2)*(1+transformX/transformY));
		    	
		    //calculate height of the sprite on screen
		    int spriteHeight = Math.abs((int)(height / (transformY))); //using "transformY" instead of the real distance prevents fisheye
		    //calculate lowest and highest pixel to fill in current stripe
		    int drawStartY = -spriteHeight / 2 + height / 2;
		    if(drawStartY < 0) drawStartY = 0;
		    int drawEndY = spriteHeight / 2 + height / 2;
		    if(drawEndY >= height) drawEndY = height - 1;
		    	
		    int spriteWidth = Math.abs((int) (height / (transformY)));
	        int drawStartX = -spriteWidth / 2 + spriteScreenX;
	        if(drawStartX < 0) drawStartX = 0;
	        int drawEndX = spriteWidth / 2 + spriteScreenX;
	        //Adds this info for later use in the sprite's data
	        RaycastEngine.currentLevel.level.spriteArray[i].centerX= (drawStartX+drawEndX)/2;
	        if(drawEndX >= width) drawEndX = width - 1;
	        
	        //Draws each vertical slice of the sprite on screen
	    	for(int stripe = drawStartX; stripe < drawEndX; stripe++)
	        {
	          int TexX = (int)(256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * (spriteArray[i].currentTexture).SIZE / spriteWidth) / 256;
	          if(TexX>(spriteArray[i].currentTexture).SIZE)
	        	  TexX=(spriteArray[i].currentTexture).SIZE;
	          
	          //Detects if the sprites are visible via checking if they are being
	          //drawn on the screen.
	          if(stripe > 0 && stripe < width && transformY < ZBuffer[stripe]){
	        	  RaycastEngine.currentLevel.level.spriteArray[i].seen = true;
	          }else{
	        	  RaycastEngine.currentLevel.level.spriteArray[i].seen = false;
	          }
	          //the conditions in the if are:
	          //1) it's in front of camera plane so you don't see things behind you
	          //2) it's on the screen (left)
	          //3) it's on the screen (right)
	          //4) ZBuffer, with perpendicular distance
	          if(transformY > 0 && stripe > 0 && stripe < width && transformY < ZBuffer[stripe]){
	        	  RaycastEngine.currentLevel.level.spriteArray[i].seen = true;
		          for(int y = drawStartY; y < drawEndY; y++) //for every pixel of the current stripe
		          	{
		        	  int d = (y) * 256 - height * 128 + spriteHeight * 128; //256 and 128 factors to avoid floats
		        	  int texY = ((d * (spriteArray[i].currentTexture).SIZE) / spriteHeight) / 256;
		        	  if(TexX + (texY * (spriteArray[i].currentTexture).SIZE)>spriteArray[i].currentTexture.pixels.length-1 || TexX + (texY * (spriteArray[i].currentTexture).SIZE)<0)
		        		  break;
		        	  int color = (spriteArray[i].currentTexture).pixels[TexX + (texY * (spriteArray[i].currentTexture).SIZE)];	  	        	  
		        	  if((color & 0x00FFFFFF) != 0)//Removes black colors to create the illusion of transparency
		        		  pixels[stripe + y*(width)] = color;
		          	}
		          }
	    	}
	    }
		return pixels; //returns the updated pixels
	}
	
	/**a simple selection sort algorithm that is meant to sort the sprites
	 * via distance. This is used so that the sprites are drawn in the
	 * correct order.
	 * 
	 * @param spriteArray: array to be sorted
	 */
	private void sortSprites(Sprite[] spriteArray){
		for(int i=0; i<numOfSprites-1; i++){
			int index = i;
			for(int j=i+1; j<numOfSprites; j++)
				if(spriteArray[j].distance>spriteArray[index].distance)
					index=j;
			Sprite closerSprite = spriteArray[index];
			spriteArray[index]= spriteArray[i];
			spriteArray[i]=closerSprite;
		}
	}
}
