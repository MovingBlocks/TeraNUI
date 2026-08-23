// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.android.verify;

import android.app.Application;
import org.terasology.nui.backends.libgdx.LibGDXKeyboardDevice;
import org.terasology.nui.widgets.ActivateEventListener;
import org.terasology.nui.widgets.UIButton;
import org.terasology.nui.widgets.UIText;

/**
 * Not meant to run - exists purely so {@code assembleDebug} forces the real Android toolchain
 * (javac, then D8 desugaring/dexing, then APK assembly) to actually process nui's and
 * nui-libgdx's compiled classes, including the ones {@code gradle/common.gradle}'s AnimalSniffer
 * check found violations in previously (UIText, ObjectLayoutBuilder, ReflectionUtil).
 */
public class VerifyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Exercises a widget with real UIText/UIButton logic, and a nui-libgdx class, so more than
        // just declaring the dependency goes through the real toolchain.
        UIButton button = new UIButton();
        ActivateEventListener listener = widget -> { };
        button.subscribe(listener);

        UIText.class.getName();
        LibGDXKeyboardDevice.class.getName();
    }
}
