package org.loasoft.crazyFlappy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bird{
	
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 aceleracion;
	private float rotation;
	public Rectangle rect;
	Texture texture;
	
	
	public Bird(float x, float y, Texture texture){
		
		this.texture = texture;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		aceleracion = new Vector2(0, 350);
		rect = new Rectangle(position.x, position.y, 
				texture.getWidth(), texture.getHeight());
	}
	


	public void render(SpriteBatch batch, float rotation){
		batch.draw(new TextureRegion(texture), position.x, position.y,
				texture.getWidth() /2, texture.getHeight()/2, texture.getWidth(), texture.getHeight(), 1, 1, rotation);
	}
	
 
	public void update(float dt){
		velocity.add(aceleracion.cpy().mul(dt));
		
		if(velocity.y > 350){
			velocity.y = 350;
		}
		
		position.add(velocity.cpy().mul(-dt));
		
		//El bicho sube
		if(velocity.y < 0){
			rotation +=200 * dt; 
			if(rotation > 30){
				rotation = 30;
			}
		}
		
		if(isFalling()){
			rotation -=150 * dt;
			if(rotation < -90 ){
				rotation = -90;
			}
		}
		
		rect.x = position.x;
		rect.y = position.y;
		
	}
	
    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean shouldntFlap() {
        return velocity.y > 70;
    }
    
	public void jump() {
		velocity.y = -200;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public void setY(float y){
		position.y = y;
	}

	public float getRotation() {
		return rotation;
	}
	
}
	

