package zis.util;

import java.util.Random;

public class Rand extends Random{
	
	public Rand(){
	}
	
	public Rand( int seed){
		setSeed( seed);
	}
	
	public int nextInt( int min, int max){
		return nextInt (max - min + 1) + min;
	}
}
