package zis;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Path;

import zis.map.WorldMap;
import zis.util.Vector2i;


public class NPC extends Sprite {

	/** TiledSet of the mainMap */
	public static WorldMap world;
	
	/** New position of the Player */
	private ArrayList< Path> newPath = new ArrayList< Path>(); 

	/** Id of the NPC's building */
	private int idBuilding = -1;
	
	/** Id of the NPC's office */
	private int idRoom = -1;
	
	/** Name of the NPC */
	private String name = new String();
	 
	private int idStepPath = 0;
	
	public static enum State {
		NORMAL, INFECTED, ZOMBIE
	}
	
	public static enum Job {
		SCIENTIST, OFFICE_MANAGER, SOLDIER, TECHNICIAN
	}
	
	private Job job;

	private State state;
	
	private boolean gender;
	
	private final static String[] maleFirstName = new String[]{
		"Michael", "Matthew", "Joseph", "Anthony", "Ryan", "Nicholas", "Daniel", "Christopher", "Joshua", "David", "Oliver", "Jack", "Harry", "Alfie", "Charlie", "Thomas", "William", "Nathan", "Ethan", "Alexander", "Daniel", "Lucas", "Logan", "Liam", "Ryan", "Jaiden", "Zach", "Philips", "Xavier", "Charles", "Aiden", "Jackson", "Davi"
	};
	private final static String[] femaleFirstName = new String[]{
		"Emma", "Olivia", "Mya", "Maya", "Emily", "Sarah", "Isabella", "Chloe", "Alexis", "Sophia", "Lily", "Léa", "Juliette", "Alice", "Madison", "Mia", "Gabrielle", "Kayla", "Fiona", "Ashley", "Mary", "Amelia", "Jessica"
	};
	private final static String[] lastName = new String[]{
		"Smith", "Johnson", "Williams", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Jones", "Campbell", "Patel", "Wong", "Hall", "Lee", "Brown"
	};
	
	public NPC(Animation pSprite, int nX, int nY, int idRoom, int idBuilding) {
		super(pSprite, nX, nY);
		
		newPath.clear();

		//this.world = world;
		this.idRoom = idRoom;
		this.idBuilding = idBuilding;
		this.state = State.NORMAL;
		this.job = Job.SCIENTIST;
		
		generateName();
//
//		addNewPath( new Vector2i( 
//				(int)Math.floor( Math.random()*80), 
//				(int)Math.floor( Math.random()*60)));
	}
	
	public void addNewPath( Vector2i nP){
		Path path = world.getPath( p, nP);
		if( path != null){
			newPath.add( path);
		}
		
	}
	
	public boolean canIgo(int d){
		/*** If valid direction */
		if(d>3) return false;

		/*** Than we check for map collision */
		if(d == CONST.NORTH && 
				world.isSolid( (int)p.x, (int)p.y-1))
			return true;
		if(d == CONST.EAST &&
				world.isSolid( (int)p.x+1, (int)p.y))
			return true;
		if(d == CONST.SOUTH &&
				world.isSolid( (int)p.x, (int)p.y+1))
			return true;
		if(d == CONST.WEST &&
				world.isSolid( (int)p.x-1, (int)p.y))
			return true;
		else
			return false;
	}
	
	
	public void iALogic(){
		if(newPath.size()>0 && newPath.get(0).getLength() > idStepPath){
			if(newPath.get(0).getX(idStepPath) > p.x){
				move(CONST.EAST);
			}
			if(newPath.get(0).getY(idStepPath) > p.y){
				move(CONST.SOUTH);
			}
			if(newPath.get(0).getX(idStepPath) < p.x){
				move(CONST.WEST);
			}
			if(newPath.get(0).getY(idStepPath) < p.y){
				move(CONST.NORTH);
			}
				
			idStepPath++;
		}
		else{
//			newPath.clear();
//			
//			idStepPath = 0;
//			addNewPath( new Vector2i( 
//					(int)Math.floor( Math.random()*80), 
//					(int)Math.floor( Math.random()*60)));
		}
		
	}
	
	/**
	 * NPC update function
	 * @param gc GameContainer
	 * @param sb StateBasedGame
	 * @param delta Time between frame
	 * @param actionPt Turn between frame
	 */
    public void update(GameContainer gc, StateBasedGame sb, int delta){
    	iALogic();
    }
    
    /**
	 * NPC render function
	 * @param gc Game Container
	 * @param sb State Based Game
	 * @param gr Graphics
	 */
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
		if( state == State.NORMAL)
			aniSprite.setCurrentFrame( 0);
		else if( state == State.INFECTED)
			aniSprite.setCurrentFrame( 1);
		else
			aniSprite.setCurrentFrame( 2);
		
		aniSprite.draw( p.x * 10 - cam.x, p.y * 10 - cam.y);
	}

	public void generateName() {
		if( Math.random() >= 0.5){
			name += maleFirstName[(int) ( maleFirstName.length * Math.random())] + " ";
			setGender( CONST.MALE);
		}
		else{
			name += femaleFirstName[(int) ( femaleFirstName.length * Math.random())] + " ";
			setGender( CONST.FEMALE);
		}
		name += lastName[(int) ( lastName.length * Math.random())];
	}

	public String getName() {
		return name;
	}
	
	public Job getJob() {
		return job;
	}

	public State getState() {
		return state;
	}

	public boolean getGender() {
		return gender;
	}

	public int getIdBuilding() {
		return idBuilding;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}
}
