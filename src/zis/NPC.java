package zis;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Path;


public class NPC extends Sprite {

	/**
	 * TiledSet of the mainMap
	 */
	private final WorldMap world;
	
	/**
	 * New position of the Player
	 */
	private ArrayList< Path> newPath = new ArrayList< Path>(); 
	
	 
	private int idStepPath = 0;
	
	public NPC(Animation pSprite, int nX, int nY, WorldMap world) {
		super(pSprite, nX, nY);
		
		newPath.clear();

		this.world = world;
		

		addNewPath( new Vector2i( 
				(int)Math.floor( Math.random()*80), 
				(int)Math.floor( Math.random()*60)));
		}
	
	public void addNewPath( Vector2i nP){
		Path path = world.getPath( p, nP);
		if( path != null){
			newPath.add( path);
		}
		
	}
	
	public boolean canIgo(int d){
		/*** If valid direction */
		if(d>3) return false;

		/*** Than we check for map collision */
		if(d == CONST.NORTH && 
				world.isSolid( (int)p.x, (int)p.y-1))
			return true;
		if(d == CONST.EAST &&
				world.isSolid( (int)p.x+1, (int)p.y))
			return true;
		if(d == CONST.SOUTH &&
				world.isSolid( (int)p.x, (int)p.y+1))
			return true;
		if(d == CONST.WEST &&
				world.isSolid( (int)p.x-1, (int)p.y))
			return true;
		else
			return false;
	}
	
	
	public void iALogic(){
		if(newPath.size()>0 && newPath.get(0).getLength() > idStepPath){
			if(newPath.get(0).getX(idStepPath) > p.x){
				move(CONST.EAST);
			}
			if(newPath.get(0).getY(idStepPath) > p.y){
				move(CONST.SOUTH);
			}
			if(newPath.get(0).getX(idStepPath) < p.x){
				move(CONST.WEST);
			}
			if(newPath.get(0).getY(idStepPath) < p.y){
				move(CONST.NORTH);
			}
				
			idStepPath++;
		}
		else{
			newPath.clear();
			
			idStepPath = 0;
			addNewPath( new Vector2i( 
					(int)Math.floor( Math.random()*80), 
					(int)Math.floor( Math.random()*60)));
		}
		
	}
	
	/**
	 * NPC update function
	 * @param gc GameContainer
	 * @param sb StateBasedGame
	 * @param delta Time between frame
	 * @param actionPt Turn between frame
	 */
    public void update(GameContainer gc, StateBasedGame sb, int delta){
    	iALogic();
    }
}
