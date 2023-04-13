package pe.netdreams.invasive_pollution.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import pe.netdreams.invasive_pollution.MainGame;

public class Enemy {
    public Vector2 position;
    public Sprite sprite;
    public int type;
    public static float speed = .4f;//0.4f 5
    public boolean alive = true;

    public Enemy(Vector2 _position, int img){
        type = img;
        sprite = new Sprite(getImg(img));
        position = _position;
        sprite.setScale(.7f);
        sprite.setSize(MainGame.spacing, MainGame.spacing);
    }

    public void Draw(SpriteBatch batch){
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void update(){
        if(MainGame.dir_enemys){
            if((position.x+sprite.getWidth()) < Gdx.graphics.getWidth()){
                position.x += speed;
            }else{
                MainGame.dir_enemys = false;
            }
        }else{
            if(position.x > 0) {
                position.x -= speed;
            }else{
                MainGame.dir_enemys = true;
            }
        }
        position.y -= speed;
    }

    public Texture getImg(int tipe){
        return new Texture("Enemys/enemy"+(6-tipe)+".png");
    }
}
