/*
 * Copyright 2019 The Android Open Source Project
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

package com.rst.bottomsheet;

import static android.graphics.Color.TRANSPARENT;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static com.google.android.material.color.MaterialColors.isColorLight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.Window;

import androidx.core.graphics.ColorUtils;

import com.google.android.material.color.MaterialColors;

/**
 * Helper that saves the current window preferences for the Catalog.
 */
public class WindowPreferencesManager {

    private static final String PREFERENCES_NAME = "window_preferences";
    private static final String KEY_EDGE_TO_EDGE_ENABLED = "edge_to_edge_enabled";
    private static final int EDGE_TO_EDGE_BAR_ALPHA = 128;

    private static final int EDGE_TO_EDGE_FLAGS =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    private final Context context;

    public WindowPreferencesManager(Context context) {
        this.context = context;
    }

    @SuppressWarnings("ApplySharedPref")
    public void toggleEdgeToEdgeEnabled() {
        getSharedPreferences()
                .edit()
                .putBoolean(KEY_EDGE_TO_EDGE_ENABLED, !isEdgeToEdgeEnabled())
                .commit();
    }

    public boolean isEdgeToEdgeEnabled() {
        return VERSION.SDK_INT >= VERSION_CODES.Q;
    }

    @SuppressWarnings("RestrictTo")
    public void applyEdgeToEdgePreference(Window window) {
        boolean edgeToEdgeEnabled = isEdgeToEdgeEnabled();
//
        int statusBarColor = getStatusBarColor(isEdgeToEdgeEnabled());
        int navbarColor = getNavBarColor(isEdgeToEdgeEnabled());

        boolean lightBackground =
                isColorLight(MaterialColors.getColor(context, android.R.attr.colorBackground, Color.BLACK));
        boolean lightStatusBar = isColorLight(statusBarColor);
        boolean showDarkStatusBarIcons =
                lightStatusBar || (statusBarColor == TRANSPARENT && lightBackground);
        boolean lightNavbar = isColorLight(navbarColor);
        boolean showDarkNavbarIcons = lightNavbar || (navbarColor == TRANSPARENT && lightBackground);

        int currentStatusBar =
                showDarkStatusBarIcons
                        ? SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        : 0;
        int currentNavBar =
                showDarkNavbarIcons && VERSION.SDK_INT >= VERSION_CODES.O
                        ? SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        : 0;

        window.setNavigationBarColor(navbarColor);
        window.setStatusBarColor(statusBarColor);
        int systemUiVisibility = EDGE_TO_EDGE_FLAGS
                | currentStatusBar
                | currentNavBar;

        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(systemUiVisibility);
    }

    @SuppressWarnings("RestrictTo")
    @TargetApi(VERSION_CODES.LOLLIPOP)
    private int getStatusBarColor(boolean isEdgeToEdgeEnabled) {
        if (isEdgeToEdgeEnabled) {
            return TRANSPARENT;
        }
        return MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK);
    }

    @SuppressWarnings("RestrictTo")
    @TargetApi(VERSION_CODES.LOLLIPOP)
    private int getNavBarColor(boolean isEdgeToEdgeEnabled) {
        if (isEdgeToEdgeEnabled && VERSION.SDK_INT < VERSION_CODES.O_MR1) {
            int opaqueNavBarColor =
                    MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK);
            return ColorUtils.setAlphaComponent(opaqueNavBarColor, EDGE_TO_EDGE_BAR_ALPHA);
        }
        if (isEdgeToEdgeEnabled) {
            return TRANSPARENT;
        }
        return MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
