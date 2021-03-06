package com.ksls.funmall.adapter;

import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.ksls.funmall.R;
import com.ksls.funmall.entity.ChatMsgEntity;

public class ChatMsgViewAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	private List<ChatMsgEntity> coll;

	private AQuery aq;
	private Context ctx;

	private LayoutInflater mInflater;
	private MediaPlayer mMediaPlayer = new MediaPlayer();

	public ChatMsgViewAdapter(AQuery aq, Context context, List<ChatMsgEntity> coll) {
		this.aq = aq;
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);
		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.ivUserHead = (ImageView) convertView.findViewById(R.id.iv_userhead);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvSendTime.setText(entity.getDate());
		if (entity.getText().contains(".amr")) {
			viewHolder.tvContent.setText("");
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chatto_voice_playing, 0);
			viewHolder.tvTime.setText(entity.getTime());
		} else {
			viewHolder.tvContent.setText(entity.getText());			
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			viewHolder.tvTime.setText("");
		}
		viewHolder.tvContent.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (entity.getText().contains(".amr")) {
					playMusic(android.os.Environment.getExternalStorageDirectory()+"/"+entity.getText()) ;
				}
			}
		});
		aq.id(viewHolder.ivUserHead).image(entity.getHead(), true, true, 0, R.drawable.header_logo);

		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public ImageView ivUserHead;
		public TextView tvContent;
		public TextView tvTime;
		public boolean isComMsg = true;
	}

	private void playMusic(String name) {
		try {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(name);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stop() {

	}
}
