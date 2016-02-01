package com.ksls.funmall;

import android.os.Bundle;

import com.ksls.funmall.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class ChatActivity extends BaseActivity {

    private String open_id;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://121.40.97.183:4000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.on("receive-message", onReceiveMessage);
        mSocket.on("receive-history", onReceiveHistory);
        mSocket.on("show-status", onShowStatus);

        mSocket.connect();

        Integer user_id = appManager.getLoginUser().optInt("id");
        open_id = getIntent().getExtras().getString("open_id");

        try {
            JSONObject obj = new JSONObject();
            obj.put("user_id", user_id);
            obj.put("target_id", open_id);
            obj.put("user_type", 2);
            obj.put("reset_flag", 1);
            mSocket.emit("online", obj.toString());

            obj.remove("reset_flag");
            mSocket.emit("show-history", obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("receive-message", onReceiveMessage);
        mSocket.off("receive-history", onReceiveHistory);
        mSocket.off("show-status", onShowStatus);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onConnect");
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onDisconnect");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onConnectError");
        }
    };

    private Emitter.Listener onReceiveMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onReceiveMessage");
        }
    };

    private Emitter.Listener onReceiveHistory = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            System.out.println("+++++++++++++++++++++ onReceiveHistory");
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(String.valueOf(args[0]));
                        JSONArray messages = data.optJSONArray("results");
                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject message = new JSONObject(messages.optString(i));
                            System.out.println(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onShowStatus = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onShowStatus");
        }
    };
}
