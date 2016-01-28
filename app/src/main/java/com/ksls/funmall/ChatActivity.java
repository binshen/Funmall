package com.ksls.funmall;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class ChatActivity extends Activity {

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

        try {
            JSONObject obj = new JSONObject();
            obj.put("user_id", 2);
            obj.put("user_type", 2);
            mSocket.emit("online", obj.toString());
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
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onReceiveHistory");
        }
    };

    private Emitter.Listener onShowStatus = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("+++++++++++++++++++++ onShowStatus");
        }
    };
}
