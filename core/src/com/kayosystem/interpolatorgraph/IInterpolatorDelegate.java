package com.kayosystem.interpolatorgraph;

import android.view.animation.Interpolator;

import java.util.ArrayList;

/**
 * Created by kayo on 15/06/05.
 */
public interface IInterpolatorDelegate {
    /**
     * Interpolator のリスト
     * @return
     */
    ArrayList<Interpolator> create() throws IllegalAccessException;

}
