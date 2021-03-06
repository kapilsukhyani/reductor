package com.yheriatovych.reductor.rx;

import com.yheriatovych.reductor.Cancelable;
import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Store;
import rx.Emitter;
import rx.Observable;

public final class RxStore {

    /**
     * Create observable of state changes from specified {@link Cursor}
     * <p>
     * Note: This method will emit current sate immediately after subscribe
     */
    public static <State> Observable<State> asObservable(final Cursor<State> cursor) {
        return Observable.fromEmitter(emitter -> {
            emitter.onNext(cursor.getState());
            final Cancelable cancelable = cursor.subscribe(emitter::onNext);
            emitter.setCancellation(cancelable::cancel);
        }, Emitter.BackpressureMode.LATEST);
    }
}
