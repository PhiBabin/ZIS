/**
 * 
 * @author Philippe Babin
 *
 *	This class works as the gameplay logic manager.
 *	The current drawable entity is render.
 */
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;


public class PlayState extends BasicGameState {
	
	private BasicMap cityMap;
	
	private int turn = 0;
	
	private int gameTime = 0;

	public float gameSpeed = 1.0f;

	private ArrayList<NPC> population = null; 
	
	private ImgManager imageMan;
	
	
	private enum STATES {
        START_GAME_STATE, NEW_PIECE_STATE, MOVING_PIECE_STATE, LINE_DESTRUCTION_STATE,
        PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE, GO_WORK, WORK, GO_EAT, EAT, GO_WORK2, WORK2, GO_BED 
    }
	
	int stateID = -1;
	
	/**
	 * Construct our PlayState
	 * @param stateID
	 */
	public PlayState(int stateID){
		this.stateID = stateID;
	}
	

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	imageMan = new ImgManager();
    	 cityMap = new BasicMap( new TiledMap("map/test_basic_design.tmx"), "solid");
    	 
    	 population = new ArrayList<NPC>();
    	 population.clear();
    	 
    	 for(int e=0; e<100; e++){
    		 population.add(new NPC( imageMan.player, (float)Math.random()*60, (float)Math.random()*50, cityMap));
    	 }
    	// population.add(new NPC( imageMan.player, 2.f, 10.f, cityMap) );
    	// population.add(new NPC( imageMan.player, 2.f, 20.f, cityMap) );
    	 

    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
		cityMap.map.render( 0, 0);
		//System.out.println(mainMap.getTileProperty(mainMap.getTileId( 2, 5, 0), "kill", "0"));
		gr.drawString( "- Time (" + gameTime + ")x" + Math.round( gameSpeed * 100) + "% " +
				"- Turn (" + turn + ")", 80, 10); 
		
		//gc1.scale( 0.5f, 0.5f);
		
		for(NPC popu : population){
			popu.render(gc, sbg, gr);
		}
 
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	
    	if(input.isKeyDown(Input.KEY_MINUS) && gameSpeed>=0.1){
    		gameSpeed -= 0.1;
    	}
    	if(input.isKeyDown(Input.KEY_EQUALS) && gameSpeed<4.9){
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
 //   	player.update(gc, sbg, delta);
    }
    
	@Override
	public int getID() {
		return stateID;
	}

}