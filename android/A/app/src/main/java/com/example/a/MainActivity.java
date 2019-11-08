package com.example.a;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String s="丰富的是否达到定丰富的是否达到定丰富的是否达到定丰富的是否达到定丰富的是否达到定顶顶顶顶钉钉的的" + "  高能";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.content);
        textView.setText(s);
        textView.post(new Runnable() {
            @Override
            public void run() {
                String content = s;
                int ellipsisCount = textView.getLayout().getEllipsisCount(textView.getLineCount() - 1);
                Log.e("Main", "count:"+ellipsisCount);
                if (ellipsisCount > 0) {
                    content = content.substring(0, content.length() - ellipsisCount - 4) + "...  高能";
                }
                SpannableString spannableString = new SpannableString(content);
                RadiusBackgroundSpan radiusBackgroundSpan = new RadiusBackgroundSpan(Color.RED, 18);
                spannableString.setSpan(radiusBackgroundSpan, content.length() - 2, content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(spannableString);
            }
        });
    }
}
