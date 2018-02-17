/*
 * Copyright (C) 2018 Google Inc.
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

package com.example.android.customfancontrollersettings;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tbove on 12/29/17.
 */

/**
 * Custom view renders a multi-position "dial". Each click advances to the
 * next dial position. Initially set to 4 selections (0-3), with
 * 0 = Off.
 */

public class DialView extends View {

    private int mSelectionCount = 4;  // Default number of selections
    private float mWidth;                    // Custom view width
    private float mHeight;                   // Custom view height
    private Paint mTextPaint;                // For text in the view
    private Paint mDialPaint;                // For dial circle in the view
    private float mRadius;                   // Radius of the dial
    private int mActiveSelection;            // The active selection
    // String buffer for dial labels and float for ComputeXY result
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];
    // Challenge: The color is set in attributes
    private int mFanOnColor;                // Dial color set in the attributes
    private int mFanOffColor;               // Dial color set in the attributes

    /**
     * Standard constructor.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public DialView(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * This constructor is called when a view is built from an XML file,
     * supplying attributes that were specified in the XML file.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public DialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * This constructor is called to supply the default style.
     *
     * @param context      The Context the view is running in, through which
     *                     it can access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag inflating the view.
     * @param defStyleAttr The default style attributes.
     */
    public DialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Helper method to initialize instance variables. Called by constructors.
     */
    private void init(Context context, AttributeSet attrs) {
        // Paint styles used for rendering are created here. This
        // is a performance optimization, since onDraw() is called
        // for every screen refresh.

        // Set default fan on and fan off colors.
        mFanOnColor = Color.CYAN;
        mFanOffColor = Color.GRAY;

        // Get the custom attributes fanOnColor and fanOffColor, if available.
        if (attrs != null) {
            TypedArray typedArray = getContext()
                    .obtainStyledAttributes(attrs,
                            R.styleable.DialView,
                            0, 0);

            // Set the fan on and fan off colors from the attribute values.
            mFanOnColor = typedArray.getColor(R.styleable.DialView_fanOnColor, mFanOnColor);
            mFanOffColor = typedArray.getColor(R.styleable.DialView_fanOffColor, mFanOffColor);
            mSelectionCount = typedArray.getInt(R.styleable.DialView_selectionIndicators,
                    mSelectionCount);
            // Must recycle the TypedArray when finished.
            typedArray.recycle();
        }

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(mFanOffColor);

        // Initialize current selection (where the dial's "indicator" is
        // pointing).
        mActiveSelection = 0;

        // Set up onClick listener for this view.
        // Rotates between each of the different selection
        // states on each click.
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rotate selection forward to the next valid choice.
                mActiveSelection = (mActiveSelection + 1) % mSelectionCount;
                // Set dial background color to green if selection is >= 1.
                if (mActiveSelection >= 1) {
                    mDialPaint.setColor(mFanOnColor);
                } else {
                    mDialPaint.setColor(mFanOffColor);
                }
                // Redraw the view.
                invalidate();
            }
        });
    }

    /**
     * Sets the selection count, which is the number of
     * selection indicators on the dial, based on
     * user's choice. Also invalidates custom view to
     * force redraw.
     * @param count The user-chosen count of indicators
     */
    public void setSelectionCount(int count) {
        this.mSelectionCount = count;
        this.mActiveSelection = 0;
        mDialPaint.setColor(mFanOffColor);
        invalidate();
    }

    /**
     * Called during layout when the size of this view has changed. If
     * the view was just added to the view hierarchy, it is called with the initial
     * values of 0. The code determines the drawing bounds for the custom view.
     *
     * @param w    Current width of this view
     * @param h    Current height of this view
     * @param oldw Initial width of this view
     * @param oldh Initial height of this view
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Calculate the radius from the width and height.
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    }

    /**
     * Render view content: an outer grey circle to serve as the "dial",
     * and a smaller black circle to serve as the indicator.
     * The position of the indicator is based on mActiveSelection.
     *
     * @param canvas The canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the dial.
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint);

        // Draw the text labels.
        final float labelRadius = mRadius + 20;
        StringBuffer label = mTempLabel;
        for (int i = 0; i < mSelectionCount; i++) {
            // Call computeXYForPosition with selection count, radius, and
            // boolean isLabel set to true (for text labels).
            float[] xyData = computeXYForPosition(i, labelRadius, true);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
        }

        // Draw the indicator mark.
        final float markerRadius = mRadius - 35;
        // Call computeXYForPosition with active selection, marker radius, and
        // boolean isLabel set to false (for marker indicator).
        float[] xyData = computeXYForPosition(mActiveSelection, markerRadius, false);
        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);
    }

    /**
     * Compute the X/Y-coordinates for a label or indicator,
     * given the position number and radius
     * where the label anf marker should be drawn.
     *
     * @param pos Zero-based position index
     * @param radius Radius where label/indicator is to be drawn.
     * @param isLabel True if for text labels, false if for indicator marker.
     * @return 2-element array. Element 0 is X-coordinate, element 1 is Y-coordinate.
     */
    private float[] computeXYForPosition(final int pos, final float radius , boolean isLabel) {
        float[] result = mTempResult;
        Double startAngle;
        Double angle;
        if (mSelectionCount > 4) {
            // If greater than 4 selections, draw around entire dial.
            startAngle = Math.PI * (3 / 2d);   // Angles are in radians.
            angle= startAngle + (pos * (Math.PI / mSelectionCount));
            result[0] = (float) (radius * Math.cos(angle * 2)) + (mWidth / 2);
            result[1] = (float) (radius * Math.sin(angle * 2)) + (mHeight / 2);
            if((angle > Math.toRadians(360)) && isLabel) {
                result[1] += 20;
            }
        } else {
            // Draw around the upper half of the dial.
            startAngle = Math.PI * (9 / 8d);
            angle= startAngle + (pos * (Math.PI / mSelectionCount));
            result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
            result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        }
        return result;
    }
}
