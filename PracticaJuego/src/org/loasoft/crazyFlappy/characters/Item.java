package org.loasoft.crazyFlappy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Item extends Character{
	
	public int score;

	public Item(Vector2 position, float speed, Texture texture, int score) {
		super(position, speed, texture);

		this.score = score;
	}


	@Override
	public void update(float dt) {
		move(new Vector2(-dt * speed, 0));
	}
	
}
