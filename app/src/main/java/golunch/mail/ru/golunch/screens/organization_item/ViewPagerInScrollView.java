package golunch.mail.ru.golunch.screens.organization_item;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import golunch.mail.ru.golunch.MainActivity;

public class ViewPagerInScrollView extends ViewPager {
    int height;

    public ViewPagerInScrollView(Context context) {
        super(context);
    }
    public ViewPagerInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childrenHeight = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > childrenHeight) childrenHeight = h;
        }
        //ViewGroup.LayoutParams params = getChildAt(getCurrentItem()).getLayoutParams();
        //params.height = Math.max(height,childrenHeight);
        //getChildAt(getCurrentItem()).setLayoutParams(params);
        //setMeasuredDimension(getMeasuredWidth(), height);
        setMeasuredDimension(getMeasuredWidth(), Math.max(height,childrenHeight));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        //((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels - (int) OrganizationItemActivity.convertDpToPixel(24 + 56, getContext()) ;
        Log.d("MyViewPager", "Height = " + height );
    }
}