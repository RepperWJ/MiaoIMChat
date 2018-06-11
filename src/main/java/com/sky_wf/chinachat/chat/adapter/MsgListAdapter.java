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
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.chat.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Date : 2018/6/6
 * @Author : WF
 * @Description :消息列表Adapter
 */
public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MsgListItemViewHolder>
{
    private OnItemClickListener clickListener;
    private List<EMConversation> conversationList;
    private OnItemClickListener listener;
    private Context context;

    public MsgListAdapter(Context context, List<EMConversation> conversationList)
    {
        this.context = context;
        this.conversationList = conversationList;
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
    public void onBindViewHolder(@NonNull final MsgListItemViewHolder msgListItemViewHolder, int i)
    {
        EMConversation conversation = conversationList.get(i);
        Log.d("wftt>>>>>>", conversation.conversationId());
        if (conversation.isGroup())
        {
            EMGroup group = EMClient.getInstance().groupManager()
                    .getGroup(conversation.conversationId());
            Log.d("wftt>>>>>>", group.getGroupId() + "<<>>" + group.getGroupName());
        } else
        {

        }

        if (null != listener)
        {
            msgListItemViewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = msgListItemViewHolder.getLayoutPosition();
                    listener.onClick(msgListItemViewHolder.itemView, pos);
                }
            });

            msgListItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = msgListItemViewHolder.getLayoutPosition();
                    listener.onLongClick(msgListItemViewHolder.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount()
    {
        return conversationList.size();
    }

    public static class MsgListItemViewHolder extends RecyclerView.ViewHolder
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
        }

    }
}
