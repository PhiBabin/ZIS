
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class MapGenerator {
	
	private TiledMap map;
	
	private Random rand = new Random();
	
	public ArrayList<Rectangle> temRegion = new ArrayList<Rectangle>();
	
	public ArrayList<Rectangle> buildRegion = new ArrayList<Rectangle>();
	
	public ArrayList<Rectangle> hallway = new ArrayList<Rectangle>();
	
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
		if(  r.getHeight() > r.getWidth()){
			if( r.getHeight() > 10 && 
					( rand.nextInt( interator) <= 2 ||
					r.getHeight() * r.getWidth() >= 196)){
				
				int line = (int)r.getHeight()/2 ;//- rand.nextInt(3) + 1;
				
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
				System.out.println( id + " - " + (line > r.getWidth()) + "w: " + r.getWidth() + " h: " + line);
				
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

				int line = (int)r.getWidth()/2 ;//- rand.nextInt(3) + 1;
				
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
				System.out.println( interator + " - " + (line < r.getHeight()) + "w: " + r.getWidth() + " h: " + line);
						
				sliceInHalf( newId, interator + 1);
				sliceInHalf( newId + 1, interator + 1);
			}
			else{
				buildRegion.add( r);
			}
		}
	}
	
	public void sliceRegion(int id, boolean ver){
		Rectangle r = temRegion.get( id);
		if( ver){
			if( Math.random() > CONST.BISLICE && r.getHeight() > 16 /*&& r.getWidth() > 4*/){
				int line = (int)r.getHeight()/2 + rand.nextInt(6) - 3;
				
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
				sliceRegion( newId, line > r.getWidth());
				sliceRegion( newId + 1, r.getHeight() - line + 1 > r.getWidth());
			}
			else if( r.getHeight() > 16 /*&& r.getWidth() > 4*/) {
				int line = rand.nextInt( (int)r.getHeight()/2 - 7 ) + 5;

				int newId = temRegion.size();
				temRegion.add(
						new Rectangle( r.getX(),
						r.getY(),
						r.getWidth(),
						line));
				temRegion.add(
						new Rectangle( r.getX(),
						r.getY() + r.getHeight() - line,
						r.getWidth(),
						line));
				temRegion.add(
						new Rectangle( r.getX(),
						r.getY() + line - 1,
						r.getWidth(),
						r.getHeight() -2 * line  + 2));
				if( r.getHeight() -2 * line  + 2 > line){
					sliceRegion( newId + 2, r.getHeight() -2 * line  + 2 > r.getWidth());

					roomBuildingValidation(  newId);
					roomBuildingValidation(  newId + 1);
				}
				else{
					sliceRegion( newId, r.getWidth() < line);
					sliceRegion( newId + 1, r.getWidth() < line);

					roomBuildingValidation(  newId + 2);
				}
			}
			else{
				buildRegion.add( r);
			}
		}
		else{
			if( Math.random() > CONST.BISLICE && r.getWidth() > 16 /*&& r.getHeight() > 4*/){

				int line = (int)r.getWidth()/2 + rand.nextInt(6) - 3;
				
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
				sliceRegion( newId, line < r.getHeight());
				sliceRegion( newId + 1, r.getWidth() - line + 1 < r.getHeight());
			}
			else if( r.getWidth() > 16 && r.getHeight() > 4) {
				int line = rand.nextInt( (int)r.getWidth() / 2 - 7 ) + 5;
				int newId = temRegion.size();
				
				temRegion.add(
						new Rectangle(r.getX(),
						r.getY(),
						line,
						r.getHeight()));
				temRegion.add(
						new Rectangle( r.getX() + r.getWidth() - line,
						r.getY(),
						line,
						r.getHeight()));
				temRegion.add(
						new Rectangle( r.getX() + line - 1,
						r.getY(),
						r.getWidth() -2 * line  + 2,
						r.getHeight()));
				if( r.getWidth() -2 * line  + 2 > line){
					sliceRegion( newId + 2, r.getWidth() -2 * line  + 2 < r.getHeight());
					
					roomBuildingValidation(  newId);
					roomBuildingValidation(  newId + 1);
				}
				else{
					sliceRegion( newId, r.getHeight() > line);
					sliceRegion( newId + 1, r.getHeight() > line);
					
					roomBuildingValidation(  newId + 2);
				}
			}
			else{
				buildRegion.add( r);
			}
			
		}
	}


	public void addLoopHallway(int id){
		Rectangle r = temRegion.get( id);
		int c = rand.nextInt(3) + 6;
		int p = (int) (r.getWidth() * 0.5 - 5 - c);

		int cy = (int) (c * 1.618);
		int py = (int) (r.getHeight()*0.5 - cy - 5);
		
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
		System.out.println( " c" + c + " p" + p);

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
				2 * cy + 10));
		temRegion.add(
				new Rectangle( r.getX() + p + 9 + 2 * c,
				r.getY() + py,
				p + 1,
				2 * cy + 10));

		
		temRegion.remove(id);
	}

	public void addHallway(int id){
		addHallway( id, temRegion.get( id).getHeight() < temRegion.get( id).getWidth());
	}
	public void addHallway(int id, boolean ver){
		
		Rectangle r = temRegion.get( id);
		int line;
		
		if( ver){
			if( (float)5 / (float)r.getWidth() > 0.2)
				line = (int)r.getWidth()/2 + rand.nextInt(6) - 3;
			else
				line = (int)r.getWidth()*( rand.nextInt(2) + 1)/4 + rand.nextInt(6) - 3;
			
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
			if( 5/r.getHeight() > 0.2)
				line = (int)r.getHeight()/2 + rand.nextInt(6) - 3;
			else
				line = (int)r.getHeight()*( rand.nextInt(2) + 1)/4 + rand.nextInt(6) - 3;
			
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
	
	public void roomBuildingValidation( int id){
		 Rectangle r = temRegion.get( id);
		 if( r.getHeight() < r.getWidth() && r.getHeight() * r.getWidth() > 100){
			 sliceRegion( id, true);
			 
		 }
		 else if( r.getHeight() >= r.getWidth() && r.getHeight() * r.getWidth() > 100){
			 sliceRegion( id, false);
		 }
		 else{
			 buildRegion.add( r);
		 }
	}

	public void generateBuildingFloor( int x, int y, int W, int H){
		temRegion.clear();
		buildRegion.clear();
		hallway.clear();
		
		//drawOutline( x, y, W, H, 57);
		
		//	temRegion.add( new Rectangle( x + 4, y + 4, W - 8, H - 8));
		temRegion.add( new Rectangle( x, y, W, H));
		
		if( (float)W / 5.f <= 2){
			if( H == W)
				addHallway( 0, rand.nextBoolean());
			else
				addHallway( 0, rand.nextBoolean());
			
			sliceInHalf( 0, 1);
			sliceInHalf( 1, 1);
		}
		else if( (float)W / 5.f <= 10 && false){
			System.out.println(" Rapport: " + ((float)W / 5.f));
			if( H == W){
				addHallway( 0, rand.nextBoolean());
				addHallway( 0);
			}
			else{
				addHallway( 0, rand.nextBoolean());
				addHallway( 0);
			}
			sliceInHalf( 0, 1);
			sliceInHalf( 1, 1);
			sliceInHalf( 2, 1);
		}
		else{
			addLoopHallway( 0);
			addHallway( temRegion.size() - 2, false);
			sliceInHalf( 0, 1);
			sliceInHalf( 1, 1);
			sliceInHalf( 2, 1);
			sliceInHalf( 3, 1);
			sliceInHalf( 4, 1);
			sliceInHalf( 5, 1);
		}
		
		//hallway.add( new Rectangle( x + W - 1, y, 5, H));
		
		//hallway.add( new Rectangle( x + W + 3, y + H / 2, 40, 5));
		
		
		// addHallway( 1, false);
		
		///temRegion.add( new Rectangle( x + W + 3, y, 40, H / 2 + 1));
		
		///temRegion.add( new Rectangle( x + W + 3, y + H / 2 + 4, 40, H / 2 - 4));

		//drawOutline( temRegion.get(0), 57);
		

		 //sliceRegion( 2, true);
		
		///sliceRegion( 1, W < H);
		
		///sliceRegion( 2, W < H);
		int j = 1;
		for(Rectangle r : buildRegion){
			System.out.println( j +
					" x: " + r.getX() +
					" y: " + r.getY() + 
					" W: " + r.getWidth() +
					" H: " + r.getHeight());
			drawOutline( r, 57);
			
			j++;
		}
		
	}
	
	public TiledMap getMap(){
		return map;
	}
}
