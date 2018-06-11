package com.sky_wf.chinachat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.base.BaseActivity;
import com.sky_wf.chinachat.chat.entity.LoginExceptionHandle;
import com.sky_wf.chinachat.chat.listener.CallBakcListener;
import com.sky_wf.chinachat.chat.manager.ChatManager;
import com.sky_wf.chinachat.utils.Debugger;
import com.sky_wf.chinachat.utils.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * @Date : 2018/5/7
 * @Author : WF
 * @Description :登录
 */
public class LoginActivity extends BaseActivity implements CallBakcListener
{

    private final String TAG = "LoginActivity";
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pw)
    EditText etLoginPw;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_problem)
    TextView tvProblem;
    @BindView(R.id.btn_gt_register)
    Button btnGtRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Debugger.d(TAG, "<<onCreate>>");

    }

    @Override
    protected void initTitle()
    {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(R.string.login_title);
    }

    @Override
    protected void setListener()
    {
        etLoginPhone.addTextChangedListener(new TelTextChange());
        etLoginPw.addTextChangedListener(new PwdTextWatch());
        ChatManager.getInstance().setCallBackListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Debugger.d(TAG, "<<onResume>>");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Debugger.d(TAG, "<<onStop>>");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Debugger.d(TAG, "<<onDestroy>>");
    }

    private void gtRegister()
    {
        MyApplication.addAcitivtyList(this);
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        // finish();
    }

    private void gtChat()
    {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        finish();
    }

    @OnClick({ R.id.btn_login, R.id.tv_problem, R.id.btn_gt_register })
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_login:
                gtLogin();
                break;
            case R.id.tv_problem:
                break;
            case R.id.btn_gt_register:
                gtRegister();
                break;
        }
    }

    private void gtLogin()
    {
        if (isNetAvaliable())
        {
            ChatManager.getInstance().login(this, etLoginPhone.getText().toString(),
                    etLoginPw.getText().toString());
        } else
        {
            Utils.showLongToast(btnLogin, getString(R.string.net_error));
        }
    }

    @Override
    public void onSuccess()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Utils.showLongToast(btnLogin, getString(R.string.login_sucess));
                Observable.timer(3, TimeUnit.SECONDS).subscribe(new Action1<Long>()
                {
                    @Override
                    public void call(Long aLong)
                    {
                        gtChat();
                    }
                });
            }
        });

    }

    @Override
    public void onFailed(final Exception e)
    {

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                LoginExceptionHandle.handleErrorMsg(btnLogin, e);
            }
        });

    }

    // 手机号EditText监听器
    class TelTextChange implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // Debugger.d(TAG, ">>TelTextChange<<onTextChanged");
            String phone = etLoginPhone.getText().toString();
            boolean isPwd = (etLoginPw.getText().toString().length() > 5)
                    && (etLoginPw.getText().toString().length() < 13);
            if (phone.length() == 11)
            {
                if (Utils.isMobileNomber(phone))
                {
                    if (isPwd)
                    {
                        btnLogin.setBackgroundResource(R.drawable.btn_bg_green);
                        btnLogin.setTextColor(0xFFFFFFFF);
                        btnLogin.setEnabled(true);
                    }

                } else
                {
                    etLoginPhone.requestFocus();
                    Utils.showLongToast(etLoginPhone, getString(R.string.error_register_phone));
                }
            } else
            {

                btnLogin.setBackgroundResource(R.drawable.btn_enable_green);
                btnLogin.setTextColor(0xFFD0EFC6);
                btnLogin.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s)
        {
        }
    }

    // 密码输入监听器
    class PwdTextWatch implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            boolean isPhone = (etLoginPhone.getText().toString().length() == 11);
            boolean isPwd = (etLoginPw.getText().toString().length() > 5)
                    && (etLoginPw.getText().toString().length() < 13);
            if (isPhone && isPwd)
            {
                btnLogin.setBackgroundResource(R.drawable.btn_bg_green);
                btnLogin.setTextColor(0xFFFFFFFF);
                btnLogin.setEnabled(true);
            } else
            {
                btnLogin.setBackgroundResource(R.drawable.btn_enable_green);
                btnLogin.setTextColor(0xFFD0EFC6);
                btnLogin.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }
}
