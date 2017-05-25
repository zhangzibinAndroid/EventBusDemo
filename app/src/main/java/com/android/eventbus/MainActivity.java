package com.android.eventbus;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_jump,btn_jump_send;
    private TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_jump = (Button) findViewById(R.id.btn_jump);
        btn_jump_send = (Button) findViewById(R.id.btn_jump_send);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        btn_jump.setOnClickListener(this);
        btn_jump_send.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jump:
                jumpActivity();
                break;
            case R.id.btn_jump_send:
                EventBus.getDefault().postSticky(new StickyEvent("这是发送的粘性数据"));
                jumpActivity();
                break;
        }
    }

    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageEventBus event){
        tv_msg.setText(event.message);
    }


    private void jumpActivity() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
