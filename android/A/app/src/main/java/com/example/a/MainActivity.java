package com.example.a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String s="丰富的是否达到定丰" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.content);
        textView.setText(s);
        textView.post(new Runnable() {
            @Override
            public void run() {
                List<String> tags=new ArrayList<>();
                tags.add("高能");
                tags.add("悬一悬一");
                tags.add("的");
                int width = 0;
                if (tags.size()!=0){
                    int tagsize=tags.size();
                    int textLength = 0;
                    for (String tag : tags) {
                        textLength+=tag.length();
                    }
                    width = (10*textLength+12*tagsize + 12 + 4 * (tagsize-1))*3;
                }
                StringBuilder content = new StringBuilder(getText(s, width, textView));
                int start = content.length();
                for (String tag : tags) {
                    content.append(tag);
                }
                SpannableString spannableString = new SpannableString(content);
                int end = content.length();
                for (int i = 0; i <tags.size(); i++) {
                    String tag = tags.get(i);
                    RadiusBackgroundSpan radiusBackgroundSpan = new RadiusBackgroundSpan(MainActivity.this, Color.RED, Color.BLACK, 48, (i==0 ? 36:12), 30,45);
//                    TagSpan radiusBackgroundSpan = new TagSpan(Color.RED, BaseUtil.dp2px(MainActivity.this,10),
//                            BaseUtil.dp2px(MainActivity.this,16),BaseUtil.dp2px(MainActivity.this,12));
                    spannableString.setSpan(radiusBackgroundSpan, start,start+tag.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    start=start+tag.length();
                }

                textView.setText(spannableString);
            }
        });
        List<String> tags=new ArrayList<>();
        tags.add("高能");
        tags.add("悬一悬一");
        tags.add("的");
        EllipsizeTagTextView ellipsizeTagTextView = findViewById(R.id.desc);
        ellipsizeTagTextView.setText(s);
        ellipsizeTagTextView.addTags(tags, BaseUtil.dp2px(MainActivity.this,10),BaseUtil.dp2px(MainActivity.this,12),BaseUtil.dp2px(MainActivity.this,4)
                ,BaseUtil.dp2px(MainActivity.this,12));
    }

    private String getText(String old, int usedWidth, TextView view) {
        if (TextUtils.isEmpty(old)) {
            return null;
        }
        int maxLines = TextViewCompat.getMaxLines(view);
        if (maxLines == Integer.MAX_VALUE) {
            return old;
        }
        if (maxLines < 0) {
            maxLines = 0;
        }
        //有效文本展示区域
        int availableWidth = maxLines * (view.getWidth() -
                view.getPaddingLeft() - view.getPaddingRight())
                - usedWidth - (int) view.getTextSize() / 3 * (maxLines - 1);
        return (String) TextUtils.ellipsize(old, view.getPaint(), availableWidth, TextUtils.TruncateAt.END);

    }
}
