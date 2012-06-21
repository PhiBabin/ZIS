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

import zis.map.City;
import zis.map.MiniMap;
import zis.map.Road;
import zis.map.WorldMap;
import zis.util.Vector2i;

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
	
	private City city;
	
	private MiniMap miniMap;
	
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
		city.generateEmptyMap();
		
    	long generationTime = System.currentTimeMillis();
    	
//    	city.generateCity( 311312313);
    	city.generateCity( (int)System.currentTimeMillis());
    	city.tileCorrection();
    	
    	miniMap.updateMiniMap( city.getMap());
    	
    	System.out.println("Map generate in " + (int)(System.currentTimeMillis() - generationTime) + "ms.");
		
	}

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	
    	resMan = new RessourceManager();
        
    	city = new City( new WorldMap( resMan.tilesetImg));
    	miniMap = new MiniMap( new Vector2f( 5, CONST.SCREEN_HEIGHT - 155));
    	
    	newRandomMap(); 
    	
    	//mapGen.generateLabyrinth( 0, 0, 80, 60)

    	 population = new ArrayList<NPC>();
    	 population.clear();
    	 
    	 Vector2i pPop;
    	 int e=2;
     	 long generationTime;
    	 while(e < 30){
    		 pPop = new Vector2i(
    				(int)Math.floor( Math.random() * 80) + 3,
    				(int)Math.floor( Math.random() * 60) + 3);
    		 
    		 if( !city.map.isSolid( pPop.x, pPop.y)){
    			 generationTime = System.currentTimeMillis();
        		// System.out.println( "I'm number "+ e + ". Who's number 1?");
    			 population.add(new NPC( resMan.player, pPop.x, pPop.y, city.map));
    			 e++;
    		    	System.out.println("Habitant generate in " + (int)(System.currentTimeMillis() - generationTime) + "ms.");
    		 }
    	 }
    	 
    	 cam = new Vector2f( 0, 0);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
		gr.setColor( Color.white);
		
    	gr.setBackground( new Color( 13, 37, 47));
    	city.map.render(gc, sbg, gr, cam);
		
		for( NPC popu : population){
			popu.render(gc, sbg, gr, cam);
		}

		gr.drawString( "- Time (" + gameTime + "ms)x" + Math.round( gameSpeed * 100) + "% " +
				"- Turn (" + turn + ")" +
				"- Population (" + population.size() + ")" +
				"- Symmetric (" + CONST.SYMMETRICROOM + ")", 80, 10);
		
		gr.drawString( "( " + cam.x + "," + " " + cam.y + ")", 620, 580); 
		
		if( debugSquare){
			gr.drawRect( pSquare.x, pSquare.y, 10, 10);
			gr.drawString( "( " + Math.floor( ( pCursor.x) ) + "," +
							" " + Math.floor( ( pCursor.y) ) + ")",
							5, 580);
		}
		if( debugMod){
			gr.translate( -cam.x, -cam.y);
			gr.setColor( Color.orange);
			gr.setLineWidth(4);
			for( Road road : city.getRoads()){
				Rectangle r = road.getRect();
				gr.drawRect( 
						r.getX() * 10,
						r.getY() * 10, r.getWidth() * 10,
						r.getHeight() * 10);
				if ( road.isAvenue())
					gr.rotate( r.getX() * 10 + r.getWidth() * 5, 
						r.getY() * 10 + r.getHeight() * 5,
						-90);
				gr.drawString( road.getName(),
						r.getX() * 10 + r.getWidth() * 5,
						r.getY() * 10 + r.getHeight() * 5 + r.getWidth() * 3);
				if ( road.isAvenue())
					gr.rotate( r.getX() * 10 + r.getWidth() * 5, 
							r.getY() * 10 + r.getHeight() * 5,
							90);
			}
			gr.setColor( Color.white);
			gr.setLineWidth(1);
			gr.translate( cam.x, cam.y);
		}
		
		miniMap.render( gc, sbg, gr, cam);
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
    	
    	if( input.isMouseButtonDown( input.MOUSE_LEFT_BUTTON)){
    		cam = miniMap.onClick( new Vector2f( input.getMouseX(), input.getMouseY()), cam);
    	}
    	
    	
    	cam.x = (float) Math.floor( cam.x);
    	cam.y = (float) Math.floor( cam.y);
    	
    	/*** Make sure that the camera is in the map */
    	if (cam.x < 0)
    		cam.x = 0;
    	if(cam.x > CONST.MAP_WIDTH * CONST.TILE_WIDTH - CONST.SCREEN_WIDTH)
    		cam.x = CONST.MAP_WIDTH * CONST.TILE_WIDTH - CONST.SCREEN_WIDTH;
    	if (cam.y < 0)
    		cam.y = 0;
    	if(cam.y > CONST.MAP_HEIGHT * CONST.TILE_HEIGHT - CONST.SCREEN_HEIGHT)
    		cam.y = CONST.MAP_HEIGHT * CONST.TILE_HEIGHT - CONST.SCREEN_HEIGHT;
    	
    	
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
    	
    	/*** We pass to the next turn */
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

    	if( city.map.isSolid( (int)pCursor.x, (int)pCursor.y)){
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