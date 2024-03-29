/*
 * Copyright 2015 MovingBlocks
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

package org.terasology.nui.translate;

import java.util.Locale;

/**
 * A translation system that consists of different projects. An i18n string can either
 * contain a project URI or be used directly in a project.
 */
public interface Translator {
    /**
     * If no perfect match is found for the default locale, fallback strategies will attempt to find the closest match.
     * @param id must match the pattern <code>${module:project#id}</code>
     * @return the translated string
     */
    String translate(String id);

    /**
     * If no perfect match is found for the given locale, fallback strategies will attempt to find the closest match.
     * @param id must match the pattern <code>${module:project#id}</code>
     * @param locale the target locale
     * @return the translated string
     */
    String translate(String id, Locale locale);
}
