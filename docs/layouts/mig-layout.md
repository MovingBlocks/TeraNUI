# MiG Layout

Terasology's `MigLayout` is a full implementation of Swing's MiG Layout using Terasology's NUI interface framework. This page will provide a quick introduction to the `MigLayout` widget, based on the Swing guide at http://www.miglayout.com/QuickStart.pdf.

## Adding Components to the Grid

Adding components to a `MigLayout` is as simple as writing text and follows the same basic principle. By default, elements added to the layout will end up on the same row - to proceed to the next row, add `wrap` to the `cc` property in the `layoutInfo` of a widget, which will cause the next widget to appear on the second row. For example:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "wrap" // Wrap to the next row
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": ""
    }
}
```

<fig src="layouts/images/mig-layout_basic.png" alt="">Basic MiG Layout</fig>

The grid can also be set to auto-wrap at a specific column index in the layout constraint when creating the `MigLayout`. The following example shows how to create the same grid as above without having to specify `wrap` in `comp3`'s definition. It means that the grid should auto-wrap after column 3 and therefore a fourth column will not be created.

```javascript
"type": "migLayout",
"layoutConstraints": "newline 3",
"contents": [
    // ...
]
```

You can also set the next row's gap directly after the `wrap` keyword. For example, `"cc": "wrap 15"` will make the row gap 15 pixels high.

## Merging and Splitting Cells

It is equally easy to `split` or `span` cells. Take a look at the following example and the grid it creates:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "span 2" // The UILabel will span two cells
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "wrap" // Wrap to the next row
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "span" // Span without count means span whole row
    }
}
```

<fig src="layouts/images/mig-layout_span-cols.png" alt="">MiG Layout with column-spanning cells</fig>

`span` optionally takes two indexes, `x` and `y`. This means that you can `span` cells like this:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "span 2 2" // The UILabel will span 2 x 2 cells
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "wrap" // Wrap to the next row
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp5",
    "layoutInfo": {
        "cc": "wrap" // Note that it 'jumps' over the occupied cells
    }
},
{
    "type": "UILabel",
    "text": "comp6",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp7",
    "layoutInfo": {
        "cc": ""
    }
}
```

<fig src="layouts/images/mig-layout_span-rows.png" alt="">MiG Layout with row-spanning cells</fig>

It is equally easy and intuitive to `split` cells.

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "split 2" // Split the cell into 2
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "" // Will be in the same cell as previous
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "wrap" // Wrap to the next row
    }
},
{
    "type": "UILabel",
    "id": "",
    "text": "comp5",
    "layoutInfo": {
        "cc": ""
    }
}
```

<fig src="layouts/images/mig-layout_split.png" alt="">MiG Layout with split cells</fig>

It's also possible to both `span` and `split` cells at the same time - you can, for instance, `span` three cells and `split` the resulting "three-cell-wide" cell into two.

## Using Absolute Cell Coordinates

If you don't want to use the "flow" way of putting components into grid positions, you can instead use absolute coordinates. For instance:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": "cell 0 0" // "cell, column, row"
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "cell 1 0"
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "cell 2 0"
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "cell 0 1" 
    }
}
```

This will produce the same grid as the first example at the top.

<fig src="layouts/images/mig-layout_basic.png" alt="">Basic MiG Layout using absolute cell coordinates</fig>

You can also use absolute cell coordinates to `span` and `split` cells. If a component is put in a cell that already has a component, the cell will be split and both cells will end up sharing the same space. To construct the same grid as shown in the second example above, you can use the following:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": "cell 0 0"
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "cell 1 0 2 1" // "cell, column, row, width, height"
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "cell 3 0"
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "cell 0 1 4 1" 
    }
}
```

<fig src="layouts/images/mig-layout_span-cols.png" alt="">MiG Layout with column-spanning cells using absolute cell coordinates</fig>

## Specifying Gaps

There are two kinds of gaps - Grid row gaps and Component gaps. They have default values but you can change them however you like.

### Grid gaps

In the grid illustrations above, grid gaps are the small spacing columns and rows between the real columns and rows. Their size can be set in the column and rows constraints under the `MigLayout` object. For instance:

```javascript
"type": "migLayout",
"layoutConstraints": "", // Layout constraints
"colConstraints": "[][]20[]", // Column constraints
"rowConstraints": "[]20[]", // Row constraints
"contents" : [
    // ...
]
```

This would create something like this:

<fig src="layouts/images/mig-layout_grid-gap.png" alt="">MiG Layout with widened grid gaps</fig>

where the bigger spacing row and column is 20 pixels. You can use any unit to specify the size (the default is pixels). For instance, `20mm` will make a gap 20 millimeters wide.  Note that you can specify the gap when using the `wrap` keyword. e.g. `wrap 15px` as mentioned above.

The space between the square brackets `[ ... ]` is where you can specify the row and component constraints such as alignment and size - more on this later.

### Component gaps

The only situation where there will be a default component gap > 0 is between components in the same (thus split) cell. You can however change this by specifying a gap size when adding the component. Gaps around components refer to the distance to the closest edge, whether it's the cell "wall" or another component in the same cell. 

If we wish to recreate the grid from the first example, we can use the following:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": "cell 0 0"
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "gapleft 30"
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "wrap"
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "" 
    }
}
```

<fig src="layouts/images/mig-layout_component-gap.png" alt="">MiG Layout with component gap</fig>

There are many other gap constraints, such as `gaptop`. You can read about them in the Cheat Sheet or White Paper at www.migcomponents.com.

## Component Sizes

`MigLayout` has support for minimum/preferred/maximum size as well. You can override these sizes and extend their functionality by providing the sizes in the constraints. 

These sizes are specified in the form: "min:preferred:max" (e.g. `10:20:40`). A size not specified will default to the component's corresponding size (e.g. `10::40` will set the min and max size but preserve the original preferred size).

There are shorter ways to set the sizes. For instance, `40!` means that all three sizes will be set to 40. For example:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": "width 10:20:40"
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "height ::40" // Same as "hmax 40".
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "w 40!" // w is short for width.
    }
}
```

## Row and Column Sizes

Rows and columns default to the size of the largest component in the column/row. This can be overridden and it works in exactly the same way as the component sizes above; you specify the column/row size in the corresponding constraint, normally when defining the `MigLayout` object. Here are some examples:

```javascript
"type": "migLayout",
"layoutConstraints": "", // Layout constraints
"colConstraints": "[10][20:30:40][40!][::40]", // Column constraints
"rowConstraints": "[min!][10::20][40mm!]", // Row constraints
"contents" : [
    // ...
]
```

Did you see the `min!` part? `min`, `pref`, `max` can be used on both component and column/row sizes to refer to the original size. So `min!` means that the minimum, preferred and maximum size will all be set to the minimum size of the row, ensuring the row will occupy its minimum size.

## Component Alignment

Components that are alone in a cell can be aligned within that cell if there is space left over. You can specify this in the column/row constraints to get a default alignment for the components. For example:

```javascript
"type": "migLayout",
"layoutConstraints": "", // Layout constraints
"colConstraints": "[center][right][left][c]", // Column constraints with default align
"rowConstraints": "[top][center][b]", // Row constraints default align
"contents" : [
    // ...
]
```

You can specify this in the component's constraints as well:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": "align left"
    }
}
```

Note that you can use the first letter of the alignment if you prefer.

If you have more than one component in a cell the alignment keywords will not work since the behavior would be indeterministic. You can however achieve the same effect by setting a gap before and/or after the components. That gap may have a minimum size of 0 and a preferred size of a really large value to create a "pushing" gap. There is even a keyword for this: `push`. So `gapleft push` will be the same as `align right` and work for multi-component cells as well.

## Docking Components

You can dock components as well - docking components are always placed outside the normal layout. You can mix grid and docking components in the same layout.

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": ""
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "wrap" // Wrap to next row
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "" 
    }
},
{
    "type": "UILabel",
    "text": "comp1N",
    "layoutInfo": {
        "cc": "dock north" 
    }
},
{
    "type": "UILabel",
    "text": "comp2W",
    "layoutInfo": {
        "cc": "dock west" 
    }
},
{
    "type": "UILabel",
    "text": "comp3S",
    "layoutInfo": {
        "cc": "dock south" 
    }
},
{
    "type": "UILabel",
    "text": "comp4E",
    "layoutInfo": {
        "cc": "east" // "dock" keyword is optional 
    }
}
```

<fig src="layouts/images/mig-layout_component-dock.png" alt="">MiG Layout with docked components</fig>

Note that a docked component "cuts off" the part it's docked to which means that the ordering of the docking components is important for how corners are used. If the `comp4E` component was moved first in the code, the table would look like this instead:

<fig src="layouts/images/mig-layout_component-dock-cutoff.png" alt="">MiG Layout with cut-off docked component</fig>

Docking components is a very good and easy way to layout panels but also has many other usages. You can get spacing around the docking components by using normal Component Gaps as described above.

## Growing and Shrinking Components Depending on Available Space

The `grow` and `shrink` behavior for both columns/rows and components are extremely customizable with `MigLayout`. You can divide them into `grow`/`shrink` priority groups, so that one or a group `grows`/`shrinks` to their maximum or minimum size before the next groups are even considered. It is also possible to set the weight for how likely they will `grow`/`shrink` within that priority group. 

Components and rows/columns will by default `shrink` to their minimum sizes if space is scarce. A column/row's minimum size is by default the largest minimum size of its components.

What is normally enough to know is how to make a component or row/column `grow` and/or disallow it to `shrink`. For example:

```javascript
"type": "migLayout",
"layoutConstraints": "", // Layout constraints
"colConstraints": "[grow][][grow]", // Column constraints 
"rowConstraints": "[][shrink 0]", // Row constraints 
"contents" : [
    // ...
]
```

And for components:

```javascript
{
    "type": "UILabel",
    "text": "comp1",
    "layoutInfo": {
        "cc": "growx" // Grow horizontally. Same as "growx 100"
    }
},
{
    "type": "UILabel",
    "text": "comp2",
    "layoutInfo": {
        "cc": "growy" // Grow vertically. Same as "growy 100"
    }
},
{
    "type": "UILabel",
    "text": "comp3",
    "layoutInfo": {
        "cc": "grow" // Grow both. Same as "grow 100 100"
    }
},
{
    "type": "UILabel",
    "text": "comp4",
    "layoutInfo": {
        "cc": "shrink 0" // Will not shrink
    }
}
```
Components will never "push" the column/row's size to be larger using the `grow` keyword.


For a full documentation of `MigLayout`'s features, check out the official site of the Swing implementation at http://www.migcomponents.com/.
See also the Javadoc for [MigLayout](https://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/job/master/javadoc/org/terasology/nui/layouts/miglayout/MigLayout.html ":target=blank").

