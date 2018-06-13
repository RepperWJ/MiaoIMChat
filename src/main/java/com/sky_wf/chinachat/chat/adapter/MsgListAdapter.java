package com.sky_wf.chinachat.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;
import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.chat.entity.User;
import com.sky_wf.chinachat.chat.listener.OnItemClickListener;
import com.sky_wf.chinachat.chat.listener.QueryCallBackListener;
import com.sky_wf.chinachat.chat.manager.ChatManager;
import com.sky_wf.chinachat.chat.utils.SmileUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Date : 2018/6/6
 * @Author : WF
 * @Description :消息列表Adapter
 */
public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MsgListItemViewHolder>
{
    private OnItemClickListener clickListener;
    private List<EMConversation> conversationList;
    private static OnItemClickListener listener;
    private Context context;
    private ChatManager chatManager;

    public MsgListAdapter(Context context, List<EMConversation> conversationList)
    {
        this.context = context;
        this.conversationList = conversationList;
        chatManager = ChatManager.getInstance();
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MsgListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_msg_item,
                viewGroup, false);
        return new MsgListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MsgListItemViewHolder itemViewHolder, int i)
    {
        EMConversation conversation = conversationList.get(i);
        final String chat_ID = conversation.conversationId();
        Log.d("wftt>>>>>>", chat_ID);
        if (conversation.isGroup())
        {
            itemViewHolder.holder_item_avatar.setImageResource(R.drawable.defult_group);
            EMGroup group = EMClient.getInstance().groupManager().getGroup(chat_ID);
            if (null != group)
            {
                itemViewHolder.holder_txt_name.setText(group.getGroupName());
            } else
            {

            }

            Log.d("wftt>>>>>>", chat_ID + "<<>>" + group.getGroupName());
        } else
        {
            chatManager.QueryUser(chat_ID);
            chatManager.setQueryUserListener(new QueryCallBackListener<User>()
            {
                @Override
                public void onSucess(User user)
                {
                    MyApplication.userMap.put(chat_ID, user);
                    itemViewHolder.holder_txt_name.setText(user.getNickName());

                }

                @Override
                public void onFailed()
                {

                }
            });

        }

        int unreadMsgCount = chatManager.getUnReadMsgCount(conversation);
        if (unreadMsgCount > 0)
        {
            itemViewHolder.holder_txt_unread.setVisibility(View.VISIBLE);
            itemViewHolder.holder_txt_unread.setText(String.valueOf(unreadMsgCount));
        } else
        {
            itemViewHolder.holder_txt_unread.setVisibility(View.INVISIBLE);
        }

        int msgCount = chatManager.getMsgCount(conversation);
        if (msgCount > 0)
        {
            EMMessage lastMsg = conversation.getLastMessage();
            itemViewHolder.holder_txt_connent.setText(SmileUtils.getSmiledText(context,
                    chatManager.getLastMessageDigest(lastMsg, context)));
            itemViewHolder.holder_txt_time
                    .setText(DateUtils.getTimestampString(new Date(lastMsg.getMsgTime())));
            if (lastMsg.status() == EMMessage.Status.SUCCESS)
            {
                itemViewHolder.holder_txt_state.setText("送达");
            } else if (lastMsg.status() == EMMessage.Status.FAIL)
            {
                itemViewHolder.holder_txt_state.setText("失败");
            } else if (lastMsg.status() == EMMessage.Status.INPROGRESS)
            {

            }
        }

    }

    @Override
    public int getItemCount()
    {
        return conversationList.size();
    }

    public static class MsgListItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener
    {
        @BindView(R.id.connact_item_avatar)
        ImageView holder_item_avatar;
        @BindView(R.id.connact_unread_msg_nubmer)
        TextView holder_txt_unread;
        @BindView(R.id.connact_item)
        LinearLayout holder_linear_connact;
        @BindView(R.id.connact_txt_name)
        TextView holder_txt_name;
        @BindView(R.id.connact_txt_state)
        TextView holder_txt_state;
        @BindView(R.id.connact_txt_conntent)
        TextView holder_txt_connent;
        @BindView(R.id.connact_txt_time)
        TextView holder_txt_time;

        public MsgListItemViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int pos = this.getLayoutPosition();
            listener.onClick(this.itemView, pos);
        }

        @Override
        public boolean onLongClick(View v)
        {
            int pos = this.getLayoutPosition();
            listener.onLongClick(this.itemView, pos);
            return false;
        }
    }
}
