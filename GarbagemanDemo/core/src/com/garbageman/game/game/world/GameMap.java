package com.brettzonick.game.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;

/**
 * Created by dpearson6225 on 9/15/2017.
 */

public abstract class GameMap {

    public abstract void render(OrthographicCamera camera);
    public abstract void update(float delta);
    public abstract void dispose();

}
