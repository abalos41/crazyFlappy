package org.loasoft.crazyFlappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import org.loasoft.crazyFlappy.characters.*;
import org.loasoft.crazyFlappy.crazyFlappy;
import org.loasoft.crazyFlappy.managers.ManagerSound;
import org.loasoft.crazyFlappy.util.Constants;

import java.util.Iterator;

public class GameScreen2 implements Screen, InputProcessor{

	//Arrays
	Array<Item> items;
	Array<Crow> crows;
	Array<Avion> aviones;
	Array<Bala> balas;
	Array<Tubo> tubos;

	//Objetos
	Bird bird;
	crazyFlappy juego;
	Sprite spriteSuelo;
	Coin coin;
	Music sonidoFondo;
	Sound sonidoMoneda;
	Sound sonidoPausa;
	Sound sonidoBum;
	Sound sonidoCuervo;
	Sound sonidoBala;

	//Texturas
	Texture fondo;
	Texture spriteCesped;
	OrthographicCamera camara;

	private TextureAtlas crowAtlas;
	private Animation crowanimation;
	private float timePassed = 0;

	//Variables
	long lastItem;
	long lastCrow;
	long lastAvion;
	long lastTubo;
	int contadorVidas = 3;
	float scrollTimer = 0.0f;
	int monedasRecogidas;
	float tiempoJuego = 0;
	int nivel = 2;
	float velocidadBichos = 10f;
	float velocidadBala = 16f;
	float velocidadTubo = 9f;
	int velocidadSuelo = 5;

	//boolean
	boolean pausa = false;

	public GameScreen2(crazyFlappy juego) {
		 
		this.juego = juego;
			
		fondo = new Texture(Gdx.files.internal("data/fondo2.jpg"));
		spriteCesped = new Texture(Gdx.files.internal("data/lava.png"));
		sonidoMoneda = Gdx.audio.newSound(Gdx.files.internal("data/moneda.mp3"));
		sonidoPausa = Gdx.audio.newSound(Gdx.files.internal("data/pausa.mp3"));
		sonidoBum = Gdx.audio.newSound(Gdx.files.internal("data/bum.mp3"));
		sonidoCuervo = Gdx.audio.newSound(Gdx.files.internal("data/charizard.mp3"));
		sonidoCuervo.setVolume(7, 7);
		sonidoBala = Gdx.audio.newSound(Gdx.files.internal("data/impactobala.mp3"));
		
		
		spriteCesped.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		spriteSuelo = new Sprite(spriteCesped, 0, 0, 64, 64);
		spriteSuelo.setSize(Constants.SCREEN_WIDTH, 64);
		bird = new Bird(Constants.SCREEN_WIDTH /2, Constants.SCREEN_HEIGHT / 2, 
		 new Texture(Gdx.files.internal("data/flappy.png")));


		
			

		//Generar Monedas
		items = new Array<Item>();
		generarMonedas();
		
		crows = new Array<Crow>();
		generarEnemigos();

		aviones = new Array<Avion>();
		balas = new Array<Bala>();
		generarAvion();

		tubos = new Array<Tubo>();
		generarTubos();
			
		camara = new OrthographicCamera();
		camara.setToOrtho(false, 800, 600);
		
		Gdx.input.setInputProcessor(this);
	
	}

	public void render(float dt){
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camara.update();

		//juego.spriteBatch.begin();
		//timePassed += Gdx.graphics.getDeltaTime();
		//juego.spriteBatch.draw(crowanimation.getKeyFrame(timePassed, true), 300, 500);
		//juego.spriteBatch.end();
		
		if(!pausa){
			
			actualizar(dt);

		}
		dibujar();
		
	}

	private void playCurrentLevelMusic() {
		// Musica de fondo durante el juego
		if (ManagerSound.isSoundEnabled()) {
			sonidoFondo = Gdx.audio.newMusic(Gdx.files.internal("data/touch.mp3"));
			sonidoFondo.setVolume(0.20f);
			sonidoFondo.setLooping(true);

			sonidoFondo.play();
		}
	}

	private void disableCurrentLevelMusic() {
		// Musica de fondo durante el juego
		if (ManagerSound.isSoundDisabled()) {
			sonidoFondo = Gdx.audio.newMusic(Gdx.files.internal("data/touch.mp3"));
			sonidoFondo.setLooping(true);
		}
		sonidoFondo.stop();
	}

	
	private void dibujar(){
		
		
		scrollTimer += Gdx.graphics.getDeltaTime();
		if(scrollTimer>1.0f)
			scrollTimer = 0.0f;
		
		spriteSuelo.setU(scrollTimer);
		spriteSuelo.setU2(scrollTimer+ velocidadSuelo);
		
		if(tiempoJuego > 30){
			velocidadSuelo = 2;
		}
		
		
		//Lo que se va a pintar en la pantalla
		juego.spriteBatch.begin();
		
		juego.spriteBatch.draw(fondo, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		spriteSuelo.draw(juego.spriteBatch);
		

		bird.render(juego.spriteBatch, bird.getRotation());
			
		
		juego.fuente.draw(juego.spriteBatch, "Tiempo: " + (int) tiempoJuego, Constants.SCREEN_WIDTH -100, 
				Constants.SCREEN_HEIGHT -30);

		
		juego.fuente.draw(juego.spriteBatch, "Score : " + juego.monedasRecojidas , Constants.SCREEN_WIDTH - 100, 
				Constants.SCREEN_HEIGHT - 50);
		
		juego.fuente.draw(juego.spriteBatch, "Vidas : " + contadorVidas, Constants.SCREEN_WIDTH -100, Constants.SCREEN_HEIGHT-70);
		
		juego.fuente.draw(juego.spriteBatch, "Level :" + nivel,  Constants.SCREEN_WIDTH - 100, Constants.SCREEN_HEIGHT -90);
		
		if(pausa){
			juego.fuente.draw(juego.spriteBatch, "PAUSA", Constants.SCREEN_WIDTH/2 , Constants.SCREEN_HEIGHT/2);
			scrollTimer = 0;
		}

	
		for(Item item : items){
			item.render(juego.spriteBatch);
		}
		
		
		for(Crow crow : crows){
			crow.render(juego.spriteBatch);
		}

		for(Tubo tubo : tubos){
			tubo.render(juego.spriteBatch);
		}
		
		if(tiempoJuego > 15){
			
		nivel=4;
			
		for(Avion avion : aviones){
			
			avion.render(juego.spriteBatch);
			
			}
		
		for(Bala bala : balas){
			
			bala.render(juego.spriteBatch);

		}
		
		}

		juego.spriteBatch.end();
	}
	
	public void actualizar(float dt){
		
		bird.update(dt);

		//Actualiza las monedas
		for(Item item: items){
			item.update(dt);
		}
		
		if (TimeUtils.millis() - lastItem > 2000){
			generarMonedas();
			
			lastItem = TimeUtils.millis();

		}
		
		if(tiempoJuego > 15){
		//Actualizar Avion
		for(Avion avion : aviones){
			avion.update(dt);
			
		}
		for(Bala bala : balas){
			bala.update(dt);

		}
		
		if (TimeUtils.millis() - lastAvion > 4000){
			generarAvion();
			sonidoBum.play();

			lastAvion = TimeUtils.millis();

			}
		}
		
		//Actualizar Crow
		
		for(Crow crow: crows){
			crow.update(dt);
		}
		
		if(TimeUtils.millis() - lastCrow > 4000){
			generarEnemigos();
			
			lastCrow = TimeUtils.millis();
		}

		//Tubos

		for(Tubo tubo : tubos){
			tubo.update(dt);
		}

		if (TimeUtils.millis() - lastTubo > 4000){
			generarTubos();

			lastTubo = TimeUtils.millis();
		}

		//Comprobar colisiones Monedas
		
		Iterator<Item> iter = items.iterator();
		while(iter.hasNext()){
			Item item = iter.next();
			item.move(new Vector2(-dt ,0));
		 
			if(item.position.x +64 < 0){

				iter.remove();

			}
			
			if(item.rect.overlaps(bird.rect)){
				iter.remove();
				sonidoMoneda.play();

				juego.monedasRecojidas += item.score;
			

			}
		}
	
		//Comprobar colisiones crows
		Iterator<Crow> iterCrow = crows.iterator();
		while(iterCrow.hasNext()){
			Crow crow = iterCrow.next();
			
			crow.move(new Vector2(-dt, 0));
			
			if(crow.position.x +64 < 0){
				iterCrow.remove();
			}
			
			if(crow.rect.overlaps(bird.rect)){
				iterCrow.remove();
				sonidoCuervo.play();
				
				contadorVidas --;
			}
		}

		Iterator<Tubo> iterTubo = tubos.iterator();
		while(iterTubo.hasNext()){
			Tubo tubo = iterTubo.next();

			tubo.move(new Vector2(-dt, 0));

			if (tubo.position.x +64 < 0){
				iterTubo.remove();
			}

			if(tubo.rect.overlaps(bird.rect)){
				Timer.schedule(new Task() {
					@Override
					public void run() {
						dispose();
						juego.setScreen(new GameOverScreen(juego));
					}

				}, 0);

			}
		}
		
		
		//Iterator aviones
		if(tiempoJuego > 15){
		Iterator<Avion> iterAvion = aviones.iterator();
		while(iterAvion.hasNext()){
			Avion avion = iterAvion.next();
			
			avion.move(new Vector2(-dt,0));

			
			if(avion.position.x +64 < 0){
				iterAvion.remove();
			}
			
			if(avion.rect.overlaps(bird.rect)){
				iterAvion.remove();
			}
		}
		
		Iterator<Bala> iterBala = balas.iterator();
		while(iterBala.hasNext()){
			
			Bala bala = iterBala.next();
			
			bala.move(new Vector2(-dt,0));
			
			if(bala.position.x + 64 < 0){
				iterBala.remove();
				sonidoBala.play();
			}
			
			if(bala.rect.overlaps(bird.rect)){
				iterBala.remove();
				
				contadorVidas --;
			}
				
			
			}
		
		}
	
		//Pantalla GameOverScreen si cae al suelo espera un segundo luego
		
		if(bird.getY() < 64){
	        bird.setY(64);

			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			        dispose();
					juego.setScreen(new GameOverScreen(juego));
			    }

			}, 1);
		}
		
		
		//Actualiza la velocidad de bala aviones monedas cuando pasa al siguiente level
		if(tiempoJuego > 15){
			velocidadBala = 26f;
			velocidadBichos = 20f;
			velocidadTubo = 14f;
		}

		//Cuando pasa de 60 segundos has ganado
		
		if(tiempoJuego > 60){
			dispose();
			juego.setScreen(new GameScreen2Win(juego));
		}
		
		tiempoJuego += Gdx.graphics.getDeltaTime();
		
		if(contadorVidas <= 0){
			dispose();
			juego.setScreen(new GameOverScreen(juego));
		}
		
		
		if(bird.getY() >= 590){
			bird.setY(590);
		}


	}
	
	public void generarMonedas(){
		
		Item item = null;
		int y = MathUtils.random(64, Constants.SCREEN_HEIGHT -64);
		
	
		item = new Coin(new Vector2(Constants.SCREEN_WIDTH, y), 
		velocidadBichos, new Texture(Gdx.files.internal("data/coin.png")), 1);
			
		items.add(item);
		
	}
	
	public void generarEnemigos(){
		Crow crow;

		//crowAtlas = new TextureAtlas(Gdx.files.internal("data/crowtmx.atlas"));
		//crowanimation = new Animation(1/2f, crowAtlas.getRegions());







		int y = MathUtils.random(64, Constants.SCREEN_HEIGHT -64);

		crow = new Crow(new Vector2(Constants.SCREEN_WIDTH, y), velocidadBichos,
				new Texture(Gdx.files.internal("data/charizard.png")));

		crows.add(crow);
		
	}

	public void generarTubos(){
		Tubo tubo;

		int x = MathUtils.random(1, Constants.SCREEN_WIDTH -64);

		tubo = new Tubo(new Vector2(Constants.SCREEN_WIDTH, x), velocidadTubo,
				new Texture(Gdx.files.internal("data/tubo.png")));

		tubos.add(tubo);
	}
	
	public void generarAvion(){
		Avion avion;
		Bala bala;
	
		int y = MathUtils.random(64, Constants.SCREEN_HEIGHT -64);
		
		avion = new Avion(new Vector2(Constants.SCREEN_WIDTH, y), velocidadBichos, 
				new Texture(Gdx.files.internal("data/avion.png")));
	
		bala = new Bala(new Vector2(avion.position.x, y), velocidadBala, 
				new Texture(Gdx.files.internal("data/bala.png")));

		
		 
		aviones.add(avion);
		balas.add(bala);
	}

	@Override
	public void dispose() {
		items.clear();
		crows.clear();
		balas.clear();
		tubos.clear();
		aviones.clear();
		sonidoBum.dispose();
		sonidoCuervo.dispose();
		sonidoMoneda.dispose();
		sonidoPausa.dispose();
		sonidoBala.dispose();
		//crowAtlas.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.P){
			pausa = !pausa;
			sonidoPausa.play();
		
		}
		
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		
		if (character == ' ') {
			bird.jump();
		}
		
				
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Pulsar en pantalla
		bird.jump();
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		playCurrentLevelMusic();
	}


	@Override
	public void hide() {
		disableCurrentLevelMusic();
	}


	@Override
	public void pause() {
		pausa = true;
	}


	@Override
	public void resume() {
		pausa = true;
	}
	
	
}
