package xyz.aungpyaephyo.padc.firebase.components.mmfont.components;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import xyz.aungpyaephyo.padc.firebase.components.mmfont.MMFontUtils;


/**
 * Created by aung on 10/7/16.
 */

public class MMTextView extends AppCompatTextView {

    public MMTextView(Context context) {
        super(context);
    }

    public MMTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MMTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!MMFontUtils.isSupportUnicode()) {
            super.setText(Html.fromHtml(MMFontUtils.mmText(text, MMFontUtils.TEXT_UNICODE, true, true)), type);
        } else {
            super.setText(Html.fromHtml(text.toString()), type);
        }
    }
}
