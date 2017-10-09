package com.brettzonick.game.java.world;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.brettzonick.game.java.Garbageman;
import com.brettzonick.game.java.screens.Trashcan;

/**
 * Created by bzonick5979 on 9/21/2017.
 */



public class InputHandler implements InputProcessor {
    Garbageman game;

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.L){
            //game.setScreen(new Trashcan(game));
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
