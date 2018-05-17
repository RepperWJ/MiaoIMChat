package com.sky_wf.chinachat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.utils.Debugger;

/**
 * @Date : 2018/5/7
 * @Author : WF
 * @Description :
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    private Button btn_login;
    private Button btn_gt_register;
    private TextView txt_title;
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Debugger.d(TAG, ">>onCreate<<");
        findViewById();
        initViews();

    }

    private void initViews() {
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText("登录");
        btn_gt_register.setEnabled(true);
        btn_login.setEnabled(true);
        btn_login.setOnClickListener(this);
        btn_gt_register.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debugger.d(TAG, ">>onResume<<");
    }

    private void findViewById()
    {
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_gt_register = (Button)findViewById(R.id.btn_gt_register);
        txt_title = (TextView)findViewById(R.id.txt_title);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Debugger.d(TAG, ">>onStop<<");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debugger.d(TAG, ">>onDestroy<<");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.btn_login:
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                finish();
                break;
            case R.id.btn_gt_register:
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                finish();
                break;
        }
    }
}
