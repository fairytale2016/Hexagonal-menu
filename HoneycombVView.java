package com.fairytale110.demo.studio21application.Widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fairytale110.demo.studio21application.R;


/**
 * Created by   fairytale110
 * Creat date   2016/8/22 17:01
 * Copy Right   www.xkyiliao.com
 * function
 */
public class HoneycombVView extends View {
    //六边形间距 b
    private int paddingWidth = 15;
    //六边形宽度 a
    private float lineLength = 5;

    private int viewWidth;
    private int viewHeight;
    private float centerX;
    private float centerY;

    // 每个六边形的中心X坐标，从左上角开始
    private float[] arrayCenterX;
    // 每个六边形的中心Y坐标，从左上角开始
    private float[] arrayCenterY;
    private String[] tittles;
    // 2016/8/23 这个是 六边形默认颜色 自己设置成你需要的就可以
    private int[] colorS = {Color.parseColor("#33B5E5"),
            Color.parseColor("#AA66CC"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#FFBB33"),
            Color.parseColor("#AA66CC"),
            Color.parseColor("#FF4444"),
            Color.parseColor("#FFFFFF")};
    // 2016/8/23 这个是 六边形点击时的 颜色 自己设置成你需要的就可以
    private int[] colorPressed = {Color.parseColor("#AA66CC"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#FF4444"),
            Color.parseColor("#AA66CC"),
            Color.parseColor("#FFBB33"),
            Color.parseColor("#33B5E5"),
            Color.parseColor("#99CC00")};
    private Paint linePaint;
    private Paint TextPaint;
    private Paint mBitPaint;
    private Path linePath;
    private Rect mSrcRect, mDestRect;
    private Bitmap[] mBitmaps;
    private Resources mResources;
    private ActionListener onActionListener;
    public HoneycombVView(Context context) {
        this(context, null);
    }

    public HoneycombVView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoneycombVView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();


        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setShadowLayer(6f,5F,-5f,Color.GRAY);

        TextPaint = new Paint();
        TextPaint.setStyle(Paint.Style.STROKE);
        TextPaint.setAntiAlias(true);
        TextPaint.setColor(Color.WHITE);

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);

        mSrcRect = new Rect();
        mDestRect = new Rect();
        linePath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);

        centerX = viewWidth / 2F;
        centerY = viewHeight / 2F;
        float lineLengthH = (viewHeight - 2 * paddingWidth) / 5;
        float lineLengthW = (viewWidth - 2 * paddingWidth) / (3 * (float) Math.sqrt((3)));
        lineLength = viewHeight > viewWidth ? lineLengthW : lineLengthH;

        initData();

        setMeasuredDimension(viewWidth, viewHeight);

    }

    private void initData() {
        arrayCenterX = new float[]{centerX - lineLength * (float) Math.sqrt(3) / 2 - paddingWidth / 2,
                centerX + lineLength * (float) Math.sqrt(3) / 2 + paddingWidth / 2,
                centerX + lineLength * (float) Math.sqrt(3) + paddingWidth,
                centerX + lineLength * (float) Math.sqrt(3) / 2 + paddingWidth / 2,
                centerX - lineLength * (float) Math.sqrt(3) / 2 - paddingWidth / 2,
                centerX - lineLength * (float) Math.sqrt(3) - paddingWidth,
                centerX};
        arrayCenterY = new float[]{centerY - 3 * lineLength / 2 - paddingWidth ,
                centerY - 3 * lineLength / 2 - paddingWidth ,
                centerY,
                centerY + 3 * lineLength / 2 + paddingWidth ,
                centerY + 3 * lineLength / 2 + paddingWidth ,
                centerY,centerY};
        tittles = new String[]{"优惠活动", "技术文档", "供求信息", "新闻", "商品查询", "广告", "公司简介"};

        // TODO: 2016/8/23 把 mBitmaps 的图片bitmap换成你项目里面的就可以了
        Bitmap mBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.ic_launcher)).getBitmap();
        mBitmaps = new Bitmap[]{mBitmap, mBitmap, mBitmap, mBitmap, mBitmap, mBitmap, mBitmap};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制四周的六边形
        for (int index = 0; index < 6; index++) {
            linePaint.setColor(curAction == index ? colorPressed[index] :colorS[index] );
            drawHoneycomb(canvas, arrayCenterX[index], arrayCenterY[index], tittles[index], mBitmaps[index]);
        }
        linePaint.setColor(curAction == 6 ? colorPressed[6] :colorS[6] );
        drawHoneycomb(canvas, centerX, centerY, tittles[6], mBitmaps[6]);
    }

    private int curAction = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float touchedX = event.getX();
                float touchedY = event.getY();
                curAction = onTouchquareS(touchedX, touchedY);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (onActionListener != null){
                     onActionListener.ActionListener(curAction);
                }
                curAction = -1;
                postInvalidate();
                break;
        }
        return true;
    }

    private void drawHoneycomb(Canvas canvas, float X, float Y, String title, Bitmap bitmap) {
        TextPaint.setColor(Color.WHITE);

        linePath.reset();
        linePath.moveTo(X, Y - lineLength);
        linePath.lineTo(X + lineLength * (float) Math.sqrt(3) / 2, Y - lineLength / 2);
        linePath.lineTo(X + lineLength * (float) Math.sqrt(3) / 2, Y + lineLength / 2);
        linePath.lineTo(X, Y + lineLength);
        linePath.lineTo(X - lineLength * (float) Math.sqrt(3) / 2, Y + lineLength / 2);
        linePath.lineTo(X - lineLength * (float) Math.sqrt(3) / 2, Y - lineLength / 2);
        linePath.close();

        canvas.drawPath(linePath, linePaint);

        if (TextUtils.isEmpty(title)) {
            title = "无标题";
        }

        //最多5个字
        title = title.length() > 5 ? title.substring(0, 5) : title;
        int lengthTxt = title.length() > 5 ? 5 : title.length();
        float textSize = lineLength * (float) Math.sqrt(3) / 6;
        float startX = X - textSize * lengthTxt / 2;
        float startY = Y + lineLength / 2;
        TextPaint.setTextSize(textSize);

        if (title.equals("公司简介")){
            TextPaint.setColor(Color.parseColor("#A4D3EE"));
        }
        canvas.drawText(title, startX, startY, TextPaint);

        mDestRect.left = (int) (X - lineLength / 4);
        mDestRect.right = (int) (X + lineLength / 4);
        mDestRect.top = (int) (Y - lineLength / 2);
        mDestRect.bottom = (int) Y;

        canvas.drawBitmap(bitmap, null, mDestRect, new Paint());
    }

    private int onTouchquareS(float x, float y) {

        int curAction = -1;

        for (int index = 0; index < arrayCenterX.length; index++) {
            float curX = arrayCenterX[index];
            float curY = arrayCenterY[index];

            if (Math.pow(curX - x, 2) + Math.pow(curY - y, 2) <= 3 * Math.pow(lineLength, 2) / 4) {
                curAction = index;
                break;
            }
        }
        return curAction;

    }

    public interface ActionListener{
        /**
         *
         * @param actionIndex 点击的六边形位置，坐上角为开始0，顺时针计数，中间的为最后一个
         */
        void ActionListener(int actionIndex);
    }

    /**
     * 设置点击事件
     * @param onActionListener
     */
    public void setOnActionListener(ActionListener onActionListener){
        this.onActionListener = onActionListener;
    }
}
