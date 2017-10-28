/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.scorekeeplocale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Implements a basic score keeper with plus and minus buttons for 2 teams.
 * - Clicking the plus button increments the score by 1.
 * - Clicking the minus button decrements the score by 1.
 */
public class MainActivity extends AppCompatActivity {
    private int mScore1 = 0;
    private int mScore2 = 0;
    private boolean mNightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNightMode = getIntent().getBooleanExtra("nightMode", false);
        if(mNightMode){
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (mNightMode) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else{
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.night_mode){
            Intent intent = new Intent(this, MainActivity.class);
            if(!mNightMode){
                intent.putExtra("nightMode",true);
            } else{
                intent.putExtra("nightMode",false);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void minus1(View view) {
        mScore1--;
        TextView score_text = (TextView)findViewById(R.id.score_1);
        if (score_text != null) {
            score_text.setText(String.valueOf(mScore1));
        }
    }

    public void plus1(View view) {
        mScore1++;
        TextView score_text = (TextView)findViewById(R.id.score_1);
        if (score_text != null) {
            score_text.setText(String.valueOf(mScore1));
        }
    }

    public void minus2(View view) {
        mScore2--;
        TextView score_text = (TextView)findViewById(R.id.score_2);
        if (score_text != null) {
            score_text.setText(String.valueOf(mScore2));
        }
    }

    public void plus2(View view) {
        mScore2++;
        TextView score_text = (TextView)findViewById(R.id.score_2);
        if (score_text != null) {
            score_text.setText(String.valueOf(mScore2));
        }
    }


}
