package com.android.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.entity.MessageEventBus;
import com.android.entity.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_send_main,btn_get_msg;
    private TextView tv_result;

    private boolean isClicked = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_send_main = (Button) findViewById(R.id.btn_send_main);
        btn_get_msg = (Button) findViewById(R.id.btn_get_msg);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_send_main.setOnClickListener(this);
        btn_get_msg.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getStickyMsg(StickyEvent event){
        tv_result.setText(event.message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_main:
                EventBus.getDefault().post(new MessageEventBus("这是向主线程发送的消息"));
                finish();
                break;
            case R.id.btn_get_msg:
                if (isClicked){
                    isClicked = false;
                    EventBus.getDefault().register(this);
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();//移除所有粘性事件
        EventBus.getDefault().unregister(this);
    }
}
