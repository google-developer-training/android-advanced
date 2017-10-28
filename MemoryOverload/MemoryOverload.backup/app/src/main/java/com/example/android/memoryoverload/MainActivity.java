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

package com.example.android.memoryoverload;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Demo app to fill up available memory.
 * After the app opens, tap to allocate rows of new objects.
 * When the memory available to the app is used up, the app will crash.
 * Used for demonstrating Android Profiler tools.
 */

public class MainActivity extends AppCompatActivity {

    public static final int NO_OF_TEXTVIEWS_ADDED = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Adds a new row of text views when the floating action button is pressed.
     */
    public void addRowOfTextViews(View view) {

        LinearLayout root = findViewById(R.id.rootLinearLayout);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearLayoutParams);

        LinearLayout.LayoutParams textViewParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textViews[] = new TextView[NO_OF_TEXTVIEWS_ADDED];

        for (int i = 0; i < NO_OF_TEXTVIEWS_ADDED; i++) {
            textViews[i] = new TextView(this);
            textViews[i].setLayoutParams(textViewParams);
            textViews[i].setText(String.valueOf(i));
            textViews[i].setBackgroundColor(getRandomColor());
            linearLayout.addView(textViews[i]);
        }
        root.addView(linearLayout);
    }

    /**
     * Creates a random color for background color of the text view.
     */
    private int getRandomColor() {
        Random r = new Random();
        int red = r.nextInt(255);
        int green = r.nextInt(255);
        int blue = r.nextInt(255);

        return Color.rgb(red, green, blue);
    }
}
