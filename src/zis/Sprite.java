package zis;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Simplest drawable entity
 * 
 * @author Philippe Babin
 *
 */
public class Sprite {
	/** Animation of the sprite  */
	protected Animation aniSprite;
	
	/** Position of the sprite  */
	protected Vector2i p;
	
	/** Scale of the sprite  */
	protected float scale;

	/** Dimension of the sprite  */
	protected int h, w;
	
	/**
	 * Constructor of a simple sprite
	 * @param pSprite
	 */
	public Sprite(Animation pSprite){
		aniSprite=pSprite;
		h = aniSprite.getHeight();
		w = aniSprite.getWidth();
		
		p = new Vector2i(0,0);
	}
	
	/**
	 * Constructor of a simple Sprite
	 * @param pSprite , nX, nY
	 */
	public Sprite(Animation pSprite, int nX, int nY){
		aniSprite=pSprite;
		h = aniSprite.getHeight();
		w = aniSprite.getWidth();
		
		p= new Vector2i(nX,nY);
	}
	/**
	 * Simple Sprite render function
	 * @param gc GameContainer
	 * @param sb StateBasedGame
	 * @param gr Graphics
	 */
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
		aniSprite.draw( p.x * 10 - cam.x, p.y * 10 - cam.y);
	}
	
	/**
	 * Simple Sprite update function
	 * @param gc GameContainer
	 * @param sb StateBasedGame
	 * @param delta Time between frame
	 */
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
    	
    }

    /**
     * Move simple Sprite to new position
     * @param pX int
     * @param pY int
     */
	public void move(int pX, int pY){
		p = new Vector2i( p.x+pX,  p.y+pY);
	}
	
    /**
     * Move simple Sprite to new position
     * @param pM Vector2i 
     */
	public void move(Vector2i pM){
		p = new Vector2i( p.x+pM.x,  p.y+pM.y);
	}
	
    /**
     * Move simple Sprite in target direction
     * @param d int 
     */
	public void move(int d){
		if(d == CONST.NORTH) move( 0, -1);
		if(d == CONST.EAST) move( 1, 0);
		if(d == CONST.SOUTH) move( 0, 1);
		if(d == CONST.WEST) move( -1, 0);
	}
	
	/**
	 * Return Sprite position
	 * @return position Vector2i
	 */
	public Vector2i getPosition() {
		return p;
	}

	/**
	 * Return Sprite scale
	 * @return scale float
	 */
    public float getScale(){
    	return scale;
    }

	/**
	 * Return Sprite x position
	 * @return X position  float
	 */
	public void setX(int x) {
		p.x = x;
	}

	/**
	 * Return Sprite y position
	 * @return Y position  float
	 */
	public void setY(int y) {
		p.y = y;
	}
	
	/**
	 * Return Sprite's animation
	 * @return Sprite's Animation
	 */
	public Animation getAnimation() {
		return aniSprite;
	}
	
	/**
	 * Set Sprite scale
	 * @param nScale new Scale float
	 */
	public void setScale(float nScale){
    	scale = nScale;
    }
	
}
