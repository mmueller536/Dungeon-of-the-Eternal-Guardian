package Textures;
import java.util.ArrayList;
/**
 * Object that stores an image with a given filname
 * and creates a "flipbook" effect via a creation of
 * frames from the original image. This is designed
 * to be done at a rate of 60 frames per second outside
 * of this Object.
 * 
 * @author Michael Mueller, Paul Coen, Dakota Oria
 *
 */
public class TextureAnimation {
	//Default and custom delays between frames
	final static int FRAME_DELAY = 5;
	int frameDelay;
	int timer;
	
	//current frame of animation and animation frames
	int currentFrame;
	ArrayList<Texture> animationFrames;
	
	//Name of animation spreadsheet
	String filename;
	
	//Whether or not to loop the animation
	boolean looping;
	
	/**
	 * Creates an animation with a default frame delay, 
	 * a desired animation spreadsheet, and a certain
	 * amount of frames
	 * 
	 * @param s Name of file
	 * @param frameNum number of frames
	 */
	public TextureAnimation(String s, int frameNum){
		this(s, frameNum, FRAME_DELAY);
	}
	
	/**
	 * Creates an animation with a default frame delay, 
	 * a desired animation spreadsheet, a certain amount 
	 * of frames, and a specific frame delay.
	 * 
	 * @param s Name of file
	 * @param frameNum Number of frames
	 * @param frameDelay Amount of time between frames
	 */
	public TextureAnimation(String s, int frameNum, int frameDelay){
		this(s, frameNum, frameDelay, true);//assumes that the animation is loopable
	}
	
	/**
	 * Creates an animation with a default frame delay, 
	 * a desired animation spreadsheet, a certain amount 
	 * of frames, a specific frame delay, and whether or
	 * not the animation is loopable.
	 * 
	 * @param s Name of file
	 * @param frameNum Number of frames
	 * @param frameDelay Amount of time between frames
	 * @param loopable Specifies if animation is loopable
	 */
	public TextureAnimation(String s, int frameNum, int frameDelay, boolean loopable){
		this.frameDelay = frameDelay;
		looping = loopable;
		animationFrames = new ArrayList<>();
		currentFrame = 0;
		for(int i = 0; i < frameNum; i++){
			Texture temp = new Texture(s, 64, 0, (i*64));
			animationFrames.add(temp);
		}
		//System.out.println("Loaded Textures for: " + s);
	}
	
	/**
	 * Cycles the animation, returning the next image of
	 * the spreadsheet. Will reset the animation if
	 * desired.
	 * 
	 * @return Current frame of animation.
	 */
	public Texture Animate(){
		ensureCurrentFrame();
		Texture result = animationFrames.get(currentFrame);
		if(timer%frameDelay == 0){
			timer = 1;
			currentFrame++;
		}
		timer++;
		return result;
	}
	
	/**
	 * Ensures that the animation is not outside of
	 * its limit of frames.
	 */
	public void ensureCurrentFrame(){
		if(currentFrame>=animationFrames.size())
			currentFrame = looping? 0: animationFrames.size()-1;
	}
	
	/**
	 * Determines if an animation has completed its cycle at
	 * a given frame.
	 * 
	 * @return 
	 * True if the animation has just completed.
	 * False if the animation is in progress.
	 */
	public boolean animationFinished(){
		if(animationFrames.size()>1)
			return currentFrame == animationFrames.size();
		else
			return timer%frameDelay==0;
	}
}
