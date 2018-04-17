package Sprites;
import Game.Player;
import Game.RaycastEngine;
import Textures.TextureAnimation;

public class Chest extends Sprite{
	TextureAnimation idleAnimation;
	TextureAnimation openingAnimation;
	public Chest(double x, double y) {
		super(x, y);
	}
	protected void initializeSprite(){
		name = "chest";
		hasGivenLoot = false;    
		attackRange = 0;
		damage=0;
		health = 10;
		topBehavior=null;
		distance=0;
		speed = 60;
		push("idle");
		if(Math.random()*3 < 2){
			loot = RaycastEngine.root.getChild(1).getChild((int)Math.floor(Math.random()*3)).getRootData();
		}else{
			loot = RaycastEngine.root.getChild(2).getChild((int)Math.floor(Math.random()*5)).getRootData();
		}
		loadAnimations();
	}
	protected void loadAnimations(){
		idleAnimation = new TextureAnimation("res/Chest/chest.png", 1, 5);
		openingAnimation = new TextureAnimation("res/Chest/openChest.png", 18, 10, false);
	}
	
	public void updateBehavior(Player player) {
		if(!hasGivenLoot){
			switch(peek()){
			case "idle":
				idle();
				break;
			case "opening":
				opening();
				break;
			}
		}
		animation();
	}
	protected void idle(){
		currentAnimation = idleAnimation;
	}
	public void damaged(int damageTaken) {
		if(!hasGivenLoot && currentAnimation != openingAnimation)
			push("opening");
	}
	protected void opening(){
		currentAnimation = openingAnimation;
		if(!hasGivenLoot){
			RaycastEngine.player.addLoot(getLoot());
			hasGivenLoot = true;
			RaycastEngine.playSound("sound/Hit.wav");
		}
	}
}