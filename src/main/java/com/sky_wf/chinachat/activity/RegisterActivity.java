package com.sky_wf.chinachat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.utils.Debugger;

/**
 * @Date : 2018/5/10
 * @Author : WF
 * @Description :
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener
{
    private Button btn_register;
    private final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Debugger.d(TAG, ">>onCreate<<");
        findViewById();
        initViews();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Debugger.d(TAG, ">>onResume<<");
    }

    private void findViewById()
    {
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    private void initViews()
    {
        btn_register.setEnabled(true);
        btn_register.setOnClickListener(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Debugger.d(TAG, ">>onStop<<");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Debugger.d(TAG, ">>onDestroy<<");
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.btn_register:
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                finish();
                break;

        }
    }
}
