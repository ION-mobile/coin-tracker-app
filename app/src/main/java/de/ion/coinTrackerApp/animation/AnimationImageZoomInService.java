package de.ion.coinTrackerApp.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import de.ion.coinTrackerApp.R;

public class AnimationImageZoomInService implements Animation.AnimationListener {
    private final Context context;
    private final ImageView image;

    /**
     * @param context
     * @param image
     */
    public AnimationImageZoomInService(Context context, ImageView image) {
        this.context = context;
        this.image = image;
        Animation animZoomin = AnimationUtils.loadAnimation(this.context,
                R.anim.zoom_in);
        animZoomin.setAnimationListener(this);
        this.image.startAnimation(animZoomin);

    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new AnimationImageZoomOutService(this.context, this.image);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
