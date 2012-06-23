package zis;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import zis.util.Vector2i;

/**
 * Movable and scalable image
 * 
 * @author Philippe Babin
 *
 */
public class Sprite {
	/** Animation of the sprite  */
	protected final Animation aniSprite;
	
	/** Position of the sprite  */
	protected Vector2i p;
	
	/** Scale of the sprite  */
	protected float scale;

	/** Dimension of the sprite  */
	protected int h, w;
	
	/**
	 * Constructor of the Sprite
	 * @param pSprite Image of the sprite
	 */
	public Sprite(Animation pSprite){
		aniSprite = pSprite;
		w = aniSprite.getWidth();
		h = aniSprite.getHeight();
		
		p = new Vector2i( 0, 0);
	}
	
	/**
	 * Constructor of the Sprite
	 * @param pSprite Image of the sprite
	 * @param nX Position X of the sprite
	 * @param nY Position Y of the sprite
	 */
	public Sprite(Animation pSprite, int nX, int nY){
		aniSprite=pSprite;
		h = aniSprite.getHeight();
		w = aniSprite.getWidth();
		
		p = new Vector2i(nX,nY);
	}
	/**
	 * Sprite render function
	 * @param gc Game Container
	 * @param sb State Based Game
	 * @param gr Graphics
	 */
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
		aniSprite.draw( p.x * 10 - cam.x, p.y * 10 - cam.y);
	}
	
	/**
	 * Sprite update function
	 * @param gc Game Container
	 * @param sb State Based Game
	 * @param delta Time between frame
	 */
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
    	
    }

    /**
     * Move the Sprite to a new position
     * @param pX 
     * @param pY 
     */
	public void move( int pX, int pY){
		p = new Vector2i( p.x + pX,  p.y + pY);
	}
	
    /**
     * Move simple Sprite to new position
     * @param pM Translation vector
     */
	public void move( Vector2i pM){
		p = new Vector2i( p.x + pM.x,  p.y + pM.y);
	}
	
    /**
     * Move simple Sprite in target direction
     * @param d Movement direction 
     */
	public void move( int d){
		if( d == CONST.NORTH) move( 0, -1);
		if( d == CONST.EAST) move( 1, 0);
		if( d == CONST.SOUTH) move( 0, 1);
		if( d == CONST.WEST) move( -1, 0);
	}
	
	/**
	 * Return Sprite position
	 * @return Position of the Sprite
	 */
	public Vector2i getPosition() {
		return p;
	}
	
	/**
	 * Return Sprite X position
	 * @return X Position of the Sprite
	 */
	public int getX(){
		return p.x;
	}
	
	/**
	 * Return Sprite Y position
	 * @return Y Position of the Sprite
	 */
	public int getY(){
		return p.y;
	}
	/**
	 * Return Sprite scale
	 * @return Sprite's scale
	 */
    public float getScale(){
    	return scale;
    }
    
	/**
	 * Return Sprite's animation
	 * @return Sprite's image
	 */
	public Animation getAnimation() {
		return aniSprite;
	}

	/**
	 * Return Sprite x position
	 * @return New position X
	 */
	public void setX(int x) {
		p.x = x;
	}

	/**
	 * Return Sprite y position
	 * @return New position Y
	 */
	public void setY(int y) {
		p.y = y;
	}
	
	/**
	 * Set Sprite scale
	 * @param nScale New Sprite's scale
	 */
	public void setScale(float nScale){
    	scale = nScale;
    }
	
}
