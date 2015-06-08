package com.kayosystem.interpolatorgraph.utils;

import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

/**
 * Created by kayo on 15/06/08.
 */
public class DisposeRegister implements Disposable {

    final ArrayList<Disposable> disposables = new ArrayList<Disposable>();

    public <T extends Disposable> T reg(T obj) {
        disposables.add(obj);
        return obj;
    }

    @Override
    public void dispose() {
        for (Disposable d : disposables) {
            d.dispose();
        }
        disposables.clear();
    }
}
