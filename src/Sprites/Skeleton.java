package Sprites;
import Game.RaycastEngine;
import Textures.TextureAnimation;

public class Skeleton extends Sprite {

	public Skeleton(double x, double y) {
		super(x, y);
	}
	protected void initializeSprite(){
		name = "Skeleton";
		hasGivenLoot = false;
		health = 100;
		attackRange = 1;
		damage=5;
		topBehavior=null;
		distance=0;
		speed = 120;
		push("patrol");
		loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
		loadAnimations();
	}
	protected void loadAnimations(){
		walkingAnimation = new TextureAnimation("res/Skeleton/walkingSkeleton.png",24);
		attackAnimation = new TextureAnimation("res/Skeleton/attackingSkeleton.png", 1, 30);//example attacking animation
		dyingAnimation = new TextureAnimation("res/Skeleton/deadSkeleton.png",1, 5, false);//example death animation
	}
}