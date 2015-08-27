package mobi.gspd.segmentedprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
import java.util.List;

public class SegmentedBarView extends View {

    private List<Segment> segments;
    private String unit;
    private float value = -1;

    private Rect rectBounds;
    private Rect textBounds;
    private Rect valueSignBounds;
    private RectF roundRectangleBounds;
    private Paint paint;
    private Paint textPaint;
    private Paint descriptionTextPaint;

    private DecimalFormat df = new DecimalFormat();

    private int valueSignHeight = 120;
    private int valueSignColor = Color.parseColor("#7492E2");
    private int emptySegmentColor = Color.parseColor("#858585");
    private int valueSignWidth = 300;
    private int arrowHeight = 20;
    private int arrowWidth = 40;
    private int gapSize = 12;
    private int barHeight = 90;

    private int barRoundingRadius = 0;
    private int valueSignCenter = -1;

    private boolean showDescriptionText = false;
    private boolean showText = true;

    private int sideStyle = SegmentedBarViewSideStyle.ROUNDED;
    private int sideTextStyle = SegmentedBarViewSideTextStyle.ONE_SIDED;

    private Paint paintStroke;

    public SegmentedBarView(Context context) {
        super(context);
        init();
    }

    public SegmentedBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("SBV", "onDraw()");

//        //temp rectangle to show paddings
//        Rect rect = new Rect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
//        Log.d("rect temp", rect.width() + " " + rect.height());
//        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), paintStroke);

        int segmentsSize = segments == null ? 0 : segments.size();
        if (segmentsSize > 0) {
            for (int i = 0; i < segmentsSize; i++) {
                Segment segment = segments.get(i);

                drawSegment(canvas, segment, i, segmentsSize);
            }
        } else {
            drawEmptySegment(canvas);
        }

        if (!valueIsEmpty()) {
            drawValueSign(canvas, valueSignSpaceHeight(), valueSignCenter);
        }
    }

    private void drawEmptySegment(Canvas canvas) {
        int segmentsSize = 1;

        int singleSegmentWidth = getContentWidth() / segmentsSize;
        rectBounds.set(getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), singleSegmentWidth + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());

        paint.setColor(emptySegmentColor);

        barRoundingRadius = rectBounds.height() / 2;
        roundRectangleBounds.set(rectBounds.left, rectBounds.top, rectBounds.right, rectBounds.bottom);
        canvas.drawRoundRect(roundRectangleBounds, barRoundingRadius, barRoundingRadius, paint);

        if (showText) {
            String textToShow;
            textToShow = "Empty";
            rectBounds = new Rect((int) roundRectangleBounds.left,
                    (int) roundRectangleBounds.top, (int) roundRectangleBounds.right, (int) roundRectangleBounds.bottom);

            drawTextCentredInRect(canvas, textPaint, textToShow, rectBounds);
        }
    }

    private int getContentWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getContentHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private void drawSegment(Canvas canvas, Segment segment, int i, int segmentsSize) {
        boolean isLeftSegment = i == 0;
        boolean isRightSegment = i == segmentsSize - 1;
        boolean isLeftAndRight = isLeftSegment && isRightSegment;

        int singleSegmentWidth = getContentWidth() / segmentsSize;
        int segmentLeft = singleSegmentWidth * i;

        if (!isLeftSegment) {
            segmentLeft += gapSize;
        }
        int segmentRight = singleSegmentWidth * (i + 1);

        // Segment bounds
        rectBounds.set(segmentLeft + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());

        // Calculating value sign position
        if (!valueIsEmpty()) {
            if (value >= segment.getMinValue() && value < segment.getMaxValue() || (isRightSegment && segment.getMaxValue() == value)) {
                float valueSignCenterPercent = (value - segment.getMinValue()) / (segment.getMaxValue() - segment.getMinValue());
                valueSignCenter = (int) (segmentLeft + getPaddingLeft() + valueSignCenterPercent * singleSegmentWidth);
            }
        }

        paint.setColor(segment.getColor());

        float textCenterX = rectBounds.centerX();
        float textCenterY = rectBounds.centerY();
        Rect rect = new Rect(rectBounds);

        // Drawing segment (with specific bounds if left or right)
        if (isLeftSegment || isRightSegment) {
            barRoundingRadius = rectBounds.height() / 2;
            if (barRoundingRadius > singleSegmentWidth / 2) {
                sideStyle = SegmentedBarViewSideStyle.NORMAL;
            }

            switch (sideStyle) {
                case SegmentedBarViewSideStyle.ROUNDED:
                    roundRectangleBounds.set(rectBounds.left, rectBounds.top, rectBounds.right, rectBounds.bottom);
                    canvas.drawRoundRect(roundRectangleBounds, barRoundingRadius, barRoundingRadius, paint);
                    if (!isLeftAndRight) {
                        if (isLeftSegment) {
                            rectBounds.set(segmentLeft + barRoundingRadius + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                        } else {
                            rectBounds.set(segmentLeft + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight - barRoundingRadius + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                        }
                    }
                    break;
                case SegmentedBarViewSideStyle.ANGLE:
                    if (isLeftAndRight) {
                        rectBounds.set(segmentLeft + barRoundingRadius + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight - barRoundingRadius + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                        canvas.drawRect(
                                rectBounds,
                                paint
                        );
                        //Draw left triangle
                        Point point1 = new Point(rectBounds.left - barRoundingRadius, rectBounds.top + barRoundingRadius);
                        Point point2 = new Point(rectBounds.left, rectBounds.top);
                        Point point3 = new Point(rectBounds.left, rectBounds.bottom);

                        drawTriangle(canvas, point1, point2, point3);

                        //Draw right triangle
                        Point point4 = new Point(rectBounds.right + barRoundingRadius, rectBounds.top + barRoundingRadius);
                        Point point5 = new Point(rectBounds.right, rectBounds.top);
                        Point point6 = new Point(rectBounds.right, rectBounds.bottom);

                        drawTriangle(canvas, point4, point5, point6);
                    } else {
                        if (isLeftSegment) {
                            rectBounds.set(segmentLeft + barRoundingRadius + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                            //Draw left triangle
                            Point point1 = new Point(rectBounds.left - barRoundingRadius, rectBounds.top + barRoundingRadius);
                            Point point2 = new Point(rectBounds.left, rectBounds.top);
                            Point point3 = new Point(rectBounds.left, rectBounds.bottom);

                            drawTriangle(canvas, point1, point2, point3);
                        } else {
                            rectBounds.set(segmentLeft + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight - barRoundingRadius + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                            //Draw right triangle
                            Point point4 = new Point(rectBounds.right + barRoundingRadius, rectBounds.top + barRoundingRadius);
                            Point point5 = new Point(rectBounds.right, rectBounds.top);
                            Point point6 = new Point(rectBounds.right, rectBounds.bottom);

                            drawTriangle(canvas, point4, point5, point6);
                        }
                    }
                    break;
                case SegmentedBarViewSideStyle.NORMAL:
                    canvas.drawRect(
                            rectBounds,
                            paint
                    );
                default:
                    break;
            }
        } else {
            canvas.drawRect(
                    rectBounds,
                    paint
            );
        }

        // Drawing segment text
        if (showText) {
            String textToShow;
            if (isLeftSegment || isRightSegment) {
                if (isLeftAndRight || sideTextStyle == SegmentedBarViewSideTextStyle.TWO_SIDED) {
                    textToShow = String.format("%s - %s", df.format(segment.getMinValue()), df.format(segment.getMaxValue()));
                } else if (isLeftSegment) {
                    textToShow = String.format("<%s", df.format(segment.getMaxValue()));
                } else {
                    textToShow = String.format(">%s", df.format(segment.getMinValue()));
                }
            } else {
                textToShow = String.format("%s - %s", df.format(segment.getMinValue()), df.format(segment.getMaxValue()));
            }

//            drawTextCentredInRectSides(canvas, textPaint, textToShow, textCenterX, textCenterY);
            drawTextCentredInRect(canvas, textPaint, textToShow, rect);
        }

        //Drawing segment description text
        if (showDescriptionText) {
            Log.d("desc text y", rectBounds.height() + "");
//            drawTextCentredInRectSides(canvas, descriptionTextPaint, segment.getDescriptionText(), textCenterX, textCenterY + rectBounds.height());
            drawTextCentredInRectSides(canvas, descriptionTextPaint, segment.getDescriptionText(), rect.left, rect.top + rectBounds.height(), rect.right, rect.bottom + rectBounds.height());
        }
    }

    private void drawValueSign(Canvas canvas, int valueSignSpaceHeight, int valueSignCenter) {
        boolean valueNotInSegments = valueSignCenter == -1;
        if (valueNotInSegments) {
            valueSignCenter = getContentWidth() / 2;
        }
        valueSignBounds.set(valueSignCenter - valueSignWidth / 2,
                getPaddingTop(),
                valueSignCenter + valueSignWidth / 2,
                valueSignHeight - arrowHeight + getPaddingTop());
        paint.setColor(valueSignColor);
        if (valueSignBounds.left < getPaddingLeft()) {
            int difference = -valueSignBounds.left + getPaddingLeft();
            roundRectangleBounds.set(valueSignBounds.left + difference, valueSignBounds.top, valueSignBounds.right + difference, valueSignBounds.bottom);
        } else if (valueSignBounds.right > getMeasuredWidth() - getPaddingRight()) {
            int difference = valueSignBounds.right - getMeasuredWidth() + getPaddingRight();
            roundRectangleBounds.set(valueSignBounds.left - difference, valueSignBounds.top, valueSignBounds.right - difference, valueSignBounds.bottom);
        } else {
            roundRectangleBounds.set(valueSignBounds.left, valueSignBounds.top, valueSignBounds.right, valueSignBounds.bottom);
        }
        canvas.drawRoundRect(
                roundRectangleBounds,
                10f,
                10f,
                paint
        );

        String text = String.format("%s", df.format(value));
        if (unit != null && !unit.isEmpty()) text += " " + unit;
        drawTextCentredInRect(canvas, textPaint, text,
                new Rect((int) roundRectangleBounds.left, (int) roundRectangleBounds.top, (int) roundRectangleBounds.right, (int) roundRectangleBounds.bottom));

        // Draw arrow
        if (!valueNotInSegments) {
            int difference = 0;
            if (valueSignCenter - arrowWidth / 2 < barRoundingRadius + getPaddingLeft()) {
                difference = barRoundingRadius - valueSignCenter + getPaddingLeft();
            } else if (valueSignCenter + arrowWidth / 2 > getMeasuredWidth() - barRoundingRadius - getPaddingRight()) {
                difference = (getMeasuredWidth() - barRoundingRadius) - valueSignCenter - getPaddingRight();
            }

            Point point1 = new Point(valueSignCenter - arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight + getPaddingTop());
            Point point2 = new Point(valueSignCenter + arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight + getPaddingTop());
            Point point3 = new Point(valueSignCenter + difference, valueSignSpaceHeight + getPaddingTop());

            drawTriangle(canvas, point1, point2, point3);
        }
    }

    private void drawTriangle(Canvas canvas, Point point1, Point point2, Point point3) {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        path.lineTo(point1.x, point1.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minWidth = getPaddingLeft() + getPaddingRight();
        int minHeight = barHeight + getPaddingBottom() + getPaddingTop();
        if (!valueIsEmpty()) {
            minHeight += valueSignHeight;
        }
        if (showDescriptionText) {
            minHeight += barHeight; // todo not bar height but text height
        }
        int w = resolveSizeAndState(minWidth, widthMeasureSpec, 0);
        int h = resolveSizeAndState(minHeight, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("SBV", "onLayout()");
    }

    private int valueSignSpaceHeight() {
        if (valueIsEmpty()) return 0;
        return valueSignHeight;
    }

    private boolean valueIsEmpty() {
        return value == -1;
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setStyle(Paint.Style.FILL);

        descriptionTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        descriptionTextPaint.setColor(Color.DKGRAY);
        descriptionTextPaint.setTextSize(50);
        descriptionTextPaint.setStyle(Paint.Style.FILL);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(5f);

        rectBounds = new Rect();
        textBounds = new Rect();
        valueSignBounds = new Rect();
        roundRectangleBounds = new RectF();
    }

    public void drawTextCentredInRect(Canvas canvas, Paint paint, String text, Rect outsideRect) {
        drawTextCentredInRectSides(canvas, paint, text, outsideRect.left, outsideRect.top, outsideRect.right, outsideRect.bottom);
    }

    public void drawTextCentredInRectSides(Canvas canvas, Paint paint, String text, float left, float top, float right, float bottom) {
        paint.getTextBounds(text, 0, text.length(), textBounds);
        paint.setTextAlign(Paint.Align.CENTER);

        float textHeight = paint.descent() - paint.ascent();
        float textOffset = (textHeight / 2) - paint.descent();

        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawText(text, bounds.centerX(), bounds.centerY() + textOffset, paint);
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setValue(float value) {
        this.value = value;
        invalidate();
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public void setShowDescriptionText(boolean showDescriptionText) {
        this.showDescriptionText = showDescriptionText;
    }

    public void setValueSignSize(int width, int height) {
        this.valueSignWidth = width;
        this.valueSignHeight = height;
    }

    public void setValueSignColor(int valueSignColor) {
        this.valueSignColor = valueSignColor;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public void setSideStyle(int sideStyle) {
        this.sideStyle = sideStyle;
    }

    public void setEmptySegmentColor(int emptySegmentColor) {
        this.emptySegmentColor = emptySegmentColor;
    }

    public void setSideTextStyle(int sideTextStyle) {
        this.sideTextStyle = sideTextStyle;
    }
}
