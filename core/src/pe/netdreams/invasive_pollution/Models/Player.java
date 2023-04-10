package pe.netdreams.invasive_pollution.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import pe.netdreams.invasive_pollution.MainGame;

public class Player {
    public Vector2 position;
    public static Texture src;
    public static float x, y;
    public Sprite sprite;
    public float speed;

    public static ArrayList<Ammo> list_ammo;
    public Player() {
        list_ammo = new ArrayList<>();
        sprite = new Sprite(MainGame.img);
        src = MainGame.img;
        speed = MainGame.speed;
        x = Gdx.graphics.getWidth()/2-src.getWidth()/2;
        y = 1;
        position = new Vector2(x,y);
        sprite.setScale(1);
    }

    public void update(float deltatimes){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                list_ammo.add(new Ammo());
                return true;
            }
        });

        float azimuth = Gdx.input.getAccelerometerX() / 10;;
        if (azimuth < 0) {
            if(position.x < Gdx.graphics.getWidth()-src.getWidth()/2)
                position.x += deltatimes * speed;
        } else if (azimuth > 0) {
            if(position.x > 0-src.getWidth()/2)
                position.x -= deltatimes * speed;
        }
    }
    public void Draw(SpriteBatch batch){
        update(Gdx.graphics.getDeltaTime());
        for (Ammo elemento : list_ammo) {
            elemento.Draw(batch);
            elemento.update();
        }
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
