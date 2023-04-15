package pe.netdreams.invasive_pollution;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;

import pe.netdreams.invasive_pollution.Fragments.frg_garage;
import pe.netdreams.invasive_pollution.Fragments.frg_home;
import pe.netdreams.invasive_pollution.Fragments.frg_puntaje;
import pe.netdreams.invasive_pollution.Service.SongService;
import pe.netdreams.invasive_pollution.Utils.Constans;
import pe.netdreams.invasive_pollution.Utils.FirebaseManager;
import pe.netdreams.invasive_pollution.Utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int STATE_OUT = 1;
    public static final int STATE_IN = 2;
    public static final int STATE_PLAY = 3;
    public static int STATE = STATE_IN;
    private ImageView btnpuntaje, btnplay, btngarage;
    private FrameLayout fragment_container;
    private TextView tvCoins;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Objects.isNull(SharedPreferencesManager.getStringValue(this, Constans.ID_USER)))
            FirebaseManager.saveData(this);

        startService(new Intent(MainActivity.this, SongService.class));

        fragment_container = findViewById(R.id.fragment_container);

        tvCoins = findViewById(R.id.tvCoins);
        tvCoins.setOnClickListener(this);
        tvCoins.setText(""+ SharedPreferencesManager.getIntValue(this, Constans.TOTAL_COINS));

        btnpuntaje = findViewById(R.id.btnpuntaje);
        btnpuntaje.setOnClickListener(this);
        btnplay = findViewById(R.id.btnplay);
        btnplay.setOnClickListener(this);
        btngarage = findViewById(R.id.btngarage);
        btngarage.setOnClickListener(this);

        btnpuntaje.setBackgroundResource(R.drawable.frm_rec_purple);
        btnplay.setBackgroundResource(R.drawable.frm_rec_orange);
        btngarage.setBackgroundResource(R.drawable.frm_rec_purple);

        setMenu(new frg_home());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, SongService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(MainActivity.this, SongService.class));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(MainActivity.this, SongService.class));
        tvCoins.setText(""+ SharedPreferencesManager.getIntValue(this, Constans.TOTAL_COINS));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startService(new Intent(MainActivity.this, SongService.class));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnpuntaje:{
                setMenu(new frg_puntaje());
                btnpuntaje.setBackgroundResource(R.drawable.frm_rec_orange);
                btnplay.setBackgroundResource(R.drawable.frm_rec_purple);
                btngarage.setBackgroundResource(R.drawable.frm_rec_purple);
            }break;
            case R.id.btnplay:{
                setMenu(new frg_home());
                btnpuntaje.setBackgroundResource(R.drawable.frm_rec_purple);
                btnplay.setBackgroundResource(R.drawable.frm_rec_orange);
                btngarage.setBackgroundResource(R.drawable.frm_rec_purple);
            }break;
            case R.id.btngarage:{
                setMenu(new frg_garage());
                btnpuntaje.setBackgroundResource(R.drawable.frm_rec_purple);
                btnplay.setBackgroundResource(R.drawable.frm_rec_purple);
                btngarage.setBackgroundResource(R.drawable.frm_rec_orange);
            }break;
            case R.id.tvCoins:{

            }break;
        }
    }
    public void setMenu(Fragment fragment){
        YoYo.with(Techniques.SlideOutDown)
            .duration(500)
            .onEnd(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    YoYo.with(Techniques.SlideInUp)
                            .duration(500)
                            .playOn(fragment_container);
                }
            })
            .playOn(fragment_container);
    }

}