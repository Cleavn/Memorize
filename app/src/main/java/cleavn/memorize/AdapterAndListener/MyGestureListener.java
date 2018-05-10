package cleavn.memorize.AdapterAndListener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import cleavn.memorize.ActivitiesAndFragments.ToonActivity;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    private ToonActivity mToonActivity;
    public MyGestureListener(ToonActivity activity) {
        mToonActivity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;

        // Define right swipe
        final int SWIPE_THRESHOLD = 100;
        final int SWIPE_VELOCITY_THRESHOLD = 100;

        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // onSwipeRight
                        mToonActivity.flipCard();
                    }
                    result = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
