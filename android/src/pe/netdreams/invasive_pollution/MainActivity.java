package pe.netdreams.invasive_pollution;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import pe.netdreams.invasive_pollution.Fragments.frg_garage;
import pe.netdreams.invasive_pollution.Fragments.frg_home;
import pe.netdreams.invasive_pollution.Fragments.frg_puntaje;
import pe.netdreams.invasive_pollution.Utils.Constans;
import pe.netdreams.invasive_pollution.Utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout btnpuntaje, btnplay, btngarage;
    private FrameLayout fragment_container;
    private TextView tvCoins;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray);
        btnplay.setBackgroundResource(R.drawable.rectangulo_gray_dark);
        btngarage.setBackgroundResource(R.drawable.rectangulo_gray);

        setMenu(new frg_home());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnpuntaje:{
                setMenu(new frg_puntaje());
                btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray_dark);
                btnplay.setBackgroundResource(R.drawable.rectangulo_gray);
                btngarage.setBackgroundResource(R.drawable.rectangulo_gray);
            }break;
            case R.id.btnplay:{
                setMenu(new frg_home());
                btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray);
                btnplay.setBackgroundResource(R.drawable.rectangulo_gray_dark);
                btngarage.setBackgroundResource(R.drawable.rectangulo_gray);
            }break;
            case R.id.btngarage:{
                setMenu(new frg_garage());
                btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray);
                btnplay.setBackgroundResource(R.drawable.rectangulo_gray);
                btngarage.setBackgroundResource(R.drawable.rectangulo_gray_dark);
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