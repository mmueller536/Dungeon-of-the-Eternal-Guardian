package Sprites;
import Game.RaycastEngine;
import Textures.TextureAnimation;

public class Monsters extends Sprite {
	public Monsters(double x, double y) {
		super(x, y);
	}
	/**Initialize these monsters and randomly pick what type of
	 * special monster they will spawn as, and the attributes
	 * that come with it.
	 * 
	 */
	protected void initializeSprite(){
		switch((int)Math.round(Math.random()*4)+1){
		case 1:
			name = "Monster Glass Cannon";
			hasGivenLoot = false;
			health = 10;
			attackRange = 0.75;
			damage=75;
			topBehavior=null;
			distance=0;
			speed = 60;
			push("patrol");
			loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
			loadAnimations();
			break;
		case 2:
			name = "Monster Tank";
			hasGivenLoot = false;
			health = 500;
			attackRange = 0.5;
			damage=5;
			topBehavior=null;
			distance=0;
			speed = 75;
			push("patrol");
			loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
			loadAnimations();
			break;
		case 3:
			name = "Monster Speedy";
			hasGivenLoot = false;
			health = 60;
			attackRange = 0.25;
			damage=10;
			topBehavior=null;
			distance=0;
			speed = 45;
			push("patrol");
			loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
			loadAnimations();
			break;
		case 4:
			name = "Monster Slower";
			hasGivenLoot = false;
			health = 70;
			attackRange = 0.75;
			damage=5;
			topBehavior=null;
			distance=0;
			speed = 60;
			push("patrol");
			loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
			loadAnimations();
			break;
		case 5:
			name = "Monster Poision";
			hasGivenLoot = false;
			health = 50;
			attackRange = 0.5;
			damage=2;
			topBehavior=null;
			distance=0;
			speed = 60;
			push("patrol");
			loot = RaycastEngine.root.getChild(0).getChild((int)Math.floor(Math.random()*3)).getRootData();
			loadAnimations();
			break;
		}
	}
	protected void loadAnimations(){
		switch(name){
		case "Monster Glass Cannon":
			walkingAnimation = new TextureAnimation("res/Monsters/Monster1.png", 1);
			attackAnimation = new TextureAnimation("res/Monsters/Monster1.png", 1, 60, false);
			break;
		case "Monster Tank":
			walkingAnimation = new TextureAnimation("res/Monsters/Monster2.png", 1);
			attackAnimation = new TextureAnimation("res/Monsters/Monster2.png", 1, 45, false);
			break;
		case "Monster Speedy":
			walkingAnimation = new TextureAnimation("res/Monsters/Monster3.png", 1);
			attackAnimation = new TextureAnimation("res/Monsters/Monster3.png", 1, 30, false);
			break;
		case "Monster Slower":
			walkingAnimation = new TextureAnimation("res/Monsters/Monster4.png", 1);
			attackAnimation = new TextureAnimation("res/Monsters/Monster4.png", 1, 70, false);
			break;
		case "Monster Poision":
			walkingAnimation = new TextureAnimation("res/Monsters/Monster5.png", 1);
			attackAnimation = new TextureAnimation("res/Monsters/Monster5.png", 1, 60, false);
			break;
		}
		dyingAnimation = new TextureAnimation("res/Monsters/deadMonster.png", 1);
	}
	public void attack() {
		currentAnimation = attackAnimation;
		if(currentAnimation.animationFinished()){
			RaycastEngine.player.damaged(damage);
			if(name == "Monster Slower"){
				RaycastEngine.player.slow();
			}
			if(name == "Monster Poision"){
				RaycastEngine.player.poision();
			}
			RaycastEngine.playSound("sound/Hit2.wav");
		}
		dx=0;
		dy=0;
		if(distance>attackRange)
			pop();
	}
}