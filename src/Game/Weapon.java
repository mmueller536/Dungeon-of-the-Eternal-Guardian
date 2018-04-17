package Game;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Textures.ImageAnimation;


/**
 * Creates a weapon that the player can use
 */
public class Weapon {
	boolean hasWeapon;
	private BufferedImage currentWeaponFrame;
	private ImageAnimation attackAnimation;
	private int leftReticle;
	private int rightReticle;
	private int damage;
	private double weaponRange;
	/**
	 * Creates a Weapon object and initializes their animations, 
	 * reticles, damage, and their range.
	 * 
	 * @param weaponImage	The image of the weapon
	 * @param frameNum 	Number of frames for the attack animation
	 * @param left	the left most point on the screen the weapon can hit
	 * @param right	the right most point on the screen the weapon can hit
	 * @param attack	the amount of damage it does
	 * @param range		the reach of the weapon
	 * @throws IOException 
	 */
	public Weapon(String imageFile, int frameNum ,int left, int right, int attack, double range) throws IOException{
		hasWeapon = true;
		this.attackAnimation = new ImageAnimation(imageFile, frameNum, 5, true);
		this.leftReticle = left;
		this.rightReticle = right;
		damage = attack;
		weaponRange = range;
	}
	
	public void resetAnimation(){
		attackAnimation.resetAnimation();
	}
	//Getters
	public int getRightRect(){
		return rightReticle;
	}
	
	public int getLeftRect(){
		return leftReticle;
	}
	
	public double getWeaponRange(){
		return weaponRange;
	}
	
	public int getDamage(){
		return damage;
	}
	public BufferedImage animation(){
		currentWeaponFrame = attackAnimation.Animate();
		return currentWeaponFrame;
	}
	
	public BufferedImage getImage(){
		return currentWeaponFrame;
	}
	
	public boolean animationFinished(){
		return attackAnimation.animationFinished();
	}
	
	//Setters
	public void setAnimation(String fileName) throws IOException{
		ImageAnimation temp = new ImageAnimation(fileName, 1);
		this.attackAnimation = temp;
	}
	public void setRightRect(int right){
		rightReticle=right;
	}
	
	public void setLeftRect(int left){
		rightReticle=left;
	}
	
	public void setWeaponRange(double range){
		weaponRange = range;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
}
