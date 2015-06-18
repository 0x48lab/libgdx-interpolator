package com.kayosystem.interpolatorgraph;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by kayo on 2015/06/18.
 */
public class Assets {
    public static final String FILE_FONT_VERDANA39 = "verdana39.fnt";
    public static final String FILE_ATLAS_BTN = "btn.atlas";
    public final AssetManager am = new AssetManager();

    public void load() {
        am.load(FILE_FONT_VERDANA39, BitmapFont.class);
        am.load(FILE_ATLAS_BTN, TextureAtlas.class);
    }

    public void dispose() {
        am.dispose();
    }
}
