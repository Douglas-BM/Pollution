package pe.netdreams.invasive_pollution.Fragments;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import pe.netdreams.invasive_pollution.Adapters.AmmoAdapter;
import pe.netdreams.invasive_pollution.Adapters.NaveAdapter;
import pe.netdreams.invasive_pollution.Model.Ammo;
import pe.netdreams.invasive_pollution.Model.Nave;
import pe.netdreams.invasive_pollution.R;
import pe.netdreams.invasive_pollution.Utils.Constans;
import pe.netdreams.invasive_pollution.Utils.SharedPreferencesManager;
import pe.netdreams.invasive_pollution.listener.RecyclerItemClickListener;

public class frg_garage extends Fragment {
    RecyclerView rvNaves,rvAmmos;
    ImageView ivnave;
    TextView tvblindage, tvvida, tvcadencia, tvdamage;
    ArrayList<Nave> list_naves;
    ArrayList<Ammo> list_ammos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frg_garage, container, false);

        tvblindage = view.findViewById(R.id.tvblindage);
        tvvida = view.findViewById(R.id.tvvida);
        tvcadencia = view.findViewById(R.id.tvcadencia);
        tvdamage = view.findViewById(R.id.tvdamage);

        rvNaves = view.findViewById(R.id.rvNaves);
        rvAmmos = view.findViewById(R.id.rvAmmos);
        ivnave = view.findViewById(R.id.ivnave);

        list_naves = new ArrayList<>();

        list_naves.add(new Nave(0,"H2151-1", R.drawable.nave1, 1000,50,10, 50000));
        list_naves.add(new Nave(1,"H2SAD-2", R.drawable.nave2, 2000,55,11, 50000));
        list_naves.add(new Nave(2,"JTRY5-3", R.drawable.nave3, 3000,60,12, 50000));
        list_naves.add(new Nave(3,"JSZEN-4", R.drawable.nave4, 4000,65,13, 50000));
        list_naves.add(new Nave(4,"ZVAVV-5", R.drawable.nave5, 5000,70,14, 50000));
        list_naves.add(new Nave(5,"GWAWG-5", R.drawable.nave6, 6000,75,15, 50000));
        list_naves.add(new Nave(6,"BDFDB-5", R.drawable.nave7, 7000,80,16, 50000));
        list_naves.add(new Nave(7,"UYOER-5", R.drawable.nave8, 8000,85,17, 50000));
        list_naves.add(new Nave(8,"PJDFF-5", R.drawable.nave9, 9000,90,18, 50000));

        NaveAdapter naveAdapter = new NaveAdapter(list_naves, getContext());

        rvNaves.setAdapter(naveAdapter);

        rvNaves.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvNaves, new RecyclerItemClickListener.OnItemClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onItemClick(View view, int position) {
                    YoYo.with(Techniques.SlideOutRight)
                        .duration(500)
                        .onEnd(new YoYo.AnimatorCallback() {
                            @Override
                            public void call(Animator animator) {
                                SharedPreferencesManager.setIntValue(getContext(), Constans.NAVE_SET, list_naves.get(position).getId());
                                naveAdapter.notifyDataSetChanged();
                                ivnave.setImageResource(list_naves.get(position).getRecurso());
                                YoYo.with(Techniques.SlideInLeft)
                                    .duration(500)
                                    .playOn(ivnave);
                                rvNaves.scrollToPosition(SharedPreferencesManager.getIntValue(getContext(), Constans.NAVE_SET)-1);

                                getStats();
                            }
                        })
                        .playOn(ivnave);
                }
                @Override
                public void onLongItemClick(View view, int position) {

                }
            })
        );

        list_ammos = new ArrayList<>();

        list_ammos.add(new Ammo(0, "Clasic", R.drawable.ic_ammo_1, 100, 100000));
        list_ammos.add(new Ammo(1, "Blindada", R.drawable.ic_ammo_2, 200, 100000));
        list_ammos.add(new Ammo(2, "Perforante", R.drawable.ic_ammo_3, 300, 100000));
        list_ammos.add(new Ammo(3, "Atomica", R.drawable.ic_ammo_4, 400, 100000));
        list_ammos.add(new Ammo(4, "Lazer", R.drawable.ic_ammo_5, 500, 100000));

        AmmoAdapter ammoAdapter = new AmmoAdapter(list_ammos, getContext());

        rvAmmos.setAdapter(ammoAdapter);

        rvAmmos.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvAmmos, new RecyclerItemClickListener.OnItemClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onItemClick(View view, int position) {
                    SharedPreferencesManager.setIntValue(getContext(), Constans.AMMO_SET, list_ammos.get(position).getId());
                    ammoAdapter.notifyDataSetChanged();
                    YoYo.with(Techniques.Swing)
                            .duration(500)
                            .playOn(ivnave);
                    getStats();
                }
                @Override
                public void onLongItemClick(View view, int position) {

                }
            })
        );

        ivnave.setImageResource(list_naves.get(SharedPreferencesManager.getIntValue(getContext(), Constans.NAVE_SET)).getRecurso());
        rvNaves.scrollToPosition(SharedPreferencesManager.getIntValue(getContext(), Constans.NAVE_SET)-1);

        getStats();

        return view;
    }

    public void getStats(){
        tvblindage.setText(""+list_naves.get(SharedPreferencesManager.getIntValue(getContext(), Constans.NAVE_SET)).getBlindaje());
        tvvida.setText(""+list_naves.get(SharedPreferencesManager.getIntValue(getContext(), Constans.NAVE_SET)).getVida());
        tvcadencia.setText(""+list_naves.get(SharedPreferencesManager.getIntValue(getContext(), Constans.NAVE_SET)).getCadencia());
        tvdamage.setText(""+list_ammos.get(SharedPreferencesManager.getIntValue(getContext(), Constans.AMMO_SET)).getDamage());
    }
}