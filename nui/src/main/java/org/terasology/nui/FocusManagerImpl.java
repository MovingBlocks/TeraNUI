/*
 * Copyright 2020 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui;

import org.terasology.context.annotation.Service;

@Service
public class FocusManagerImpl implements FocusManager {
    private UIWidget currentFocus;

    @Override
    public void setFocus(UIWidget widget) {
        if (widget == currentFocus || (widget != null && !widget.canBeFocus())) {
            return;
        }

        if (currentFocus != null) {
            currentFocus.onLoseFocus();
        }
        currentFocus = widget;

        if (currentFocus != null) {
            currentFocus.onGainFocus();
        }
    }

    @Override
    public UIWidget getFocus() {
        return currentFocus;
    }
}
