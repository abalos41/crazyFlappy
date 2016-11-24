package org.loasoft.crazyFlappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.loasoft.crazyFlappy.crazyFlappy;
import org.loasoft.crazyFlappy.util.Constants;

/**
 * Created by arN on 04/03/2016.
 */
public class MainOptionScreen implements Screen {

    crazyFlappy juego;

    Stage stage;
    Texture background;
    TextButton buttonback;
    TextButton buttonchange;

    public Label label;
    public Window window;
    public Music intro;
    public Preferences prefs;


    public MainOptionScreen(crazyFlappy juego) {
        this.juego = juego;
        background = new Texture(Gdx.files.internal("data/options.jpg"));
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

    private void loadScreen() {

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table(juego.getSkin());

        //Etiqueta
        label = new Label("Opciones", juego.getSkin());

        //Botones
        buttonback = new TextButton("Volver al Menu Principal", juego.getSkin());
        buttonchange = new TextButton("Seleccion de Niveles", juego.getSkin());

        //CheckBox
        final CheckBox checkSound = new CheckBox(" Sonido", juego.getSkin());
        checkSound.setChecked(prefs.getBoolean("sound"));


        //Ventana
        window = new Window("Instrucciones", juego.getSkin());

        table.setFillParent(true);
        table.add(label).width(150).height(50);
        table.row();
        table.add(checkSound).center().pad(5f);
        table.row().height(20);
        table.add(buttonchange).width(250).height(50).padTop(10);
        table.row();
        table.add(buttonback).width(250).height(50).padTop(10);
        table.row();

        stage.addActor(table);


        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.putBoolean("sound", checkSound.isChecked());
            }
        });

        buttonchange.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new MainSelectLevelScreen(juego));
            }
        });

        buttonback.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new MainMenuScreen(juego));
            }
        });


    }

    private void loadPreferences() {

        prefs = Gdx.app.getPreferences("PracticaJuego");

        // Coloca los valores por defecto (para la primera ejecución)
        if (!prefs.contains("sound"))
            prefs.putBoolean("sound", true);
    }


    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        loadPreferences();
        loadScreen();
        intro.play();
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
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
