// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.layouts;

import com.google.common.collect.Lists;
import org.joml.Vector2i;
import org.terasology.nui.Canvas;
import org.terasology.nui.CoreLayout;
import org.terasology.nui.LayoutConfig;
import org.terasology.nui.LayoutHint;
import org.terasology.nui.UIWidget;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.databinding.DefaultBinding;
import org.terasology.nui.util.RectUtility;

import java.util.Iterator;
import java.util.List;

/**
 * The Flow layout arranges its children in a directional flow that wraps at the layout's boundary, very much like words
 * wrap at the end of a line when writing a text. The children are laid out in a row in the flow direction, each widget
 * sized by its preferred size. The individual elements are top-aligned, and wrapped at the first element that does not
 * fit in the row.
 * <p>
 * Flow lays out each managed child regardless of the child's visible property value - invisible children won't be
 * rendered but will take up space.
 * <p>
 * The layout may be styled as other widgets with {@link org.terasology.nui.skin.UISkin}.
 * <p>
 * The Flow layout can be configured in UI assets ({@code .ui} files):
 * <pre>
 * {@code
 * {
 *   "type": "FlowLayout",
 *   "verticalSpacing": 8,
 *   "horizontalSpacing": 24,
 *   "rightToLeftAlign": true,
 *   "contents": [...]
 *   // all properties of AbstractWidget
 * }
 * }
 * </pre>
 */
public class FlowLayout extends CoreLayout<LayoutHint> {

    /**
     * The ordered list of widgets to be arranged in the flow direction.
     */
    private List<UIWidget> contents = Lists.newArrayList();

    /**
     * The vertical spacing between adjacent widgets, in pixels
     */
    @LayoutConfig
    private int verticalSpacing;

    /**
     * The horizontal spacing between adjacent widgets, in pixels
     */
    @LayoutConfig
    private int horizontalSpacing;

    /**
     * Whether the directional flow of this layout goes from left-to-right or right-to-left.
     * <p>
     * The children are laid out from left-to-right by default (false), aligned at the left border of the canvas. If
     * this toggle is explicitly enabled (true) the children are laid out right-to-left, aligned at the right border of
     * the canvas.
     * <p>
     * This toggle can be set programmatically or in {@code .ui} files that use the Flow layout.
     *
     * @see #bindRightToLeftAlign(Binding)
     * @see #setRightToLeftAlign(boolean)
     */
    @LayoutConfig
    private Binding<Boolean> rightToLeftAlign = new DefaultBinding<>(false);

    // vertical alignment code addition
    @LayoutConfig
    private Binding<Boolean> topToBottomAlign = new DefaultBinding<>(false);

    @Override
    public void addWidget(UIWidget element, LayoutHint hint) {
        contents.add(element);
    }

    @Override
    public void removeWidget(UIWidget element) {
        contents.remove(element);
    }

    @Override
    public void removeAllWidgets() {
        contents.clear();
    }

    @Override
    public void onDraw(Canvas canvas) {
        layout(canvas, canvas.size(), true);
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        return layout(canvas, sizeHint, false);
    }

    /**
     * Applies the flow layout to its children, arranging them in a directional flow.
     * <p>
     * The layout algorithm will wrap elements to a new line if adding them in the same line would exceed the bounding
     * size. The first element of a row is placed regardless of its size, and thus may exceed the bounding size. The
     * Flow layout attempts to stay within the preferred width, but will add new lines until all widgets have been laid
     * out.
     * <p>
     * If the preferred bounding size is wider than the preferred size of the widest child the Flow layout guarantees to
     * fit into the preferred width. There is no guarantee for the actual height.
     *
     * @param canvas the canvas to draw on and/or calculate sizes for
     * @param boundingSize the boundary for this layout, may be a canvas size or a size hint
     * @param draw whether to actually draw the widgets to the canvas
     * @return the computed bounding box size for when arranging the widget
     */
    private Vector2i layout(Canvas canvas, Vector2i boundingSize, boolean draw) {
        // current bounding box for the widgets already laid out
        Vector2i result = new Vector2i();
        // the filled width of a row; current horizontal "cursor" position for next element to be placed (ltr alignment)
        int widthOffset = 0;
        // current vertical "cursor" position for next element to be placed
        int heightOffset = 0;
        // local maximum for row height
        int rowHeight = 0;
        // local maximum for row width
        int rowWidth = 0;


        if (isTopToBottomAlign()) {
            for (UIWidget widget : contents) {
                Vector2i size = canvas.calculatePreferredSize(widget);

                if (heightOffset != 0 && heightOffset + verticalSpacing + size.y <= boundingSize.y) {
                    // place widget in current column
                    heightOffset += verticalSpacing;
                } else if (heightOffset != 0) {
                    // wrap the column
                    result.x += rowWidth + horizontalSpacing;
                    result.y = Math.max(result.y, heightOffset);
                    widthOffset = result.x;
                    heightOffset = 0;
                    rowWidth = 0;
                }

                if (draw) {
                    int xPosition = isRightToLeftAlign() ? canvas.size().x - widthOffset - size.x : widthOffset;
                    canvas.drawWidget(widget, RectUtility.createFromMinAndSize(xPosition, heightOffset, size.x, size.y));
                }
                heightOffset += size.y;
                rowWidth = Math.max(rowWidth, size.x);
            }

            result.x += rowWidth;
            result.y = Math.max(result.y, heightOffset);
        } else {
            for (UIWidget widget : contents) {
                Vector2i size = canvas.calculatePreferredSize(widget);

                if (widthOffset != 0 && widthOffset + horizontalSpacing + size.x <= boundingSize.x) {
                    // place widget in the current row
                    widthOffset += horizontalSpacing;
                } else if (widthOffset != 0) {
                    // wrap the row
                    result.x = Math.max(result.x, widthOffset);
                    result.y += rowHeight + verticalSpacing;
                    heightOffset = result.y;
                    widthOffset = 0;
                    rowHeight = 0;
                }

                if (draw) {
                    int xPosition = isRightToLeftAlign() ? canvas.size().x - widthOffset - size.x : widthOffset;
                    canvas.drawWidget(widget, RectUtility.createFromMinAndSize(xPosition, heightOffset, size.x, size.y));
                }
                widthOffset += size.x;
                rowHeight = Math.max(rowHeight, size.y);
            }

            result.x = Math.max(result.x, widthOffset);
            result.y += rowHeight;
        }

        return result;
    }

    @Override
    public Vector2i getMaxContentSize(Canvas canvas) {
        return new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Iterator<UIWidget> iterator() {
        return contents.iterator();
    }

    /**
     * Retrieves the horizontal spacing between adjacent widgets in this {@code FlowLayout}.
     *
     * @return the spacing, in pixels
     */
    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    /**
     * Retrieves the vertical spacing between adjacent widgets in this {@code FlowLayout}.
     *
     * @return the spacing, in pixels
     */
    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    /**
     * Sets the horizontal spacing between adjacent widgets in this {@code FlowLayout}.
     *
     * @param spacing the spacing, in pixels
     * @return this {@code FlowLayout}
     */
    public FlowLayout setHorizontalSpacing(int spacing) {
        this.horizontalSpacing = spacing;
        return this;
    }

    /**
     * Sets the vertical spacing between adjacent widgets in this {@code FlowLayout}.
     *
     * @param spacing the spacing, in pixels
     * @return this {@code FlowLayout}
     */
    public FlowLayout setVerticalSpacing(int spacing) {
        this.verticalSpacing = spacing;
        return this;
    }

    /**
     * Whether the directional flow of this layout goes from left-to-right or right-to-left.
     * <p>
     * If false, the children are laid out from left-to-right and aligned at the left border of the canvas. If true, the
     * children are laid out right-to-left and aligned at the right border of the canvas.
     * <p>
     * The default value is {@code false}, laying out children left-to-right.
     * <p>
     * This toggle can be set programmatically or in {@code .ui} files that use the Flow layout.
     *
     * @return whether the children are laid out right-to-left and aligned to the right
     * @see #bindRightToLeftAlign(Binding)
     * @see #setRightToLeftAlign(boolean)
     */
    public boolean isRightToLeftAlign() {
        return rightToLeftAlign.get();
    }

    /**
     * Set whether the directional flow of this layout goes from left-to-right or right-to-left.
     *
     * See {@link #isRightToLeftAlign()} for more details on the directional flow.
     *
     * @param rightToLeftAlign whether the children are laid out right-to-left and aligned to the right
     */
    public void setRightToLeftAlign(boolean rightToLeftAlign) {
        this.rightToLeftAlign.set(rightToLeftAlign);
    }

    /**
     * Bind whether the directional flow of this layout goes from left-to-right or right-to-left with given binding.
     * <p>
     * The directional flow will change whenever the given binding changes its value. See {@link #isRightToLeftAlign()}
     * for more details on the directional flow.
     *
     * @param binding the binding defining whether to lay out children right-to-left or left-to-right.
     */
    public void bindRightToLeftAlign(Binding<Boolean> binding) {
        this.rightToLeftAlign = binding;
    }

    /**
     * Clear any binding to the the right-to-left align property and reset to the default value (false).
     */
    public void clearRightToLeftAlignBinding() {
        this.rightToLeftAlign = new DefaultBinding<>(false);
    }



    // vertical alignment code addition
    public boolean isTopToBottomAlign() {
        return topToBottomAlign.get();
    }

    public void setTopToBottomAlign(boolean topToBottomAlign) {
        this.topToBottomAlign.set(topToBottomAlign);
    }

    public void bindTopToBottomAlign(Binding<Boolean> binding) {
        this.topToBottomAlign = binding;
    }

    public void clearTopToBottomAlignBinding() {
        this.topToBottomAlign = new DefaultBinding<>(false);
    }
}
