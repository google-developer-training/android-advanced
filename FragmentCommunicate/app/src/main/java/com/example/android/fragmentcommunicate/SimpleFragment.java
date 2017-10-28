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

package com.example.android.fragmentcommunicate;

import android.content.Context;
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
 * If the user closes the fragment, the choice is saved in
 * memory so that if the user opens the fragment again, the
 * previous choice is shown.
 */
public class SimpleFragment extends Fragment {

    // The radio button choice has 3 states: 0 = yes, 1 = no,
    // 2 = default (no choice). Using all 3 states.
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    // Initialize the choice to the default (no choice)
    private int mRadioButtonChoice = NONE;

    // The "choice" key for the bundle.
    private static final String CHOICE = "choice";

    // The listener interface.
    OnFragmentInteractionListener mListener;

    public SimpleFragment() {
        // Required empty public constructor
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }

    /**
     * This method checks if the hosting activity has implemented
     * the OnFragmentInteractionListener interface. If it does not,
     * an exception is thrown.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }

    /**
     * Create the fragment view by inflating the layout, and if a choice
     * has already been made, get the choice argument from the bundle
     * and check the appropriate choice (yes or no) in the fragment.
     * Using a radioGroup listener, get the radio button choice from the
     * user.
     *
     * @param inflater           LayoutInflater to inflate any views in the fragment
     * @param container          ViewGroup of parent view to attach fragment
     * @param savedInstanceState Bundle for previous state
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        // If user reopens the fragment after making a choice, the bundle
        // contains "choice".
        if (getArguments().containsKey(CHOICE)) {
            // A choice was made, so get the choice.
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            // Check the radio button choice.
            if (mRadioButtonChoice != NONE) {
                radioGroup.check
                        (radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

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
                                mRadioButtonChoice = YES;
                                mListener.onRadioButtonChoice(YES);
                                break;
                            case NO: // User chose "No".
                                textView.setText(R.string.no_message);
                                mRadioButtonChoice = NO;
                                mListener.onRadioButtonChoice(NO);
                                break;
                            default: // No choice made.
                                mRadioButtonChoice = NONE;
                                mListener.onRadioButtonChoice(NONE);
                                break;
                        }
                    }
                });

        // Return the View for the fragment's UI.
        return rootView;
    }

    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }
}
