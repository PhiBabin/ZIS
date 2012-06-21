package zis.map;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import zis.CONST;
import zis.util.Rand;
import zis.util.Vector2i;

/***
 * Generator of the interior and exterior of a city
 * 
 * @author Philippe Babin
 */
public class City {
	
	/*** Container of the generated map */
	public WorldMap map;

	/*** Randomizer */
	private Rand rand;
	
	/*** Stack of generated buildings */
	public ArrayList<Building> buildings = new ArrayList<Building>();
	
	/*** Stack of Street and Avenue */
	public ArrayList<Road> roads = new ArrayList<Road>();

	/*** Name of the city */
	private String name = "Debug City";
	
	/***
	 * Procedurally generate a city
	 * @param map Container of the generated map
	 * @throws SlickException
	 */
	public City( WorldMap map) throws SlickException {
		this.map = map;
		generateEmptyMap();
	}
	
	/***
	 * Clear the current map
	 * @throws SlickException
	 */
	public void generateEmptyMap() throws SlickException {
		map.clear();
	}
		
	/***
	 * Generate the city
	 */
	public void generateCity( int seed){
		rand = new Rand( seed);
		
		buildings.add( new Building( this, seed * 5, 2, 2, rand.nextInt( 20, 290), rand.nextInt( 20, 290)));
		
		/*** Avenue generation */
		int avePosition = rand.nextInt( CONST.BLOCK_WIDTH_MIN, CONST.BLOCK_WIDTH_MAX);
		int i = 1;
		while( avePosition  <= CONST.MAP_WIDTH) {
			roads.add( new Road( avePosition, 0, map.getHeightInTiles(), true, i));
			avePosition += CONST.AVENUE_WIDTH + rand.nextInt( CONST.BLOCK_WIDTH_MIN, CONST.BLOCK_WIDTH_MAX);
			i++;
		}

		/*** Street generation */
		int strPosition = rand.nextInt( CONST.BLOCK_HEIGHT_MIN, CONST.BLOCK_HEIGHT_MAX);
		i = 1;
		while( strPosition  <= CONST.MAP_HEIGHT) {
			roads.add( new Road( 0, strPosition, map.getWidthInTiles(), false, i));
			strPosition += CONST.STREET_WIDTH + rand.nextInt( CONST.BLOCK_HEIGHT_MIN, CONST.BLOCK_HEIGHT_MAX);
			i++;
		}
		
	}
	
	/***
	 * Draw the outline of a rectangle on the map.
	 * @param r Rectangle
	 * @param tileId Id of the outline 
	 */
	public void drawOutline( Rectangle r, int tileId){
		drawOutline( (int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight(), tileId);
	}
	/***
	 * Draw the outline of a rectangle on the map.
	 * @param x Position X of the rectangle
	 * @param y Position Y of the rectangle
	 * @param W Width of the rectangle
	 * @param H Height of the rectangle
	 * @param tileId Id of the outline 
	 */
	public void drawOutline( int x, int y, int W, int H, int tileId){
		for(int v = x; v < x + W; v++){
			for(int g = y; g < y + H; g++){
				
				if(v == x || g == y || v == x + W - 1 || g == y + H - 1)
					map.setTileId( v, g, 0, tileId);
			}
		}
	}
	
	/***
	 * Draw a rectangle on the map.
	 * @param x Position X of the rectangle
	 * @param y Position Y of the rectangle
	 * @param W Width of the rectangle
	 * @param H Height of the rectangle
	 * @param tileId Id of the fill rectangle 
	 */
	public void fillRect( int x, int y, int W, int H, int tileId){
		for(int v = x; v < x + W - 1; v++){
			for(int g = y; g < y + H - 1; g++){
				map.setTileId( v, g, 0, tileId);
			}
		}
	}
	
	/***
	 * Add a door
	 * @param d Position of the door
	 * @param ver Direction of the door
	 */
	public void addDoor( Vector2i p, boolean ver){
		if( map.getTileId( (int)p.x, (int)p.y - 1, 0) != 57 
				&& map.getTileId( (int)p.x, (int)p.y + 1, 0) != 57 
				&& ver){
			map.setTileId( (int)p.x, (int)p.y, 0, 157);
		}
		if( map.getTileId( (int)p.x - 1, (int)p.y , 0) != 57 
				&& map.getTileId( (int)p.x + 1, (int)p.y, 0) != 57 
				&& !ver){
			map.setTileId( (int)p.x, (int)p.y, 0, 158);
		}
		
	}
	
	/***
	 * Generate a maze with a Growing Tree algorithm.
	 * @param x Position X of the maze
	 * @param y Position Y of the maze
	 * @param W Width of the maze
	 * @param H Height of the maze
	 */
	public void generateLabyrinth( int x, int y, int W, int H){
		ArrayList<Vector2i> C = new ArrayList<Vector2i>();
		
		fillRect( x, y, W, H, 57);
		
		C.add(new Vector2i( 
			1 + x + (float)Math.floor( Math.random() * ( W /2)) * 2 , 
			1 + y + (float)Math.floor( Math.random() * ( H /2)) * 2 ));
		map.setTileId( (int)C.get(0).x, (int)C.get(0).y, 0, 1);
		
		boolean n, s, w, e, end;
		int d, id, cX, cY;
		
		while( C.size()!=0){
			if( Math.random() > 0.5){
				id = (int)Math.floor( Math.random() * C.size());
			}
			else{
				id = C.size()-1;
			}
			id = C.size()-1;
			
			cX = (int)C.get(id ).x;
			cY = (int)C.get(id ).y;
			n = ( (cY - 2)> y && map.getTileId( cX, cY - 2, 0) == 57);
			s = ( (cY + 2)< y + H && map.getTileId( cX, cY + 2, 0) == 57);
			w = ( (cX - 2)> x && map.getTileId( cX - 2, cY, 0) == 57);
			e = ( (cX + 2)< x + W && map.getTileId( cX + 2, cY, 0) == 57);
			
			if( n || s || w || e){

				end = false;
				while( !end){
					
					d = (int)Math.floor( Math.random() * 4);
					
					if(d == 0 && n){
						end = true;
						C.add(new Vector2i( cX, cY - 2));
						map.setTileId( cX, cY - 2, 0, 1);
						map.setTileId( cX, cY - 1, 0, 1);
					}
					else if(d == 2 && w){
						end = true;
						C.add(new Vector2i( cX - 2, cY));
						map.setTileId(  cX - 2, cY, 0, 1);
						map.setTileId(  cX - 1, cY, 0, 1);
					}
					else if(d == 1 && s){
						end = true;
						C.add(new Vector2i( cX, cY + 2));
						map.setTileId( cX, cY + 2, 0, 1);
						map.setTileId( cX, cY + 1, 0, 1);
					}
					else if(d == 3 && e){
						end = true;
						C.add(new Vector2i( cX + 2, cY));
						map.setTileId( cX + 2, cY, 0, 1);
						map.setTileId( cX + 1, cY, 0, 1);
					}
				}		
			}
			else{
				C.remove( id);
			}
		}
	
	}
	
	/***
	 * Do visual correction of the map
	 */
	public void tileCorrection(){
		boolean n, s, w, e;
		int H = map.getHeightInTiles(), W = map.getWidthInTiles();
		for(int x=0; x < W; x++){
			for(int y=0; y < H; y++){
				if(map.getTileId( x, y, 0) == 57){
					n = ((y - 1) < 0 
							|| map.getTileId( x, y - 1, 0) == 1 
							|| map.getTileId( x, y - 1, 0) == 157 
							|| map.getTileId( x, y - 1, 0) == 158);
					s = ((y + 1) > H 
							|| map.getTileId( x, y + 1, 0) == 1 
							|| map.getTileId( x, y + 1, 0) == 157 
							|| map.getTileId( x, y + 1, 0) == 158);
					w = ((x - 1) < 0 
							|| map.getTileId( x - 1, y, 0) == 1 
							|| map.getTileId( x - 1, y, 0) == 157 
							|| map.getTileId( x - 1, y, 0) == 158);
					e = ((x + 1) > W 
							|| map.getTileId( x + 1, y, 0) == 1 
							|| map.getTileId( x + 1, y, 0) == 157 
							|| map.getTileId( x + 1, y, 0) == 158);
					if( n && s && e && w){
					}
					else if( !n && !s && e && w){
						map.setTileId(  x, y, 0, 73);
					}
					else if( n && s && !e && !w){
						map.setTileId(  x, y, 0, 54);
					}
					else if( n && s && !e && w){
						map.setTileId(  x, y, 0, 52);
					}
					else if( n && s && e && !w){
						map.setTileId(  x, y, 0, 55);
					}
					else if( n && !s && e && w){
						map.setTileId(  x, y, 0, 33);
					}
					else if( !n && s && e && w){
						map.setTileId(  x, y, 0, 93);
					}
					else if( n && !s && !e && w){
						map.setTileId(  x, y, 0, 95);
					}
					else if( n && !s && e && !w){
						map.setTileId(  x, y, 0, 96);
					}
					else if( !n && s && !e && w){
						map.setTileId(  x, y, 0, 115);
					}
					else if( !n && s && e && !w){
						map.setTileId(  x, y, 0, 116);
					}
					else if( !n && !s && !e && !w){
						map.setTileId(  x, y, 0, 53);
					}
					else if( n && !s && !e && !w){
						map.setTileId(  x, y, 0, 149);
					}
					else if( !n && s && !e && !w){
						map.setTileId(  x, y, 0, 151);
					}
					else if( !n && !s && e && !w){
						map.setTileId(  x, y, 0, 153);
					}
					else if( !n && !s && !e && w){
						map.setTileId(  x, y, 0, 155);
					}
				}
			}
		}
		
	}
	
	
	/***
	 * Return current map
	 * @return Current map
	 */
	public WorldMap getMap(){
		return map;
	}
	
	/***
	 * Return the Building list
	 * @return Building list
	 */
	public ArrayList< Building> getBuildings() {
		return buildings;
	}
	
	/***
	 * Return the Road list
	 * @return Road list
	 */
	public ArrayList< Road> getRoads() {
		return roads;
	}
	
	/***
	 * Return City's name
	 * @return City's name
	 */
	public String getName() {
		return name;
	}
}
