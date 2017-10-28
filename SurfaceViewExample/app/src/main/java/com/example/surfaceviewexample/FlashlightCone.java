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

package com.example.surfaceviewexample;

/**
 * Cone of flashlight that the user moves around the view.
 */

public class FlashlightCone {

    private int mX;
    private int mY;
    private int mRadius;

    public FlashlightCone(int viewWidth, int viewHeight) {
        mX = viewWidth / 2;
        mY = viewHeight / 2;
        // Adjust the radius for the narrowest view dimension.
        mRadius = ((viewWidth <= viewHeight) ? mX / 3 : mY / 3);
    }

    /**
     * Update the coordinates of the flashlight cone.
     *
     * @param newX Changed value for x coordinate.
     * @param newY Changed value for y coordinate.
     */
    public void update(int newX, int newY) {
        mX = newX;
        mY = newY;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getRadius() {
        return mRadius;
    }
}
