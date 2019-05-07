package com.github.kk.activityresult;

import android.content.Intent;
import android.database.Observable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.util.Random;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class ActivityResultFragment extends Fragment {

    private SparseArray<PublishSubject<ActivityResultInfo>> mSubjects = new SparseArray<>();
    private SparseArray<ActivityResultCallback> mCallbacks = new SparseArray<>();


    public ActivityResultFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PublishSubject<ActivityResultInfo> subject = mSubjects.get(requestCode);
        if (subject != null) {
            mSubjects.delete(requestCode);
            subject.onNext(new ActivityResultInfo(resultCode, data));
            subject.onComplete();
        }

        ActivityResultCallback callback = mCallbacks.get(requestCode);
        if (callback != null) {
            mCallbacks.delete(requestCode);
            callback.onResult(new ActivityResultInfo(resultCode, data));
        }
    }


    public Observable<ActivityResultInfo> startResult(final Intent intent) {
        final PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) {
                int requestCode = generateRequestCode();
                mSubjects.put(requestCode, subject);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    public void startResult(Intent intent, ActivityResultCallback callback) {
        int requestCode = generateRequestCode();
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }


    private int generateRequestCode() {
        Random random = new Random();
        for (; ; ) {
            int code = random.nextInt(65535);
            if (mSubjects.get(code) == null && mCallbacks.get(code) == null) {
                return code;
            }
        }
    }
}
