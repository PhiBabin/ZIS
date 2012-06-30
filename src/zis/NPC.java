/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package zis;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Path;

import zis.map.WorldMap;
import zis.util.Rand;
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
	
	/** Id of the NPC's Apartment */
	private int idApart = -1;

	/** Id of the NPC's Apartment Room */
	private int idApartRoom = -1;
	
	/** Name of the NPC */
	private String name = new String();
	 
	private int idStepPath = 0;
	
	private int infectionTime = 0;
	
	private Job job;

	private State state;
	
	private boolean gender;
	
	public static enum State {
		NORMAL, INFECTED, ZOMBIE, IMMUNE
	}
	
	public static enum Job {
		SCIENTIST, OFFICE_MANAGER, POLICE, TECHNICIAN
	}
	
	private final static String[] maleFirstName = new String[]{
		"Michael", "Matthew", "Joseph", "Anthony", "Ryan", "Nicholas", "Daniel", "Christopher", "Joshua", "David", "Oliver", "Jack", "Harry", "Alfie", "Charlie", "Thomas", "William", "Nathan", "Ethan", "Alexander", "Daniel", "Lucas", "Logan", "Liam", "Ryan", "Jaiden", "Zach", "Philips", "Xavier", "Charles", "Aiden", "Jackson", "Davi", "Will"
	};
	private final static String[] femaleFirstName = new String[]{
		"Emma", "Olivia", "Mya", "Maya", "Emily", "Sarah", "Isabella", "Chloe", "Alexis", "Sophia", "Lily", "Léa", "Juliette", "Alice", "Madison", "Mia", "Gabrielle", "Kayla", "Fiona", "Ashley", "Mary", "Amelia", "Jessica"
	};
	private final static String[] lastName = new String[]{
		"Smith", "Johnson", "Williams", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Jones", "Campbell", "Patel", "Wong", "Hall", "Lee", "Brown", "Connor"
	};
	
	public NPC(Animation pSprite, int nX, int nY, int idRoom, int idBuilding, int idApart, int idApartRoom) {
		super(pSprite, nX, nY);
		
		newPath.clear();

		this.idRoom = idRoom;
		this.idApart = idApart;
		this.idApartRoom = idApartRoom;
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
		if(d == CONST.NORTH && !world.isSolid( (int)p.x, (int)p.y-1))
			return true;
		if(d == CONST.EAST && !world.isSolid( (int)p.x+1, (int)p.y))
			return true;
		if(d == CONST.SOUTH && !world.isSolid( (int)p.x, (int)p.y+1))
			return true;
		if(d == CONST.WEST && !world.isSolid( (int)p.x-1, (int)p.y))
			return true;
		else
			return false;
	}
	
	
	public void iALogic(){
		Rand rand = new Rand();
		int d = rand.nextInt( CONST.NORTH, CONST.WEST);
		if( canIgo( d)) 
			move( d);
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
    	if( getState() == State.INFECTED)
    		infectionTime++;
    	
    	if( infectionTime >= 1000)
    		setState( State.ZOMBIE);
    }
    
    /**
	 * NPC render function
	 * @param gc Game Container
	 * @param sb State Based Game
	 * @param gr Graphics
	 */
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
		if( state == State.NORMAL || state == State.IMMUNE)
			aniSprite.setCurrentFrame( 0);
		if( state == State.INFECTED)
			aniSprite.setCurrentFrame( 1);
		if( state == State.ZOMBIE)
			aniSprite.setCurrentFrame( 2);
		
		aniSprite.draw( p.x * CONST.TILE_WIDTH - cam.x, p.y * CONST.TILE_HEIGHT - cam.y);
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
	
	public String toString(){
		String out = new String();
		
		if( getGender() == CONST.FEMALE)
			out = getName() + " (F)";
		else
			out = getName() + " (M)";
		out += "\nWork: " + getIdBuilding() + "-" + getIdRoom() + " Live: " + getIdApart() + "-" + getIdApartRoom();
		out += "\nState: " + getState();
		return out;
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
	
	public int getIdApart() {
		return idApart;
	}

	public int getIdApartRoom() {
		return idApartRoom;
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
