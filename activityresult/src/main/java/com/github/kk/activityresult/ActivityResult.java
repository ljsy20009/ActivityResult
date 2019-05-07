package com.github.kk.activityresult;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import io.reactivex.Observable;

public class ActivityResult {

    private static final String TAG = "kk_ActivityResult";
    private ActivityResultFragment fragment;

    private ActivityResult(FragmentActivity activity) {
        fragment = getFragment(activity);
    }

    private ActivityResultFragment getFragment(FragmentActivity activity) {
        ActivityResultFragment activityResultFragment = (ActivityResultFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (activityResultFragment == null) {
            activityResultFragment = new ActivityResultFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(activityResultFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return activityResultFragment;
    }

    public Observable<ActivityResultInfo> startResult(Intent intent){
        return fragment.startResult(intent);
    }

    public Observable<ActivityResultInfo> startResult(Class cls){
        return startResult(new Intent(fragment.getActivity(), cls));
    }

    public void startResult(Intent intent, ActivityResultCallback callback){
        fragment.startResult(intent, callback);
    }

    public void startResult(Class cls, ActivityResultCallback callback){
        startResult(new Intent(fragment.getContext(), cls), callback);
    }


    public static ActivityResult in(FragmentActivity activity){
        return new ActivityResult(activity);
    }
}
