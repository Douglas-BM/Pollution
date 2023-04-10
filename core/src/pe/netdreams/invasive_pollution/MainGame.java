package pe.netdreams.invasive_pollution;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import pe.netdreams.invasive_pollution.Models.Ammo;
import pe.netdreams.invasive_pollution.Models.Player;

public class MainGame extends ApplicationAdapter {

	public static Player player;
	SpriteBatch batch;
	public static Texture img;
	public static Texture img_bullet;
	Texture fondo;

	int nave, ammo, gun;

	public static float speed;
	int currCoins;
	int currScore;
	int volume;


	public MainGame(int nave, int ammo, int gun, float speed, int currCoins, int currScore, int volume) {
		this.nave = nave;
		this.ammo = ammo;
		this.gun = gun;
		this.speed = speed;
		this.currCoins = currCoins;
		this.currScore = currScore;
		this.volume = volume;
	}

	@Override
	public void create () {

		batch = new SpriteBatch();

		img = new Texture("Naves/nave"+(nave+1)+".png");
		img_bullet = new Texture("Ammos/ammo"+(ammo+1)+".png");
		fondo = new Texture("Fondos/fondo1.jpg");

		player = new Player();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(fondo, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player.Draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
