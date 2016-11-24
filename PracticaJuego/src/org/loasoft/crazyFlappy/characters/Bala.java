package org.loasoft.crazyFlappy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bala extends Character{
	
	

	public Bala(Vector2 position, float speed, Texture texture) {
		super(position, speed, texture);

	}

	@Override
	public void update(float dt) {
		move(new Vector2(-dt * speed, 0));
	}



	
}
