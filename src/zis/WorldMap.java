package zis;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class WorldMap implements TileBasedMap {
	public SpriteSheet tilesetImg;
	
	private ArrayList< short[][]> map = new ArrayList< short[][]>();
	
	private Set<String> blockers = new HashSet<String>();
	
	private AStarPathFinder pathFinder;

    public WorldMap(SpriteSheet tilesetImg) {
    	map.add( new short[CONST.MAP_WIDTH][CONST.MAP_HEIGHT]);
    	
    	System.out.println( "New WorldMap");

        this.tilesetImg = tilesetImg;

		regenerateBlocker();
		
		pathFinder = new AStarPathFinder( this, CONST.MAX_PATH_LENGTH, false);
		
    }
    
    public int getTileId( int x, int y, int z){
    	return map.get(z)[x][y];
    } 
    public void setTileId( int x, int y, int z, int idTile){
    	map.get(z)[x][y] = (short)idTile;
    }
    
    public void clear(){
    	for(int i = 0; i < map.size(); i++){
    		 map.set( i, new short[CONST.MAP_WIDTH][CONST.MAP_HEIGHT]);
    	}
    	 
		for(int x = 0; x < CONST.MAP_WIDTH; x++){
	    	for(int y = 0; y < CONST.MAP_HEIGHT; y++){
	    		map.get( 0)[x][y] = 1;
	    	}
		}
    }
    
    public void regenerateBlocker(){
    	boolean canBlock;
    	blockers.clear();

    	for(int x = 0; x < CONST.MAP_WIDTH; x++){
        	for(int y = 0; y < CONST.MAP_HEIGHT; y++){
        		canBlock = getTileId( x, y, 0) == 1;
        		
        		if(!canBlock)
        			blockers.add( new String( x+"-"+y));
        	}
    	}
    }
    
    public Path getPath( Vector2i p, Vector2i nP){
		//System.out.println("LLLLAAAAGGGG");
		return pathFinder.findPath(null, p.x, p.y, nP.x, nP.y);
	}
    
    @Override
    public boolean blocked( PathFindingContext ctx, int x, int y) {
        return isSolid( x, y);
    }
    
    public boolean isSolid( int x, int y){
    	return !blockers.contains( new String( x+"-"+y));
    }
    
    
    public void render( GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
    	
    	int tileId, tileInTilesetX = tilesetImg.getHorizontalCount();
    	
		for(int x = 0; x < CONST.MAP_WIDTH; x++){
	    	for(int y = 0; y < CONST.MAP_HEIGHT; y++){
	    		tileId = map.get( 0)[x][y];
	    		
	    		System.out.println( tileId % tileInTilesetX);
	    		System.out.println( (tileId - tileId % tileInTilesetX) / tileInTilesetX);
        		if ( tileId != 1)gr.drawImage( 
        				tilesetImg.getSubImage( tileId % tileInTilesetX - 1, (tileId - tileId % tileInTilesetX) / tileInTilesetX),
        				x * CONST.TILE_WIDTH - cam.x,
        				y * CONST.TILE_HEIGHT -cam.y);
        	}
    	}
	//	aniSprite.draw( p.x * 10 - cam.x, p.y * 10 - cam.y);
	}

    @Override
    public float getCost( PathFindingContext ctx, int x, int y) {
        return 1.0f;
    }

    @Override
    public int getHeightInTiles() {
        return CONST.MAP_HEIGHT;
    }

    @Override
    public int getWidthInTiles() {
        return CONST.MAP_WIDTH;
    }

    @Override
    public void pathFinderVisited(int arg0, int arg1) {}

	
}
