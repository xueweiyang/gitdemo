package com.example.a;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;

import java.util.List;

/**
 * Created by changle.fang on 2019-11-11.
 * 文本末尾添加标签，过长时在标签前显示省略号
 *
 * @author changle.fang
 * @email changle.fang@ximalaya.com
 * @phoneNumber 15050162674
 */
public class EllipsizeTagTextView extends TextView {

    public EllipsizeTagTextView(Context context) {
        super(context);
    }

    public EllipsizeTagTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsizeTagTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTags(final List<String> tags, final int textSize, final int marginFirst, final int marginCommon, final int padding) {
        if (tags == null || tags.size() == 0) {
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                setTags(tags, textSize, marginFirst, marginCommon, padding);
            }
        });

    }

    private void setTags(List<String> tags, int textSize, int marginFirst, int marginCommon, int padding) {
        if (tags == null || tags.size() == 0) {
            return;
        }
        int tagsize = tags.size();
        int textLength = 0;
        for (String tag : tags) {
            textLength += tag.length();
        }
        //计算标签区域需要的宽度
        int width = textSize * textLength + padding * tagsize + marginFirst + marginCommon * (tagsize - 1);
        String text = getEllipsizeText(getText().toString(), width);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        StringBuilder content = new StringBuilder(text);
        int start = content.length();
        for (String tag : tags) {
            content.append(tag);
        }
        SpannableString spannableString = new SpannableString(content);
        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
            RadiusBackgroundSpan radiusBackgroundSpan =
                    new RadiusBackgroundSpan(getContext(), Color.RED, Color.BLACK,
                            BaseUtil.dp2px(getContext(), 16),
                            i == 0 ? marginFirst : marginCommon,
                            textSize, (int) getTextSize());
            spannableString.setSpan(radiusBackgroundSpan, start, start + tag.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            start = start + tag.length();
        }
        setText(spannableString);
    }

    private String getEllipsizeText(String old, int usedWidth) {
        if (TextUtils.isEmpty(old)) {
            return null;
        }
        int maxLines = TextViewCompat.getMaxLines(this);
        if (maxLines == Integer.MAX_VALUE) {
            return old;
        }
        if (maxLines < 0) {
            maxLines = 0;
        }
        //有效文本展示区域
        int availableWidth = maxLines * (getWidth() -
                getPaddingLeft() - getPaddingRight())
                - usedWidth - (int) getTextSize() / 2 * (maxLines - 1);
        return (String) TextUtils.ellipsize(old, getPaint(), availableWidth, TextUtils.TruncateAt.END);
    }
}
