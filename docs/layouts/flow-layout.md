# Flow Layout

The _Flow_ layout arranges its children in a directional flow that wraps at the layout's boundary,
very much like words wrap at the end of a line when writing a text. The children are layed out in
row in the flow direction, each widget sized by its preferred size. The individual elements are
top-aligned, and wrapped at the first element that does not fit in the row.

<fig src="layouts/images/flow-layout_ltr.svg" alt="">Arrangement of widgets in Flow layout with left-to-right alignment.</fig>

_Flow_ lays out each managed child regardless of the child's visible property value - invisible
children won't be rendered but will take up space. The layout may be styled as other widgets with
[Skins](). See [AbstractWidget]() superclass for more details.

See also the Javadoc for [FlowLayout](https://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/job/master/javadoc/org/terasology/nui/layouts/FlowLayout.html ':target=blank').

Example of a _Flow_ layout:

```json5
{
  "type": "FlowLayout",
  "verticalSpacing": 8,
  "horizontalSpacing": 24,
  "contents": [...]
  // all properties of AbstractWidget
}
```

---

## Implementation

<fig src="layouts/images/flow-layout_internal.svg" alt="">Internal fields and measures of the Flow layout.</fig>
