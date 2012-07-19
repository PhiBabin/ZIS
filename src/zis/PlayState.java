/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

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

import zis.NPC.State;
import zis.map.Apartment;
import zis.map.Building;
import zis.map.City;
import zis.map.MiniMap;
import zis.map.Road;
import zis.map.Room;
import zis.map.WorldMap;
import zis.util.Vector2i;

public class PlayState extends BasicGameState {
	
	private int turn = 0;
	
	private int gameTime = 0;

	public float gameSpeed = 1.0f;

	private ArrayList<NPC> population = null; 
	
	private RessourceManager resMan;
	
	private City city;
	
	private MiniMap miniMap;
	
	private Vector2f cam;
	
	private int selectedId = -1;
	
	int stateID = -1;
	
	private Vector2f pSquare = new Vector2f( 0, 0);
	private Vector2i pCursor = new Vector2i( 0, 0);
	
	private boolean debugMod = false;
	private boolean debugSquare = false;
	private boolean debStr = false, debBdg = false, debRom = false, debHall = false, debCur = true;

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
	   	population.clear();
	   	selectedId = -1;
		
    	long generationTime = System.currentTimeMillis();
    	
//    	city.generateCity( 311312313);
    	city.create( (int)System.currentTimeMillis());
    	city.tileCorrection();
    	
    	miniMap.updateMiniMap( city.getMap());
    	
    	System.out.println("Map generate in " + (int)(System.currentTimeMillis() - generationTime) + "ms.");
		
	}
	
	public void addHabitant( Vector2i p, int idRoom, int idBuilding, int idApart, int idApartRoom){
		population.add( new NPC( resMan.player, p.x, p.y, idRoom, idBuilding, idApart, idApartRoom));
	}
	
	public void distributeInfection(){
		int I = (int) ( population.size() * 0.1);
		int Z = (int) ( population.size() * 0.1);
		int Im = (int) ( population.size() * 0.05);
		
		int id;
		
		while( I != 0){
			id =  (int) ( population.size() * Math.random());
			if( population.get( id).getState() == State.NORMAL){
				population.get( id).setState( State.INFECTED);
				I--;
			}
		}
		while( Z != 0){
			id =  (int) ( population.size() * Math.random());
			if( population.get( id).getState() == State.NORMAL){
				population.get( id).setState( State.ZOMBIE);
				Z--;
			}
		}
		while( Im != 0){
			id =  (int) ( population.size() * Math.random());
			if( population.get( id).getState() == State.NORMAL){
				population.get( id).setState( State.IMMUNE);
				Im--;
			}
		}
	}

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	
    	resMan = new RessourceManager();
        
    	city = new City( new WorldMap( resMan.tilesetImg), this);
    	miniMap = new MiniMap( new Vector2f( 5, CONST.SCREEN_HEIGHT - 155));

	   	population = new ArrayList<NPC>();
	   	population.clear();
    	
    	newRandomMap(); 
    	
    	NPC.world = city.map;
    	
    	//mapGen.generateLabyrinth( 0, 0, 80, 60)
    	 
    	cam = new Vector2f( 0, 0);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
		gr.setColor( Color.white);
		
    	gr.setBackground( new Color( 13, 37, 47));
    	city.map.render(gc, sbg, gr, cam);
		
    	Rectangle camera = new Rectangle( cam.x - CONST.TILE_WIDTH, cam.y - CONST.TILE_HEIGHT, CONST.SCREEN_WIDTH, CONST.SCREEN_HEIGHT);
    	
    	int Z = 0;
    	int I = 0;
    	int Im = 0;
		for( NPC popu : population){
			if( popu.getState() == State.IMMUNE)
				Im++;
			if( popu.getState() == State.INFECTED)
				I++;
			if( popu.getState() == State.ZOMBIE)
				Z++;
			if( camera.contains( popu.getPosition().x * CONST.TILE_WIDTH, popu.getPosition().y * CONST.TILE_HEIGHT)){
				popu.render(gc, sbg, gr, cam);
			}
		}
		
		/***************************************************************
		 * 
		 * <DEBUGING>
		 * 
		 * ************************************************************
		 */
		
		gr.drawString( "- Turn (" + turn + ")x" + Math.round( gameSpeed * 100) + "% " +
				"- Symmetric (" + CONST.SYMMETRICROOM + ")", 80, 10);
		
		gr.drawString( "Pop(" + population.size() + ") " + "N:" + (population.size() - I - Z) + " I:" + I + " Z:" + Z + " Im:" + Im, 10, 25);
		
		gr.drawString( "X:" + cam.x + " Y:" + cam.y, 10, 41); 
		
		if( selectedId != -1){
			gr.drawString( population.get( selectedId).toString(), 600, 10);
			
			gr.setLineWidth( 2);
			gr.setColor( Color.yellow);
			gr.drawRect( 
					population.get( selectedId).getX() * CONST.TILE_WIDTH + 1 - cam.x,
					population.get( selectedId).getY() * CONST.TILE_HEIGHT - cam.y,
					12, 12);
			
			gr.setLineWidth( 1);
			gr.setColor( Color.white);
		}

    	
		
		if( debugMod){
			/*** Show rectangle on the tile that the cursor touch **/
    		gr.setColor( Color.yellow);
			if( debCur){
				gr.drawString( "(C)", 5, 80);
				
				if( !city.map.isSolid( (int)pCursor.x, (int)pCursor.y))
		    		gr.setColor( Color.white);
				gr.drawRect( pCursor.x * CONST.TILE_WIDTH - cam.x, pCursor.y * CONST.TILE_HEIGHT - cam.y, CONST.TILE_WIDTH - 1, CONST.TILE_HEIGHT - 1);	
			}
			else
				gr.drawString( " C", 5, 80);
			gr.setColor( Color.white);
			gr.drawString( "( " + Math.floor( ( pCursor.x) ) + "," +
							" " + Math.floor( ( pCursor.y) ) + ")",
							5, 420);
			
			/*** Show graphical help to debugging **/
			gr.translate( -cam.x, -cam.y);
			gr.setLineWidth(4);
			/*** Streets **/
			gr.setColor( Color.orange);
			if( debStr){
				gr.drawString( "(S)", 5 + cam.x, 100 + cam.y);
				for( Road road : city.getRoads()){
					Rectangle r = road.getRect();
					gr.drawRect( r.getX() * CONST.TILE_WIDTH, r.getY() * CONST.TILE_HEIGHT, r.getWidth() * CONST.TILE_WIDTH, r.getHeight() * CONST.TILE_HEIGHT);
					if ( road.isAvenue()){
						gr.rotate( r.getX() * CONST.TILE_WIDTH + r.getWidth() * 5, 
							r.getY() * CONST.TILE_HEIGHT + r.getHeight() * 5,
							-90);
						gr.drawString( road.getName(),
							r.getX() * CONST.TILE_WIDTH + r.getWidth() * 5,
							r.getY() * CONST.TILE_HEIGHT + r.getHeight() * 5 + r.getWidth() * 3);
						gr.rotate( r.getX() * CONST.TILE_WIDTH + r.getWidth() * 5, 
							r.getY() * CONST.TILE_HEIGHT + r.getHeight() * 5,
							90);
					}
					else
						gr.drawString( road.getName(),
							r.getX() * CONST.TILE_WIDTH + r.getWidth() * 5,
							r.getY() * CONST.TILE_HEIGHT + r.getHeight() * 5);
				}
			}
			else
				gr.drawString( " S", 5 + cam.x, 100 + cam.y);
			/*** Buildings **/
			gr.setColor( Color.cyan);
			if( debBdg){
				gr.drawString( "(B)", 5 + cam.x, 120 + cam.y);
				for( Building b : city.getBuildings()){
					Rectangle r = b.getRect();
					gr.drawRect( r.getX() * CONST.TILE_WIDTH, r.getY() * CONST.TILE_HEIGHT, r.getWidth() * CONST.TILE_WIDTH, r.getHeight() * CONST.TILE_HEIGHT);
					
				}
			}
			else
				gr.drawString( " B", 5 + cam.x, 120 + cam.y);
			/*** Hallways **/
			gr.setColor( Color.magenta);
			if( debHall){
				gr.drawString( "(H)", 5 + cam.x, 140 + cam.y);
				for( Building b : city.getBuildings()){
					for( Rectangle r : b.getHallways()){
						gr.drawRect( r.getX() * CONST.TILE_WIDTH, r.getY() * CONST.TILE_HEIGHT, r.getWidth() * CONST.TILE_WIDTH, r.getHeight() * CONST.TILE_HEIGHT);
					}
				}
			}
			else
				gr.drawString( " H", 5 + cam.x, 140 + cam.y);
			/*** Rooms **/
			gr.setColor( Color.lightGray);
			if( debRom){
				gr.drawString( "(R)", 5 + cam.x, 160 + cam.y);
				for( Building b : city.getBuildings()){
					for( Room rom : b.getRooms()){
						Rectangle r = rom.getRect();
						gr.drawRect( r.getX() * CONST.TILE_WIDTH, r.getY() * CONST.TILE_HEIGHT, r.getWidth() * CONST.TILE_WIDTH, r.getHeight() * CONST.TILE_HEIGHT);
					}
				}
			}
			else
				gr.drawString( " R", 5 + cam.x, 160 + cam.y);
			gr.setColor( Color.white);
			gr.setLineWidth(1);
			gr.translate( cam.x, cam.y);
		}

		/***************************************************************
		 * 
		 * </DEBUGING>
		 * 
		 * ************************************************************
		 */
		
		miniMap.render( gc, sbg, gr, cam);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	
    	pCursor.x = (int)Math.floor( ( input.getMouseX() + cam.x) / CONST.TILE_WIDTH);
    	pCursor.y = (int)Math.floor( ( input.getMouseY() + cam.y) / CONST.TILE_HEIGHT);
    	
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

    	if( input.isMousePressed( input.MOUSE_LEFT_BUTTON)){
    		int id = 0;
    		for( NPC popu : population){
    			if( pCursor.x >= popu.getX() && pCursor.y >= popu.getY() && pCursor.x < popu.getX() + 1 && pCursor.y < popu.getY() + 1){
    				selectedId = id;
    			}
    			id++;
    		}
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
    	if(!debStr && input.isKeyPressed(Input.KEY_S))
    		debStr = true;
    	else if(debStr && input.isKeyPressed(Input.KEY_S))
    		debStr = false;
    	if(!debBdg && input.isKeyPressed(Input.KEY_B))
    		debBdg = true;
    	else if(debBdg && input.isKeyPressed(Input.KEY_B))
    		debBdg = false;
    	if(!debCur && input.isKeyPressed(Input.KEY_C))
    		debCur = true;
    	else if(debCur && input.isKeyPressed(Input.KEY_C))
    		debCur = false;
    	if(!debRom && input.isKeyPressed(Input.KEY_R))
    		debRom = true;
    	else if(debRom && input.isKeyPressed(Input.KEY_R))
    		debRom = false;
    	if(!debHall && input.isKeyPressed(Input.KEY_H))
    		debHall = true;
    	else if(debHall && input.isKeyPressed(Input.KEY_H))
    		debHall = false;
    	
    	
    	/** Symmetric Room */
    	if(!(CONST.SYMMETRICROOM) && input.isKeyPressed(Input.KEY_S))
    		CONST.SYMMETRICROOM = true;
    	else if(CONST.SYMMETRICROOM && input.isKeyPressed(Input.KEY_S))
    		CONST.SYMMETRICROOM = false;
    	
    	/*** Control the speed of the game */
    	if( input.isKeyDown(Input.KEY_MINUS) && gameSpeed >= 0.1){
    		gameSpeed -= 0.1;
    	}
    	if( input.isKeyDown(Input.KEY_EQUALS) && gameSpeed < 100.0){
    		gameSpeed += 0.1;
    	}
    	if( input.isKeyDown(Input.KEY_P)){
    		gameSpeed = 0;
    	}
    	
    	gameTime += delta * gameSpeed;
    	
    	/*** Next turn */
    	int elapseTurn = (int) (gameTime * 0.001) - turn;

    	/*** We move each NPC */
    	for( int i = 1; i <= elapseTurn; i++){
    		System.out.println("==== TURN " + (turn + i) + " ====");
    		for(NPC popu : population){
    			popu.update(gc, sbg, delta);
    		}
    	}
    	turn += elapseTurn;
    	
    	/*** We infect the NPC */
		for(NPC popu : population){
			if( popu.getState() == State.INFECTED ||  popu.getState() == State.ZOMBIE){
				for(NPC aroundPop : population){
					if( aroundPop.getState() == State.NORMAL){
						if( popu.getX() == aroundPop.getX() &&  popu.getY() == aroundPop.getY()){
							aroundPop.setState( State.INFECTED);
						}
						if( popu.getX() - 1 == aroundPop.getX() &&  popu.getY() == aroundPop.getY()){
							aroundPop.setState( State.INFECTED);
						}
						if( popu.getX() + 1 == aroundPop.getX() &&  popu.getY() == aroundPop.getY()){
							aroundPop.setState( State.INFECTED);
						}
						if( popu.getX() == aroundPop.getX() &&  popu.getY() == aroundPop.getY() + 1){
							aroundPop.setState( State.INFECTED);
						}
						if( popu.getX() == aroundPop.getX() &&  popu.getY() == aroundPop.getY() - 1){
							aroundPop.setState( State.INFECTED);
						}
					}
				}
			}
		}
    }
    
	@Override
	public int getID() {
		return stateID;
	}

}