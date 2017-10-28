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
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Implements a button that bounces like a spring when tapped.
 * Uses physics-based animation SpringAnimation.
 */
public class MySpringButton extends android.support.v7.widget.AppCompatButton {

    public MySpringButton(final Context context) {
        super(context);
    }

    public MySpringButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySpringButton(Context context,
                          AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Create a spring animation along the view's Y position.
                // Let resting position be at the view's current Y position.
                final SpringAnimation anim = new SpringAnimation(this,
                        (FloatPropertyCompat) DynamicAnimation.Y, this.getY())
                    .setStartVelocity(10000); // In pixels per second.
                // Low stiffness makes the spring bouncy.
                anim.getSpring().setStiffness(STIFFNESS_LOW);
                anim.start();
                break;
            default:
                // Do nothing.
        }
        return super.onTouchEvent(event);
    }
}