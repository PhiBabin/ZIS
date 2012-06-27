package zis;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class RessourceManager {
	public Animation player = null;
	public SpriteSheet imgPlayer, tilesetImg = null;
	
	public RessourceManager(){
		loadImage();
	}
	
	public void loadImage(){
		try {
			if( CONST.APPLET){
				imgPlayer = new SpriteSheet( new Image( Thread.currentThread().getContextClassLoader().getResourceAsStream("img/player.png"), "img/player.png", false), 10, 10);
				tilesetImg = new SpriteSheet( new Image( Thread.currentThread().getContextClassLoader().getResourceAsStream("img/buildingTileSet.png"), "img/buildingTileSet.png", false), 10, 10);
			}
			else {
				imgPlayer = new SpriteSheet( new Image("img/player.png", false), 10, 10);
				tilesetImg = new SpriteSheet( new Image("img/buildingTileSet.png", false), CONST.TILE_WIDTH, CONST.TILE_HEIGHT);
			}
			
			player = new Animation( imgPlayer, 100000);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
