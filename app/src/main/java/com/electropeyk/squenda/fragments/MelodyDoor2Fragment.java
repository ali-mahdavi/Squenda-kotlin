package com.electropeyk.squenda.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.electropeyk.squenda.R;
import com.electropeyk.squenda.activities.MainActivity;
import com.electropeyk.squenda.adpter.AudioAdapter;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.general.errors.OnInvalidPathListener;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.service.JcPlayerManagerListener;
import com.example.jean.jcplayer.view.JcPlayerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MelodyDoor2Fragment extends Fragment implements OnInvalidPathListener, JcPlayerManagerListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private JcPlayerView player;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    public int pos;

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
        player.initPlaylist(jcAudios, this);
        adapterSetup();
        return rootView;
    }

    private void adapterSetup() {
        audioAdapter = new AudioAdapter(player.getMyPlaylist());
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(int position) {


                player.playAudio(player.getMyPlaylist().get(position));
                pos=position;

            }


        });
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                                                           LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    }
    @Override
    public void onStop() {
        super.onStop();
       // player.createNotification();
    }


    @Override
    public void onPathError(@NotNull JcAudio jcAudio) {
        Toast.makeText(getActivity(), jcAudio.getPath() + " with problems", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCompletedAudio() {

     pos =-1;

    }


    @Override
    public void onContinueAudio(@NotNull JcStatus jcStatus) {
        if(pos ==-1)
            onStop();
    }

    @Override
    public void onJcpError(@NotNull Throwable throwable) {

    }

    @Override
    public void onPaused(@NotNull JcStatus jcStatus) {
        super.onPause();
        player.createNotification();
    }

    @Override
    public void onPlaying(@NotNull JcStatus jcStatus) {
        if(pos ==-1)
            onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.kill();
    }

    @Override
    public void onPreparedAudio(@NotNull JcStatus jcStatus) {
        if(pos ==-1)
            onStop();
    }

    @Override
    public void onTimeChanged(@NotNull JcStatus jcStatus) {
        if(pos ==-1)
            onStop();
        else
        updateProgress(jcStatus);
    }


    private void updateProgress(final JcStatus jcStatus) {
        Log.d(TAG, "Song duration = " + jcStatus.getDuration()
                + "\n song pos = " + jcStatus.getCurrentPosition());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // calculate progress
                float progress = (float) (jcStatus.getDuration() - jcStatus.getCurrentPosition())
                        / (float) jcStatus.getDuration();
                progress = 1.0f - progress;
                audioAdapter.updateProgress(jcStatus.getJcAudio(), progress);
            }
        });
    }
}
