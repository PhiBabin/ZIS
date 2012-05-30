import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;


public class NPC extends Sprite {

	/**
	 * TiledSet of the mainMap
	 */
	private BasicMap world;
	
	/**
	 * New position of the Player
	 */
	private ArrayList<Path> newPath = new ArrayList<Path>(); 
	
	 
	private int idStepPath = 0;
	
	public NPC(Animation pSprite, BasicMap world) {
		super(pSprite);
		
		newPath.clear();
		this.world = world;
		
		addNewPath( new Vector2f( 4, 38));
	}
	
	public NPC(Animation pSprite, float nX, float nY, BasicMap world) {
		super(pSprite, nX, nY);
		
		newPath.clear();
		this.world = world;
		
		addNewPath( new Vector2f( 4, 38));
	}
	
	public void addNewPath( Vector2f nP){
		newPath.add( world.getPath(p, nP));
	}
	
	public boolean canIgo(int d){
		/*** If valid direction */
		if(d>3) return false;

		/*** Than we check for map collision */
		if(d == CONST.NORTH && world.isSolid( (int) p.x, (int) p.y-1))
			return true;
		if(d == CONST.EAST &&
				world.isSolid( (int)p.x+1, (int)p.y))
			return true;
		if(d == CONST.SOUTH &&
				"1" != world.map.getTileProperty( world.map.getTileId( (int)p.x, (int)p.y+1, 0), "solid", "1"))
			return true;
		if(d == CONST.WEST &&
				"1" != world.map.getTileProperty( world.map.getTileId( (int)p.x-1, (int)p.y, 0), "solid", "1"))
			return true;
		else
			return false;
	}
	
	public void pathFinding( Vector2f g){
		
	}
	
	public void iALogic(){
		
		System.out.println( "Yes?");
		if(newPath.size()>0){
		//	if(newPath.get(0).GetLenth == p){
		//		newPath.remove(0);
		//	}
		//	else{
		//		System.out.println( "Let's go to ( " + newPath.get(0).x + ", "+ newPath.get(0).y + ")");
		//	}
			
			if(canIgo(CONST.EAST)){
				move(CONST.EAST);
			}
			else{
				if(canIgo(CONST.SOUTH)){
					move(CONST.SOUTH);
				}
				System.out.println("It's solid man!");
			}
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
