package com.mperfit.perfit.utils;

import com.orhanobut.logger.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorNotImplementedException;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhangbing on 2017/1/6.
 */

    public class RxBusUtils {
        private static volatile RxBusUtils mDefaultInstance;
        private final Subject<Object, Object> mBus;

        private Map<Class<?>, Object> mStickyEventMap = null;

        public RxBusUtils() {
            mBus = new SerializedSubject<>(PublishSubject.create());
            if (mStickyEventMap == null){
                mStickyEventMap = new ConcurrentHashMap<>();
            }
        }

        public static RxBusUtils getDefault() {
            if (mDefaultInstance == null) {
                synchronized (RxBusUtils.class) {
                    if (mDefaultInstance == null) {
                        mDefaultInstance = new RxBusUtils();
                    }
                }
            }
            return mDefaultInstance;
        }

        /**
         * 发送事件
         */
        public void post(Object event) {
            try {
                mBus.onNext(event);
            } catch (OnErrorNotImplementedException e){
                Logger.e("Rxutil:" + e.getMessage());
            }
        }

        /**
         * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
         */
        public <T> Observable<T> toObservable(Class<T> eventType) {
            return mBus.ofType(eventType);
        }

        /**
         * 判断是否有订阅者
         */
        public boolean hasObservers() {
            return mBus.hasObservers();
        }

        public void reset() {
            mDefaultInstance = null;
        }

        /**
         * Stciky 相关
         */

        /**
         * 发送一个新Sticky事件
         */
        public void postSticky(Object event) {
            synchronized (mStickyEventMap) {
                if (mStickyEventMap!=null){
                    mStickyEventMap.put(event.getClass(), event);
                }
            }
            post(event);
        }

        /**
         * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
         */
        public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
            synchronized (mStickyEventMap) {
                Object event = null;
                Observable<T> tObservable = null;
                Observable<T> observable = mBus.ofType(eventType);

                if (mStickyEventMap!=null){
                    event = mStickyEventMap.get(eventType);
                }

                if (event != null) {
                    final Object finalEvent = event;
                    try {
                       tObservable = observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                            @Override
                            public void call(Subscriber<? super T> subscriber) {
                                try {

                                    subscriber.onNext(eventType.cast(finalEvent));
                                } catch (OnErrorNotImplementedException e){

                                }
                            }
                        }));

                    }catch (Exception e){
                        Logger.e("RxbusUtiles:" +  e.getMessage());
                    } finally {

                        return tObservable;
                    }

                } else {
                    return observable;
                }
            }
        }

        /**
         * 根据eventType获取Sticky事件
         */
        public <T> T getStickyEvent(Class<T> eventType) {
            synchronized (mStickyEventMap) {
                return eventType.cast(mStickyEventMap.get(eventType));
            }
        }

        /**
         * 移除指定eventType的Sticky事件
         */
        public <T> T removeStickyEvent(Class<T> eventType) {
            synchronized (mStickyEventMap) {
                return eventType.cast(mStickyEventMap.remove(eventType));
            }
        }

        /**
         * 移除所有的Sticky事件
         */
        public void removeAllStickyEvents() {
            synchronized (mStickyEventMap) {
                mStickyEventMap.clear();
            }
        }
    }
    
