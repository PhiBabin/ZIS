package zis.map;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import zis.CONST;
import zis.PlayState;
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
	
	/*** Reference to the game play state */
	public PlayState playState;

	/*** Randomizer */
	private Rand rand;
	
	/*** Stack of generated buildings */
	public ArrayList<Building> buildings = new ArrayList<Building>();
	
	/*** Stack of generated apartments */
	public ArrayList<Apartment> apartments = new ArrayList<Apartment>();
	
	/*** Stack of spot that can turn into building or apartment */
	public ArrayList<Rectangle> constructionSpot = new ArrayList<Rectangle>();
	
	/*** Stack of Street and Avenue */
	public ArrayList<Road> roads = new ArrayList<Road>();

	/*** Name of the city */
	private String name = "Debug City";
	
	/***
	 * Procedurally generate a city
	 * @param map Container of the generated map
	 * @throws SlickException
	 */
	public City( WorldMap map, PlayState playState) throws SlickException {
		this.map = map;
		this.playState = playState;
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
		buildings.clear();
		apartments.clear();
		roads.clear();
		constructionSpot.clear();
		ArrayList<Rectangle> spot = new ArrayList<Rectangle>();
		spot.clear();
		
		rand = new Rand( seed);
		
		//buildings.add( new Building( this, seed * 5, 2, 2, rand.nextInt( 20, 290), rand.nextInt( 20, 290)));
		
		/*** Avenue generation */
		int avePosition = 0;
		int a = 0;
		while( avePosition + CONST.AVENUE_WIDTH <= CONST.MAP_WIDTH) {
			roads.add( new Road( avePosition, 0, map.getHeightInTiles(), CONST.AVENUE, a + 1));
			avePosition += CONST.AVENUE_WIDTH + rand.nextInt( CONST.BLOCK_WIDTH_MIN, CONST.BLOCK_WIDTH_MAX);
			a++;
		}

		/*** Street generation */
		int strPosition = 0;
		int s = 0;
		while( strPosition + CONST.STREET_WIDTH <= CONST.MAP_HEIGHT) {
			roads.add( new Road( 0, strPosition, map.getWidthInTiles(), CONST.STREET, s + 1));
			strPosition += CONST.STREET_WIDTH + rand.nextInt( CONST.BLOCK_HEIGHT_MIN, CONST.BLOCK_HEIGHT_MAX);
			s++;
		}
		
		drawRoads();
		
		/*** Generate buildings **/
		for(int h = 0; h < s - 1; h++){
			for(int w = 0; w < a - 1; w++){
				Rectangle rSpot = new Rectangle( 0, 0, 1, 1);
				
				rSpot.setX( roads.get( w).getRect().getX() + CONST.AVENUE_WIDTH);
				rSpot.setY( roads.get( a + h).getRect().getY() + CONST.STREET_WIDTH);
				
				if( w + 1 >= a) 
					rSpot.setWidth( CONST.MAP_WIDTH - rSpot.getX() - 1);
				else
					rSpot.setWidth( roads.get( w + 1).getRect().getX() - rSpot.getX() );
				
				if( a + h + 1>= roads.size()) 
					rSpot.setHeight( CONST.MAP_HEIGHT - rSpot.getY() - 1);
				else
					rSpot.setHeight( ( roads.get( a + h + 1).getRect().getY()) - rSpot.getY());
				
				spot.add( rSpot);
				constructionSpot.add( rSpot);
			}
			
		}
		
		
		int nbrBuilding = (int) ( spot.size() * 0.4);
		
		for( int i = 0; i < nbrBuilding; i++){
			int id = rand.nextInt( spot.size());
			buildings.add( new Building( this, seed * i, spot.get( id)));

			spot.remove( id);
		}
		
//		int nbrApartment = (int) ( constructionSpot.size() * 0.4);
		int nbrApartment = spot.size();
		
		int testpop = 0;
		
		for( int i = 0; i < nbrApartment; i++){
			int id = rand.nextInt( spot.size());
			apartments.add( new Apartment( this, seed * i, spot.get( id)));
			spot.remove( id);
			
			testpop += 2 * apartments.get( i).getRooms().size()/7;
		}
		
		System.out.println( "pop: " + testpop);
		
		
		/*** Add population */
		Vector2i pHabitant = new Vector2i( 0, 0);
		Rectangle r;
		int idBuilding = 0;
		long generationTime = System.currentTimeMillis();
		
		for( Building b : buildings){
			int idRoom = 0;
			for( Room room : b.getRooms()){
				r = room.getRect();
				if( r.getWidth() * r.getHeight() <= CONST.MAX_OFFICE_ROOM_DOMAIN){
					pHabitant.x = (int) ( r.getX() + rand.nextInt( 1, (int) ( r.getWidth() - 2)));
					pHabitant.y = (int) ( r.getY() + rand.nextInt( 1, (int) ( r.getHeight() - 2)));
					playState.addHabitant( pHabitant, idRoom, idBuilding);
				}
				idRoom++;
			}
			idBuilding++;
		}
		
		playState.distributeInfection();
		
	    System.out.println( "Population generate in " + (int)(System.currentTimeMillis() - generationTime) + "ms.");
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
		if( map.getTileId( (int)p.x, (int)p.y - 1, 0) != 3 
				&& map.getTileId( (int)p.x, (int)p.y + 1, 0) != 3 
				&& ver){
			map.setTileId( (int)p.x, (int)p.y, 0, 8);
		}
		if( map.getTileId( (int)p.x - 1, (int)p.y , 0) != 3 
				&& map.getTileId( (int)p.x + 1, (int)p.y, 0) != 3 
				&& !ver){
			map.setTileId( (int)p.x, (int)p.y, 0, 9);
		}
		
	}
	
	public void drawRoads(){
		Rectangle r;
		int tileId;
		for( Road rd : roads){
			int k = 1;
			r = rd.getRect();
			if( rd.isAvenue() == CONST.AVENUE){
				for( int y = 0; y < r.getHeight(); y++){
					for( int x = 0; x < r.getWidth(); x++){
						tileId = 11;
						
						if( x == 0)
							tileId = 10;
						else if( x == r.getWidth() - 1)
							tileId = 13;
						else if( x == (int)(r.getWidth() / 2)){
							if( k == 1)
								tileId = 12;
							else if( k == 2)
								tileId = 32;
							else
								k = 0;
							k++;
						}
						
						map.setTileId( x + (int)r.getX(), y + (int)r.getY(), 0, tileId);
					}
				}
			}
			else{
				for( int x = 0; x < r.getWidth(); x++){
					for( int y = 0; y < r.getHeight(); y++){
						tileId = 11;
						
						if( y == 0)
							tileId = 31;
						else if( y == r.getHeight() - 1)
							tileId = 30;
						else if( y == (int)(r.getHeight() / 2)){
							if( k == 1)
								tileId = 33;
							else if( k == 2)
								tileId = 34;
							else
								k = 0;
							k++;
						}
						
						map.setTileId( x + (int)r.getX(), y + (int)r.getY(), 0, tileId);
					}
				}
				
			}
				
		}
		
		/*** We add the intersection */
		for( Road ave : roads){
			if( ave.isAvenue() == CONST.AVENUE){
				for( Road st : roads){
					if( st.isAvenue() == CONST.STREET){
						fillRect( 
								(int)ave.getRect().getX(),
								(int)st.getRect().getY(),
								CONST.AVENUE_WIDTH + 1,
								CONST.STREET_WIDTH + 1, 11);
					}
				}
			}
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
		
		fillRect( x, y, W, H, 3);
		
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
			n = ( (cY - 2)> y && map.getTileId( cX, cY - 2, 0) == 3);
			s = ( (cY + 2)< y + H && map.getTileId( cX, cY + 2, 0) == 3);
			w = ( (cX - 2)> x && map.getTileId( cX - 2, cY, 0) == 3);
			e = ( (cX + 2)< x + W && map.getTileId( cX + 2, cY, 0) == 3);
			
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
				if(map.getTileId( x, y, 0) == 3){
					n = ((y - 1) < 0 
							|| map.getTileId( x, y - 1, 0) == 1 
							|| map.getTileId( x, y - 1, 0) == 8 
							|| map.getTileId( x, y - 1, 0) == 9);
					s = ((y + 1) > H 
							|| map.getTileId( x, y + 1, 0) == 1 
							|| map.getTileId( x, y + 1, 0) == 8 
							|| map.getTileId( x, y + 1, 0) == 9);
					w = ((x - 1) < 0 
							|| map.getTileId( x - 1, y, 0) == 1 
							|| map.getTileId( x - 1, y, 0) == 8 
							|| map.getTileId( x - 1, y, 0) == 9);
					e = ((x + 1) > W 
							|| map.getTileId( x + 1, y, 0) == 1 
							|| map.getTileId( x + 1, y, 0) == 8 
							|| map.getTileId( x + 1, y, 0) == 9);
					if( n && s && e && w){
					}
					else if( !n && !s && e && w){
						map.setTileId(  x, y, 0, 42);
					}
					else if( n && s && !e && !w){
						map.setTileId(  x, y, 0, 23);
					}
					else if( n && s && !e && w){
						map.setTileId(  x, y, 0, 21);
					}
					else if( n && s && e && !w){
						map.setTileId(  x, y, 0, 24);
					}
					else if( n && !s && e && w){
						map.setTileId(  x, y, 0, 2);
					}
					else if( !n && s && e && w){
						map.setTileId(  x, y, 0, 62);
					}
					else if( n && !s && !e && w){
						map.setTileId(  x, y, 0, 43);
					}
					else if( n && !s && e && !w){
						map.setTileId(  x, y, 0, 44);
					}
					else if( !n && s && !e && w){
						map.setTileId(  x, y, 0, 63);
					}
					else if( !n && s && e && !w){
						map.setTileId(  x, y, 0, 64);
					}
					else if( !n && !s && !e && !w){
						map.setTileId(  x, y, 0, 22);
					}
					else if( n && !s && !e && !w){
						map.setTileId(  x, y, 0, 4);
					}
					else if( !n && s && !e && !w){
						map.setTileId(  x, y, 0, 5);
					}
					else if( !n && !s && e && !w){
						map.setTileId(  x, y, 0, 6);
					}
					else if( !n && !s && !e && w){
						map.setTileId(  x, y, 0, 7);
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
	 * Return the Apartment list
	 * @return Apartment list
	 */
	public ArrayList< Apartment> getApartments() {
		return apartments;
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
