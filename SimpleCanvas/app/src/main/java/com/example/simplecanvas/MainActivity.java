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

package com.example.simplecanvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 *  The SimpleCanvas app demonstrates how to create and draw on a Canvas,
 *  and display the result in an ImageView.
 *
 *  When the user taps the screen, shapes and text are drawn.
 *  All the canvas interaction is implemented in the click handler,
 *  and you do not need a custom view.
 *
 *  Note:
 *  For simplicity, app does not use a custom view.
 *  This app does not teach you to draw.
 * */

public class MainActivity extends AppCompatActivity {

    // Initial offset for rectangle.
    private static final int OFFSET = 120;
    // Multiplier for randomizing color.
    private static final int MULTIPLIER = 100;

    // The Canvas object stores information on WHAT to draw
    // onto its associated bitmap.
    // For example, lines, circles, text, and custom paths.
    private Canvas mCanvas;

    // The Paint object stores HOW to draw.
    // For example, what color, style, line thickness, or text size.
    // The Paint class offers a rich set of coloring and drawing options.
    private Paint mPaint = new Paint();

    // Create a Paint object for underlined text.
    // The Paint clas offers a full complement of typographical styling methods.
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

    // The bitmap represents the pixels that will be displayed.
    private Bitmap mBitmap;

    // The view is the container for the bitmap.
    // Layout on the screen and all user interaction is through the view.
    private ImageView mImageView;

    private Rect mRect = new Rect();
    // Distance of rectangle from edge of canvas.
    private int mOffset = OFFSET;
    // Bounding box for text.
    private Rect mBounds = new Rect();

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);

        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);

        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);

        // Set properties of the Paint used to draw on the canvas.
        mPaint.setColor(mColorBackground);
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);

        // Get a reference to the ImageView.
        mImageView = (ImageView) findViewById(R.id.myimageview);

        // You cannot create the Canvas in onCreate(),
        // because the views have not been
        // laid out, so their final size is not available.
        // When you create a custom view in a later lesson,
        // you will learn other ways of initializing your drawing surface.
    }

    /**
     * Click handler that responds to user taps by drawing an increasingly
     * smaller rectangle until it runs out of room. Then it draws a circle
     * with the text "Done!". Demonstrates basics of drawing on canvas.
     *   1. Create bitmap.
     *   2. Associate bitmap with view.
     *   3. Create canvas with bitmap.
     *   4. Draw on canvas.
     *   5. Invalidate the view to force redraw.
     *
     * @param view The view in which we are drawing.
     */
    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;
        // Only do this first time view is clicked after it has been created.
        if (mOffset == OFFSET) { // Only true once, so don't need separate flag.
            // Each pixel takes 4 bytes, with alpha channel.
            // Use RGB_565 if you don't need alpha and a huge color palette.
            mBitmap = Bitmap.createBitmap(
                    vWidth, vHeight, Bitmap.Config.ARGB_8888);
            // Associate the bitmap to the ImageView.
            mImageView.setImageBitmap(mBitmap);
            // Create a Canvas with the bitmap.
            mCanvas = new Canvas(mBitmap);
            // Fill the entire canvas with this solid color.
            mCanvas.drawColor(mColorBackground);
            // Draw some text styled with mPaintText.
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            // Increase the indent.
            mOffset += OFFSET;
        } else {
            // Draw in response to user action.
            // As this happens on the UI thread, there is a limit to complexity.
            if (mOffset < halfWidth && mOffset < halfHeight) {
                // Change the color by subtracting an integer.
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
                mRect.set(
                        mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                // Increase the indent.
                mOffset += OFFSET;
            } else {
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(
                        halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.done);
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        // Invalidate the view, so that it gets redrawn.
        view.invalidate();
    }
}
