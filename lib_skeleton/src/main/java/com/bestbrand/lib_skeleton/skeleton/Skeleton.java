package com.bestbrand.lib_skeleton.skeleton;

import android.view.View;

/**
 * Created by ethanhua on 2017/7/29.
 */

public class Skeleton {

    public static ViewSkeletonScreen.Builder bind(View view) {
        return new ViewSkeletonScreen.Builder(view);
    }

}
