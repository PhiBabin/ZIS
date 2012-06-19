package zis.map;

import org.newdawn.slick.geom.Rectangle;

import zis.CONST;

/***
 * Room of a office building
 * 
 * @author Philippe Babin
 */
public class Room {
	
	private boolean doorN = false, doorE = false, doorS = false, doorW = false;
	
	/*** Rectangle of the Room */
	private Rectangle r;
	
	/**
	 * Constructor of a office Room
	 * @param r Rectangle of this room
	 */
	public Room( Rectangle r){
		this.r = r;
	}
	
	/***
	 * Put a door in the room
	 * @param d position of the wall
	 */
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
	
	/***
	 * Is the wall containing a door.
	 * @param d Wall position
	 * @return is containing a door
	 */
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
	
	/***
	 * Get room's rectangle
	 * @return room's rectangle
	 */
	public Rectangle getRect(){
		return r;
	}
}
