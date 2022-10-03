package de.ion.coinTrackerApp.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import de.ion.coinTrackerApp.R;

public class AnimationImageZoomOut implements Animation.AnimationListener{
    private Context context;
    private ImageView image;

    /**
     * @param context
     * @param image
     */
    public AnimationImageZoomOut(Context context, ImageView image) {
        this.context = context;
        this.image = image;
        Animation animZoomout = AnimationUtils.loadAnimation(this.context,
                R.anim.zoom_out );
        animZoomout.setAnimationListener(this);
        image.startAnimation(animZoomout);
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        AnimationImageZoomIn animationImageZoomIn = new AnimationImageZoomIn(this.context, this.image);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
