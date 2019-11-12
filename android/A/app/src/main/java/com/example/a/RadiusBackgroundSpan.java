package com.example.a;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Created by changle.fang on 2019-11-07.
 *
 * @author changle.fang
 * @email changle.fang@ximalaya.com
 * @phoneNumber 15050162674
 */
public class RadiusBackgroundSpan extends ReplacementSpan {

    private Context context;
    private int mSize;
    private int bgColor;
    private int textColor;
    private int mRadius;
    private int leftMargin;
    private int textSize;
    private int mainTextSize;
    private int leftPadding;

    public RadiusBackgroundSpan(Context context,int bgColor, int textColor, int radius, int leftMargin, int textSize, int mainTextSize) {
        this.bgColor = bgColor;
        this.textColor = textColor;
        mRadius = radius;
        this.leftMargin = leftMargin;
        this.textSize = textSize;
        this.mainTextSize = mainTextSize;
        this.context = context;
        leftPadding = BaseUtil.dp2px(context, 6);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (end - start) * textSize + 2 * leftPadding + leftMargin;
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int deltaY = BaseUtil.dp2px(context, 1);
        paint.setColor(bgColor);
        paint.setAntiAlias(true);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        RectF oval = new RectF(x + leftMargin, y + fontMetrics.ascent + deltaY, (float) (x + mSize), y + fontMetrics.descent - deltaY);
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        drawTagText(canvas, text, start, end, x, y, paint);
    }

    private void drawTagText(Canvas canvas, CharSequence text, int start, int end, float x, int y, Paint paint) {
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        canvas.drawText(text, start, end, (float) (x + leftMargin + leftPadding), (float) (y - (mainTextSize - textSize) / 3.0), paint);
    }
}
