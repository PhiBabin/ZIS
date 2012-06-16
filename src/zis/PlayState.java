/**
 * 
 * @author Philippe Babin
 *
 *	This class works as the gameplay logic manager.
 *	The current drawable entity is render.
 */

package zis;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {
	
	private boolean debugSquare = false;
	
	private Vector2f pSquare = new Vector2f( 0, 0);
	private Vector2i pCursor = new Vector2i( 0, 0);
	
	private boolean debugMod = false;
	
	
	
//	private WorldMap cityMap;
	
	private int turn = 0;
	
	private int gameTime = 0;

	public float gameSpeed = 1.0f;

	private ArrayList<NPC> population = null; 
	
	private RessourceManager resMan;
	
	private MapGenerator mapGen;
	
	private Vector2f cam;
	
	
//	private enum STATES {
//        START_GAME_STATE, NEW_PIECE_STATE, MOVING_PIECE_STATE, LINE_DESTRUCTION_STATE,
//        PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE, GO_WORK, WORK, GO_EAT, EAT, GO_WORK2, WORK2, GO_BED 
//    }
	
	int stateID = -1;

	/**
	 * Construct our PlayState
	 */
	public PlayState(){
		
	}
	/**
	 * Construct our PlayState
	 * @param stateID
	 */
	public PlayState(int stateID){
		this.stateID = stateID;
	}
	
	public void newRandomMap() throws SlickException{
		mapGen.generateEmptyMap();
		
    	long generationTime = System.currentTimeMillis();
    	
    	mapGen.generateBuildingFloor( 2, 2, (int)(Math.random() * 900) + 5, (int)(Math.random() * 900) + 5);
    	mapGen.tileCorrection();
    	
    	System.out.println("Map generate in " + (int)(System.currentTimeMillis() - generationTime) + "ms.");
		
	}

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	
    	resMan = new RessourceManager();
        
    	mapGen = new MapGenerator( new WorldMap( resMan.tilesetImg));
    	newRandomMap(); 
    	
    	//mapGen.generateLabyrinth( 0, 0, 80, 60)

    	 population = new ArrayList<NPC>();
    	 population.clear();
    	 
    	 Vector2i pPop;
    	 int e=2;
     	 long generationTime;
    	 while(e < 4){
    		 pPop = new Vector2i(
    				(int)Math.floor( Math.random()*80),
    				(int)Math.floor( Math.random()*60));
    		 
    		 if( !mapGen.map.isSolid( pPop.x, pPop.y)){
    			 generationTime = System.currentTimeMillis();
        		// System.out.println( "I'm number "+ e + ". Who's number 1?");
    			 population.add(new NPC( resMan.player, pPop.x, pPop.y, mapGen.map));
    			 e++;
    		    	System.out.println("Habitant generate in " + (int)(System.currentTimeMillis() - generationTime) + "ms.");
    		 }
    	 }
    	 
    	 cam = new Vector2f( 0, 0);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
    	gr.setBackground( new Color( 13, 37, 47));
    	mapGen.map.render(gc, sbg, gr, cam);
		
		for( NPC popu : population){
			popu.render(gc, sbg, gr, cam);
		}

		gr.drawString( "- Time (" + gameTime + "ms)x" + Math.round( gameSpeed * 100) + "% " +
				"- Turn (" + turn + ")" +
				"- Population (" + population.size() + ")" +
				"- Symmetric (" + CONST.SYMMETRICROOM + ")", 80, 10);
		gr.drawString( "( " + Math.floor( ( cam.x) ) + "," +
				" " + Math.floor( ( cam.y) ) + ")",
				690, 580); 
		
		if( debugSquare){
			gr.drawRect( pSquare.x, pSquare.y, 10, 10);
			gr.drawString( "( " + Math.floor( ( pCursor.x) ) + "," +
							" " + Math.floor( ( pCursor.y) ) + ")",
							5, 580);
		}
		
		int g = 1;
		
		if( debugMod){
			for( Rectangle r : mapGen.buildRegion){
				if(g == 1)gr.setColor( Color.green);
				else if(g == 2)gr.setColor( Color.black);
				else if(g == 3)gr.setColor( Color.yellow);
				else if(g == 4)gr.setColor( Color.magenta);
				else gr.setColor( Color.orange);
				
				if(g < 4) gr.setLineWidth(g);
				else gr.setLineWidth(4);
				
				gr.drawRect( r.getX() * 10,
							r.getY() * 10,
							r.getWidth() * 10,
							r.getHeight() * 10);
				gr.drawString( "" + g,
						r.getX() * 10 + r.getWidth() * 5 - 5,
						r.getY() * 10 + r.getHeight() * 5 - 5);
				g++;
			}
		}
		
		gr.setColor( Color.white);
		gr.setLineWidth(1);
 
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	
    	/*** Move the camera */
    	if(input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT)){
    		if( input.isKeyDown(Input.KEY_LSHIFT)) 
    			cam.x += delta * 0.5;
    		else
    			cam.x += delta * 0.1;    		
    	}
    	if(!input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)){
    		if( input.isKeyDown(Input.KEY_LSHIFT)) 
    			cam.x -= delta * 0.5;
    		else
    			cam.x -= delta * 0.1;    		
    	}
    	if(input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP)){
    		if( input.isKeyDown(Input.KEY_LSHIFT)) 
    			cam.y += delta * 0.5;
    		else
    			cam.y += delta * 0.1;    		
    	}
    	if(!input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_UP)){
    		if( input.isKeyDown(Input.KEY_LSHIFT)) 
    			cam.y -= delta * 0.5;
    		else
    			cam.y -= delta * 0.1;    		
    	}
    	
    	/** Regenerate the current map */
    	if(input.isKeyPressed(Input.KEY_SPACE)){ 
        	newRandomMap();
    	}

    	/** Debug GUI */
    	if(!debugMod && input.isKeyPressed(Input.KEY_D))
    		debugMod = true;
    	else if(debugMod && input.isKeyPressed(Input.KEY_D))
    		debugMod = false;
    	
    	/** Symmetric Room */
    	if(!(CONST.SYMMETRICROOM) && input.isKeyPressed(Input.KEY_S))
    		CONST.SYMMETRICROOM = true;
    	else if(CONST.SYMMETRICROOM && input.isKeyPressed(Input.KEY_S))
    		CONST.SYMMETRICROOM = false;
    	
    	/*** Control the speed of the game */
    	if(input.isKeyDown(Input.KEY_MINUS) && gameSpeed>=0.1){
    		gameSpeed -= 0.1;
    	}
    	if(input.isKeyDown(Input.KEY_EQUALS) && gameSpeed<20.0){
    		gameSpeed += 0.1;
    	}
    	if(input.isKeyDown(Input.KEY_P)){
    		gameSpeed = 0;
    	}
    	
    	gameTime += delta * gameSpeed;

    	int elapseTurn = (int) (gameTime * 0.001) - turn;
    	
    	for(int i=1; i <= elapseTurn; i++){
    		System.out.println("==== TURN " + (turn+i) + " ====");
    		for(NPC popu : population){
    			popu.update(gc, sbg, delta);
    		}
    	}
    	turn += elapseTurn;
    	
    	pCursor.x = (int)Math.floor( ( input.getMouseX() + cam.x) * 0.1);
    	pCursor.y = (int)Math.floor( ( input.getMouseY() + cam.y) * 0.1);

    	if( mapGen.map.isSolid( (int)pCursor.x, (int)pCursor.y)){
    		debugSquare = true;
    		pSquare = new Vector2f( pCursor.x * 10 - cam.x, pCursor.y * 10 - cam.y);
    	}
    	else
    		debugSquare = false;
    }
    
	@Override
	public int getID() {
		return stateID;
	}

}