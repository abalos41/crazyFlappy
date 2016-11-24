package org.loasoft.crazyFlappy.screens;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.loasoft.crazyFlappy.crazyFlappy;
import org.loasoft.crazyFlappy.characters.Avion;
import org.loasoft.crazyFlappy.characters.Bala;
import org.loasoft.crazyFlappy.characters.Bird;
import org.loasoft.crazyFlappy.characters.Coin;
import org.loasoft.crazyFlappy.characters.Crow;
import org.loasoft.crazyFlappy.characters.Item;
import org.loasoft.crazyFlappy.managers.ManagerSound;
import org.loasoft.crazyFlappy.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameScreen1 implements Screen, InputProcessor{
	
	//Arrays
	Array<Item> items;
	Array<Crow> crows;
	Array<Avion> aviones;
	Array<Bala> balas;
	
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

	private float timePassed = 0;
	
	//Variables
	long lastItem;
	long lastCrow;
	long lastAvion;
	int contadorVidas = 3;
	float scrollTimer = 0.0f; 
	int monedasRecogidas;
	float tiempoJuego = 0;
	int nivel = 1;
	float velocidadBichos = 10f;
	float velocidadBala = 16f; 
	int velocidadSuelo = 5;

	
	//boolean 
	boolean pausa = false;
	
	public GameScreen1(crazyFlappy juego) {
		 
		this.juego = juego;
			
		fondo = new Texture(Gdx.files.internal("data/fondo1.jpg"));
		spriteCesped = new Texture(Gdx.files.internal("data/cesped.png"));
		sonidoMoneda = Gdx.audio.newSound(Gdx.files.internal("data/moneda.mp3"));
		sonidoPausa = Gdx.audio.newSound(Gdx.files.internal("data/pausa.mp3"));
		sonidoBum = Gdx.audio.newSound(Gdx.files.internal("data/bum.mp3"));
		sonidoCuervo = Gdx.audio.newSound(Gdx.files.internal("data/saber.mp3"));
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
			sonidoFondo = Gdx.audio.newMusic(Gdx.files.internal("data/1stage.mp3"));
			sonidoFondo.setLooping(true);
			sonidoFondo.setVolume(0.20f);
			sonidoFondo.play();
		}
	}

	private void disableCurrentLevelMusic() {
		// Musica de fondo durante el juego
		if (ManagerSound.isSoundDisabled()) {
			sonidoFondo = Gdx.audio.newMusic(Gdx.files.internal("data/1stage.mp3"));
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
		
		if(tiempoJuego > 30){
			

			
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
		
		if(tiempoJuego > 30){
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
		
		
		//Iterator aviones
		if(tiempoJuego > 30){
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
		
		
		//Actualiza la velocidad
		if(tiempoJuego > 30){
			velocidadBala = 26f;
			velocidadBichos = 20f;
		}

		//Cuando pasa de 50 segundos has ganado
		if(tiempoJuego > 50){
			dispose();
			juego.setScreen(new GameScreen1Win(juego));
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
				new Texture(Gdx.files.internal("data/greenLaserRay.png")));

		crows.add(crow);
		
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
