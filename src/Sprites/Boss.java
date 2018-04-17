package Sprites;
import java.util.ArrayList;

import Game.Player;
import Game.RaycastEngine;
import Textures.TextureAnimation;

/**
 * The boss of the game
 *
 */
public class Boss extends Sprite {
	//New Animations
	TextureAnimation mechaConvertAnimation;
	TextureAnimation mechWalking;
	TextureAnimation mechAttaking;
	//Boolean that checks transformation
	boolean transform;
	/**
	 * spawn the boss at coordinate x and y
	 * @param x	location x to be spawned at
	 * @param y location y to be spawned at
	 */
	public Boss(double x, double y) {
		super(x, y);
		transform = false;
	}
	
	/**
	 * Initialize the boss
	 */
	protected void initializeSprite(){
		name = "Boss";
		hasGivenLoot = false;
		attackRange = 0.5;
		health = 121;
		damage=1;
		topBehavior=null;
		distance=0;
		speed = 60;
		push("patrol");
		loot = RaycastEngine.root.getChild((int)Math.random()*2).getChild((int)Math.random()*2).getRootData();
		loadAnimations();
	}
	protected void loadAnimations(){
		attackAnimation = new TextureAnimation("res/Mechama/walkingTes.png", 1, 50);
		dyingAnimation = new TextureAnimation("res/Mechama/dyingMechama.png", 14, 5, false);
		mechaConvertAnimation = new TextureAnimation("res/Mechama/conversionMechama.png", 156, 5, false);
		walkingAnimation = new TextureAnimation("res/Mechama/walkingTes.png", 16);
		
		mechWalking = new TextureAnimation("res/Mechama/walkingMechama.png", 8);
		mechAttaking = new TextureAnimation("res/Mechama/attackingMechama.png", 1, 50, true);
	}
	/**
	 * Updates the behavior of the AI using a stack.
	 * Also handles what happens when a sprite dies
	 * 
	 * @param map takes the current map as a variable
	 * @param player takes the current player as a variable
	 */
	@Override
	public void updateBehavior(Player player) {
		if(alive()){
			switch(peek()){
			case "patrol":
				patrol();
				break;
			case "persue":
				persue();
				break;
			case "attack":
				attack();
				break;
			case "persuePath":
				persuePath();
				break;
			case "transform":
				transform();
				break;
			}
		} else {//When sprite dies
			dx=0;
			dy=0;
			currentAnimation = dyingAnimation;
			if(!hasGivenLoot){
				RaycastEngine.player.addLoot(getLoot());
				hasGivenLoot = true;
				RaycastEngine.playSound("sound/Hit.wav");
			}
		}
		animation();
	}
	private void transform() {
		currentAnimation = mechaConvertAnimation;
		dx=0;
		dy=0;
		health = 1000;
		damage=20;
		walkingAnimation = mechWalking;
		attackAnimation = mechAttaking;
		if(currentAnimation.animationFinished()){
			pop();
		}
	}

	/**
	 * Overwrites the previous sound of attack
	 */
	@Override
	public void attack() {
		currentAnimation = attackAnimation;
		if(currentAnimation.animationFinished()){
			RaycastEngine.player.damaged(damage);
			RaycastEngine.playSound("sound/MechLaser.wav");
		}
		dx=0;
		dy=0;
		if(distance>attackRange)
			pop();
	}
	
	public void damaged(int damageTaken) {
		if(!transform){
			System.out.println("Here");
			health = 1000;
			push("transform");
			transform = true;
		}else{
			health-=damageTaken;
		}
	}
	
	/**
	 * Tests to see if the sprite is still alive.
	 * 
	 * @return returns true if alive, false if dead
	 */
	public boolean alive() {
		boolean alive = true;
		if(health<=0){
			alive = false;
			RaycastEngine.winCon = true;
		}
		return alive;
	}
}
