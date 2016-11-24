package org.loasoft.crazyFlappy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Avion extends Character {
	
	public Avion(Vector2 position, float speed, Texture texture) {
		super(position, speed, texture);
		
		this.position = position;
	}

	@Override
	public void update(float dt) {
		move(new Vector2(-dt * speed, 0));

	}

	
	
}
