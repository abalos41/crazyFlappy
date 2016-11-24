package org.loasoft.crazyFlappy.screens;

import org.loasoft.crazyFlappy.crazyFlappy;
import org.loasoft.crazyFlappy.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;


public class MainMenuScreen implements Screen{

	crazyFlappy juego;
	Stage stage;
	Texture background;
	
	public TextButton buttonplay;
	public TextButton buttonexit;
	public TextButton buttonhowto;
	public TextButton buttonoptions;
	public TextButton accept;
	public Label label;
	public Label labelInstruc;
	public Window window;
	public Music intro;
	
	
	public MainMenuScreen(crazyFlappy juego) {
		this.juego = juego;
		background = new Texture(Gdx.files.internal("data/fondoinicio.png"));
		intro = Gdx.audio.newMusic(Gdx.files.internal("data/intro.mp3"));
	}
	
	@Override
	public void render(float dt) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//Pintar fondo de pantalla
		juego.spriteBatch.begin();
		juego.spriteBatch.draw(background, 0, 0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
		juego.spriteBatch.end();
		
		//Pintar el menu
		stage.act(dt);
		stage.draw();
	
	}

	private void loadScreen(){
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);

		Table table = new Table(juego.getSkin());
			
			//Etiqueta
			label = new Label("Bienvenid@s a crazyflappy! ", juego.getSkin());
			
			//Botones
			buttonplay = new TextButton("Juego Nuevo", juego.getSkin());
			buttonhowto = new TextButton("Instrucciones", juego.getSkin());
			accept = new TextButton("Aceptar", juego.getSkin());
			buttonoptions = new TextButton("Opciones", juego.getSkin());
			buttonexit = new TextButton("Salir", juego.getSkin());

			
			//Ventana
			window = new Window("Instrucciones", juego.getSkin());
			
			table.setFillParent(true);
			table.add(label).width(250).height(50);
			table.row();
			table.add(buttonplay).width(250).height(50).padTop(10);
			table.row();
			table.add(buttonhowto).width(250).height(50).padTop(10);
			table.row();
			table.add(buttonoptions).width(250).height(50).padTop(10);
			table.row();
			table.add(buttonexit).width(250).height(50).padTop(10);

			stage.addActor(table);
			
			buttonplay.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					dispose();
					juego.setScreen(new GameScreen1(juego));
				}
			});
			
			buttonhowto.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					window.padTop(20);
					stage.addActor(window);
					window.setPosition(100, 100);
					window.setSize(500, 300);
					window.addActor(accept);

					window.add("Instrucciones \n"
							+ "1- Pulsa ESPACIO repetidamente para sobrevivir \n \n"
							+ "2- Si chocas 3 veces o tocas el suelo estas eliminado!\n \n"
							+ "3- Hay 4 niveles \n "
							+ "4- Modo FACIL: Aguantar 50 segundos \n Final Modo NORMAL: Aguantar 60 segundos\n " +
							"	  Modo EXPERTO: Aguantar 70 segundos \n Modo IMPOSIBLE: Aguantar 100 segundos \n"
							+ " Boton P para pausar la partida");
				
				accept.addListener(new InputListener(){
					
						public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
							return true;
						}
					
						public void touchUp(InputEvent event, float x, float y, int pointer, int button){
							window.remove();
							window.clear();
					}
				});
				
				}
			});

			buttonoptions.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					dispose();
						juego.setScreen(new MainOptionScreen(juego));
				}
			});

			buttonexit.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					dispose();
					System.exit(0);
				}
			});
	
	}

	@Override
	public void show() {
		loadScreen();
		intro.play();
	}

	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	@Override
	public void hide() {
		intro.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	
}
