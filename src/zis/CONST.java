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
	public static final int MAP_WIDTH = 300;
	public static final int MAP_HEIGHT = 300;
	public static final int TILE_WIDTH = 10;
	public static final int TILE_HEIGHT = 10;

	/** Pathfinder parameter */
	public static final int MAX_PATH_LENGTH = 1000;
	
	/** Building generator parameter */
	public static boolean SYMMETRICROOM = true;
	public static final boolean AVENUE = true;
	public static final boolean STREET = false;
	public static final int MIN_AVENUE = 30;
	public static final int MAX_AVENUE = 30;
	public static final int MAX_AVENUE_WIDTH = 12;
}
