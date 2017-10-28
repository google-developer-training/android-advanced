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

package com.example.android.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * A custom view that draws animated colored circles.
 */
public class PulseAnimationView extends View {

    private static final int ANIMATION_DURATION = 4000;
    private static final long ANIMATION_DELAY = 1000;
    private static final int COLOR_ADJUSTER = 5;

    private float mX;
    private float mY;

    private float mRadius;
    private final Paint mPaint = new Paint();

    private AnimatorSet mPulseAnimatorSet = new AnimatorSet();


    public PulseAnimationView(Context context) {
        this(context, null);
    }

    public PulseAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        // This method is called when the size of the view changes.
        // For this app, it is only called when the activity is started or restarted.
        // getWidth() cannot return anything valid in onCreate(), but it does here.
        // We create the animators and animator set here once, and handle the starting and
        // canceling in the event handler.

        // Animate the "radius" property with an ObjectAnimator,
        // giving it an interpolator and duration.
        // This animator creates an increasingly larger circle from a
        // radius of 0 to the width of the view.
        ObjectAnimator growAnimator = ObjectAnimator.ofFloat(this,
                "radius", 0, getWidth());
        growAnimator.setDuration(ANIMATION_DURATION);
        growAnimator.setInterpolator(new LinearInterpolator());

        // Create a second animator to
        // animate the "radius" property with an ObjectAnimator,
        // giving it an interpolator and duration.
        // This animator creates a shrinking circle
        // from a radius of the view's width to 0.
        // Add a delay to starting the animation.
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofFloat(this,
                "radius", getWidth(), 0);
        shrinkAnimator.setDuration(ANIMATION_DURATION);
        shrinkAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        shrinkAnimator.setStartDelay(ANIMATION_DELAY);

        // If you don't need a delay between the two animations,
        // you can use one animator that repeats in reverse.
        // Uses the default AccelerateDecelerateInterpolator.
        ObjectAnimator repeatAnimator = ObjectAnimator.ofFloat(this,
                "radius", 0, getWidth());
        repeatAnimator.setStartDelay(ANIMATION_DELAY);
        repeatAnimator.setDuration(ANIMATION_DURATION);
        repeatAnimator.setRepeatCount(1);
        repeatAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Create an AnimatorSet to combine the two animations into a sequence.
        // Play the expanding circle, wait, then play the shrinking circle.
        mPulseAnimatorSet.play(growAnimator).before(shrinkAnimator);
        mPulseAnimatorSet.play(repeatAnimator).after(shrinkAnimator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

            // Where the center of the circle will be.
            mX = event.getX();
            mY = event.getY();

            // If there is an animation running, cancel it.
            // This resets the AnimatorSet and its animations to the starting values.
            if(mPulseAnimatorSet != null && mPulseAnimatorSet.isRunning()) {
                mPulseAnimatorSet.cancel();
            }
            // Start the animation sequence.
            mPulseAnimatorSet.start();
        }
        return super.onTouchEvent(event);
    }

    /**
     * Required setter for the animated property.
     * Called by the Animator to update the property.
     *
     * @param radius This view's radius property.
     */
    public void setRadius(float radius) {
        mRadius = radius;
        // Calculate a new color from the radius.
        mPaint.setColor(Color.GREEN + (int) radius / COLOR_ADJUSTER);
        // Updating the property does not automatically redraw.
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mRadius, mPaint);
    }
}