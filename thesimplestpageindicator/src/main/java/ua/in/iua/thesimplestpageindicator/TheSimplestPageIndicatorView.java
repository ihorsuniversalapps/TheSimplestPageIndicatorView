package ua.in.iua.thesimplestpageindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * VUDUU application.
 * Created by rusin on 04.12.15.
 */
public class TheSimplestPageIndicatorView extends View {

    private Paint mPaintInactive = new Paint();
    private Paint mPaintSelected = new Paint();
    private int mIndicatorRadius = 5;
    private int mIndicatorPadding = 0;
    private int mIndicatorsCount = 5;

    private int mIndicatorSize = 10;
    private int mCurrentSelectedItem = 0;

    public TheSimplestPageIndicatorView(Context context) {
        super(context);
    }

    public TheSimplestPageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TheSimplestPageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TheSimplestPageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int pageIndicatorDefaultInactiveColor = 0xffbbbbbb;
        int pageIndicatorDefaultSelectedColor = 0xffffbbbb;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TheSimplestPageIndicatorView,
                0, 0);
        try {
            mIndicatorsCount = a.getInt(R.styleable.TheSimplestPageIndicatorView_indicatorsCount, 5);
            mIndicatorRadius = a.getDimensionPixelSize(R.styleable.TheSimplestPageIndicatorView_indicatorRadius, 5);
            mIndicatorPadding = a.getDimensionPixelSize(R.styleable.TheSimplestPageIndicatorView_indicatorPadding, 5);
            mIndicatorSize = mIndicatorRadius * 2 + mIndicatorPadding;

            mPaintInactive.setFlags(Paint.ANTI_ALIAS_FLAG);
            mPaintInactive.setAntiAlias(true);
            mPaintInactive.setColor(a.getColor(R.styleable.TheSimplestPageIndicatorView_indicatorInactiveColor, pageIndicatorDefaultInactiveColor));

            mPaintSelected.setFlags(Paint.ANTI_ALIAS_FLAG);
            mPaintSelected.setAntiAlias(true);
            mPaintSelected.setColor(a.getColor(R.styleable.TheSimplestPageIndicatorView_indicatorSelectedColor, pageIndicatorDefaultSelectedColor));
        } finally {
            a.recycle();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Get size requested and size mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        //Determine Width
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(mIndicatorsCount * mIndicatorSize, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                width = mIndicatorRadius * mIndicatorSize;
                break;
        }

        //Determine Height
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(mIndicatorSize, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                height = mIndicatorSize;
                break;
        }

        setMeasuredDimension(width, height);
    }

    public void setCurrentSelectedItem(int itemIndex) {
        if (itemIndex >= 0 && itemIndex < mIndicatorsCount) {
            mCurrentSelectedItem = itemIndex;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mIndicatorsCount; i++) {
            canvas.drawCircle(i * mIndicatorSize + mIndicatorSize / 2, mIndicatorRadius + mIndicatorPadding / 2, mIndicatorRadius, mCurrentSelectedItem == i ? mPaintSelected : mPaintInactive);
        }
    }
}
