package com.sky_wf.chinachat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.sky_wf.chinachat.chat.listener.CallBakcListener;
import com.sky_wf.chinachat.chat.manager.ChatManager;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.SharedUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * @Date : 2018/6/5
 * @Author : WF
 * @Description :用户信息编辑
 */
public class EditUserNameActivity extends BaseActivity implements CallBakcListener
{

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_user_icon)
    ImageView imgUserIcon;
    @BindView(R.id.img_user_icon_edit)
    ImageView imgUserIconEdit;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.btn_start)
    Button btnStart;
    private Context context;
    private final String TAG = "EditUserNameActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_edit_userinfo);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        context = this;
        ChatManager.getInstance().setCallBackListener(this);
    }

    @Override
    protected void initTitle()
    {
        txtTitle.setText(R.string.app_name);
        txtTitle.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener()
    {
        etUsername.addTextChangedListener(new UserTextWatcher());
    }

    @OnClick({ R.id.img_user_icon_edit, R.id.btn_start })
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.img_user_icon_edit:
                break;
            case R.id.btn_start:
                gtMain();
                break;
        }
    }

    private void gtMain()
    {
        ChatManager.getInstance().updateNickName(etUsername.getText().toString());
    }

    @Override
    public void onSuccess() {
        SharedUtils.getInstance(context).putString(Constansts.USERNAME,
                etUsername.getText().toString());
        Observable.timer(1,TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Intent intent = new Intent();
                intent.setClass(EditUserNameActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
            }
        });
    }

    @Override
    public void onFailed(Exception e) {

    }

    class UserTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (etUsername.getText().toString().length() > 0)
            {
                btnStart.setBackgroundResource(R.drawable.btn_bg_green);
                btnStart.setTextColor(0xFFFFFFFF);
                btnStart.setEnabled(true);
            } else
            {
                btnStart.setBackgroundResource(R.drawable.btn_enable_green);
                btnStart.setTextColor(0xFFD0EFC6);
                btnStart.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }
}
