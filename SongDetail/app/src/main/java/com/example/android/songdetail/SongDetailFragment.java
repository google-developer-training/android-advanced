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

package com.example.android.songdetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.songdetail.content.SongUtils;

/**
 * A simple {@link Fragment} subclass that displays the song
 * detail based on the song selected from a list.
 */
public class SongDetailFragment extends Fragment {

    // Song includes the song title and detail.
    public SongUtils.Song mSong;

    public SongDetailFragment() {
        // Required empty public constructor
    }

    /**
     * This method loads the content specified by the fragment arguments.
     *
     * @param savedInstanceState Bundle with recent saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(SongUtils.SONG_ID_KEY)) {
            // Load the content specified by the fragment arguments.
            mSong = SongUtils.SONG_ITEMS.get(getArguments()
                    .getInt(SongUtils.SONG_ID_KEY));
        }
    }

    /**
     * This method inflates the fragment's view and shows the song
     * detail information.
     *
     * @param inflater LayoutInflater object to inflate views
     * @param container ViewGroup that the fragment's UI should be attached to
     * @param savedInstanceState Bundle containing previous state
     * @return Fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.song_detail,
                container, false);

        // Show the detail information in a TextView.
        if (mSong != null) {
            ((TextView) rootView.findViewById(R.id.song_detail))
                    .setText(mSong.details);
        }

        return rootView;
    }

    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param selectedSong Integer position of selected song in song list
     * @return fragment
     */
    public static SongDetailFragment newInstance (int selectedSong) {
        SongDetailFragment fragment = new SongDetailFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putInt(SongUtils.SONG_ID_KEY, selectedSong);
        fragment.setArguments(arguments);
        return fragment;
    }
}
