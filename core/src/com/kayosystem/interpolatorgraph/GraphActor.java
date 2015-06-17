package com.kayosystem.interpolatorgraph;

import android.view.animation.Interpolator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by kayo on 15/06/08.
 */
public class GraphActor extends Actor implements Disposable {

    private final ShapeRenderer sr = new ShapeRenderer();
    final Rectangle rectangle = new Rectangle();
    private final Array<Vector2> points = new Array<Vector2>();
    private Interpolator interpolator;
    private float currentTime;
    private float duration = 1.f;
    private boolean isDrawFinished = true;

    public GraphActor() {
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        reset();
    }

    public void reset() {
        this.currentTime = 0;
        this.isDrawFinished = false;
        points.clear();
    }

    @Override
    protected void sizeChanged() {
        float paddingTop = 80.f;
        float paddingRight = 60.f;
        float paddingBottom = 80.f;
        float paddingLeft = 60.f;
        float rectWidth = getWidth() - paddingLeft - paddingRight;
        float rectHeight = getHeight() - paddingBottom - paddingTop;
        float rectSize = Math.min(rectWidth, rectHeight);

        rectangle.set(getX() + getWidth() - rectSize - paddingRight,
                (getHeight() * 0.5f) - (rectSize * 0.5f),
                rectSize, rectSize);
        reset();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        float deltaTime = Gdx.graphics.getDeltaTime();

        update(deltaTime);

        sr.setProjectionMatrix(this.getStage().getViewport().getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLACK);
        sr.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        float oldX = rectangle.x;
        float oldY = rectangle.y;
        for (Vector2 point : points) {
            sr.line(oldX, oldY, point.x, point.y);
            oldX = point.x;
            oldY = point.y;
        }

        sr.end();
        batch.begin();
    }

    private void update(float deltaTime) {
        if (this.interpolator != null) {
            this.currentTime += deltaTime;
            if (duration > currentTime) {
                float currentTimeFraction = (currentTime / duration);
                float interpolation = this.interpolator.getInterpolation(currentTimeFraction);
                float x = (rectangle.width * currentTimeFraction) + rectangle.x;
                float y = (rectangle.height * interpolation) + rectangle.y;
                points.add(new Vector2(x, y));
                isDrawFinished = false;
            } else {
                if (!isDrawFinished) {
                    points.add(new Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
                }
            }
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
