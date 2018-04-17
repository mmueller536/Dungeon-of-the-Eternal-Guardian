package Levels;
import Sprites.Bob;
import Sprites.Chest;
import Sprites.Skeleton;
import Sprites.Spider;

public class Dungeon3 extends Level {
	public Dungeon3(){
		int[][] tempMap = {
				{4,4,5,4,4,5,4,4,4,4,4,4,4,4,5,4,4,5,4,4,4,4,4,5,4,4},
				{6,0,0,0,0,0,0,0,4,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,4},
				{4,4,5,4,4,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,5,0,0,0,0,5},
				{4,0,4,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{4,0,4,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{5,0,5,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,5,0,0,0,0,5},
				{4,0,4,0,0,4,0,0,4,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,4},
				{4,0,4,0,0,4,0,0,4,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,4},
				{5,0,5,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,5,0,0,0,0,5},
				{4,0,0,0,0,4,0,0,4,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,4},
				{4,0,0,0,0,4,0,0,4,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,4},
				{5,0,5,0,0,5,0,0,5,4,4,5,4,4,5,0,0,5,4,4,5,4,4,5,4,4},
				{4,0,4,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{4,0,4,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{5,0,5,4,4,5,4,4,5,4,4,5,4,4,5,0,0,5,4,4,5,4,4,5,4,4},
				{4,0,0,0,0,0,0,0,4,0,0,4,0,0,0,0,0,4,0,0,0,0,0,0,0,4},
				{4,0,0,0,0,0,0,0,4,0,0,4,0,0,0,0,0,4,0,0,0,0,0,0,0,4},
				{4,4,5,4,4,5,0,0,5,0,0,5,0,0,5,4,4,5,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,4,0,0,4,0,0,0,0,0,4,0,0,0,0,0,0,0,4},
				{4,0,0,0,0,0,0,0,4,0,0,4,0,0,0,0,0,4,0,0,0,0,0,0,4,4},
				{4,0,0,0,0,0,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
				{4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,4,4},
				{4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,4},
				{5,0,0,0,0,0,0,0,5,0,0,5,4,4,5,4,4,5,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,4},
				{4,4,5,4,4,5,4,4,4,4,4,5,4,4,5,4,4,4,4,4,5,4,4,5,4,4}
		};
		map = tempMap;
		numOfSprites = 27;
		spriteArray[0] = new Spider(1.5,4);
		spriteArray[1] = new Skeleton(10,7.5);
		spriteArray[2] = new Skeleton(11,7);
		spriteArray[3] = new Spider(11,13);
		spriteArray[4] = new Spider(11,14);
		spriteArray[5] = new Spider(11,16);
		spriteArray[6] = new Skeleton(5,19);
		spriteArray[7] = new Skeleton(3,22);
		spriteArray[8] = new Skeleton(7,22);
		spriteArray[9] = new Skeleton(5,24);
		spriteArray[10] = new Skeleton(16,6);
		spriteArray[11] = new Skeleton(18,6);
		spriteArray[12] = new Skeleton(16,6);
		spriteArray[13] = new Skeleton(18,7);
		spriteArray[14] = new Bob(17,20);
		spriteArray[15] = new Bob(17,23);
		spriteArray[16] = new Bob(23,6);
		spriteArray[17] = new Bob(23,9);
		spriteArray[18] = new Bob(17,22);
		spriteArray[19] = new Skeleton(19,16);
		spriteArray[20] = new Skeleton(17,24);
		spriteArray[21] = new Skeleton(23,18);
		spriteArray[22] = new Skeleton(23,23);
		
		spriteArray[23] = new Chest(10,10);
		spriteArray[24] = new Chest(10,24);
		spriteArray[25] = new Chest(24.5,16);
		spriteArray[26] = new Chest(24,24);
		
		startNextX = 1;
		startNextY = 1.5;
		startPrevX = 20;
		startPrevY = 25;
		endNextX = 21;
		endNextY = 25;
		endPrevX = 1;
		endPrevY = 1;
	}
	public int[][] getMap(){
		return map;
	}
}
