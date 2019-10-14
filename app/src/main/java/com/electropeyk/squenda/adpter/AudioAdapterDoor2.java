package com.electropeyk.squenda.adpter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.electropeyk.squenda.R;
import com.electropeyk.squenda.models.MetaAudio;
import com.electropeyk.squenda.utils.Common;
import com.example.jean.jcplayer.model.JcAudio;
import com.rey.material.widget.ImageView;
import io.paperdb.Paper;

import java.util.List;

public class AudioAdapterDoor2 extends RecyclerView.Adapter<AudioAdapterDoor2.AudioAdapterViewHolder> {
    private static final String TAG = AudioAdapterDoor2.class.getSimpleName();
    private static OnItemClickListener mListener;
    private List<JcAudio> jcAudioList;
    private SparseArray<Float> progressMap = new SparseArray<>();
    private Context context;

    public int checkedPosition = -1;

    public AudioAdapterDoor2(Context context, List<JcAudio> jcAudioList) {
        this.jcAudioList = jcAudioList;
        setHasStableIds(true);
        this.context = context;
        Paper.init(context);
        MetaAudio metaAudio = Paper.book(Common.DATABASE).read(Common.AUDIO_DOOR2);
        if (metaAudio != null)
            checkedPosition = metaAudio.getPosition();

    }

    // Define the method that allows the parent activity or fragment to define the jcPlayerManagerListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public AudioAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        return new AudioAdapterViewHolder(view);
//        audiosViewHolder.itemView.setOnClickListener(this);
//        return audiosViewHolder;
    }

    @Override
    public void onBindViewHolder(AudioAdapterViewHolder holder, int position) {
        String title = position + 1 + "    " + jcAudioList.get(position).getTitle();
        holder.audioTitle.setText(title);
        holder.itemView.setTag(jcAudioList.get(position));

        if (checkedPosition == -1) {
            holder.img_check.setVisibility(View.GONE);
        } else {
            if (checkedPosition == holder.getAdapterPosition()) {
                holder.img_check.setVisibility(View.VISIBLE);
              } else {
                holder.img_check.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return jcAudioList == null ? 0 : jcAudioList.size();
    }

    public void updateProgress(JcAudio jcAudio, float progress) {
        int position = jcAudioList.indexOf(jcAudio);
        Log.d(TAG, "Progress = " + progress);


        progressMap.put(position, progress);
        if (progressMap.size() > 1) {
            for (int i = 0; i < progressMap.size(); i++) {
                if (progressMap.keyAt(i) != position) {
                    Log.d(TAG, "KeyAt(" + i + ") = " + progressMap.keyAt(i));
                    notifyItemChanged(progressMap.keyAt(i));
                    progressMap.delete(progressMap.keyAt(i));
                }
            }
        }
        notifyItemChanged(position);
    }


    // Define the mListener interface

    public interface OnItemClickListener {
        void onItemClick(int position);


    }

    class AudioAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView audioTitle;
        private ImageView img_check;

        public AudioAdapterViewHolder(View view) {
            super(view);
            this.audioTitle = view.findViewById(R.id.audio_title);
            this.img_check = view.findViewById(R.id.img_check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    img_check.setVisibility(View.VISIBLE);

                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        Paper.book(Common.DATABASE).write(Common.AUDIO_DOOR2,
                                new MetaAudio(checkedPosition, jcAudioList.get(checkedPosition).getPath()));


                    }
                    if (mListener != null) mListener.onItemClick(getAdapterPosition());

                }
            });
        }
    }
}


