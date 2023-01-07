package com.caldremch.android.common.sample.ui.main;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Leon
 * @date 2023/1/7
 * @desc
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG = "SingleLiveEvent";

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @MainThread
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                /**
                 *
                 * 参考: https://blog.csdn.net/zhushuai1221/article/details/94313313
                 * compareAndSet的做法:
                 * 仅当预期值和内存值相等时, 才会将内存值改为 执行的新值
                 *
                 * 比较并交换
                 * 内存值, 旧的预期值, 新值
                 *
                 * 内存值: mPending当前的值
                 * 旧的预期值:true
                 * 新值:false
                 *
                 * 仅仅当预期的值和内置
                 *
                 * 当在调用setValue的时候, 会执行, mPending.set(true); 我们预期它是true
                 *  然后,
                 *
                 *  在原来的LiveData中, 页面重建以后, 会走到这里,
                 *
                 *  但是我们并没有执行setValue, 所以内存值肯定是false, 所以预期的新值和内存值不一样, 就不会导致
                 *  消息的发送了
                 */
                boolean a = mPending.compareAndSet(true, false);
                /**
                 * 返回值a=true表示更新成功, 否则表示更新失败
                 * 所以页面重建后, 这里比较和更新是会失败的, 所以不会导致
                 */
                Log.d(TAG, "onChanged: a=" + a);
                if (a) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}
