package pe.netdreams.invasive_pollution;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

import pe.netdreams.invasive_pollution.Models.Ammo;
import pe.netdreams.invasive_pollution.Models.Ammo_enemy;
import pe.netdreams.invasive_pollution.Models.Enemy;
import pe.netdreams.invasive_pollution.Models.Player;

public class MainGame extends ApplicationAdapter {
	public static int REC_MONEY = 0;
	public static int SCORE = 0;
	public static int LEVEL = 0;
	public static final int STATE_GAME_OVER = 0;
	public static final int STATE_PAUSE = 1;
	public static final int STATE_RUNNING= 2;
	public static final int STATE_WIN= 3;
	public static int STATE = STATE_RUNNING;
	public static Player player;
	SpriteBatch batch;
	public static Texture img;
	public static Texture img_bullet;
	public static Texture img_bullet_enemy;
	Texture fondo;
	Texture fondo_cont;
	Texture game_over_logo;
	Texture win_logo;
	Texture bg_text;
	Texture ic_corazon, ic_score, ic_money, ic_level;

	int nave, ammo, gun;
	int currCoins;
	int currScore;
	int currLevel;
	float volume;
	public static int cant_enemys_w = 6;
	public static int cant_enemys_h = 6;
	public static int spacing;

	public static ArrayList<Enemy> list_enemys;
	public static ArrayList<Ammo_enemy> list_ammo_enemy;
	public static boolean dir_enemys = false;

	private static MyGameCallback myGameCallback;

	public static int hec;
	public static Sound sound_bala;
	private static Sound sound_fondo;
	private static Sound sound_die;
	private static Sound sound_game_over, sound_win, sound_level_up;
	ShapeRenderer shapeRenderer;
	public static FreeTypeFontGenerator generator;
	public static BitmapFont font;
	public static BitmapFont font_title;
	public static BitmapFont font_resume;
	public static float speed_enemy_increase =.05f;
	public static int blindaje;
	public static float speed;
	public static int hp;
	public static int damage;

	public static int nave_blindaje;
	public static float nave_speed;
	public static int nave_hp;
	public static int nave_damage;
	public Sprite btnreintentar, btnsalir, btnnextlevel;
	public OrthographicCamera oCam;
	private static final GlyphLayout glyphLayout = new GlyphLayout();
	private Music music;


	public MainGame(int nave, int ammo, int gun, int nave_blindaje, float nave_speed, int nave_hp, int nave_damage, int currCoins, int currScore, int currLevel, float volume) {
		this.nave = nave;
		this.ammo = ammo;
		this.gun = gun;

		MainGame.nave_blindaje = nave_blindaje;
		MainGame.nave_speed = nave_speed;
		MainGame.nave_hp = nave_hp;
		MainGame.nave_damage = nave_damage;

		this.currCoins = currCoins;
		this.currScore = currScore;
		this.currLevel = currLevel;

		double currVolume = 100;
		this.volume = (float) (1-(Math.log(100 - currVolume) / Math.log(100)));
		resetStates();
	}

	public void resetStates(){
		if(currScore < SCORE){
			currScore = SCORE;
		}
		if(currLevel < LEVEL){
			currLevel = LEVEL;
		}

		currCoins+=REC_MONEY;

		SCORE = 0;
		REC_MONEY = 0;
		LEVEL = 1;

		blindaje = MainGame.nave_blindaje;
		speed = MainGame.nave_speed;
		hp = MainGame.nave_hp;
		damage = MainGame.nave_damage;

		STATE = STATE_RUNNING;
	}
	public interface MyGameCallback {
		public void onStartActivityScoreBoard(int score, int coins, int gameCounter);

		public void saveCoinsToSharedPref(int coins);

		public void saveBestScoreToSharedPref(int score);
		public void saveBestLevelToSharedPref(int level);

		public void onStartActivityB();

		public void onStartSomeActivity(int someParameter, String someOtherParameter);

		public void onBackPress();
	}

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("lilitaone_regular.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 45;
		font = generator.generateFont(parameter);
		parameter.size = 200;
		font_title  = generator.generateFont(parameter);
		parameter.size = 50;
		font_resume  = generator.generateFont(parameter);
		generator.dispose();
		hec = Gdx.graphics.getWidth() / 12;

		batch = new SpriteBatch();

		img = new Texture("Naves/icnave"+(nave+1)+"_"+(gun+1)+".png");
		img_bullet = new Texture("Ammos/ammo"+(ammo+1)+".png");
		img_bullet_enemy = new Texture("Ammos/balaenemiga.png");

		fondo = new Texture("Fondos/fondo1.jpg");
		fondo_cont = new Texture("fondo_cont.png");
		game_over_logo = new Texture("gameover.jpg");
		win_logo = new Texture("logo_win.png");

		ic_corazon = new Texture("Datos/img_corazon.png");
		ic_score = new Texture("Datos/img_nave.png");
		ic_money = new Texture("Datos/img_money.png");
		ic_level = new Texture("Datos/img_level.png");

		bg_text = new Texture("fondo_text.png");

		win_logo = new Texture("logo_win.png");

		sound_bala = Gdx.audio.newSound(Gdx.files.internal("Sonidos/lacer0"+(ammo+1)+".mp3"));

		sound_die = Gdx.audio.newSound(Gdx.files.internal("Sonidos/die_enemy.mp3"));

		sound_game_over = Gdx.audio.newSound(Gdx.files.internal("Sonidos/lose.wav"));
		sound_win = Gdx.audio.newSound(Gdx.files.internal("Sonidos/win.wav"));
		sound_level_up = Gdx.audio.newSound(Gdx.files.internal("Sonidos/lvlup.wav"));

		sound_fondo = Gdx.audio.newSound(Gdx.files.internal("Sonidos/fondo_activo.mp3"));
		music = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/fondo_activo.mp3"));
		music.setLooping(true);
		music.setVolume(volume);
		music.play();

		player = new Player();

		list_enemys = new ArrayList<>();
		list_ammo_enemy = new ArrayList<>();

		btnreintentar = new Sprite(new Texture("btnreintentar.png"));
		btnreintentar.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getWidth()/6);
		btnreintentar.setPosition(Gdx.graphics.getWidth()/2-btnreintentar.getWidth()/2,
				Gdx.graphics.getWidth()/3);

		btnnextlevel = new Sprite(new Texture("btnnextlevel.png"));
		btnnextlevel.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getWidth()/6);
		btnnextlevel.setPosition(Gdx.graphics.getWidth()/2-btnnextlevel.getWidth()/2,
				Gdx.graphics.getWidth()/3);

		btnsalir = new Sprite(new Texture("btnsalir.png"));
		btnsalir.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getWidth()/6);
		btnsalir.setPosition(Gdx.graphics.getWidth()/2-btnsalir.getWidth()/2,
				Gdx.graphics.getWidth()/6);

		oCam = new OrthographicCamera();
		oCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		oCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);

		if(list_enemys.size() == 0)
			createEnemys();
	}
	public void createEnemys(){
		spacing = Gdx.graphics.getWidth()/(cant_enemys_w+1);
		int base = Gdx.graphics.getHeight() - spacing*cant_enemys_h;
		for(int y = 0; y < cant_enemys_h; y++){
			for(int x = 0; x < cant_enemys_w; x++){
				list_enemys.add(new Enemy(new Vector2(x*spacing + (spacing/2) , base-(hec*2)+y*spacing), y));
			}
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector3 temp = new Vector3();
				temp.set(screenX, screenY, 0);
				oCam.unproject(temp);
				if(MainGame.STATE == MainGame.STATE_RUNNING){
					player.list_ammo.add(new Ammo());
					sound_bala.play(volume);
				}else if(MainGame.STATE == MainGame.STATE_GAME_OVER){
					if(btnreintentar.getBoundingRectangle().contains(temp.x, temp.y)){
						resetStates();
						player.list_ammo.clear();
						list_enemys.clear();
						createEnemys();
						STATE = STATE_RUNNING;
					} else if (btnsalir.getBoundingRectangle().contains(temp.x, temp.y)) {
						resetStates();
						saveData();
						goBack();
					}
				}else if(MainGame.STATE == MainGame.STATE_WIN){
					if(btnnextlevel.getBoundingRectangle().contains(temp.x, temp.y)){
						player.list_ammo.clear();
						list_enemys.clear();
						Enemy.speed += speed_enemy_increase;
						LEVEL++;
						createEnemys();
						STATE = STATE_RUNNING;
						sound_level_up.play(volume);
					} else if (btnsalir.getBoundingRectangle().contains(temp.x, temp.y)){
						createEnemys();
						resetStates();
						saveData();
						goBack();
					}
				}
				return true;
			}
		});

		if(STATE == STATE_RUNNING){
			if(hp < 0){
				STATE = STATE_GAME_OVER;
			}

			batch.draw(fondo, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			batch.draw(fondo_cont, 0 , Gdx.graphics.getHeight() - 2*hec, Gdx.graphics.getWidth(), hec*2);

			int margin = 5;

			shapeRenderer.setColor(Color.WHITE);

			batch.draw(ic_corazon,
					0*(Gdx.graphics.getWidth()/2)+margin , Gdx.graphics.getHeight() - hec+margin,
					hec-margin*2, hec-margin*2);
			font.draw(batch, ""+hp,
					0*(Gdx.graphics.getWidth()/2)+margin+hec, Gdx.graphics.getHeight() - margin*2);


			batch.draw(ic_money,
					1*(Gdx.graphics.getWidth()/2)+margin , Gdx.graphics.getHeight() - hec+margin,
					hec-margin*2, hec-margin*2);
			font.draw(batch, ""+REC_MONEY,
					1*(Gdx.graphics.getWidth()/2)+margin+hec, Gdx.graphics.getHeight()  - margin*2);

			batch.draw(ic_score,
					0*(Gdx.graphics.getWidth()/2)+margin , Gdx.graphics.getHeight() - 2*hec+margin,
					hec-margin*2, hec-margin*2);
			font.draw(batch, ""+SCORE,
					0*(Gdx.graphics.getWidth()/2)+margin+hec, Gdx.graphics.getHeight() - hec - margin*2);

			batch.draw(ic_level,
					1*(Gdx.graphics.getWidth()/2)+margin , Gdx.graphics.getHeight() - 2*hec+margin,
					hec-margin*2, hec-margin*2);
			font.draw(batch, ""+LEVEL,
					1*(Gdx.graphics.getWidth()/2)+margin+hec, Gdx.graphics.getHeight() - hec - margin*2);

			player.Draw(batch);
			for(Enemy enemy : list_enemys){
				for (Ammo ammo : player.list_ammo) {
					if (enemy.alive && enemy.position.y <= 0) {
						STATE = STATE_GAME_OVER;
					}
					if (ammo.sprite_bullet.getBoundingRectangle().overlaps(enemy.sprite.getBoundingRectangle())
							&& enemy.alive) {
						ArrayList<Ammo> ammoToRemove = new ArrayList<>();

						ammoToRemove.add(ammo);
						player.list_ammo.removeAll(ammoToRemove);
						sound_die.play(volume);
						enemy.alive = false;

						SCORE += enemy.type;
						REC_MONEY += enemy.type*10;

						break;
					}
				}
				if(enemy.alive){
					enemy.Draw(batch);
					enemy.update();
				}
				Random random = new Random();
				int numeroAleatorio = random.nextInt(5000) + 1;
				if (numeroAleatorio <= 1)
					list_ammo_enemy.add(new Ammo_enemy(enemy.position.x,enemy.position.y));
			}

			ArrayList<Ammo_enemy> ammoToRemove = new ArrayList<>();
			for(Ammo_enemy ammoEnemy : list_ammo_enemy){
				if(ammoEnemy.exist){
					ammoEnemy.Draw(batch);
					if (player.sprite.getBoundingRectangle().overlaps(ammoEnemy.sprite.getBoundingRectangle())) {
						ammoToRemove.add(ammoEnemy);
						hp -= 50;
						sound_die.play(volume);
					}
				}
				if(ammoEnemy.position.y < 0){
					ammoToRemove.add(ammoEnemy);
				}
			}
			list_ammo_enemy.removeAll(ammoToRemove);
			for(Enemy enemy : list_enemys){
				if (enemy.alive &&
					enemy.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
					STATE = STATE_GAME_OVER;

					sound_game_over.play(volume);
				}
				if(!enemy.alive){
					ArrayList<Enemy> enemyToRemove = new ArrayList<>();
					enemyToRemove.add(enemy);

					list_enemys.removeAll(enemyToRemove);
					break;
				}
			}
			if(list_enemys.size()==0){
				STATE = STATE_WIN;
				sound_win.play(volume);
			}
		} else if (STATE == STATE_GAME_OVER) {
			batch.draw(game_over_logo,
				Gdx.graphics.getWidth()/8,
				Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/2,
				Gdx.graphics.getWidth()*6/8,
				Gdx.graphics.getWidth()/3);
			String text_lvl = "LV " + LEVEL;
			font_title.draw(batch, text_lvl,
					Gdx.graphics.getWidth()/2-getTextWidth(font_title, text_lvl)/2,
					Gdx.graphics.getHeight()/2);
			String text_resume = "Money : "+ REC_MONEY + "   SCORE : " + SCORE;
			font_resume.draw(batch, text_resume,
					Gdx.graphics.getWidth()/2-getTextWidth(font_resume, text_resume)/2,
					Gdx.graphics.getHeight()/2 + getTextHeight(font_title, text_lvl) + spacing);
			btnsalir.draw(batch);
			btnreintentar.draw(batch);
		} else if (STATE == STATE_WIN) {
			batch.draw(win_logo,
				Gdx.graphics.getWidth()/8,
				Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/2,
				Gdx.graphics.getWidth()*6/8,
				Gdx.graphics.getWidth()/3);
			String text_lvl = "LV " + LEVEL;
			font_title.draw(batch, text_lvl,
				Gdx.graphics.getWidth()/2-getTextWidth(font_title, text_lvl)/2,
				Gdx.graphics.getHeight()/2);
			String text_resume = "Money : "+ REC_MONEY + "   SCORE : " + SCORE;
			font_resume.draw(batch, text_resume,
					Gdx.graphics.getWidth()/2-getTextWidth(font_resume, text_resume)/2,
					Gdx.graphics.getHeight()/2 + getTextHeight(font_title, text_lvl) + spacing);
			btnsalir.draw(batch);
			btnnextlevel.draw(batch);
		}
		batch.end();
	}

	public static float getTextWidth(BitmapFont font, String text) {
		glyphLayout.setText(font, text);
		return glyphLayout.width;
	}

	public static float getTextHeight(BitmapFont font, String text) {
		glyphLayout.setText(font, text);
		return glyphLayout.height;
	}

	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
	}
	public void setMyGameCallback(MyGameCallback callback) {
		myGameCallback = callback;
	}
	public static void goBack() {
		if (myGameCallback != null) {
			myGameCallback.onBackPress();
		}
	}

	public void saveData(){
		myGameCallback.saveCoinsToSharedPref(currCoins);
		myGameCallback.saveBestLevelToSharedPref(currLevel);
		myGameCallback.saveBestScoreToSharedPref(currScore);
	}
}
