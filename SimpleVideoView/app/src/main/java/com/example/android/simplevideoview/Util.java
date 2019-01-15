package com.example.android.simplevideoview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

public class Util {
    /**
     * Like {@link android.os.Build.VERSION#SDK_INT}, but in a place where it can be conveniently
     * overridden for local testing.
     */
    public static final int SDK_INT =
            (Build.VERSION.SDK_INT == 25 && Build.VERSION.CODENAME.charAt(0) == 'O') ? 26
                    : Build.VERSION.SDK_INT;


    /**
     * Checks whether it's necessary to request the {@link Manifest.permission#READ_EXTERNAL_STORAGE}
     * permission read the specified {@link Uri}s, requesting the permission if necessary.
     *
     * @param activity The host activity for checking and requesting the permission.
     * @param uris {@link Uri}s that may require {@link Manifest.permission#READ_EXTERNAL_STORAGE} to read.
     * @return Whether a permission request was made.
     */
    @TargetApi(23)
    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, Uri... uris) {
        if (Util.SDK_INT < 23) {
            return false;
        }
        for (Uri uri : uris) {
            if (Util.isLocalFileUri(uri)) {
//                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    activity.requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//                    return true;
//                }

                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Log.d("fengyun", "show request permissionRationale");
                    } else {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                0);
                    }
                    return true;
                }
                break;
            }
        }
        return false;
    }

    /**
     * Returns true if the URI is a path to a local file or a reference to a local file.
     *
     * @param uri The uri to test.
     */
    public static boolean isLocalFileUri(Uri uri) {
        String scheme = uri.getScheme();
        return TextUtils.isEmpty(scheme) || "file".equals(scheme);
    }
}
