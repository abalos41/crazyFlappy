package org.loasoft.crazyFlappy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by arN on 05/03/2016.
 */
public class Tubo extends Character{

    public Tubo(Vector2 position, float speed, Texture texture) {

        super(position, speed, texture);

    }

    @Override
    public void update(float dt) {
        move(new Vector2(-dt * speed, 0));
        //friendFrame = friendAnimation.getKeyFrame(stateTime, true);
    }
}
