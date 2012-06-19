package zis.map;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import zis.CONST;
import zis.util.Vector2i;

public class WorldMap implements TileBasedMap {
	public SpriteSheet tilesetImg;
	
	private ArrayList< short[][]> map = new ArrayList< short[][]>();
	
	private ArrayList< Image> tiles = new ArrayList< Image>();
	
	private Set<String> blockers = new HashSet<String>();
	
	private AStarPathFinder pathFinder;

    public WorldMap(SpriteSheet tilesetImg) throws SlickException {
    	map.add( new short[CONST.MAP_WIDTH][CONST.MAP_HEIGHT]);

        this.tilesetImg = tilesetImg;

		regenerateBlocker();
		loadTileset();
		
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
    
    public void loadTileset() throws SlickException{
	    for(int y = 0; y < tilesetImg.getVerticalCount(); y++){
	    	for(int x = 0; x < tilesetImg.getHorizontalCount(); x++){
	    		tiles.add( tilesetImg.getSubImage( x, y));
	    		tiles.get( tiles.size()-1).setFilter( Image.FILTER_NEAREST );
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
    	//return !blockers.contains( new String( x+"-"+y));

		if( x >= 0 && y >= 0 && y < getHeightInTiles() && x < getWidthInTiles())
			return map.get( 0)[x][y] != 1 && map.get( 0)[x][y] != 157 && map.get( 0)[x][y] != 158;
		else 
			return true;
    }
    
    
    public void render( GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
    	int tileId;
    	Vector2i pScreen = new Vector2i(
    			(int)Math.floor( cam.x * 0.1),
    			(int)Math.floor( cam.y * 0.1));
    	
		for(int x = pScreen.x; x <= pScreen.x + CONST.SCREEN_WIDTH / CONST.TILE_WIDTH; x++){
	    	for(int y = pScreen.y; y <= pScreen.y + CONST.SCREEN_HEIGHT / CONST.TILE_HEIGHT; y++){
	    		if( x > 0 && y > 0 && y < getHeightInTiles() && x < getWidthInTiles()){
	    			tileId = map.get( 0)[x][y];
	    		
	        		if ( tileId != 1)
	        			gr.drawImage( tiles.get( tileId - 1), x * CONST.TILE_WIDTH - cam.x, y * CONST.TILE_HEIGHT - cam.y);
	    		}
        	}
    	}
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
