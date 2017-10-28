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

package com.example.android.largeimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int toggle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Click handler that swaps between a large and smaller image.
     * Used with performance tools to show memory and GPU performance.
     *
     * There are two "dinosaur" images in res/drawable.
     *     - dinosaur_medium is about 495K and the default
     *     - dinosaur_large is about 1M and you should use it if your device
     *       can handle it as you will get clearer profiling results.
     *
     * @param view
     */
    public void changeImage(View view){
        if (toggle == 0) {
            view.setBackgroundResource(R.drawable.dinosaur_medium);
            toggle = 1;
        } else {
            // Add code to let your app sleep for two screen refreshes
            // before switching the background to the smaller image.
            // This means that instead of refreshing the screen every 16 ms,
            // your app now refreshes every 48 ms with new content.
            // This will be reflected in the bars displayed by the
            // Profile GPU Rendering tool.
            try {
                Thread.sleep(32); // two refreshes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            view.setBackgroundResource(R.drawable.ankylo);
            toggle = 0;
        }
    }
}
