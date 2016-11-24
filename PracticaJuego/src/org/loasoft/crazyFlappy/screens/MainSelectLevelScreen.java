package org.loasoft.crazyFlappy.screens;

import com.badlogic.gdx.*;
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
import org.loasoft.crazyFlappy.crazyFlappy;
import org.loasoft.crazyFlappy.util.Constants;

import javax.xml.soap.Text;

/**
 * Created by arN on 04/03/2016.
 */
public class MainSelectLevelScreen implements Screen {

    crazyFlappy juego;

    Stage stage;
    Texture background;

    public TextButton buttonstage1;
    public TextButton buttonstage2;
    public TextButton buttonstage3;
    public TextButton buttonstage4;
    public TextButton buttonback;
    public Label label;
    public Window window;
    public Music intro;


    public MainSelectLevelScreen(crazyFlappy juego) {
        this.juego = juego;
        background = new Texture(Gdx.files.internal("data/select.jpg"));
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
        label = new Label("Seleccion de Niveles", juego.getSkin());

        //Botones
        buttonstage1 = new TextButton("Modo FACIL", juego.getSkin());
        buttonstage2 = new TextButton("Modo NORMAL", juego.getSkin());
        buttonstage3 = new TextButton("Modo EXPERTO", juego.getSkin());
        buttonstage4 = new TextButton("Modo IMPOSIBLE", juego.getSkin());
        buttonback = new TextButton("Atras", juego.getSkin());

        //Ventana
        window = new Window("Instrucciones", juego.getSkin());

        table.setFillParent(true);
        table.add(label).width(150).height(50);
        table.row();
        table.add(buttonstage1).width(250).height(50).padTop(10);
        table.row();
        table.add(buttonstage2).width(250).height(50).padTop(10);
        table.row();
        table.add(buttonstage3).width(250).height(50).padTop(10);
        table.row();
        table.add(buttonstage4).width(250).height(50).padTop(10);
        table.row();
        table.add(buttonback).width(250).height(50).padTop(10);
        table.row();

        stage.addActor(table);

        buttonstage1.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new GameScreen1(juego));
            }
        });

        buttonstage2.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new GameScreen2(juego));
            }
        });

        buttonstage3.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new GameScreen3(juego));
            }
        });

        buttonstage4.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new GameScreen4(juego));
            }
        });

        buttonback.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                dispose();
                juego.setScreen(new MainOptionScreen(juego));
            }
        });

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
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
