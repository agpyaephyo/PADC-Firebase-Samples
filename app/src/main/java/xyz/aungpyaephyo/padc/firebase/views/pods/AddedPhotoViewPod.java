package xyz.aungpyaephyo.padc.firebase.views.pods;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.firebase.R;

/**
 * Created by aung on 8/19/17.
 */

public class AddedPhotoViewPod extends FrameLayout {

    @BindView(R.id.iv_added_photo)
    ImageView ivAddedPhoto;

    private String mPhotoUrl;

    public AddedPhotoViewPod(@NonNull Context context) {
        super(context);
    }

    public AddedPhotoViewPod(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddedPhotoViewPod(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setData(String photoUrl) {
        mPhotoUrl = photoUrl;
        Glide.with(getContext())
                .load(photoUrl)
                .centerCrop()
                .placeholder(R.drawable.img_take_photo)
                .error(R.drawable.img_take_photo)
                .into(ivAddedPhoto);
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }
}
