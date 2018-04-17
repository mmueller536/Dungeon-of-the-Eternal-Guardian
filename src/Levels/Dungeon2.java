package Levels;
import Sprites.Bob;
import Sprites.Chest;
import Sprites.Skeleton;
import Sprites.Spider;

public class Dungeon2 extends Level {
	public Dungeon2(){
		int[][] tempMap = {
				{1,3,1,1,1,1,1},
				{1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1},
				{1,0,0,1,0,0,1},
				{1,0,0,1,0,0,1},
				{1,0,0,1,0,0,1},
				{1,1,1,1,1,3,1},
			};
		map = tempMap;
		numOfSprites = 4;
		spriteArray[0] = new Bob(4, 2);
		spriteArray[1] = new Spider(2, 5);
		spriteArray[2] = new Skeleton(6, 2.5);
		spriteArray[3] = new Chest(6.5, 2.5);
		
		startNextX = 2.5;
		startNextY = 2.5;
		startPrevX = 6.4;
		startPrevY = 6;
		endNextX = 7;
		endNextY = 6;
		endPrevX = 1;
		endPrevY = 1;
	}
	public int[][] getMap(){
		return map;
	}
}
