package zis.util;

public class Vector2i {
	
	public int x = 0;
	
	public int y = 0;
	
	public Vector2i( int x, int y){
		this.x = x;
		this.y = y;
	}
	public Vector2i( float x, float y){
		set( x, y);
	}
	
	public Vector2i( Vector2i vec){
		x = vec.x;
		y = vec.y;
		
	}
	
	public void set( int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void set( float x, float y){
		this.x = (int) x;
		this.y = (int) y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY( int y) {
		this.y = y;
	}

	public void setX( float x) {
		this.x = (int)x;
	}
	
	public void setY( float y) {
		this.y = (int)y;
	}
}
