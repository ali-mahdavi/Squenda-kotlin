package com.electropeyk.squenda.adpter;

import android.content.Context;
import android.graphics.Color;

import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.viewpager.widget.PagerAdapter;
import com.electropeyk.squenda.R;

import java.util.List;

/**
 * Created by mikeafc on 15/11/26.
 */
public class VideoPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private List<String> mData;
    private Context context;
    public VideoPagerAdapter(Context context, boolean isMultiScr, List<String> data) {
        this.isMultiScr = isMultiScr;
        this.mData = data;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mData.size();}

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        //new LinearLayout(container.getContext());
        VideoView videoView = (VideoView) linearLayout.findViewById(R.id.videoView1);

            String filePath = mData.get(position);
            Uri uri = Uri.parse(filePath);
            //Creating MediaController
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);
            //Setting MediaController and URI, then starting the videoView
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();

            linearLayout.setId(R.id.item_id);

            container.addView(linearLayout);
            linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
            linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());

        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}

