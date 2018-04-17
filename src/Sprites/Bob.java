package Sprites;
import Game.RaycastEngine;
import Textures.TextureAnimation;

public class Bob extends Sprite {
	public Bob(double x, double y) {
		super(x, y);
	}
	protected void initializeSprite(){
		name = "bob";
		hasGivenLoot = false;
		attackRange = 1;
		health = 50;
		damage=5;
		topBehavior=null;
		distance=0;
		speed = 60;
		push("patrol");
		loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
		loadAnimations();
	}
	protected void loadAnimations(){
		walkingAnimation = new TextureAnimation("res/Bob/walkingBob.png",24, 2);
		attackAnimation = new TextureAnimation("res/Bob/attackingBob.png",5, 10);//example attacking animation
		dyingAnimation = new TextureAnimation("res/Bob/deadBob.png",1, 5,false);//example death animation
	}
}
