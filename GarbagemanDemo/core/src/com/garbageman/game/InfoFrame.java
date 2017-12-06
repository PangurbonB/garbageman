package com.garbageman.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by dpearson6225 on 12/6/2017.
 */

public class InfoFrame {
    //uh this doesnt look like it's being used right now...
    Garbageman game;
    Stage stage;
    private ShapeRenderer shape = new ShapeRenderer();
    int x, y;

    private void makeRect(int posX, int posY, int width, int height, Color bb){
        System.out.println("MAKE RECT");
        shape.setAutoShapeType(true);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(posX, posY, width, height, bb, bb, bb, bb);
        shape.end();
    }

    public InfoFrame(Garbageman game, Stage stage, int x, int y){
        this.game = game;
        this.stage = stage;
        this.x = x;
        this.y = y;
    }

    public void render(){
        this.makeRect(x, y, 100, 100, Color.BLUE);
    }
}
