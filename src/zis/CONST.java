package zis;

public class CONST {
	/** Applet Constant */
	public static boolean APPLET = true;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	/** Direction Constant */
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	/** Map parameter */
	public static final int MAP_WIDTH = 600;
	public static final int MAP_HEIGHT = 300;
	public static final int TILE_WIDTH = 10;
	public static final int TILE_HEIGHT = 10;

	/** Pathfinder parameter */
	public static final int MAX_PATH_LENGTH = 10000;
	
	/** Building generator parameter */
	public static final int MAX_OFFICE_ROOM_DOMAIN = 100;
	public static boolean SYMMETRICROOM = true;
	public static final boolean AVENUE = true;
	public static final boolean STREET = false;
	public static final int AVENUE_WIDTH = 12;
	public static final int STREET_WIDTH = 12;
	public static final int BLOCK_WIDTH_MIN = AVENUE_WIDTH * 9;
	public static final int BLOCK_WIDTH_MAX = AVENUE_WIDTH * 15;
	public static final int BLOCK_HEIGHT_MIN = STREET_WIDTH * 3;
	public static final int BLOCK_HEIGHT_MAX = STREET_WIDTH * 5;
	
	/** Population constant */
	public static final boolean FEMALE = true;
	public static final boolean MALE = false;
}
