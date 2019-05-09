package com.example.administrator.examsystem.utils;

/**
 * Created by Administrator on 2019/5/9.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.examsystem.R;


/**
 * Created by dreamY on 2017/9/9.
 */

public class BigImageDialog extends AlertDialog {
    private static final String TAG = "BigImageDialog";
    private ImageView mImageView;
    private Context mContext;
    private String mString;
    private int lastX;
    private int lastY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, R.layout.big_image, null);
        setContentView(view);
        mImageView = (ImageView) view.findViewById(R.id.image_big);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        Log.e(TAG, "onCreate: " + mString.toString());
        Glide.with(mContext)
                .load(mString)
                .placeholder(R.mipmap.ic_launcher)
                .override(800, 1400)
                .into(mImageView);

    }

    protected BigImageDialog(Context context) {
        super(context);

    }



    public BigImageDialog(Context context, @StyleRes int themeResId, String imageView) {
        super(context, themeResId);
        this.mContext = context;
        mString = imageView;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() - lastX >= 100 || event.getY() - lastY >= 100){
                    this.cancel();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
        }


        return true;
    }


}