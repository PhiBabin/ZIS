package zis.map;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import zis.util.Rand;

/***
 * Randomly generate an Apartment
 * 
 * @author Philippe Babin
 */
public class Apartment {
	private int x, y, W, H;

	/*** Randomizer */
	private Rand rand;
	
	/*** City that contain the Apartment */
	private City city;

	/*** Stack of possible rooms */
	private ArrayList<Rectangle> temRegion = new ArrayList<Rectangle>();

	/*** Stack of generated rooms */
	private ArrayList<Rectangle> buildRegion = new ArrayList<Rectangle>();

	/*** List of the sleeping rooms */
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	/*** Name of the Apartment */
	private String name = "Apartments at good price";
	
	/***
	 * Constructor of an Apartment
	 * @param city Reference to the City
	 * @param seed Seed of the Apartment
	 * @param r Rectangle of the Apartment
	 */
	public Apartment( City city, int seed, Rectangle r){
		this.city = city;
		this.x = (int) r.getX();
		this.y = (int) r.getY();
		this.W = (int) r.getWidth();
		this.H = (int) r.getHeight();
		rand = new Rand( seed);
		
		generateApartment();
	}
	/***
	 * Constructor of a office Apartment
	 * @param city Reference to the City
	 * @param seed Seed of the Apartment
	 * @param x Position X of the Apartment
	 * @param y Position Y of the Apartment
	 * @param W Width of the Apartment
	 * @param H Height of the Apartment
	 */
	public Apartment( City city, int seed, int x, int y, int W, int H){
		this.city = city;
		this.x = x;
		this.y = y;
		this.W = W;
		this.H = H;
		rand = new Rand( seed);

		generateApartment();
	}
	
	/***
	 * Procedurally generate a Apartment in the target region
	 */
	public void generateApartment(){
		generateFloor();
	}
	
	/***
	 * Procedurally generate a floor
	 */
	public void generateFloor(){
		temRegion.clear();
		buildRegion.clear();
		rooms.clear();

		temRegion.add( new Rectangle( x, y, W, H));
		city.drawOutline( temRegion.get( 0), 57);
		
		int APART_W = 14,  APART_H = 12;
		
		int offsetX = (int) ( W % APART_W * 0.5);
		int offsetY = (int) ( H % APART_H * 0.5);
		
		int nbrApartWidth = (W - 2 * offsetX) / APART_W;
		int nbrApartHeight = (H - 2 * offsetY) / APART_H;
		Rectangle newChamber;
		for( int g = 0; g < nbrApartWidth; g++){
			for( int v = 0; v < nbrApartHeight; v++){
				newChamber = new Rectangle(
						x + offsetX + g * (APART_W),
						y + offsetY + v * (APART_H),
						APART_W + 1, APART_H + 1);
				generateChamber( newChamber, g != 0, v != 0, g != 0 && g != nbrApartWidth - 1);
				//buildRegion.add( newChamber);
			}
			
		}
		System.out.println( "off : " + offsetX );
		
		
		/** Add the outline of then room */
		int j = 0;
		for(Rectangle r : buildRegion){
			city.drawOutline( r, 57);
			rooms.add( new Room( r));
			
			j++;
		}
	}
	
	public void generateChamber( Rectangle r, boolean miroirX, boolean miroirY, boolean center){
		ArrayList<Rectangle> newRooms = new ArrayList<Rectangle>();
		
		if( center){
			newRooms.add( new Rectangle( 0, 4, 7, 7));
			newRooms.add( new Rectangle( 6, 0, 5, 6));
			newRooms.add( new Rectangle( 0, 0, 5, 5));
			newRooms.add( new Rectangle( 8, 7, 4, 4));
			newRooms.add( new Rectangle( 10, 1, 5, 7));
			newRooms.add( new Rectangle( 8, 7, 4, 4));
			newRooms.add( new Rectangle( 11, 7, 4, 4));
		}
		else{
			newRooms.add( new Rectangle( 0, 2, 7, 7));
			newRooms.add( new Rectangle( 6, 0, 5, 6));
			newRooms.add( new Rectangle( 2, 8, 5, 5));
			newRooms.add( new Rectangle( 8, 7, 4, 4));
			newRooms.add( new Rectangle( 10, 1, 5, 7));
			newRooms.add( new Rectangle( 8, 7, 4, 4));
			newRooms.add( new Rectangle( 11, 7, 4, 4));
		}
		
		Rectangle theRoom;
		
		if( !miroirX && !miroirY){
			for(Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						ro.getX() + r.getX(),
						ro.getY() + r.getY(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
			}
		}
		if( miroirX && !miroirY){
			for(Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						r.getX() - ro.getX() + r.getWidth() - ro.getWidth(),
						ro.getY() + r.getY(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
			}
		}
		if( !miroirX && miroirY){
			for(Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						ro.getX() + r.getX(),
						r.getY() - ro.getY() + r.getHeight() - ro.getHeight(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
			}
		}
		if( miroirX && miroirY){
			for(Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						r.getX() - ro.getX() + r.getWidth() - ro.getWidth(),
						r.getY() - ro.getY() + r.getHeight() - ro.getHeight(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
			}
		}
	}
	
	/***
	 * Return the Rooms of the Apartment
	 * @return Apartment's Rooms
	 */
	public ArrayList< Room> getRooms() {
		return rooms;
	}
}
