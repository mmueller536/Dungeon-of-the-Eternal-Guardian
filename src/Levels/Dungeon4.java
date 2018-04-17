package Levels;
import Sprites.Bob;
import Sprites.Chest;
import Sprites.Monsters;
import Sprites.Skeleton;
import Sprites.Spider;

public class Dungeon4 extends Level {
	public Dungeon4(){
		int[][] tempMap = {
				{4,6,4,4,5,4,4,4,4,4,5,4,4,4,4,4,4,4,4,4},
				{4,0,4,0,0,0,0,0,4,0,0,0,0,4,4,4,4,4,4,4},
				{4,0,4,0,0,0,4,0,4,0,0,0,0,4,4,5,4,4,4,4},
				{5,0,5,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,4,0,0,0,4,0,4,0,0,0,0,4,4,0,4,4,0,4},
				{4,0,0,0,0,0,4,0,4,4,5,4,4,4,4,0,4,4,0,4},
				{4,5,4,4,4,0,4,0,4,4,4,4,4,4,4,0,4,4,4,4},
				{4,4,4,4,4,0,4,0,4,4,4,4,4,4,4,0,4,4,4,4},
				{4,4,4,4,4,0,4,0,4,4,4,4,4,0,0,0,0,0,4,4},
				{4,4,4,5,0,0,4,0,4,4,4,4,4,0,0,0,0,0,4,4},
				{4,4,4,5,4,0,4,0,4,4,4,5,4,4,4,5,4,4,4,4},
				{5,0,0,0,0,0,4,0,4,0,0,0,0,0,0,0,0,0,0,4},
				{4,4,4,5,4,5,4,0,4,0,0,0,5,0,0,0,0,5,0,4},
				{4,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{4,0,5,0,0,0,4,0,4,0,0,0,0,0,5,0,0,0,0,4},
				{4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,4},
				{4,0,0,5,0,0,4,0,4,4,4,5,4,4,4,5,4,4,4,4},
				{4,0,0,0,0,0,4,0,4,4,4,4,4,4,4,4,4,4,4,4},
				{4,4,4,4,4,5,4,0,4,4,4,4,4,4,4,4,4,4,4,4},
				{4,4,4,4,4,4,4,0,0,6,4,4,4,4,4,4,4,4,4,4},
				{4,4,4,4,4,4,4,5,4,4,4,4,4,4,4,4,4,4,4,4}	
		};
		map = tempMap;
		numOfSprites = 21;
		spriteArray[0] = new Spider(1.5,4);
		spriteArray[1] = new Skeleton(3.5,3.5);
		spriteArray[2] = new Bob(9.4,4.6);
		spriteArray[3] = new Chest(12.3,18.7);
		spriteArray[4] = new Monsters(14.8,17.7);
		spriteArray[5] = new Monsters(15.5,9.3);
		spriteArray[6] = new Monsters(11.9,14);
		spriteArray[7] = new Bob(14.1,1.4);
		spriteArray[8] = new Bob(17.5,2);
		spriteArray[9] = new Bob(13.6,5);
		spriteArray[10] = new Bob(17.6,5);
		spriteArray[11] = new Bob(1.5,9.3);
		spriteArray[12] = new Bob(1.6,9.3);
		spriteArray[13] = new Bob(1.7,9.3);
		spriteArray[14] = new Skeleton(2,12.5);
		spriteArray[15] = new Skeleton(9.8,13.2);
		spriteArray[16] = new Skeleton(8.4,17.4);
		spriteArray[17] = new Chest(8.6,13.8);
		spriteArray[18] = new Chest(8.6,17.6);
		spriteArray[19] = new Chest(9.6,15.3);
		spriteArray[20] = new Chest(5.7,18.6);
		
		
		startNextX = 1.5;
		startNextY = 1.5;
		startPrevX = 20;
		startPrevY = 7.4;
		endNextX = 20;
		endNextY = 8;
		endPrevX = 1;
		endPrevY = 1;
	}
	public int[][] getMap(){
		return map;
	}
}
