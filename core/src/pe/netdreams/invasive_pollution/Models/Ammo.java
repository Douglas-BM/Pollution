package pe.netdreams.invasive_pollution.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import pe.netdreams.invasive_pollution.MainGame;

public class Ammo {

    public Vector2 position_bullet;
    public Texture src_bullet;
    public Sprite sprite_bullet;
    public float speed_bullet;

    public Ammo() {
        sprite_bullet = new Sprite(MainGame.img_bullet);
        src_bullet = MainGame.img_bullet;
        speed_bullet = 5;
        Sprite aux = MainGame.player.sprite;
        aux.setScale(1);
        position_bullet = new Vector2(MainGame.player.position.x,aux.getHeight()/2);
        sprite_bullet.setScale(.1f);
    }

    public void update(){
        position_bullet.y += speed_bullet;
    }

    public void Draw(SpriteBatch batch){
        update();
        sprite_bullet.setPosition(position_bullet.x, position_bullet.y);
        sprite_bullet.draw(batch);
    }
}
