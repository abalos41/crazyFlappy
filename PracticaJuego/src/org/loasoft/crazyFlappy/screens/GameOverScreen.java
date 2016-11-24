package org.loasoft.crazyFlappy.screens;

import org.loasoft.crazyFlappy.crazyFlappy;
import org.loasoft.crazyFlappy.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class GameOverScreen implements Screen{
	
	final crazyFlappy juego;
	
	Stage menu;
	Table tablaMenu;
	TextField tfNombre;
	Texture fondo;
	Sound sonidoPerder;
	
	
	OrthographicCamera camara;
	
	
	public GameOverScreen(crazyFlappy juego) {
		this.juego = juego;
		
		camara = new OrthographicCamera();
		camara.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		fondo = new Texture(Gdx.files.internal("data/gameover.png"));
		sonidoPerder = Gdx.audio.newSound(Gdx.files.internal("data/crying.mp3"));

	}

	@Override
	public void render(float dt) {

		Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camara.update();
		juego.spriteBatch.setProjectionMatrix(camara.combined);
	
		juego.spriteBatch.begin();
		juego.spriteBatch.draw(fondo, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		juego.fuente.draw(juego.spriteBatch, "Has hecho llorar al pobre hombre de lo mal@ que eres", 100, 150);
		juego.fuente.draw(juego.spriteBatch, "Tu puntuacion: " + juego.monedasRecojidas, 100, 130);
		juego.fuente.draw(juego.spriteBatch, "Si quieres volver al menu principal, pulsa 'N'", 100, 110);
		juego.fuente.draw(juego.spriteBatch, "Pulsa 'ESCAPE' para SALIR", 100, 90);

		juego.spriteBatch.end();
		


		if (Gdx.input.isKeyPressed(Keys.N)) {
				
			juego.monedasRecojidas = 0;
			juego.setScreen(new MainMenuScreen(juego));

		}
	
		
		else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			dispose();
			System.exit(0);
		}
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		sonidoPerder.play();
		sonidoPerder.setLooping(-80, true);
	}

	@Override
	public void hide() {
		sonidoPerder.stop();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		juego.dispose();
	}

	
}
