package zis;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Graphics;

public class MiniMap {
	
	
	private Image miniMap = null;
	
	private Graphics mapGraphics;
	
	public MiniMap(){
		
	}
	
	public void updateMiniMap( WorldMap bigMap) throws SlickException{
		
		miniMap = new Image( 2 * CONST.MAP_WIDTH, 2 * CONST.MAP_HEIGHT);

		mapGraphics = miniMap.getGraphics();
		mapGraphics.setColor( new Color( 49, 145, 19));
    	for(int y = 0; y < CONST.MAP_HEIGHT; y++){
    		for(int x = 0; x < CONST.MAP_WIDTH; x++){
	    		if( bigMap.isSolid( x, y)){
	    			mapGraphics.drawRect( 2 * x, 2 * y, 1f, 1f);
	    		}
	    	}
		}
		mapGraphics.flush();
	}
	
	public Vector2f onClick( Vector2f cursor, Vector2f cam){
		return null;
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f p, Vector2f cam){

		Rectangle background =  new Rectangle ( p.x, p.y, CONST.SCREEN_WIDTH / 5 + 50, CONST.SCREEN_HEIGHT / 5 + 50);
		Rectangle camRect =  new Rectangle ( p.x + 25 , p.y + 25, CONST.SCREEN_WIDTH / 5, CONST.SCREEN_HEIGHT / 5);
		
		gr.setLineWidth(3);
		gr.draw( background);
		gr.setLineWidth(1);

		gr.setColor( new Color( 4, 23, 3, 200));
		gr.fill( background);

		gr.fill( camRect);
		gr.drawImage( miniMap.getSubImage( (int)cam.x / 5 - 25, (int)cam.y / 5 -25,
				CONST.SCREEN_WIDTH / 5 + 48,
				CONST.SCREEN_HEIGHT / 5 + 48),
				p.x, p.y);

		gr.setColor( Color.white);
		gr.draw( camRect);
	}
	
}
