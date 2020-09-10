// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.reflect;

/**
 */
public class InaccessibleFieldException extends Exception {

    private static final long serialVersionUID = 3617077464713877750L;

    public InaccessibleFieldException() {
    }

    public InaccessibleFieldException(String message) {
        super(message);
    }

    public InaccessibleFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public InaccessibleFieldException(Throwable cause) {
        super(cause);
    }

    public InaccessibleFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
