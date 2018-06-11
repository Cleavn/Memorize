package cleavn.memorize.Utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

import cleavn.memorize.ActivitiesAndFragments.LearningsessionActivity;
import cleavn.memorize.ActivitiesAndFragments.ToonActivity;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    int checkActivity;
    private ToonActivity mToonActivity;
    private LearningsessionActivity mLearningsessionActivity;

    public MyGestureListener(ToonActivity activity) {
        mToonActivity = activity;
        checkActivity = 1;
    }

    public MyGestureListener(LearningsessionActivity activity) {
        mLearningsessionActivity = activity;
        checkActivity = 2;
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
                        if(checkActivity == 1){
                            mToonActivity.flipCard();
                        }else if(checkActivity == 2){
                            mLearningsessionActivity.flipCard();
                        }
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
