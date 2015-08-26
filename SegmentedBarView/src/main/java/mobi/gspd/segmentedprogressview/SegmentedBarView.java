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

        int singleSegmentWidth = getWidth() / segmentsSize;
        rectBounds.set(0, valueSignSpaceHeight(), singleSegmentWidth, barHeight + valueSignSpaceHeight());

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

    private void drawSegment(Canvas canvas, Segment segment, int i, int segmentsSize) {
        boolean isLeftSegment = i == 0;
        boolean isRightSegment = i == segmentsSize - 1;
        boolean isLeftAndRight = isLeftSegment && isRightSegment;

        int singleSegmentWidth = getWidth() / segmentsSize;
        int segmentLeft = singleSegmentWidth * i;

        if (!isLeftSegment) {
            segmentLeft += gapSize;
        }
        int segmentRight = singleSegmentWidth * (i + 1);

        // Segment bounds
        rectBounds.set(segmentLeft, valueSignSpaceHeight(), segmentRight, barHeight + valueSignSpaceHeight());

        // Calculating value sign position
        if (!valueIsEmpty()) {
            if (value >= segment.getMinValue() && value < segment.getMaxValue() || (isRightSegment && segment.getMaxValue() == value)) {
                float valueSignCenterPercent = (value - segment.getMinValue()) / (segment.getMaxValue() - segment.getMinValue());
                valueSignCenter = (int) (segmentLeft + valueSignCenterPercent * singleSegmentWidth);
            }
        }

        paint.setColor(segment.getColor());

        float textCenterX = rectBounds.centerX();
        float textCenterY = rectBounds.centerY();

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
                            rectBounds.set(segmentLeft + barRoundingRadius, valueSignSpaceHeight(), segmentRight, barHeight + valueSignSpaceHeight());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                        } else {
                            rectBounds.set(segmentLeft, valueSignSpaceHeight(), segmentRight - barRoundingRadius, barHeight + valueSignSpaceHeight());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                        }
                    }
                    break;
                case SegmentedBarViewSideStyle.ANGLE:
                    if (isLeftAndRight) {
                        rectBounds.set(segmentLeft + barRoundingRadius, valueSignSpaceHeight(), segmentRight - barRoundingRadius, barHeight + valueSignSpaceHeight());
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
                            rectBounds.set(segmentLeft + barRoundingRadius, valueSignSpaceHeight(), segmentRight, barHeight + valueSignSpaceHeight());
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
                            rectBounds.set(segmentLeft, valueSignSpaceHeight(), segmentRight - barRoundingRadius, barHeight + valueSignSpaceHeight());
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
                rectBounds = new Rect((int) roundRectangleBounds.left,
                        (int) roundRectangleBounds.top, (int) roundRectangleBounds.right, (int) roundRectangleBounds.bottom);
            } else {
                textToShow = String.format("%s - %s", df.format(segment.getMinValue()), df.format(segment.getMaxValue()));
            }

            drawTextCentredInPosition(canvas, textPaint, textToShow, textCenterX, textCenterY);
//            drawTextCentredInRect(canvas, textPaint, textToShow, rectBounds);
        }

        //Drawing segment description text
        if (showDescriptionText) {
            drawTextCentredInPosition(canvas, descriptionTextPaint, segment.getDescriptionText(), textCenterX, textCenterY + rectBounds.height());
        }
    }

    private void drawValueSign(Canvas canvas, int valueSignSpaceHeight, int valueSignCenter) {
        boolean valueNotInSegments = valueSignCenter == -1;
        if (valueNotInSegments) {
            valueSignCenter = getMeasuredWidth() / 2;
        }
        valueSignBounds.set(valueSignCenter - valueSignWidth / 2, 0, valueSignCenter + valueSignWidth / 2, valueSignHeight - arrowHeight);
        paint.setColor(valueSignColor);
        if (valueSignBounds.left < 0) {
            int difference = -valueSignBounds.left;
            roundRectangleBounds.set(valueSignBounds.left + difference, valueSignBounds.top, valueSignBounds.right + difference, valueSignBounds.bottom);
        } else if (valueSignBounds.right > getMeasuredWidth()) {
            int difference = valueSignBounds.right - getMeasuredWidth();
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
            if (valueSignCenter - arrowWidth / 2 < barRoundingRadius) {
                difference = barRoundingRadius - valueSignCenter;
            } else if (valueSignCenter + arrowWidth / 2 > getMeasuredWidth() - barRoundingRadius) {
                difference = (getMeasuredWidth() - barRoundingRadius) - valueSignCenter;
            }

            Point point1 = new Point(valueSignCenter - arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight);
            Point point2 = new Point(valueSignCenter + arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight);
            Point point3 = new Point(valueSignCenter + difference, valueSignSpaceHeight);

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
        Log.d("SBV", "onMeasure()");
        int width = getMeasuredWidth();
        int height = barHeight;
        if (value != -1) {
            height += valueSignHeight;
        }
        if (showDescriptionText) {
            height += barHeight;
        }
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingBottom() + getPaddingTop();
        int w = resolveSizeAndState(width, widthMeasureSpec, 0);
        int h = resolveSizeAndState(height, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
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

        rectBounds = new Rect();
        textBounds = new Rect();
        valueSignBounds = new Rect();
        roundRectangleBounds = new RectF();
    }

    public void drawTextCentredInRect(Canvas canvas, Paint paint, String text, Rect outsideRect) {
        paint.getTextBounds(text, 0, text.length(), textBounds);

        if (outsideRect.width() < textBounds.width()) {
            return;
        }

        int cx = outsideRect.centerX();
        int cy = outsideRect.centerY();
        canvas.drawText(text, cx - textBounds.exactCenterX(), cy - textBounds.exactCenterY(), paint);
    }

    public void drawTextCentredInPosition(Canvas canvas, Paint paint, String text, float cx, float cy) {
        paint.getTextBounds(text, 0, text.length(), textBounds);

        canvas.drawText(text, cx - textBounds.exactCenterX(), cy - textBounds.exactCenterY(), paint);
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
