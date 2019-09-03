package com.electropeyk.squenda.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import com.electropeyk.squenda.R;

import java.io.File;
import java.util.List;

/**
 * Created by mikeafc on 15/11/26.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private List<String> mData;
    private Context context;

    public ImagePagerAdapter(Context context, boolean isMultiScr, List<String> data) {
        this.isMultiScr = isMultiScr;
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_image_child, null);
        //new LinearLayout(container.getContext());
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.imageView);
        String filePath = mData.get(position);
        File imgFile = new File(filePath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
        linearLayout.setId(R.id.item_id);
        container.addView(linearLayout);
        // linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
        // linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());

        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}

