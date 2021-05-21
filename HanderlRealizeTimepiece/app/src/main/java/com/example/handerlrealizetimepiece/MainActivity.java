package com.example.handerlrealizetimepiece;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    TextView tv_time,tv_stoptime,tv_tab;
    ImageButton img_start;
    private Boolean flat = false;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_time = findViewById(R.id.tv_time);
        img_start = findViewById(R.id.img_start);
        tv_stoptime = findViewById(R.id.tv_stoptime);
        tv_tab = findViewById(R.id.tv_tab);
        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","开始");
                tv_tab.setText("工作中");
                img_start.setBackgroundResource(R.drawable.pause);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        int time = 1;
                        flat = !flat;
                        while (flat){
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();

                            }
                           Message message = new Message();
                            message.arg1 = time;
                            handler.sendMessage(message);
                            time++;
                        }
                        if (flat == false){
                            tv_tab.setText("计时器");
                            img_start.setBackgroundResource(R.drawable.start);

                        }
                    }
                }.start();
            }
        });
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int min,sec;
            min = msg.arg1/60;
            sec = msg.arg1%60;
            String time1 = (min<10?"0"+min:""+min)+":"+(sec<10?"0"+sec:""+sec);
            tv_time.setText(time1+"");
            if (flat == false){
                tv_stoptime.setText("用时"+(min<10?"0"+min:""+min)+":"+(sec<10?"0"+sec:""+sec));
                tv_time.setText("00:00");
            }
        }
    };
}