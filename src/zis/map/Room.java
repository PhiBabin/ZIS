/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

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

	/***
	 * Get room's surface
	 * @return room's surface
	 */
	public int getSurface(){
		return (int) ( r.getWidth() * r.getHeight());
	}
}
