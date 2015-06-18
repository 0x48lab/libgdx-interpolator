package com.kayosystem.interpolatorgraph;

import android.view.animation.Interpolator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kayosystem.interpolatorgraph.ui.ListActor;

public class MyGdxGame extends ApplicationAdapter {

    // 描画範囲
    private final Array<Interpolator> interpolators;

    // テクスチャ
    private final Assets assets = new Assets();
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

        assets.load();
        assets.am.finishLoading();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        graphActor = new GraphActor();
        listActor = new ListActor(interpolators, assets) {
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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        listActor.setHeight(height);
        float right = listActor.getRight() + 80.f;
        Gdx.app.log("MyGdxGame", "right=" + right);
        graphActor.setBounds(right, 0, width - right, height);
    }

    @Override
    public void dispose() {
        Gdx.app.log("MyGdxGame", "dispose()");

        stage.dispose();
        assets.dispose();
    }

}
