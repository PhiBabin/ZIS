package zis;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.geom.Vector2f;
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

    	for(int x=0; x<map.getWidth(); x++){
        	for(int y=0; y<map.getHeight(); y++){
        		canBlock=map.getTileProperty(
                		map.getTileId(x, y, 0), blockingPropertyName, "true").equals("true");
        		
        		if(!canBlock)
        			blockers.add(new String( x+"-"+y));
        	}
    	}
    }
    
    public Path getPath( Vector2f p, Vector2f nP){
		//System.out.println("LLLLAAAAGGGG");
		return pathFinder.findPath(null, (int)p.x, (int)p.y, (int)nP.x, (int)nP.y);
	}
    
    @Override
    public boolean blocked(PathFindingContext ctx, int x, int y) {
        // NOTE: Using getTileProperty like this is slow. You should instead cache the results. 
        // For example, set up a HashSet<Integer> that contains all of the blocking tile ids. 
        return isSolid( x, y);
    }
    
    public boolean isSolid( int x, int y){
    	return !blockers.contains( new String( x+"-"+y));
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
