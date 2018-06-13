package com.sky_wf.chinachat.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.ChatActivity;
import com.sky_wf.chinachat.activity.base.BaseFragment;
import com.sky_wf.chinachat.chat.adapter.MsgListAdapter;
import com.sky_wf.chinachat.chat.listener.OnItemClickListener;
import com.sky_wf.chinachat.chat.manager.ChatManager;
import com.sky_wf.chinachat.utils.Debugger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Date : 2018/5/14
 * @Author : WF
 * @Description :Msg界面
 */
public class Fragment_Msg extends BaseFragment implements OnItemClickListener
{
    private final String TAG = "Fragment_Msg";

    @BindView(R.id.msg_list)
    RecyclerView msgList;
    @BindView(R.id.chat_net_error)
    LinearLayout lineaNetError;
    @BindView(R.id.msg_no_chatlist)
    TextView tv_no_chat;
    Unbinder unbinder;
    private MsgListAdapter msgListAdapter;
    private List<EMConversation> conversationList;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Debugger.d(TAG, "<<onAttach>>");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();
        conversationList = new ArrayList<>();
        msgListAdapter = new MsgListAdapter(context, conversationList);
        msgListAdapter.setOnItemClickListener(this);
        Debugger.d(TAG, "<<onCreate>>");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        Debugger.d(TAG, "<<onCreateView>>");
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        unbinder = ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Debugger.d(TAG, "<<onActivityCreated>>");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Debugger.d(TAG, "<<onResume>>");

    }

    @Override
    public void onStop()
    {
        super.onStop();
        Debugger.d(TAG, "<<onStop>>");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Debugger.d(TAG, "<<onDestroyView>>");
        unbinder.unbind();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Debugger.d(TAG, "<<onDestroy>>");
    }

    @Override
    protected void initView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        msgList.setLayoutManager(manager);
        // msgList.setAdapter(msgListAdapter);
        // lineaNetError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.chat_net_error)
    public void onViewClicked()
    {
        ChatManager.getInstance().openSetNetWork(context);
    }

    private void initConnactList()
    {
        conversationList.clear();
        conversationList.addAll(ChatManager.getInstance().loadConversationWithRecentChat());
        if (conversationList != null && conversationList.size() != 0)
        {
            tv_no_chat.setVisibility(View.INVISIBLE);
            // 添加一个公众号
            Debugger.d(TAG, "<<loadConversationWithRecentChat---initConnactList当前用户的会话总数>>"
                    + conversationList.size());
            msgList.setAdapter(msgListAdapter);
        } else
        {
            tv_no_chat.setVisibility(View.VISIBLE);
        }
    }

    public void refresh()
    {
        initConnactList();
    }

    @Override
    public void onClick(View view, int position)
    {
        Debugger.d("wftt", "onClick------>>" + position);
        EMConversation conversation = conversationList.get(position);
        ChatManager.getInstance().gtChat(context,conversation);
    }

    @Override
    public void onLongClick(View view, int position)
    {
        Debugger.d("wftt", "onLongClick------>>" + position);
    }
}
