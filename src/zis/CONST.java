/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

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
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;

	/** Pathfinder parameter */
	public static final int MAX_PATH_LENGTH = 10000;
	
	/** Building generator parameter */
	public static final int MAX_OFFICE_ROOM_DOMAIN = 70;
	public static boolean SYMMETRICROOM = true;
	public static final boolean AVENUE = true;
	public static final boolean STREET = false;
	public static final int AVENUE_WIDTH = 13;
	public static final int STREET_WIDTH = 13;
	public static final int BLOCK_WIDTH_MIN = AVENUE_WIDTH * 9;
	public static final int BLOCK_WIDTH_MAX = AVENUE_WIDTH * 15;
	public static final int BLOCK_HEIGHT_MIN = STREET_WIDTH * 3;
	public static final int BLOCK_HEIGHT_MAX = STREET_WIDTH * 5;
	public static final int APARTMENT_WIDTH = 12;
	public static final int APARTMENT_HEIGHT = 14;
	
	/** Population constant */
	public static final boolean FEMALE = true;
	public static final boolean MALE = false;
}
