/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

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
		if( x >= 0 && y >= 0 && z >= 0 && x < getWidthInTiles() && y < getHeightInTiles() && z < map.size())
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
			return map.get( 0)[x][y] != 1 && map.get( 0)[x][y] != 8 && map.get( 0)[x][y] != 9;
		else 
			return true;
    }
    
    
    public void render( GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
    	int tileId;
    	Vector2i pScreen = new Vector2i(
    			(int)Math.floor( cam.x / CONST.TILE_WIDTH),
    			(int)Math.floor( cam.y / CONST.TILE_HEIGHT));
    	
		for(int x = pScreen.x; x <= pScreen.x + CONST.SCREEN_WIDTH / CONST.TILE_WIDTH; x++){
	    	for(int y = pScreen.y; y <= pScreen.y + CONST.SCREEN_HEIGHT / CONST.TILE_HEIGHT; y++){
	    		if( x >= 0 && y >= 0 && y < getHeightInTiles() && x < getWidthInTiles()){
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
