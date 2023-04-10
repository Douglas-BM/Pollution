package pe.netdreams.invasive_pollution;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import pe.netdreams.invasive_pollution.MainGame;
import pe.netdreams.invasive_pollution.Utils.Constans;
import pe.netdreams.invasive_pollution.Utils.SharedPreferencesManager;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGame(
			SharedPreferencesManager.getIntValue(this, Constans.NAVE_SET),
			SharedPreferencesManager.getIntValue(this, Constans.AMMO_SET),
			SharedPreferencesManager.getIntValue(this, Constans.GUN_SET),
			250,
			SharedPreferencesManager.getIntValue(this, Constans.TOTAL_COINS),
			SharedPreferencesManager.getIntValue(this, Constans.BEST_SCORE),
			SharedPreferencesManager.getIntValue(this, Constans.VOLUME_SOUND)
		), config);
	}
}
