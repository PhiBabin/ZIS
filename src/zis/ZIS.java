package zis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class ZIS  extends StateBasedGame {
	
	/** Screen size */
	private static int SCREEN_WIDTH = 800;

	private static int SCREEN_HEIGHT = 600;
	
	/** Name of the game */
	private static String GAME_NAME = "Zombie Infection Simulator";
	
	/** Version of the game */
	private static String GAME_VERSION = "v0.01";
	
    public static final int PLAYSTATE = 1;
	
	
	public ZIS(){
		super( "ZIS"); 
		
        this.addState( new PlayState(PLAYSTATE));
        this.enterState( PLAYSTATE);
	}
	
    public void initStatesList( GameContainer gameContainer) throws SlickException {
    	
        //this.getState(PLAYSTATE).init(gameContainer, this);
    }
	 
	public static void main( String[] args) {
		try {
			
			CONST.APPLET = false;
			
			AppGameContainer app = new AppGameContainer( new ZIS());
			app.setDisplayMode( SCREEN_WIDTH, SCREEN_HEIGHT, false); 
			app.setVSync(true); 
			app.setShowFPS(true);
			app.setTitle( GAME_NAME + "" + GAME_VERSION);
			
			app.start();
		} catch ( SlickException e) {
			e.printStackTrace(); 
		}
	}
	
}
