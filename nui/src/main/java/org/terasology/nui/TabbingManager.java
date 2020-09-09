// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui;

import org.terasology.nui.input.Input;
import org.terasology.nui.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controls the tabbing for widgets.
 */
public class TabbingManager {

    public static final int UNINITIALIZED_DEPTH = -9999;

    private static boolean widgetIsOpen;

    public static WidgetWithOrder focusedWidget;
    public static boolean focusSetThrough;
    public static Input tabBackInputModifier = Keyboard.Key.LEFT_SHIFT;
    public static Input tabForwardInput = Keyboard.Key.TAB;
    public static Input activateInput;

    private static int currentNum;
    private static int maxNum;
    private static int nextNum;
    private static ArrayList<Integer> usedNums;
    private static ArrayList<WidgetWithOrder> widgetsList;
    private static boolean initialized = false;
    private static FocusManager focusManager;

    /**
     * Resets TabbingManager values.
     */
    public static void init() {
        widgetIsOpen = false;
        focusedWidget = null;
        focusSetThrough = false;
        currentNum = 0;
        maxNum = 0;
        nextNum = 0;
        usedNums = new ArrayList<>();
        widgetsList = new ArrayList<>();
        initialized = true;
    }

    /**
     * Changes (increases or decreases) currentNum.
     * @param increase if currentNumber should be increased.
     */
    public static void changeCurrentNum(boolean increase) {
        boolean loopedOnce = false;
        boolean adjusted = false;

        while ((!adjusted || !usedNums.contains(currentNum)) && usedNums.size()>0) {
            adjusted = true;

            if (increase) {
                currentNum++;
            } else {
                currentNum--;
            }
            if (currentNum > maxNum) {
                if (!loopedOnce) {
                    currentNum = 0;
                    loopedOnce = true;
                } else {
                    break;
                }
            } else if (currentNum < 0) {
                currentNum = Collections.max(usedNums);
                loopedOnce = true;
            }
        }
    }

    /**
     * Unfocuses the currently focused widget.
     */
    public static void unfocusWidget() {
        if (focusedWidget != null) {
            focusSetThrough = true;
            focusedWidget = null;
            focusManager.setFocus(null);
        }
    }

    /**
     * Gives an unused number.
     * @return a new number for order
     */
    public static int getNewNextNum() {
        nextNum++;
        maxNum++;
        while (usedNums.contains(nextNum)) {
            nextNum++;
            maxNum++;
        }
        return nextNum;
    }

    /**
     * Adds the order value to usedNums.
     * @param toAdd the number to add to usedNums.
     */
    public static void addToUsedNums(int toAdd) {
        if (!usedNums.contains(toAdd)) {
            usedNums.add(toAdd);
            if (toAdd > maxNum) {
                maxNum = toAdd;
            }
        }
    }

    /**
     * Adds a widget to usedNums.
     * @param widget the widget to add to usedNums.
     */
    public static void addToWidgetsList(WidgetWithOrder widget) {
        if (!widgetsList.contains(widget)) {
            widgetsList.add(widget);
        }
    }

    /**
     * Resets currentNum to zero.
     */
    public static void resetCurrentNum() {
        currentNum = 0;
    }
    public static int getCurrentNum() {
        return currentNum;
    }
    public static List<WidgetWithOrder> getWidgetsList() {
        return widgetsList;
    }
    public static boolean isInitialized() {
        return initialized;
    }
    public static void setInitialized(boolean setInit) {
        initialized = setInit;
    }
    public static boolean isWidgetOpen() {
        return widgetIsOpen;
    }
    public static void setWidgetIsOpen(boolean open) {
        widgetIsOpen = open;
    }
    public static void setFocusManager(FocusManager manager) {
        focusManager = manager;
    }
    public static FocusManager getFocusManager() {
        return focusManager;
    }
}
