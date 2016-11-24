package org.loasoft.crazyFlappy;

import org.loasoft.crazyFlappy.screens.MainMenuScreen;
import org.loasoft.crazyFlappy.util.Constants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class crazyFlappy extends Game {
	
	public Skin skin;
	public OrthographicCamera camara;
	public SpriteBatch spriteBatch;
	public BitmapFont fuente;	
	public int monedasRecojidas;
	
	public crazyFlappy(){
		super();
	}
	
	@Override
	public void create() {
		
		camara = new OrthographicCamera();
		camara.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		camara.update();
		
		spriteBatch = new SpriteBatch();
		fuente = new BitmapFont();
		
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		fuente.dispose();

	}

	public Skin getSkin() {
		if(skin == null){
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		}
		
		return skin;
	}
	

}
