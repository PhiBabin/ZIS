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
 * Street or Avenue of a city
 * 
 * @author Philippe Babin
 */
public class Road {
	/*** Street or Avenue? */
	private boolean isAvenue;
	
	/*** Road name */
	private String name;
	
	/*** Rectangle of the Room */
	private Rectangle r;
	
	/***
	 * Constructor of a road
	 * @param city City that contain the road
	 * @param x Position X of the Road
	 * @param y Position Y of the Road
	 * @param l Length of the Road
	 * @param isAvenue Is the road a avenue
	 * @param nbr Road number
	 */
	public Road( int x, int y, int l, boolean isAvenue, int nbr){
		if( isAvenue)
			r = new Rectangle( x, y, CONST.AVENUE_WIDTH, l);
		else
			r = new Rectangle( x, y, l, CONST.AVENUE_WIDTH);
		
		this.isAvenue = isAvenue;
		
		if( nbr > 3)
			this.name = new String( nbr + "th");
		else if( nbr == 1)
			this.name = new String( nbr + "st");
		else if( nbr == 2)
			this.name = new String( nbr + "nd");
		else if( nbr == 3)
			this.name = new String( nbr + "rd");
	}

	/***
	 * Is the road a avenue
	 * @return isAvenue
	 */
	public boolean isAvenue() {
		return isAvenue;
	}
	
	/***
	 * Get the Road's name
	 * @return Road's name
	 */
	public String getName() {
		if( isAvenue)
			return name + " Ave";
		else
			return name + " St";
			
	}

	public Rectangle getRect() {
		return r;
	}
}
