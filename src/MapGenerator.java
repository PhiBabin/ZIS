
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
	
	public void sliceRegion(int id, boolean ver){
		Rectangle r = temRegion.get( id);
		if( ver){
			if( r.getHeight() > 16) {
				int line = rand.nextInt( (int)r.getHeight()/2 - 7 ) + 5;
				
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
				if( r.getHeight() -2 * line  + 2 > line)
					sliceRegion( temRegion.size() - 1, false);
				else{
					sliceRegion( id + 1, false);
					sliceRegion( id + 2, false);
				}
			}
			else{
			}
		}
		else{
			if( r.getWidth() > 16) {
				int line = rand.nextInt( (int)r.getWidth()/2 - 7 ) + 5;
				
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
					sliceRegion( temRegion.size() - 1, true);
				}
				else{
					sliceRegion( id + 1, true);
					sliceRegion( id + 2, true);
				}
			}
			else{
				
			}
			
		}
	}

	public void generateBuilding( int x, int y, int W, int H){
		temRegion.clear();
		buildRegion.clear();
		
		drawOutline( x, y, W, H, 57);
		
		temRegion.add( new Rectangle( x + 4, y + 4, W - 8, H - 8));

		drawOutline( temRegion.get(0), 57);

		sliceRegion( 0, false);
		
		// sliceRegion( 1);
		
		for(Rectangle r : temRegion){
			System.out.println(
					" x: " + r.getX() +
					" y: " + r.getY() + 
					" W: " + r.getWidth() +
					" H: " + r.getHeight());
			drawOutline( r, 57);
		}
		
	}
	
	public TiledMap getMap(){
		return map;
	}
}
