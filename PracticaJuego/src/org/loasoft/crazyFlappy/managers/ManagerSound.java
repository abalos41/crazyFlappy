package org.loasoft.crazyFlappy.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ManagerSound {
    private static Preferences prefs = Gdx.app.getPreferences("PracticaJuego");;

    /**
     * Comprueba si el sonido está o no activado durante el juego
     * @return
     */
    public static boolean isSoundEnabled() {

        return prefs.getBoolean("sound");
    }

    public static boolean isSoundDisabled(){

        return !prefs.getBoolean("sound");
    }
}
