/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.webpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * This app toggles between a JPG and WebP formatted image.
 * There is no visible difference in quality in spite of huge
 * size savings. Use Memory Profiler and Profile GPU Rendering
 * to explore potential performance improvements by using WebP.
 */

public class MainActivity extends AppCompatActivity {

    private int toggle = 0;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.edittext);
    }

    /**
     * Click handler to swap the background image between a WebP
     * and JPG encoded image.
     *
     * @param view
     */
    public void changeBackground(View view) {
        if (toggle == 0) {
            mTextView.setBackgroundResource(R.drawable.mnt_diablo_2000_webp);
            mTextView.setText(getString(R.string.webp));
            toggle = 1;
        } else {
            mTextView.setBackgroundResource(R.drawable.mnt_diablo_2000);
            mTextView.setText(getString(R.string.jpg));
            toggle = 0;
        }
    }
}
