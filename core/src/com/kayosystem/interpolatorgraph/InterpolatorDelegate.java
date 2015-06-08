package com.kayosystem.interpolatorgraph;

import android.view.animation.Interpolator;
import com.kayosystem.interpolatorgraph.android.view.animation.*;

import java.util.ArrayList;

/**
 * Created by kayo on 15/06/08.
 */
public class InterpolatorDelegate implements IInterpolatorDelegate {
    @Override
    public ArrayList<Interpolator> create() throws IllegalAccessException {
        ArrayList<Interpolator> interpolators = new ArrayList<Interpolator>();
        interpolators.add(new AccelerateDecelerateInterpolator());
        interpolators.add(new AccelerateInterpolator());
        interpolators.add(new AnticipateInterpolator());
        interpolators.add(new AnticipateOvershootInterpolator());
        interpolators.add(new BounceInterpolator());
        interpolators.add(new CycleInterpolator(1.0f));
        interpolators.add(new DecelerateInterpolator());
        interpolators.add(new LinearInterpolator());
        interpolators.add(new OvershootInterpolator());
        return interpolators;
    }
}
