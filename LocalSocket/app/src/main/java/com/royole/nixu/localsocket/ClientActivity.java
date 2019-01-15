package com.royole.nixu.localsocket;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStream;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private LocalSocket mSocket;
    private OutputStream mOut;
    private EditText mEditText = null;
    private static final String SOCKET_NAME = "com.azhengye.testsocket";//这里必须要跟TestLocalSocketServer中定义的一样
    private static final String TAG = "azhengye-Socket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        connect();
        mEditText = (EditText) findViewById(R.id.input_msg);
        findViewById(R.id.replay_btn).setOnClickListener(this);
        findViewById(R.id.stop_btn).setOnClickListener(this);
    }

    private boolean connect() {
        if (mSocket != null) {
            return true;
        }
        try {
            mSocket = new LocalSocket();//创建LocalSocket，模拟客户端
            LocalSocketAddress address = new LocalSocketAddress(SOCKET_NAME,
                    LocalSocketAddress.Namespace.ABSTRACT);
            mSocket.connect(address);//连接TestLocalSocketServer
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private boolean writeCommand(String cmdString) {
        final byte[] cmd = cmdString.getBytes();
        final int len = cmd.length;
        try {
            mOut = mSocket.getOutputStream();
            mOut.write(cmd, 0, len);
            Log.i(TAG, "ClientActivity write " + new String(cmd));
        } catch (IOException ex) {
            Log.e(TAG, "ClientActivity write error:" + ex.fillInStackTrace());
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.replay_btn:
                writeCommand(mEditText.getText().toString());
                break;
            case R.id.stop_btn:
                writeCommand("stop");
                break;
            default:
                break;
        }

    }
}
