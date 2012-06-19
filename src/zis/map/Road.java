package zis.map;

import zis.CONST;

/***
 * Street or Avenue of a city
 * 
 * @author Philippe Babin
 */
public class Road {

	private int x, y, W, H;
	
	/*** Street or Avenue? */
	private boolean isAvenue;
	
	/*** Road name */
	private String name;
	
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
		this.x = x;
		this.y = y;
		this.isAvenue = isAvenue;
		
		if( isAvenue){
			this.W = CONST.MAX_AVENUE_WIDTH;
			this.H = l;
		}
		else{
			this.W = l;
			this.H = CONST.MAX_AVENUE_WIDTH;
		}
		
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
}
