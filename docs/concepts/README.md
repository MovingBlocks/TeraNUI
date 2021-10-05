# Concepts

**TeraNUI** is a canvas-based UI framework designed to provide dynamic elements reflective of game state and adaptive to different resolutions.
The following sections concisely explain the central concepts of TeraNUI required to understand how it works and how it can be worked with.

> [!NOTE]
> More details on the presented concepts may be available but are not crucial for contributors interested in working _with_ TeraNUI only.

## Canvas

The _Canvas_ is the ground all NUI elements are drawn on - basically your screen.
It provides the primitive methods for rendering (drawing on it).
This allows for flexibility in the rendering technology and implementation utilized.

## Screen Layers

## Widgets

## Layouts

While optional, the layout of UI elements can be defined in a JSON format, and then the element built from this definition.
The Control Widget - the root widget of the layout - is given the opportunity to do any data binding, event subscription or other work to provide the control of the UI.

## Events

## Data Bindings

Widget properties can be bound directly to a field elsewhere, keeping it synchronized with that field.
This once again reduces the code required to hook up the UI.
Rather than having to push changes into a widget and then subscribe to widget events to put changes back, you can bind a widget property directly to the widget and the rest is taken care of.

## Skins

A hierarchical skin definition, similar to Cascading Style Sheets (CSS), is used to define the style for each displayed element.
The Canvas automatically applies many of these settings, such as drawing the background and applying any margin.
This provides a number of benefits:

- Skins can be updated or switched at runtime, instantly changing the appearance of the UI elements that use them
- Widgets themselves can concentrate on how they behave and what they draw - not how it appears.
  Common appearance options like fonts, colors, backgrounds and margins are handled automatically by the Canvas.
- Reduces the configuration needed per widget.
  Rather than having to apply a font, background, text alignment, and text color for every label in a table you can define a style family with those settings in a skin once, and then configure all those labels to use that family.
