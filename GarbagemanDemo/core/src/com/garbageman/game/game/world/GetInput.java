package com.garbageman.game.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.garbageman.game.Garbageman;

/**
 * Created by dpearson6225 on 9/21/2017.
 */

public class GetInput implements InputProcessor {
    boolean active = false;
    @Override
    public boolean keyDown(int keycode) {
        if (active) {
            if (keycode == Input.Keys.Q) {
                System.out.println("closing");
                Gdx.app.exit();
            } else if (keycode == Input.Keys.D) {
                inc(25, 0);
            } else if (keycode == Input.Keys.A) {
                inc(-25, 0);
            }
        }
        return false;
    }

    public static void inc(int x, int y){
        //Garbageman.x = Garbageman.x + x;

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
