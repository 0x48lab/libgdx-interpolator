package com.kayosystem.interpolatorgraph.android;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kayosystem.interpolatorgraph.IInterpolatorDelegate;
import com.kayosystem.interpolatorgraph.MyGdxGame;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new MyGdxGame(new InterpolatorDelegate(getApplicationContext())), config);
    }

    public static class InterpolatorDelegate implements IInterpolatorDelegate {

        private final Context context;

        public InterpolatorDelegate(Context context) {
            this.context = context;
        }

        @Override
        public ArrayList<Interpolator> create() throws IllegalAccessException {
            ArrayList<Interpolator> list = new ArrayList<>();

            Class<android.R.interpolator> interpolatorClass = android.R.interpolator.class;
            Field[] fields = interpolatorClass.getDeclaredFields();

            int i = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                int value = field.getInt(null);
                Interpolator interpolator = AnimationUtils.loadInterpolator(context, value);
                list.add(interpolator);
            }

            // Add Support library Interpolators
            list.add(new FastOutLinearInInterpolator());
            list.add(new FastOutSlowInInterpolator());
            list.add(new LinearOutSlowInInterpolator());

            return list;
        }
    }
}
