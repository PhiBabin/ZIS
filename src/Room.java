import org.newdawn.slick.geom.Rectangle;


public class Room {
	private boolean doorN = false, doorE = false, doorS = false, doorW = false;
	private Rectangle r;
	
	public Room( Rectangle r){
		this.r = r;
	}
	
	public void setDoor( int d){
		switch( d){
			case CONST.NORTH:
				doorN = true;
				break;
			case CONST.EAST:
				doorE = true;
				break;
			case CONST.SOUTH:
				doorS = true;
				break;
			case CONST.WEST:
				doorW = true;
				break;
			default:
				return;
		}
	}
	
	public boolean getDoor( int d){
		switch( d){
			case CONST.NORTH:
				return doorN;
			case CONST.EAST:
				return doorE;
			case CONST.SOUTH:
				return doorS;
			case CONST.WEST:
				return doorW;
			default:
				return false;
		}
	}
	
	public Rectangle getRect(){
		return r;
	}
}
