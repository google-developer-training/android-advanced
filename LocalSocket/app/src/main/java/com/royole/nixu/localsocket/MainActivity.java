package com.royole.nixu.localsocket;

import android.content.Intent;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private LocalServerSocket mServerSocket = null;
    private LocalSocket mSocket = null;
    private TextView mShowMsg = null;
    private InputStream mInputStream = null;
    private static final String SOCKET_NAME = "com.azhengye.testsocket";
    private static final String TAG = "azhengye-Socket";

    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String dispMesg = (String) msg.obj;
            mShowMsg.setText(dispMesg);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.replay_btn);
        mShowMsg = (TextView) findViewById(R.id.display_msg);
        createServerSocket();// 创建LocalServerSocket
        new Thread(new Runnable() {
            @Override
            public void run() {
                acceptMsg();//必须要在子线程里接收消息
            }
        }).start();
    }

    private void createServerSocket() {
        if (mServerSocket == null) {
            try {
                /**注意这里new出LocalServerSocket的同时，系统层已经同步做了bind和listen。
                 * 我们看看new的过程：
                 * public LocalServerSocket(String name) throws IOException {
                 *       impl = new LocalSocketImpl();
                 *       impl.create(LocalSocket.SOCKET_STREAM);
                 *       localAddress = new LocalSocketAddress(name);
                 *       impl.bind(localAddress);
                 *       impl.listen(LISTEN_BACKLOG);
                 * }
                 */
                mServerSocket = new LocalServerSocket(SOCKET_NAME);
            } catch (IOException ex) {
                throw new RuntimeException(
                        "Error binding to local socket " + ex);
            }
        }
    }

    private void acceptMsg() {
        try {
            mSocket = mServerSocket.accept();//accept是个阻塞方法，这就是必须要在子线程接收消息的原因。
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        while (true) {
            try {
                byte[] buffer = new byte[1024];
                mInputStream = mSocket.getInputStream();
                int count = mInputStream.read(buffer);
                String key = new String(Arrays.copyOfRange(buffer, 0, count));
                Log.d(TAG, "ServerActivity mSocketOutStream==" + key);
                if ("stop".equals(key)) {
                    closeSocketResource();
                    break;
                }
                Message msg = mHandler.obtainMessage();
                msg.obj = key;
                msg.sendToTarget();
            } catch (IOException e) {
                Log.d(TAG, "exception==" + e.fillInStackTrace().getMessage());
                e.printStackTrace();
            }
        }
    }

    private void closeSocketResource() {
        closeSlient(mInputStream);
        closeSlient(mSocket);
        try {
            if (mServerSocket != null) {
                mServerSocket.close();
                mServerSocket = null;
            }
        } catch (IOException ex) {
            Log.e(TAG, "Failed closing ServerSocket" + ex.fillInStackTrace());
        }
    }

    private void closeSlient(Closeable closeable){
        try {
            if (closeable != null) {
                closeable.close();
                closeable = null;
            }
        } catch (IOException ex) {
            Log.e(TAG, "Failed closing : " + closeable);
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this,ClientActivity.class);
        startActivity(intent);
    }
}
