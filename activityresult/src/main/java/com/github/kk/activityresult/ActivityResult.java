package com.github.kk.activityresult;

import android.support.v4.app.FragmentActivity;

public class ActivityResult {

    private static final String TAG = "kk_ActivityResult";
    private ActivityResultFragment fragment;

    public ActivityResult(FragmentActivity activity) {
        fragment = getFragment(activity);
    }

    private ActivityResultFragment getFragment(FragmentActivity activity) {
        ActivityResultFragment activityResultFragment = (ActivityResultFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (activityResultFragment == null) {
            activityResultFragment = new ActivityResultFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(activityResultFragment, TAG)
                    .commitAllowingStateLoss();
        }
        return activityResultFragment;
    }




    public
}
