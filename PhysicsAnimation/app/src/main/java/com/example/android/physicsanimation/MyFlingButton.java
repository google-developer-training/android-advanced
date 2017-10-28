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

package com.example.android.physicsanimation;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Implements a button that rotates when tapped.
 * Uses physics-based animation FlingAnimation.
 */
public class MyFlingButton extends android.support.v7.widget.AppCompatButton {

    public MyFlingButton(final Context context) {
        super(context);
    }

    public MyFlingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlingButton(Context context,
                         AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Create an animation that rotates around the views X value.
                FlingAnimation fling = new FlingAnimation(
                        this, DynamicAnimation.ROTATION_X);
                // Set parameters and constraints for the animation.
                // This does almost a full rotation, but not quite.
                // Play with these values!
                fling.setStartVelocity(150) // In pixels per second.
                        .setFriction(0.11f) // Friction slows animation.
                        .start();
                break;
            default:
                // Do nothing.
        }
        return super.onTouchEvent(event);
    }
}