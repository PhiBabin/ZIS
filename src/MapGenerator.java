
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class MapGenerator {
	
	private TiledMap map;
	
	private Random rand = new Random();
	
	public ArrayList<Rectangle> temRegion = new ArrayList<Rectangle>();
	
	public ArrayList<Rectangle> buildRegion = new ArrayList<Rectangle>();
	
	public ArrayList<Rectangle> hallway = new ArrayList<Rectangle>();
	
	public ArrayList<Room> rooms = new ArrayList<Room>();
	
	public MapGenerator() throws SlickException {
		generateEmptyMap();
	}
	
	public void generateEmptyMap() throws SlickException {
		map = new TiledMap( "map/empty.tmx");
	}

	public void drawOutline( Rectangle r, int tileId){
		drawOutline( (int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight(), tileId);
	}
	public void drawOutline( int x, int y, int W, int H, int tileId){
		for(int v = x; v < x + W; v++){
			for(int g = y; g < y + H; g++){
				
				if(v == x || g == y || v == x + W - 1 || g == y + H - 1)
					map.setTileId( v, g, 0, tileId);
			}
		}
	}
	
	public void fillRect( int x, int y, int W, int H, int tileId){
		for(int v = x; v < x + W - 1; v++){
			for(int g = y; g < y + H - 1; g++){
				map.setTileId( v, g, 0, tileId);
			}
		}
	}
	
	public void generateLabyrinth( int x, int y, int W, int H){
		ArrayList<Vector2f> C = new ArrayList<Vector2f>();
		
		fillRect( x, y, W, H, 57);
		
		C.add(new Vector2f( 
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
						C.add(new Vector2f( cX, cY - 2));
						map.setTileId( cX, cY - 2, 0, 1);
						map.setTileId( cX, cY - 1, 0, 1);
					}
					else if(d == 2 && w){
						end = true;
						C.add(new Vector2f( cX - 2, cY));
						map.setTileId(  cX - 2, cY, 0, 1);
						map.setTileId(  cX - 1, cY, 0, 1);
					}
					else if(d == 1 && s){
						end = true;
						C.add(new Vector2f( cX, cY + 2));
						map.setTileId( cX, cY + 2, 0, 1);
						map.setTileId( cX, cY + 1, 0, 1);
					}
					else if(d == 3 && e){
						end = true;
						C.add(new Vector2f( cX + 2, cY));
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
	
	public void tileCorrection(){
		boolean n, s, w, e;
		int H = map.getHeight(), W = map.getWidth();
		for(int x=0; x < W; x++){
			for(int y=0; y < H; y++){
				if(map.getTileId( x, y, 0) == 57){
					n = ((y - 1) < 0 || map.getTileId( x, y - 1, 0) == 1);
					s = ((y + 1) > H || map.getTileId( x, y + 1, 0) == 1);
					w = ((x - 1) < 0 || map.getTileId( x - 1, y, 0) == 1);
					e = ((x + 1) > W || map.getTileId( x + 1, y, 0) == 1);
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
	

	public void sliceInHalf(int id, int interator){
		Rectangle r = temRegion.get( id);
		int line;
		if(  r.getHeight() > r.getWidth()){
			if( r.getHeight() > 10 && 
					( rand.nextInt( interator) <= 2 ||
					r.getHeight() * r.getWidth() >= 196)){
				
				if( CONST.SYMMETRICROOM)
					line = (int)r.getHeight()/2;
				else
					line = (int)r.getHeight()/2 - rand.nextInt(3) + 1;
				
				int newId = temRegion.size();
				
				temRegion.add(
						new Rectangle( r.getX(),
						r.getY(),
						r.getWidth(),
						line));
				temRegion.add(
						new Rectangle( r.getX(),
						r.getY() + line - 1,
						r.getWidth(),
						r.getHeight() - line + 1));
				
				sliceInHalf( newId, interator + 1);
				sliceInHalf( newId + 1, interator + 1);
			}
			else{
				buildRegion.add( r);
			}
		}
		else{
			if( r.getWidth() > 10 &&
					(rand.nextInt( interator) <= 2 ||
					r.getHeight() * r.getWidth() >= 196)){

				if( CONST.SYMMETRICROOM)
					line = (int)r.getWidth()/2;
				else
					line = (int)r.getWidth()/2 - rand.nextInt(3) + 1;
				
				int newId = temRegion.size();
				
				temRegion.add(
						new Rectangle( r.getX(),
						r.getY(),
						line,
						r.getHeight()));
				temRegion.add(
						new Rectangle( r.getX() + line - 1,
						r.getY(),
						r.getWidth() - line + 1,
						r.getHeight()));
						
				sliceInHalf( newId, interator + 1);
				sliceInHalf( newId + 1, interator + 1);
			}
			else{
				buildRegion.add( r);
			}
		}
	}	


	public void addLoopHallway(int id){
		Rectangle r = temRegion.get( id);
		int c = (int) (r.getWidth() * 0.2 + rand.nextInt((int) Math.abs(r.getWidth() * 0.05))) ;
		int p = (int) (r.getWidth() * 0.5 - 5 - c);

//		int cy = (int) (c * 1.618);
		int cy = (int) (r.getHeight() * 0.2);
		int py = (int) (r.getHeight() * 0.5 - cy - 5);
		
		hallway.add(
				new Rectangle( r.getX() + p,
						r.getY() + py,
						10 + 2 * c,
						5));
		hallway.add(
				new Rectangle( r.getX() + p,
						r.getY() + py + 2*cy + 5 ,
						10 + 2 * c,
						5));
		hallway.add(
				new Rectangle( r.getX() + p,
						r.getY() + py,
						5,
						10 + 2 * cy));
		hallway.add(
				new Rectangle( r.getX() + p + 2*c + 5,
						r.getY() + py,
						5,
						10 + 2 * cy));

		/*** North and South side*/
		temRegion.add(
				new Rectangle( r.getX(),
				r.getY(),
				r.getWidth(),
				py + 1));
		temRegion.add(
				new Rectangle( r.getX(),
				r.getY() + r.getHeight() - py - 1,
				r.getWidth(),
				py + 1));

		/*** Center region*/
		temRegion.add(
				new Rectangle( r.getX() + p + 4,
				r.getY() + py + 4,
				2 * c + 2,
				2 * cy + 2));
		
		/*** West and East side*/
		temRegion.add(
				new Rectangle( r.getX(),
				r.getY() + py,
				p + 1,
				2 * cy + 11));
		temRegion.add(
				new Rectangle( r.getX() + p + 9 + 2 * c,
				r.getY() + py,
				r.getWidth() - p - 2*c - 9,
				2 * cy + 11));

		
		temRegion.remove(id);
	}

	public void addHallway(int id, int position){
		addHallway( id, position, temRegion.get( id).getHeight() < temRegion.get( id).getWidth());
	}
	public void addHallway(int id, int position, boolean ver){
		
		Rectangle r = temRegion.get( id);
		int line;
		
		if( ver){
			line = (int)r.getWidth() * position / 100;
			
			hallway.add(
					new Rectangle( r.getX() + line - 1,
							r.getY(),
							5,
							r.getHeight()));
			
			temRegion.add(
					new Rectangle( r.getX(),
					r.getY(),
					line,
					r.getHeight()));
			temRegion.add(
					new Rectangle( r.getX() + line + 3,
					r.getY(),
					r.getWidth() - line - 3,
					r.getHeight()));
		}
		else{
			line = (int)r.getHeight() * position / 100;
			
			hallway.add(
					new Rectangle( r.getX(),
							r.getY() + line - 1,
							r.getWidth(),
							5));
			
			temRegion.add(
					new Rectangle( r.getX(),
					r.getY(),
					r.getWidth(),
					line));
			temRegion.add(
					new Rectangle( r.getX(),
					r.getY() + line + 3,
					r.getWidth(),
					r.getHeight() - line - 3));
		}
		
		temRegion.remove(id);
		
	}
	

	public void generateBuildingFloor( int x, int y, int W, int H){
		temRegion.clear();
		buildRegion.clear();
		hallway.clear();
		rooms.clear();
		
		//drawOutline( x, y, W, H, 57);
		
		//	temRegion.add( new Rectangle( x + 4, y + 4, W - 8, H - 8));
		
		temRegion.add( new Rectangle( x, y, W, H));

		int position = 25 * rand.nextInt(3) + 25;
		
		if( rand.nextBoolean()){
			
			if( W >= 60){
				addHallway( 0, position, true);
				if( position >= 50){
					addLoopHallway( 0);
					addHallway( temRegion.size() - 1, 33, false);
					addHallway( temRegion.size() - 1, 50, false);
				}
				if( position == 50){
					addLoopHallway( 0);
					addHallway( temRegion.size() - 2, 33, false);
					addHallway( temRegion.size() - 1, 50, false);
				}
				else if( position == 25){
					addLoopHallway( 1);
					addHallway( temRegion.size() - 2, 33, false);
					addHallway( temRegion.size() - 1, 50, false);
				}
			}
			else{
				addHallway( 0, 25, true);
				addHallway( 1, 50, true);
				if( H >=50){
					addHallway( 1, 63, false);
					addHallway( 2, 50, false);
				}
				else{
					addHallway( 1, 48, false);
				}
			}
		}
		else{
			
			if( H >= 60){
				addHallway( 0, position, false);
				if( position >= 50){
					addLoopHallway( 0);
					addHallway( temRegion.size() - 4, 50, true);
				}
				if( position == 50){
					addLoopHallway( 0);
					addHallway( temRegion.size() - 5, 50, true);
				}
				else if( position == 25){
					addLoopHallway( 1);
					addHallway( temRegion.size() - 5, 50, true);
				}
			}
			else{
				addHallway( 0, 30, false);
				addHallway( 1, 50, false);
				if( W >=50){
					addHallway( 1, 63, true);
					addHallway( 2, 50, true);
				}
				else{
					addHallway( 1, 48, true);
				}
			}
			
		}
		
		int nbrRegion = temRegion.size();
		for(int l = 0; l < nbrRegion; l++){
			sliceInHalf( l, 1);
		}

		/** Add the outline of then room */
		int j = 0;
		for(Rectangle r : buildRegion){
//			System.out.println( j +
//					" x: " + r.getX() +
//					" y: " + r.getY() + 
//					" W: " + r.getWidth() +
//					" H: " + r.getHeight());
			drawOutline( r, 57);
			rooms.add( new Room( r));
			
			j++;
		}

		/** Add the doors */
		Vector2f portal = new Vector2f( 0, 0);
		
		for(Room room : rooms){
			Rectangle r = room.getRect();
				
			for( Room childRoom : rooms){
				if( r.getWidth() > 4){
					room.setDoor( CONST.SOUTH);
					if( childRoom.getRect() != r && childRoom.getRect().intersects( r)){
						Rectangle rc = childRoom.getRect();
						if(rc.getWidth() > r.getWidth()){
							portal.x = (int)(r.getX() + r.getWidth()/2);
							portal.y = (int)(r.getY() + r.getHeight()) ;
							
							if( rc.contains( portal.x, portal.y)){
								int newDoorX = (int) (r.getX() + rand.nextInt( (int) r.getWidth() - 2) + 1);
								int newDoorY = (int)(r.getY() + r.getHeight()) - 1;
								System.out.println( " r < rc");
								childRoom.setDoor( CONST.NORTH);
								if( map.getTileId( newDoorX, newDoorY - 1, 0) != 57 
										&& map.getTileId( newDoorX, newDoorY + 1, 0) != 57){
									map.setTileId( newDoorX, newDoorY, 0, 1);
								}
							}
						}
						else{
							portal.x = (int)(rc.getX() + rc.getWidth()/2);
							portal.y = (int)(rc.getY());
							
							if( r.contains( portal.x, portal.y)){
								int newDoorX = (int) (rc.getX() + rand.nextInt( (int) rc.getWidth() - 2) + 1);
								int newDoorY = (int)rc.getY();
								childRoom.setDoor( CONST.NORTH);
								if( map.getTileId( newDoorX, newDoorY - 1, 0) != 57 
										&& map.getTileId( newDoorX, newDoorY + 1, 0) != 57){
									map.setTileId( newDoorX, newDoorY, 0, 1);
								}
							}
							
						}
					}
				}
				if( r.getHeight() > 4){
					room.setDoor( CONST.EAST);
					if( childRoom.getRect() != r && childRoom.getRect().intersects( r)){
						Rectangle rc = childRoom.getRect();
						if(rc.getHeight() > r.getHeight()){
							portal.x = (int)(r.getX() + r.getWidth());
							portal.y = (int)(r.getY() + r.getHeight()/2) ;
							
							if( rc.contains( portal.x, portal.y)){
								int newDoorX = (int)(r.getX() + r.getWidth()) - 1;
								int newDoorY = (int)(r.getY() + rand.nextInt( (int) r.getHeight() - 2)) + 1;
								System.out.println( " r < rc");
								childRoom.setDoor( CONST.WEST);
								if( map.getTileId( newDoorX - 1, newDoorY, 0) != 57 
										&& map.getTileId( newDoorX + 1, newDoorY, 0) != 57){
									map.setTileId( newDoorX, newDoorY, 0, 1);
								}
							}
						}
						else{
							portal.x = (int)(rc.getX());
							portal.y = (int)(rc.getY() + rc.getHeight()/2);
							
							if( r.contains( portal.x, portal.y)){
								int newDoorX = (int)rc.getX();
								int newDoorY = (int)rc.getY() + rand.nextInt( (int) rc.getHeight() - 2) + 1;
								childRoom.setDoor( CONST.WEST);
								if( map.getTileId( newDoorX - 1, newDoorY, 0) != 57 
										&& map.getTileId( newDoorX + 1, newDoorY, 0) != 57){
									map.setTileId( newDoorX, newDoorY, 0, 1);
								}
							}
							
						}
					}
				}
			}
//			map.setTileId( 
//					(int)(r.getX() + rand.nextInt( (int) r.getWidth() - 2) + 1),
//					(int)(r.getY() + r.getHeight()) - 1,
//					0,
//					1);
//			map.setTileId( 
//					(int)(r.getX() + r.getWidth()) - 1,
//					(int)(r.getY() + rand.nextInt( (int) r.getHeight() - 2)) + 1,
//					0,
//					1);
		}
		
		drawOutline( new Rectangle( x, y, W, H), 57);
	}
	
	public TiledMap getMap(){
		return map;
	}
}
