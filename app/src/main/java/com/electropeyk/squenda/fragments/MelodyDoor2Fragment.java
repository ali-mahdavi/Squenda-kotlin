package com.electropeyk.squenda.fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.electropeyk.squenda.R;
import com.electropeyk.squenda.activities.DisplayBrightnessMelodyActivity;
import com.electropeyk.squenda.activities.MediaActivity;
import com.electropeyk.squenda.activities.MyHomeActivity;
import com.electropeyk.squenda.activities.SettingActivity;
import com.electropeyk.squenda.adpter.AudioAdapterDoor2;
import com.electropeyk.squenda.utils.Common;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.os.Looper.getMainLooper;

public class MelodyDoor2Fragment extends Fragment implements View.OnClickListener {
    private JcPlayerView player;
    private RecyclerView recyclerView;
    private AudioAdapterDoor2 audioAdapter;
    private AppCompatImageView btn_profile_melody,
            img_back_melody,btn_home_melody, btn_setting_melody;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_melody, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_melody);
        player = rootView.findViewById(R.id.jcplayer);
        btn_profile_melody = rootView.findViewById(R.id.btn_profile_melody);
        btn_profile_melody.setOnClickListener(this);
        btn_home_melody = rootView.findViewById(R.id.btn_home_melody);
        btn_home_melody.setOnClickListener(this);
        btn_setting_melody = rootView.findViewById(R.id.btn_setting_melody);
        btn_setting_melody.setOnClickListener(this);
        img_back_melody = rootView.findViewById(R.id.img_back_melody);
        img_back_melody.setOnClickListener(this);
        TextView txt_date_melody=rootView.findViewById(R.id.txt_date_melody);
        final TextView txt_time_melody=rootView.findViewById(R.id.txt_time_melody);
        txt_time_melody.setText(new SimpleDateFormat("HH:mm", Locale.US).format(new Date()));
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {


                txt_time_melody.setText(new SimpleDateFormat("HH:mm", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String day = Common.days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];
        String month = Common.months[Calendar.getInstance().get(Calendar.MONTH)];
        String date = day + "," + month + " " + dayOfMonth;
        txt_date_melody.setText(date);
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromAssets("audio 1", "sound1.wav"));
        jcAudios.add(JcAudio.createFromAssets("audio 2", "sound2.wav"));
        jcAudios.add(JcAudio.createFromAssets("audio 3", "sound3.wav"));
        jcAudios.add(JcAudio.createFromAssets("audio 4", "sound4.wav"));
        jcAudios.add(JcAudio.createFromAssets("audio 5", "sound5.wav"));
        player.initPlaylist(jcAudios, null);
        adapterSetup();
        return rootView;
    }
    private void adapterSetup() {
        audioAdapter = new AudioAdapterDoor2(getActivity(),player.getMyPlaylist());
        audioAdapter.setOnItemClickListener(new AudioAdapterDoor2.OnItemClickListener() {


            @Override
            public void onItemClick(int position) {
                Common.startSound(getActivity(),player.getMyPlaylist().get(position).getPath());

            }


        });
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_profile_melody:
                intent=new Intent(getActivity(), MediaActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_home_melody:
                intent=new Intent(getActivity(), MyHomeActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_setting_melody:
                intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.img_back_melody:
                intent=new Intent(getActivity(), DisplayBrightnessMelodyActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
