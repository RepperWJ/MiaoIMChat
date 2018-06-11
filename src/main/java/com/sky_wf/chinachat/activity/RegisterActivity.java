package com.sky_wf.chinachat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.base.BaseActivity;
import com.sky_wf.chinachat.chat.entity.LoginExceptionHandle;
import com.sky_wf.chinachat.chat.listener.CallBakcListener;
import com.sky_wf.chinachat.chat.listener.SendCodeListener;
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
 * @Date : 2018/5/10
 * @Author : WF
 * @Description :用户注册
 */
public class RegisterActivity extends BaseActivity implements CallBakcListener, SendCodeListener
{

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.btn_captcha)
    Button btnCaptcha;
    @BindView(R.id.et_login_pw)
    EditText etLoginPw;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Debugger.d(TAG, "<<onCreate>>");
        ChatManager.getInstance().setCallBackListener(this);
        ChatManager.getInstance().setCodeListener(this);
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

    @Override
    public void initTitle()
    {
        txtTitle.setText(R.string.register_title);
        txtTitle.setVisibility(View.VISIBLE);
        imgBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener()
    {
        etRegisterPhone.addTextChangedListener(new TelTextChange());
        etLoginPw.addTextChangedListener(new PwdTextWatch());
        etLoginPhone.addTextChangedListener(new CodeTextWatch());

    }

    @OnClick({ R.id.img_back, R.id.btn_captcha, R.id.btn_register })
    public void onViewClicked(View view)
    {
        Intent intent = new Intent();
        switch (view.getId())
        {
            case R.id.img_back:
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
                break;
            case R.id.btn_captcha:
                sendSmsCode();

                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void sendSmsCode() {
        if(isNetAvaliable()) {
            ChatManager.getInstance().sendSmsCode(this, etRegisterPhone.getText().toString());
        }else {
            Utils.showShortToast(btnRegister,getString(R.string.net_error));
        }
    }

    private void register()
    {
        setButtonEnable(false);
        if(isNetAvaliable()) {
            ChatManager.getInstance().createACount(this, btnRegister,
                    etRegisterPhone.getText().toString(), etLoginPw.getText().toString(),
                    etLoginPhone.getText().toString());
        }else {
            Utils.showShortToast(btnRegister,getString(R.string.net_error));
        }
    }

    @Override
    public void onSuccess()
    {

        setButtonEnable(true);
        Observable.timer(1,TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, EditUserNameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
            }
        });

    }

    @Override
    public void onFailed(Exception e)
    {
        setButtonEnable(true);
        LoginExceptionHandle.handleErrorMsg(btnCaptcha,e);
    }


    @Override
    public void onSendCodeSucess()
    {
        Utils.showShortToast(btnRegister, getString(R.string.send_code));
        new MyCount(60000,1000).start();
    }

    @Override
    public void onSendCodeFailed()
    {
        Utils.showShortToast(btnRegister, getString(R.string.error_send_code));
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

            String phone = etRegisterPhone.getText().toString();
            boolean isCode = (etLoginPhone.getText().toString().length() == 6);
            boolean isPwd = (etLoginPw.getText().toString().length() > 5)
                    && (etLoginPw.getText().toString().length() < 13);
            if (phone.length() == 11)
            {
                if (Utils.isMobileNomber(phone))
                {
                    btnCaptcha.setBackgroundResource(R.drawable.btn_bg_green);
                    btnCaptcha.setTextColor(0xFFFFFFFF);
                    btnCaptcha.setEnabled(true);
                    if (isCode && isPwd)
                    {
                        btnRegister.setBackgroundResource(R.drawable.btn_bg_green);
                        btnRegister.setTextColor(0xFFFFFFFF);
                        btnRegister.setEnabled(true);
                    }
                } else
                {
                    etRegisterPhone.requestFocus();
                    Utils.showLongToast(etLoginPhone, getString(R.string.error_register_phone));
                }
            } else
            {
                btnCaptcha.setBackgroundResource(R.drawable.btn_enable_green);
                btnCaptcha.setTextColor(0xFFD0EFC6);
                btnCaptcha.setEnabled(false);
                btnRegister.setBackgroundResource(R.drawable.btn_enable_green);
                btnRegister.setTextColor(0xFFD0EFC6);
                btnRegister.setEnabled(false);
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
            boolean isPhone = (etRegisterPhone.getText().toString().length() > 0);
            boolean isCode = (etLoginPhone.getText().toString().length() == 6);
            boolean isPwd = (etLoginPw.getText().toString().length() > 5)
                    && (etLoginPw.getText().toString().length() < 13);
            if (isPhone && isCode && isPwd)
            {
                btnRegister.setBackgroundResource(R.drawable.btn_bg_green);
                btnRegister.setTextColor(0xFFFFFFFF);
                btnRegister.setEnabled(true);
            } else
            {
                btnRegister.setBackgroundResource(R.drawable.btn_enable_green);
                btnRegister.setTextColor(0xFFD0EFC6);
                btnRegister.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    // 验证码输入监听器
    class CodeTextWatch implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            boolean isPhone = (etRegisterPhone.getText().toString().length() > 0);
            boolean isCode = (etLoginPhone.getText().toString().length() == 6);
            boolean isPwd = (etLoginPw.getText().toString().length() > 5)
                    && (etLoginPw.getText().toString().length() < 13);

            if (isPhone && isCode && isPwd)
            {
                btnRegister.setBackgroundResource(R.drawable.btn_bg_green);
                btnRegister.setTextColor(0xFFFFFFFF);
                btnRegister.setEnabled(true);
            } else
            {
                btnRegister.setBackgroundResource(R.drawable.btn_enable_green);
                btnRegister.setTextColor(0xFFD0EFC6);
                btnRegister.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    //定义一个计时器
    private class MyCount extends CountDownTimer
    {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
          btnCaptcha.setEnabled(false);
          btnCaptcha.setBackgroundResource(R.drawable.btn_send_code);
          btnCaptcha.setText("("+millisUntilFinished/1000+")");
        }

        @Override
        public void onFinish()
        {
            btnCaptcha.setBackgroundResource(R.drawable.btn_bg_green);
            btnCaptcha.setEnabled(true);
            btnCaptcha.setText("发送验证码");
        }
    }

    private void setButtonEnable(boolean enable)
    {
        btnCaptcha.setEnabled(enable);
        btnRegister.setEnabled(enable);
        imgBack.setEnabled(enable);
    }
}
