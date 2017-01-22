package io.github.kolacbb.kolaweibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import io.github.kolacbb.kolaweibo.R;

/**
 * Created by kolab on 2016/10/22.
 */

public class WBImagesView extends GridLayout {

    private ImageView[] mImages;
    private Context mCtx;

    public WBImagesView(Context context) {
        this(context, null);
    }

    public WBImagesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WBImagesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView =  LayoutInflater.from(context).inflate(R.layout.view_wb_images, this, true);
        mImages = new ImageView[9];

        mImages[0] = (ImageView) rootView.findViewById(R.id.image0);
        mImages[1] = (ImageView) rootView.findViewById(R.id.image1);
        mImages[2] = (ImageView) rootView.findViewById(R.id.image2);
        mImages[3] = (ImageView) rootView.findViewById(R.id.image3);
        mImages[4] = (ImageView) rootView.findViewById(R.id.image4);
        mImages[5] = (ImageView) rootView.findViewById(R.id.image5);
        mImages[6] = (ImageView) rootView.findViewById(R.id.image6);
        mImages[7] = (ImageView) rootView.findViewById(R.id.image7);
        mImages[8] = (ImageView) rootView.findViewById(R.id.image8);
        this.setOrientation(HORIZONTAL);
    }

    public void setImages(Context context, String[] urls) {
        if (urls == null) {
            return;
        }

        Log.e("WBImageView", " size " + urls.length);

        int urlSize = urls.length;

        // init layout
        int newColumnCount;
        if (urlSize == 1) {
            newColumnCount = 1;
        } else if (urlSize == 2 || urlSize == 4) {
            newColumnCount = 2;
        } else {
            newColumnCount = 3;
        }
        changeColumnCount(newColumnCount);

        // fill image into ImageView
        for (int i = 0; i < urlSize; i++) {
            mImages[i].setVisibility(VISIBLE);
            Picasso.with(context)
                    .load(urls[i])
                    .resize(100, 100)
                    .centerCrop()
                    .into(mImages[i]);
        }

        // set no content images invisible
        setNoContentImages(urlSize);
    }

    private void setNoContentImages(int start) {
        for (int i = start; i < mImages.length; i++) {
            mImages[i].setVisibility(GONE);
        }
    }

    private void changeColumnCount(int columnCount) {
        Log.e("View", "old " + getColumnCount() + " new " + columnCount + " childern " + getChildCount());
        if (this.getColumnCount() != columnCount) {
            final int viewsCount = this.getChildCount();
            for (int i = 0; i < viewsCount; i++) {
                View view = this.getChildAt(i);
                //new GridLayout.LayoutParams created with Spec.UNSPECIFIED
                //which are package visible
                view.setLayoutParams(new GridLayout.LayoutParams());
                Log.e("View", "shu   " + i);
            }
            this.setColumnCount(columnCount);
        }
    }
}
