package com.brettzonick.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by bzonick5979 on 9/15/2017.
 */

public abstract class GameMap {

    public abstract void render (OrthographicCamera camera);
    public abstract void update (float delta);
    public abstract void dispose ();

}
