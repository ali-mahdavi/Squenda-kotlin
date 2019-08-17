package com.electropeyk.squenda.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import androidx.recyclerview.widget.RecyclerView;
import com.electropeyk.squenda.R;

import java.io.File;
import java.util.List;

/**
 * Created by ali on 7/26/2019 AD.
 */

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "PhotoRecyclerViewAdapte";
    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    private final static int FADE_DURATION = 1000;
    // data is passed into the constructor
    public PhotoRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String filePath = mData.get(position);
        Log.i(TAG,"filePath is: "+filePath);
        File imgFile = new  File(filePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.img_video.setImageBitmap(myBitmap);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(mData!=null)
            return mData.size();
        else
            return 0;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        com.rey.material.widget.ImageView img_video;
        com.rey.material.widget.CheckBox mCheckbox;

        ViewHolder(View itemView) {
            super(itemView);
            img_video = itemView.findViewById(R.id.img_video);
            mCheckbox = itemView.findViewById(R.id.btn_checkbox);
            mCheckbox.setVisibility(View.GONE);
            mCheckbox.setChecked(false);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            setFadeAnimation(itemView);
        }

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                if(mCheckbox.isChecked()){
                    mCheckbox.setVisibility(View.GONE);
                    mCheckbox.setChecked(false);
                    if(CameraListActivity.NUM_SELECCTED_PHOTO.contains(getAdapterPosition()))
                        CameraListActivity.NUM_SELECCTED_PHOTO.remove(getAdapterPosition());
                    return false;
                }else{
                    CameraListActivity.NUM_SELECCTED_PHOTO.add(getAdapterPosition());
                    mCheckbox.setVisibility(View.VISIBLE);
                    mCheckbox.setChecked(true);
                    mLongClickListener.onItemLongClick(view, getAdapterPosition());
                    return true;
                }
            }
            return false;
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
