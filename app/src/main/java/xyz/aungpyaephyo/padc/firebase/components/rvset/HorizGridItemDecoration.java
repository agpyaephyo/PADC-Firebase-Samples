package xyz.aungpyaephyo.padc.firebase.components.rvset;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xyz.aungpyaephyo.padc.firebase.R;


/**
 * Created by aung on 12/12/15.
 */
public class HorizGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private Drawable mDivider;
    private Context mContext;

    public HorizGridItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        mContext = context;
        a.recycle();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Resources resources = mContext.getResources();
        int marginMedium = (int) resources.getDimension(R.dimen.margin_small);
        outRect.set(0, 0, marginMedium, 0);

        /*
        SmartRecyclerView sRv = (SmartRecyclerView) parent;
        int spanCount = sRv.getSpanCount();

        int position = parent.getChildAdapterPosition(view);
        if ((position + 1) % spanCount == 0) {
            outRect.set(0, 0, 0, marginMedium);
        } else {
            outRect.set(0, 0, marginMedium, marginMedium);
        }
        */
    }
}