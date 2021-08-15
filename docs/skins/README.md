# Skins

In NUI, a skin is a collection of styles which are applied when rendering widgets.

See also the Javadoc for [Skin](https://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/job/master/javadoc/org/terasology/nui/skin/package-summary.html ':target=blank').

## Skin Options

The styles of a skin have the following settings:

### Background Options

* **background** - The texture region to be drawn behind a widget.
* **background-border** - The size of the border of the background. The border is drawn unscaled around the edge of the widget.

<fig src="skins/Skin-Border.png" alt="">The Borders of a Skin</fig>

* **background-scale-mode** - The method to use to scale the background when a widget doesn't match the size of the background region. If the background has a border, this applies to internal region of the background. The possible options are:
  * `stretch` - The image is stretched non-uniformly to fill the given space.
  * `scale fit` - The image is scaled uniformaly so that the image touches the edges of the draw area, without exceeding it.
  * `scale fill` - The image is scaled uniformaly so that the image fills the draw area. Parts of the image extending past the draw area are cropped.
  * `tiled` - The image is tiled to fill the area, without stretching or scaling it. The tiling is centered.

### Size Options

* **fixed-width** - Fixes the width with which an element will be drawn.
* **fixed-height** - Fixes the height with which an element will be drawn.
* **min-width** - Sets a minimum width for an element.
* **min-height** - Sets a minimum height for an element.
* **max-width** - Sets a maximum width for an element - it will not grow beyond this size to fill space.
* **max-height** - Sets a maximum height for an element - it will not grow beyond this size to fill space.
* **align-horizontal** - If an element has more space available than its maximum width, this is how it will be aligned (left, right or center).
* **align-vertical** - If an element has more space available than its maximum height, this is how it will be aligned (top, bottom or middle).

### Content Options

* **margin** - The space between the edges of the element and any content. Often this is the same or larger than the background-border so the content fits inside the border.
* **texture-scale-mode** - For some elements, is used to determine how to scale any texture content.

### Text Options

* **font** - The font to use.
* **text-color** - The base color of the text.
* **text-align-horizontal** - The horizontal alignment of text content.
* **text-align-vertical** - The vertical alignment of text content.
* **text-shadowed** - Whether the text should have a shadow (true/false).
* **text-shadow-color** - The color of the text's shadow, if any.

## Style Hierarchy

Skins define a hierarchy of styles, with more specific styles inheriting and overriding from the broader styles.

* **Base** - The base style defines the core settings for all styles.
* **Family** - A family is a string that is used to provide a different appearance to elements using the same skin. For instance, a screen might have normal buttons, and "flashy" buttons using a different background and font.
* **Element**- Provides settings for different types of widgets.
* **Part** - Some widgets have subparts. For instance, a dropdown has the main entry, and then the list that appears when it is clicked on. All widgets have a default "base" part which can be used to add settings for how the main part of the widget is rendered without affecting the subparts.
* **Mode** - Widgets may have different modes can have - such as when the mouse is hovered over them, or if they are being clicked on. This allows styles to differ depending on the state of a widget.

```
Base
  +-Element
  |  +-Mode
  |  +-Part
  |    +-Mode
  +-Family
    +-Element
      +-Mode
      +-Part
        +-Mode
```

## Skin Inheritance

One final feature of skins is they can inherit each other. For instance, if you want to make a custom skin that is mostly like the default skin, you can do:

```javascript
{
    "inherit" : "default",
    "elements" : {
        // Any additions or changes.
    }
}
```

## Console Commands

If you want to work on a skin while the game is running, you can use the `reloadSkin <uri>` command to reload it immediately. You will need to ensure the changed skin file has been deployed - in IntelliJ this means using _"Make Project..."_. Any UI element using the skin will be immediately updated.

Note that if you change a skin that is inherited by other skins, you will need to reload those skins as well before they will be updated.
