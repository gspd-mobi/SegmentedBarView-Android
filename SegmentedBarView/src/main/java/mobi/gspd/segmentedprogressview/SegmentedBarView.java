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
import android.view.View;

import java.text.DecimalFormat;
import java.util.List;

public class SegmentedBarView extends View {

    private List<Segment> segments;
    private String unit;
    private float minValue;
    private float maxValue;
    private float value = -1;
    private int accuracyAfterPoint = 2;
    private int gapSize = 4;
    private int barHeight = 80;

    private Rect rectBounds;
    private Rect textBounds;
    private Rect valueSignBounds;
    private RectF roundRectangleBounds;
    private Paint paint;

    private Paint textPaint;
    private Paint descriptionTextPaint;
    private DecimalFormat df = new DecimalFormat();
    private int valueSignHeight = 120;
    private int arrowHeight = 20;
    private int roundingRadius = 0;
    private int arrowWidth = 40;


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
        int segmentsCount = segments.size();

        boolean showValue = value != -1;
        int valueSignSpaceHeight = showValue ? valueSignHeight : 0;
        int valueSignCenter = 0;

        for (int i = 0; i < segmentsCount; i++) {
            Segment segment = segments.get(i);

            boolean isLeftSegment = i == 0;
            boolean isRightSegment = i == segmentsCount - 1;
            boolean isLeftAndRight = isLeftSegment && isRightSegment;

            int singleWidth = getWidth() / segmentsCount;
            int left = singleWidth * i + gapSize;
            int right = singleWidth * (i + 1) - gapSize;
            rectBounds.set(left, valueSignSpaceHeight, right, barHeight + valueSignSpaceHeight);

            if (showValue) {
                if (value >= segment.getMinValue() && value < segment.getMaxValue() || (isRightSegment && segment.getMaxValue() == value)) {
                    float valueSignCenterPercent = (value - segment.getMinValue()) / (segment.getMaxValue() - segment.getMinValue());
                    valueSignCenter = (int) (left + valueSignCenterPercent * singleWidth);
                }
            }

            paint.setColor(segment.getColor());

            if (isLeftSegment || isRightSegment) {
                roundingRadius = rectBounds.height() / 2;
                roundRectangleBounds.set(rectBounds.left, rectBounds.top, rectBounds.right, rectBounds.bottom);
                canvas.drawRoundRect(roundRectangleBounds, roundingRadius, roundingRadius, paint);
                if (!isLeftAndRight) {
                    if (isLeftSegment) {
                        rectBounds.set(left + roundingRadius, valueSignSpaceHeight, right, barHeight + valueSignSpaceHeight);
                        canvas.drawRect(
                                rectBounds,
                                paint
                        );
                    } else {
                        rectBounds.set(left, valueSignSpaceHeight, right - roundingRadius, barHeight + valueSignSpaceHeight);
                        canvas.drawRect(
                                rectBounds,
                                paint
                        );
                    }
                }
            } else {
                canvas.drawRect(
                        rectBounds,
                        paint
                );
            }

            String textToShow;
            if (isLeftSegment || isRightSegment) {
                if (isLeftAndRight) {
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

            drawTextCentredInRect(canvas, textPaint, textToShow, rectBounds);

            drawTextCentredInRect(canvas, descriptionTextPaint, segment.getDescriptionText(),
                    new Rect(rectBounds.left, rectBounds.bottom, rectBounds.right, rectBounds.bottom + rectBounds.height()));
        }

        if (showValue) {
            drawValueSign(canvas, valueSignSpaceHeight, valueSignCenter);
        }
    }

    private void drawValueSign(Canvas canvas, int valueSignSpaceHeight, int valueSignCenter) {
        valueSignBounds.set(valueSignCenter - 150, 0, valueSignCenter + 150, valueSignHeight - arrowHeight);
        paint.setColor(Color.parseColor("#7492E2"));
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

        int difference = 0;
        if (valueSignCenter - arrowWidth / 2 < roundingRadius) {
            difference = roundingRadius - valueSignCenter;
        } else if (valueSignCenter + arrowWidth / 2 > getMeasuredWidth() - roundingRadius) {
            difference = (getMeasuredWidth() - roundingRadius) - valueSignCenter;
        }

        Point point1 = new Point(valueSignCenter - arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight);
        Point point2 = new Point(valueSignCenter + arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight);
        Point point3 = new Point(valueSignCenter + difference, valueSignSpaceHeight);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        path.lineTo(point1.x, point1.y);
        path.close();

        canvas.drawPath(path, paint);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int parentHeight = valueSignHeight + arrowHeight + barHeight * 2;
//        this.setMeasuredDimension(parentWidth, parentHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (value == -1) valueSignHeight = 0;
        setMeasuredDimension(width, valueSignHeight + arrowHeight + barHeight * 2);
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

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
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

    public void setAccuracyAfterPoint(int accuracyAfterPoint) {
        this.accuracyAfterPoint = accuracyAfterPoint;
    }
}
