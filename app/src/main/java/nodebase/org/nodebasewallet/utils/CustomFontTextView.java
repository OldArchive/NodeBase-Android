package nodebase.org.nodebasewallet.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by Neoperol on 7/10/17.
 */

public class CustomFontTextView extends AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, Typeface.NORMAL);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {

            case Typeface.BOLD: // bold
                return FontCache.getTypeface("NexaBold.ttf", context);

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("NexaRustSans-Black.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("NexaLight.ttf", context);
        }
    }
}