package org.terasology.nui.skin;

import org.terasology.assets.Asset;
import org.terasology.assets.AssetType;
import org.terasology.assets.ResourceUrn;
import org.terasology.assets.format.AssetDataFile;
import org.terasology.nui.UIWidget;

public class UISkinAsset extends Asset<UISkinData> {
    private UISkinData skinData;

    private transient AssetDataFile source;

    public UISkinAsset(ResourceUrn urn, AssetType<?, UISkinData> assetType, UISkinData data) {
        super(urn, assetType);
        reload(data);
    }

    @Override
    protected void doReload(UISkinData data) {
        this.skinData = data;
        this.source = data.getSource();
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
        UIStyleFamily styleFamily = skinData.getFamily(family);
        if (styleFamily == null) {
            return skinData.getFamily("");
        }
        return styleFamily;
    }

    public Iterable<? extends String> getFamilies() {
        return skinData.getUiSkin().getFamilies();
    }

    public AssetDataFile getSource() {
        return source;
    }

    public UISkin getSkin() {
        return skinData.getUiSkin();
    }
}
