/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui.skin;

import org.terasology.nui.UIWidget;

import java.util.Map;

/**
 */
public class UISkin {

    private Map<String, UIStyleFamily> skinFamilies;

    public UISkin(Map<String, UIStyleFamily> skinFamilies) {
        this.skinFamilies = skinFamilies;
    }

    public UIStyle getDefaultStyle() {
        return getFamily("").getBaseStyle();
    }

    public UIStyle getDefaultStyleFor(String family) {
        return getFamily(family).getBaseStyle();
    }

    public UIStyle getDefaultStyleFor(Class<? extends UIWidget> element, String mode) {
        return getStyleFor("", element, mode);
    }

    public UIStyle getDefaultStyleFor(Class<? extends UIWidget> element, String part, String mode) {
        return getStyleFor("", element, part, mode);
    }

    public UIStyle getStyleFor(String family, Class<? extends UIWidget> element, String mode) {
        UIStyleFamily styleFamily = getFamily(family);
        if (element == null) {
            return styleFamily.getBaseStyle();
        }
        return styleFamily.getElementStyle(element, "", mode);
    }

    public UIStyle getStyleFor(String family, Class<? extends UIWidget> element, String part, String mode) {
        UIStyleFamily styleFamily = getFamily(family);
        if (element == null) {
            return styleFamily.getBaseStyle();
        }
        return styleFamily.getElementStyle(element, part, mode);
    }

    public UIStyleFamily getFamily(String family) {
        UIStyleFamily styleFamily = skinFamilies.get(family);
        if (styleFamily == null) {
            return skinFamilies.get("");
        }
        return styleFamily;
    }

    public Iterable<? extends String> getFamilies() {
        return skinFamilies.keySet();
    }
}
