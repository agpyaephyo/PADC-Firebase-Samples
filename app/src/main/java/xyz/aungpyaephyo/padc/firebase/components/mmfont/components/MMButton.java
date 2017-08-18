package xyz.aungpyaephyo.padc.firebase.components.mmfont.components;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import xyz.aungpyaephyo.padc.firebase.components.mmfont.MMFontUtils;

/**
 * Created by aung on 6/25/16.
 */
public class MMButton extends AppCompatButton {

    public MMButton(Context context) {
        super(context);
    }

    public MMButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MMButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
            super.setText(MMFontUtils.mmText(text, MMFontUtils.TEXT_UNICODE, true, true), type);
        } else {
            super.setText(text, type);
        }
    }
}
