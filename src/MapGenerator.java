
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class MapGenerator {
	
	private TiledMap map;
	
	public MapGenerator() throws SlickException {
		generateEmptyMap();
	}
	
	public void generateEmptyMap() throws SlickException {
		map = new TiledMap( "map/empty.tmx");
	}
	
	public void generateLabyrinth( int x, int y, int W, int H){
		ArrayList<Vector2f> C = new ArrayList<Vector2f>();
		
		/*** We build a blank rectangle*/
		for(int v=x+1; v<x+W; v++){
			for(int g=y+1; g<y+H; g++){
				map.setTileId( v, g, 0, 57);
			}
		}
		
		C.add(new Vector2f( 
				x + (float)Math.floor( Math.random() * ( W /2)) * 2, 
				y + (float)Math.floor( Math.random() * ( H /2)) * 2));
		map.setTileId( (int)C.get(0).x, (int)C.get(0).y, 0, 1);
		
		boolean n, s, w, e, end;
		int d , id, cX, cY;
		
		while(C.size()!=0){
			id = C.size()-1;
			cX = (int)C.get(id ).x;
			cY = (int)C.get(id ).y;
			n = (map.getTileId( cX, cY - 2, 0) == 57 && (cY - 2)> y);
			s = (map.getTileId( cX, cY + 2, 0) == 57 && (cY + 2)< y + H);
			w = (map.getTileId( cX - 2, cY, 0) == 57 && (cX - 2)> x);
			e = (map.getTileId( cX + 2, cY, 0) == 57 && (cX + 2)< x + W);
			
			if( n || s || w || e ){

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
					n = (map.getTileId( x, y - 1, 0) == 1);
					s = (map.getTileId( x, y + 1, 0) == 1);
					w = (map.getTileId( x - 1, y, 0) == 1);
					e = (map.getTileId( x + 1, y, 0) == 1);
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
						map.setTileId(  x, y, 0, 62);
					}
					else if( n && !s && e && !w){
						map.setTileId(  x, y, 0, 69);
					}
					else if( !n && s && !e && w){
						map.setTileId(  x, y, 0, 102);
					}
					else if( !n && s && e && !w){
						map.setTileId(  x, y, 0, 109);
					}
					else if( !n && !s && !e && !w){
						map.setTileId(  x, y, 0, 53);
					}
				}
			}
		}
		
	}
	
	public TiledMap getMap(){
		return map;
	}
}
