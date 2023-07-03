package com.delfi.vn.template.utils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

/*
 * Created by DTO-BTAD on 6/2/2021.
 */
public class ObservableCall {
    public static  <T> Observable<T> observable(final Callable<T> func) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(func.call());
                emitter.onComplete();
            } catch (Exception ex) {
                emitter.onError(ex);
            }
        });
    }
}
