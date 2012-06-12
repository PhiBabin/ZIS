package zis;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class BasicMap implements TileBasedMap {
	public TiledMap map;

	private String blockingPropertyName;
	
	private AStarPathFinder pathFinder;
	
	private Set<String> blockers = new HashSet<String>();

    public BasicMap(TiledMap map, String blockingPropertyName) {
        this.map = map;
        this.blockingPropertyName = blockingPropertyName;
        
		pathFinder = new AStarPathFinder( this, CONST.MAX_PATH_LENGTH, false);
		
		regenerateBlocker();
    }
    
    public void regenerateBlocker(){
    	boolean canBlock;
    	blockers.clear();

    	for(int x=0; x<map.getWidth(); x++){
        	for(int y=0; y<map.getHeight(); y++){
        		canBlock=map.getTileProperty(
                		map.getTileId(x, y, 0), blockingPropertyName, "true").equals("true");
        		
        		if(!canBlock)
        			blockers.add(new String( x+"-"+y));
        	}
    	}
    }
    
    public Path getPath( Vector2i p, Vector2i nP){
		//System.out.println("LLLLAAAAGGGG");
		return pathFinder.findPath(null, p.x, p.y, nP.x, nP.y);
	}
    
    @Override
    public boolean blocked(PathFindingContext ctx, int x, int y) {
        return isSolid( x, y);
    }
    
    public boolean isSolid( int x, int y){
    	return !blockers.contains( new String( x+"-"+y));
    }
    
    public void setMap( TiledMap map){
        this.map = map;
		regenerateBlocker();
    }

    @Override
    public float getCost(PathFindingContext ctx, int x, int y) {
        return 1.0f;
    }

    @Override
    public int getHeightInTiles() {
        return map.getHeight();
    }

    @Override
    public int getWidthInTiles() {
        return map.getWidth();
    }

    @Override
    public void pathFinderVisited(int arg0, int arg1) {}

	
}
