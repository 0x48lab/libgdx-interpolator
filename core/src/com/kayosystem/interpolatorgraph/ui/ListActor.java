package com.kayosystem.interpolatorgraph.ui;

import android.view.animation.Interpolator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.kayosystem.interpolatorgraph.utils.DisposeRegister;

import java.util.ArrayList;

/**
 * Created by kayo on 15/06/08.
 */
public class ListActor extends Group implements Disposable {

    private final ScrollPane scrollPane;
    private final DisposeRegister dr = new DisposeRegister();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final ArrayList<Interpolator> interpolators;

    private final BitmapFont font;
    private final GlyphLayout glyph;
    private final Color fontColor;
    private final Table table;

    public ListActor(ArrayList<Interpolator> interpolators, BitmapFont font) {
        this.interpolators = interpolators;
        this.font = font;
        this.glyph = new GlyphLayout();
        this.fontColor = new Color(0, 0, 0, 0);

        table = new Table();

        TextureAtlas textureAtlas = dr.reg(new TextureAtlas(Gdx.files.internal("btn.atlas")));
        NinePatch normal = textureAtlas.createPatch("btn_default_normal_holo_light");
        NinePatch pressed = textureAtlas.createPatch("btn_default_pressed_holo_light");

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.BLACK;
        buttonStyle.up = new NinePatchDrawable(normal);
        buttonStyle.down = new NinePatchDrawable(pressed);

        float contentWidth = 0;
        for (final Interpolator interpolator : interpolators) {
            String simpleName = interpolator.getClass().getSimpleName();

            TextButton button = new TextButton(simpleName, buttonStyle);
            ClickListener listener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onSelectedInterpolator(interpolator);
                }
            };
            button.addListener(listener);
            table.add(button).align(Align.left);
            table.row();
            contentWidth = Math.max(contentWidth, button.getWidth());
        }
        scrollPane = new ScrollPane(table);
        scrollPane.setWidth(contentWidth);
        this.addActor(scrollPane);
        setWidth(contentWidth);
    }

    public void onSelectedInterpolator(Interpolator interpolator) {
    }

    @Override
    protected void sizeChanged() {
        scrollPane.setHeight(getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        scrollPane.drawDebug(shapeRenderer);
        table.drawDebug(shapeRenderer);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void dispose() {
        dr.dispose();
    }
}
