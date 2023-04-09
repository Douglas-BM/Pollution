package pe.netdreams.invasive_pollution;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;

import pe.netdreams.invasive_pollution.Fragments.frg_garage;
import pe.netdreams.invasive_pollution.Fragments.frg_puntaje;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout btnpuntaje, btnplay, btngarage;
    private FrameLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_container = findViewById(R.id.fragment_container);

        btnpuntaje = findViewById(R.id.btnpuntaje);
        btnpuntaje.setOnClickListener(this);
        btnplay = findViewById(R.id.btnplay);
        btnplay.setOnClickListener(this);
        btngarage = findViewById(R.id.btngarage);
        btngarage.setOnClickListener(this);

        btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray);
        btnplay.setBackgroundResource(R.drawable.rectangulo_gray_dark);
        btngarage.setBackgroundResource(R.drawable.rectangulo_gray);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnpuntaje:{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new frg_puntaje())
                        .commit();
                btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray_dark);
                btnplay.setBackgroundResource(R.drawable.rectangulo_gray);
                btngarage.setBackgroundResource(R.drawable.rectangulo_gray);
            }break;
            case R.id.btnplay:{
                goToActivity(new AndroidLauncher());
            }break;
            case R.id.btngarage:{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new frg_garage())
                        .commit();
                btnpuntaje.setBackgroundResource(R.drawable.rectangulo_gray);
                btnplay.setBackgroundResource(R.drawable.rectangulo_gray);
                btngarage.setBackgroundResource(R.drawable.rectangulo_gray_dark);
            }break;
        }
    }

    public void goToActivity(AndroidApplication activity) {
        Intent intent = new Intent(this, activity.getClass());
        startActivity(intent);
    }
}