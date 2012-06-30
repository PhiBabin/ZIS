/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package zis;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class RessourceManager {
	public Animation player = null;
	public SpriteSheet imgPlayer, tilesetImg = null;
	
	public RessourceManager(){
		loadImage();
	}
	
	public void loadImage(){
		try {
			if( CONST.APPLET){
				imgPlayer = new SpriteSheet( new Image( Thread.currentThread().getContextClassLoader().getResourceAsStream("img/player.png"), "img/player.png", false), 10, 10);
				tilesetImg = new SpriteSheet( new Image( Thread.currentThread().getContextClassLoader().getResourceAsStream("img/buildingTileSet.png"), "img/buildingTileSet.png", false), 10, 10);
			}
			else {
				imgPlayer = new SpriteSheet( new Image("img/player.png", false), 10, 10);
				tilesetImg = new SpriteSheet( new Image("img/buildingTileSet.png", false), CONST.TILE_WIDTH, CONST.TILE_HEIGHT);
			}
			
			player = new Animation( imgPlayer, 100000);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
