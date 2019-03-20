/*
 * Copyright 2018 MovingBlocks
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
package org.terasology.nui.i18n;

import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.In;

/**
 * This system refreshes the translation system during the initialization, reloading the i18n files from the module environment.
 * The translation system by it's own is not refreshed, as it is an EngineSubsystem.
 */
@RegisterSystem
public class TranslationRefreshSystem extends BaseComponentSystem {

    @In
    private TranslationSystem translationSystem;

    @Override
    public void initialise() {
        //TODO See https://github.com/MovingBlocks/Terasology/issues/2433 for further translation support.
        translationSystem.refresh();
    }
}
