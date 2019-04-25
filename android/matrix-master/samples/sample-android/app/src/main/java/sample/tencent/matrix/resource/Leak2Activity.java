package sample.tencent.matrix.resource;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import sample.tencent.matrix.R;

public class Leak2Activity extends AppCompatActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(10000);
            } catch (Exception e) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak2);
        handler.sendEmptyMessageDelayed(0, 10000);
    }
}
