package com.github.inazou.omikuji;

import android.support.v7.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import com.example.omikuji.omikuji.R;
import android.widget.TextView;
import java.util.Random;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.app.NotificationCompat;


public class OmikujiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView omikujiResultView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //findViewByIdをsetContentViewの前に実行するとnullが返るので注意する
        setContentView(R.layout.activity_omikuji);
        omikujiResultView = (TextView) findViewById(R.id.omikuji_result);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        omikujiResultView.setText(R.string.omikuji_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                omikuji();
            }
        }, 3000);// 3秒後におみくじを実行する
    }


    private void omikuji() {
        Random random = new Random();
        int result = random.nextInt() % 2;
        switch (result) {
            case 1:
                noticeResult(getString(R.string.omikuji_result_daikichi));
                break;
            case 0:
            default:
                noticeResult(getString(R.string.omikuji_result_kichi));
        }
        refreshLayout.setRefreshing(false);
        omikujiResultView.setText(R.string.omikuji_result_finished);


    }

    private void noticeResult(CharSequence title) {
        String message = getString(R.string.omikuji_result_message, title);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle(title).setContentText(message).setSmallIcon(R.mipmap.ic_launcher).build();
        notificationManager.notify(R.attr.notification_omikuji, notification);
    }
}