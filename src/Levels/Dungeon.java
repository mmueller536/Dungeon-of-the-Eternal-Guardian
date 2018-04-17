package Levels;
import Sprites.Bob;
import Sprites.Chest;
import Sprites.Skeleton;
import Sprites.Spider;

public class Dungeon extends Level {
	public Dungeon(){
		int[][] tempMap = {
				{4,6,4,4,4,4,4,4,4,4,4},
				{4,0,0,4,0,0,0,0,0,0,4},
				{4,0,0,0,0,4,0,0,0,0,4},
				{4,0,0,4,0,4,4,4,0,4,4},
				{4,0,0,4,0,4,0,0,0,0,4},
				{4,4,4,4,0,4,0,0,0,0,4},
				{4,0,0,0,0,4,0,0,0,0,4},
				{4,4,4,4,4,4,4,4,4,6,4},
			};
		map = tempMap;
		numOfSprites = 4;
		spriteArray[0] = new Spider(4, 2);
		spriteArray[1] = new Skeleton(1.5, 5);
		spriteArray[2] = new Bob(6, 3);
		spriteArray[3] = new Chest(6.4,1.5);
		
		startNextX = 2;
		startNextY = 2;
		startPrevX = 6.4;
		startPrevY = 10;
		endNextX = 7;
		endNextY = 10;
		endPrevX = 1;
		endPrevY = 1;
	}
	public int[][] getMap(){
		return map;
	}
}
