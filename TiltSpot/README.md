TiltSpot (Solution Code)
========================

TiltSpot demonstrates the use of the accelerometer and geomagnetic field
sensors to  determine the device orientation.  It includes both text views
to display the current orientation angles (azimuth, pitch, roll), and spots
on each edge of the device to indicate which edge is tilted "up."  If the
device is rotated from portrait to landscape (or vice versa) TiltSpot
handles the rotation and draws the spots in the right places.


Pre-requisites
--------------

For this app you should be familiar with:
* Creating, building, and running apps in Android Studio.
* Using the Android sensor framework to gain access to available sensors on the device, and to register and unregister listeners for those sensors.
* Using the onSensorChanged() method from the SensorEventListener interface to handle changes to sensor data.


Getting Started
---------------

1. Download and open this sample in Android Studio.


License
-------

Copyright 2017 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with this
work for additional information regarding copyright ownership.  The ASF
licenses this file to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the
License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.