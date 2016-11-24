package org.loasoft.crazyFlappy.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Crow extends Character {
	private Animation animation;;


	public Crow(Vector2 position, float speed, Texture texture) {


		super(position, speed, texture);

		//cargar la imagen

	}

	@Override
	public void update(float dt) {
		move(new Vector2(-dt * speed, 0));
		//friendFrame = friendAnimation.getKeyFrame(stateTime, true);
	}

}
