package com.kayosystem.interpolatorgraph;

import android.view.animation.Interpolator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kayosystem.interpolatorgraph.ui.ListActor;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

    // 描画範囲
    private final ArrayList<Interpolator> interpolators;

    // テクスチャ
    private BitmapFont font;
    private Stage stage;
    private GraphActor graphActor;
    private ListActor listActor;

    public MyGdxGame() {
        this(null);
    }
    public MyGdxGame(IInterpolatorDelegate delegate) {
        try {
            if (delegate != null) {
                interpolators = delegate.create();
            } else {
                InterpolatorDelegate portedInterpolators = new InterpolatorDelegate();
                interpolators = portedInterpolators.create();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create() {
        Gdx.app.log("MyGdxGame", "create()");

        font = new BitmapFont(Gdx.files.internal("verdana39.fnt"));
        font.getData().setScale(0.5f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        graphActor = new GraphActor();
        listActor = new ListActor(interpolators, font) {
            @Override
            public void onSelectedInterpolator(Interpolator interpolator) {
                Gdx.app.log("MyGdxGame", "onSelectedInterpolator: " + interpolator.getClass().getSimpleName());
                graphActor.setInterpolator(interpolator);
            }
        };

        stage.addActor(listActor);
        stage.addActor(graphActor);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1); // #0099CC
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        listActor.setHeight(height);
        float right = listActor.getRight() + 60.f;
        Gdx.app.log("MyGdxGame", "right=" + right);
        graphActor.setBounds(right, 0, width - right, height);
    }

    @Override
    public void dispose() {
        Gdx.app.log("MyGdxGame", "dispose()");

        stage.dispose();
        font.dispose();
    }

}
