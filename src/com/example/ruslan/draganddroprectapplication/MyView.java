package com.example.ruslan.draganddroprectapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 21.02.15.
 */
class MyView extends View {
    private GestureDetector mGestureDetector;
    private List<Rect> mRectangles;
    private Paint mPaint;

    // переменные для перетаскивания
    private Rect mDraggableRect;
    private int mDragX;
    private int mDragY;

    public int squareSideSize = 100;

    // данный конструктор нужен чтобы использовать этот элемент в xml layout
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // данный конструктор нужен чтобы использовать этот элемент в xml layout
    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    // данный конструктор нужен чтобы создавать элемент программно
    public MyView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);

        mRectangles = new ArrayList<Rect>();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                int x = (int)e.getX();
                int y = (int)e.getY();

                Rect r = getRectWithPoint(x, y);
                // если нажатие было на существующем объекте, то удаляем его
                if (r != null) {
                    mRectangles.remove(r);
                } else {
                    // добавляем объект
                    mRectangles.add(new Rect(x - squareSideSize / 2, y - squareSideSize / 2, x + squareSideSize / 2, y + squareSideSize / 2));
                }
                invalidate();
            }
        });
    }

    public void addRect(Rect r) {
        mRectangles.add(r);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);

        // рисуем квадраты
        for(Rect R: mRectangles)
            canvas.drawRect(R.left,R.top,R.right,R.bottom, mPaint);

        drawBorder(canvas);
    }

    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

    // отрисовка границы поля
    protected void drawBorder(Canvas canvas) {
        // почему ANTI_ALIAS_FLAG? см. http://stackoverflow.com/questions/5377052/drawline-problem-with-paint-strokewidth-1-in-android
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        // координаты Touch-события
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            // касание началось
            case MotionEvent.ACTION_DOWN:
                Rect r = getRectWithPoint(x, y);
                if (r != null) {
                    // включаем режим перетаскивания
                    mDraggableRect = r;
                    // разница между левым верхним углом квадрата и точкой касания
                    mDragX = x - r.left;
                    mDragY = y - r.top;
                }
                break;
            // тащим
            case MotionEvent.ACTION_MOVE:
                // если режим перетаскивания включен
                if (mDraggableRect != null) {
                    // определеяем новые координаты для рисования
                    int width = mDraggableRect.right - mDraggableRect.left;
                    int height = mDraggableRect.bottom - mDraggableRect.top;
                    mDraggableRect.left = x - mDragX;
                    mDraggableRect.top = y - mDragY;
                    mDraggableRect.right = mDraggableRect.left + width;
                    mDraggableRect.bottom = mDraggableRect.top + height;

                    // перерисовываем экран
                    invalidate();
                }
                break;
            // касание завершено
            case MotionEvent.ACTION_UP:
                // выключаем режим перетаскивания
                mDraggableRect = null;
                break;
        }
        return true;
    }

    private Rect getRectWithPoint(int x, int y) {
        for(Rect r: mRectangles) {
            if (x >= r.left && x <= r.right && y >= r.top && y <= r.bottom) {
                return r;
            }
        }
        return null;
    }
}
