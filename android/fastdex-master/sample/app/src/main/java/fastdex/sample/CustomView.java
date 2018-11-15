package fastdex.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.typ0520.fastdex.sample.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tong on 17/3/12.
 */
public class CustomView extends RelativeLayout {
    @BindView(R.id.tv)  TextView tv;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_custom,this);
        ButterKnife.bind(this);

        tv.setText(getResources().getString(R.string.s3) + " -00");
        MainActivity.aa();
    }
}
