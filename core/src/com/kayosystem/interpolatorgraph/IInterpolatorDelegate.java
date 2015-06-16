package com.kayosystem.interpolatorgraph;

import android.view.animation.Interpolator;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by kayo on 15/06/05.
 */
public interface IInterpolatorDelegate {
    /**
     * Interpolator のリスト
     * @return
     */
    Array<Interpolator> create() throws IllegalAccessException;

}
