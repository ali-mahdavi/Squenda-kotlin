package com.electropeyk.squenda.fragments;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.electropeyk.squenda.R;
import com.electropeyk.squenda.adpter.AudioAdapterDoor2;
import com.electropeyk.squenda.utils.Common;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;
public class MelodyDoor2Fragment extends Fragment   {
    private JcPlayerView player;
    private RecyclerView recyclerView;
    private AudioAdapterDoor2 audioAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_melody, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_melody);
        player = rootView.findViewById(R.id.jcplayer);
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
}
