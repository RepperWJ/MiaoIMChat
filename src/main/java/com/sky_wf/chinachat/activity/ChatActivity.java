package com.sky_wf.chinachat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.base.BaseActivity;
import com.sky_wf.chinachat.chat.utils.ChatConstants;
import com.sky_wf.chinachat.chat.views.PaseteEditText;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Date : 2018/6/8
 * @Author : WF
 * @Description :
 */
public class ChatActivity extends BaseActivity
{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.btn_mode_voice)
    Button btnVoiceMode;
    @BindView(R.id.btn_keyboard_mode)
    Button btnKeyboardMode;
    @BindView(R.id.btn_press_to_speak)
    LinearLayout btnPressToSpeak;
    @BindView(R.id.et_message)
    PaseteEditText etMessage;
    @BindView(R.id.iv_emotions_normal)
    ImageView ivEmotionsNormal;
    @BindView(R.id.iv_emotions_enable)
    ImageView ivEmotionsEnable;
    @BindView(R.id.et_layout)
    RelativeLayout etLayout;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.linear_chat_bottom)
    LinearLayout linearChatBottom;
    @BindView(R.id.chat_vPager)
    ViewPager chatVPager;
    @BindView(R.id.chat_face_container)
    LinearLayout chatFaceContainer;
    @BindView(R.id.view_photo)
    LinearLayout viewPhoto;
    @BindView(R.id.view_camera)
    LinearLayout viewCamera;
    @BindView(R.id.view_location)
    LinearLayout viewLocation;
    @BindView(R.id.view_file)
    LinearLayout viewFile;
    @BindView(R.id.view_audio)
    LinearLayout viewAudio;
    @BindView(R.id.view_video)
    LinearLayout viewVideo;
    @BindView(R.id.chat_tools_container)
    LinearLayout chatToolsContainer;
    @BindView(R.id.more)
    LinearLayout more;
    @BindView(R.id.chat_bottom_bar)
    LinearLayout chatBottomBar;
    @BindView(R.id.pg_load_more)
    ProgressBar pgLoadMore;
    @BindView(R.id.chat_content)
    RecyclerView chatContent;
    @BindView(R.id.img_chat_talk)
    ImageView imgChatTalk;
    @BindView(R.id.recording_voice)
    TextView recordingVoice;
    @BindView(R.id.view_talk)
    LinearLayout viewTalk;

    private final int pageSize = 20;
    private String username;
    private int type;
    private int group_number;
    private String chat_id;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        getIntentData();
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.acquireWakeLock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.releaseWakeLock();
    }

    private void getIntentData()
    {
        Intent intent = getIntent();
        chat_id = intent.getStringExtra(Constansts.User_ID);
        username = intent.getStringExtra(Constansts.USERNAME);
        type = intent.getIntExtra(Constansts.TYPE, 0);

        if (type == ChatConstants.CHAT_GROUP)
        {
            group_number = intent.getIntExtra(Constansts.GROUP_NUMBER, 0);
        } else
        {
            if (null == username)
            {
                username = "好友";
            }
        }
        Log.d("wftt", "chat_id==" + chat_id + "username==" + username + "type==" + type
                + "groupnumber==" + group_number);
    }

    @Override
    protected void initTitle()
    {
        txtLeft.setVisibility(View.VISIBLE);
        if (type == ChatConstants.CHAT_GROUP)
        {
            txtLeft.setText(username + "(" + group_number + ")");
        } else
        {
            txtLeft.setText(username);
        }
        imgBack.setVisibility(View.VISIBLE);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.more);

    }

    @Override
    protected void setListener()
    {
        etMessage.addTextChangedListener(new pasteEditTextWatcher());
    }

    @OnClick({ R.id.img_back, R.id.img_right, R.id.btn_keyboard_mode, R.id.btn_press_to_speak,
            R.id.iv_emotions_normal, R.id.iv_emotions_enable, R.id.btn_more, R.id.btn_send,
            R.id.chat_face_container, R.id.view_photo, R.id.view_camera, R.id.view_location,
            R.id.view_file, R.id.view_audio, R.id.view_video, R.id.chat_tools_container, R.id.more,
            R.id.img_chat_talk, R.id.et_message,R.id.btn_mode_voice})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back:
                Utils.finish(this);
                break;
            case R.id.img_right:
                break;
            case R.id.btn_keyboard_mode:
                handleModeKeyboard();
                break;
            case R.id.btn_press_to_speak:
                break;
            case R.id.iv_emotions_normal:
                handleEmotionNormal();
                break;
            case R.id.iv_emotions_enable:
                handleEmotionEnable();
                break;
            case R.id.btn_more:
                handleBtnMore();
                break;
            case R.id.btn_send:
                break;
            case R.id.chat_face_container:
                break;
            case R.id.view_photo:
                break;
            case R.id.view_camera:
                break;
            case R.id.view_location:
                break;
            case R.id.view_file:
                break;
            case R.id.view_audio:
                break;
            case R.id.view_video:
                break;
            case R.id.chat_tools_container:
                break;
            case R.id.more:
                break;
            case R.id.img_chat_talk:
                break;
            case R.id.et_message:
                handlePasteEdit();
                break;
            case R.id.btn_mode_voice:
                handleModeVoice();
                break;
        }
    }

    // 显示表情
    private void handleEmotionNormal()
    {
        more.setVisibility(View.VISIBLE);
        ivEmotionsNormal.setVisibility(View.GONE);
        ivEmotionsEnable.setVisibility(View.VISIBLE);
        chatToolsContainer.setVisibility(View.GONE);
        chatFaceContainer.setVisibility(View.VISIBLE);
        hideKeyBoard();
        etLayout.setVisibility(View.VISIBLE);
        btnPressToSpeak.setVisibility(View.GONE);
        etMessage.requestFocus();

    }

    // 隐藏表情
    private void handleEmotionEnable()
    {
        more.setVisibility(View.GONE);
        ivEmotionsNormal.setVisibility(View.VISIBLE);
        ivEmotionsEnable.setVisibility(View.GONE);
        chatToolsContainer.setVisibility(View.VISIBLE);
        chatFaceContainer.setVisibility(View.GONE);
    }

    // 点击加号
    private void handleBtnMore()
    {
        if (more.getVisibility() == View.VISIBLE)
        {
            if (chatFaceContainer.getVisibility() == View.VISIBLE)
            {
                chatFaceContainer.setVisibility(View.GONE);
                chatToolsContainer.setVisibility(View.VISIBLE);
                ivEmotionsNormal.setVisibility(View.VISIBLE);
                ivEmotionsEnable.setVisibility(View.GONE);
            } else
            {
                more.setVisibility(View.GONE);
            }
        } else
        {
            more.setVisibility(View.VISIBLE);
            hideKeyBoard();
            chatFaceContainer.setVisibility(View.GONE);
            chatToolsContainer.setVisibility(View.VISIBLE);
            btnPressToSpeak.setVisibility(View.GONE);
            etLayout.setVisibility(View.VISIBLE);
        }

    }

    // 点击输入框隐藏底部栏，显示输入法
    private void handlePasteEdit()
    {
        if (more.getVisibility() == View.VISIBLE)
        {
            more.setVisibility(View.GONE);
            ivEmotionsNormal.setVisibility(View.VISIBLE);
            ivEmotionsEnable.setVisibility(View.GONE);
        }
    }

    // 处理键盘按钮
    private void handleModeKeyboard()
    {
        etLayout.setVisibility(View.VISIBLE);
        more.setVisibility(View.GONE);
        btnKeyboardMode.setVisibility(View.GONE);
        btnVoiceMode.setVisibility(View.VISIBLE);
        etMessage.requestFocus();
        btnPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(etMessage.getText().toString()))
        {
            btnMore.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.GONE);
        } else
        {
            btnMore.setVisibility(View.GONE);
            btnSend.setVisibility(View.VISIBLE);
        }

    }

    //处理语音按钮
    private void handleModeVoice()
    {
        hideKeyBoard();
        more.setVisibility(View.GONE);
        etLayout.setVisibility(View.GONE);
        btnKeyboardMode.setVisibility(View.VISIBLE);
        btnVoiceMode.setVisibility(View.GONE);
        btnPressToSpeak.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.GONE);
        btnMore.setVisibility(View.VISIBLE);
        ivEmotionsNormal.setVisibility(View.VISIBLE);
        ivEmotionsEnable.setVisibility(View.GONE);
        chatFaceContainer.setVisibility(View.GONE);

    }

    class pasteEditTextWatcher implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!TextUtils.isEmpty(s))
            {
                btnMore.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
            }else {
                btnSend.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
