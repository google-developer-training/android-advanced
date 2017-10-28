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

package com.example.android.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass that shows a question
 * with radio buttons for providing feedback. If the user
 * clicks "Yes" the text header changes to "Article: Like".
 * If the user clicks "No" the text header changes to "Thanks".
 */
public class SimpleFragment extends Fragment {

    // The radio button choice has 3 states: 0 = yes, 1 = no,
    // 2 = default (no choice). Using only 0 and 1.
    private static final int YES = 0;
    private static final int NO = 1;

    public SimpleFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the view for the fragment.
     *
     * @param inflater           LayoutInflater to inflate any views in the fragment
     * @param container          ViewGroup of parent view to attach fragment
     * @param savedInstanceState Bundle for previous state
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment.
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        // Set the radioGroup onCheckedChanged listener.
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        View radioButton = radioGroup.findViewById(checkedId);
                        int index = radioGroup.indexOfChild(radioButton);
                        TextView textView =
                                rootView.findViewById(R.id.fragment_header);
                        switch (index) {
                            case YES: // User chose "Yes".
                                textView.setText(R.string.yes_message);
                                break;
                            case NO: // User chose "No".
                                textView.setText(R.string.no_message);
                                break;
                            default: // No choice made.
                                // Do nothing.
                                break;
                        }
                    }
                });

        // Return the View for the fragment's UI.
        return rootView;
    }

    public static SimpleFragment newInstance() {
        return new SimpleFragment();
    }
}
