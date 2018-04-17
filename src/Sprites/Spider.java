package Sprites;
import Game.RaycastEngine;
import Textures.TextureAnimation;

public class Spider extends Sprite {

	public Spider(double x, double y) {
		super(x, y);
	}
	protected void initializeSprite(){
		name = "spider";
		hasGivenLoot = false;
		attackRange = .75;
		health = 50;
		damage=2;
		topBehavior=null;
		distance=0;
		speed = 75;
		push("patrol");
		loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
		loadAnimations();
	}
	protected void loadAnimations(){
		walkingAnimation = new TextureAnimation("res/Spider/walkingSpider.png",15);
		attackAnimation = new TextureAnimation("res/Spider/attackingSpider.png", 1, 30);//example attacking animation
		dyingAnimation = new TextureAnimation("res/Spider/deadSpider.png",1, 5,false);//example death animation
	}
}
