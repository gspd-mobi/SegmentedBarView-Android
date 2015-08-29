package mobi.gspd.segmentedbarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.List;

public class SegmentedBarView extends View {

    private List<Segment> segments;
    private String unit;
    private Float value;

    private Rect rectBounds;
    private Rect textBounds;
    private Rect valueSignBounds;
    private RectF roundRectangleBounds;
    private Paint paint;
    private Paint segmentTextPaint;
    private Paint descriptionTextPaint;

    private DecimalFormat df = new DecimalFormat();

    private int valueSignHeight;
    private int valueSignColor;
    private int emptySegmentColor;
    private int valueSignWidth;
    private int arrowHeight;
    private int arrowWidth;
    private int gapWidth;
    private int barHeight;

    private int barRoundingRadius = 0;
    private int valueSignCenter = -1;

    private boolean showDescriptionText = false;
    private boolean showText = true;

    private int sideStyle = SegmentedBarViewSideStyle.ROUNDED;
    private int sideTextStyle = SegmentedBarViewSideTextStyle.ONE_SIDED;

    private int valueTextSize;
    private int descriptionTextSize;
    private int segmentTextSize;

    private int valueTextColor = Color.WHITE;
    private int descriptionTextColor = Color.DKGRAY;
    private int segmentTextColor = Color.WHITE;

    private TextPaint valueTextPaint;
    private Path trianglePath;
    private StaticLayout valueTextLayout;
    private Point point1;
    private Point point2;
    private Point point3;
    private Rect segmentRect;

    public SegmentedBarView(Context context) {
        super(context);
        init();
    }

    public SegmentedBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        segmentTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        segmentTextPaint.setColor(Color.WHITE);
        segmentTextSize = getResources().getDimensionPixelSize(R.dimen.sbv_segment_text_size);
        segmentTextPaint.setStyle(Paint.Style.FILL);

        valueTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        valueTextPaint.setColor(Color.WHITE);
        valueTextPaint.setStyle(Paint.Style.FILL);
        valueTextSize = getResources().getDimensionPixelSize(R.dimen.sbv_value_text_size);
        valueTextPaint.setTextSize(valueTextSize);
        valueTextPaint.setColor(valueTextColor);

        descriptionTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        descriptionTextPaint.setColor(Color.DKGRAY);
        descriptionTextPaint.setStyle(Paint.Style.FILL);
        descriptionTextSize = getResources().getDimensionPixelSize(R.dimen.sbv_description_text_size);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        rectBounds = new Rect();
        textBounds = new Rect();
        valueSignBounds = new Rect();
        roundRectangleBounds = new RectF();
        segmentRect = new Rect();

        trianglePath = new Path();
        trianglePath.setFillType(Path.FillType.EVEN_ODD);
        point1 = new Point();
        point2 = new Point();
        point3 = new Point();

        barHeight = getResources().getDimensionPixelSize(R.dimen.sbv_bar_height);
        valueSignHeight = getResources().getDimensionPixelSize(R.dimen.sbv_value_sign_height);
        valueSignWidth = getResources().getDimensionPixelSize(R.dimen.sbv_value_sign_width);
        arrowHeight = getResources().getDimensionPixelSize(R.dimen.sbv_arrow_height);
        arrowWidth = getResources().getDimensionPixelSize(R.dimen.sbv_arrow_width);
        gapWidth = getResources().getDimensionPixelSize(R.dimen.sbv_segment_gap_width);

        valueSignColor = getResources().getColor(R.color.sbv_value_sign_background);
        emptySegmentColor = getResources().getColor(R.color.sbv_empty_segment_background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
            segmentTextPaint.setTextSize(segmentTextSize);
            drawTextCentredInRectWithSides(canvas, segmentTextPaint, textToShow, roundRectangleBounds.left, roundRectangleBounds.top, roundRectangleBounds.right, roundRectangleBounds.bottom);
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
            segmentLeft += gapWidth;
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

        segmentRect.set(rectBounds);

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
                        point1.set(rectBounds.left - barRoundingRadius, rectBounds.top + barRoundingRadius);
                        point2.set(rectBounds.left, rectBounds.top);
                        point3.set(rectBounds.left, rectBounds.bottom);

                        drawTriangle(canvas, point1, point2, point3, paint);

                        //Draw right triangle
                        point1.set(rectBounds.right + barRoundingRadius, rectBounds.top + barRoundingRadius);
                        point2.set(rectBounds.right, rectBounds.top);
                        point3.set(rectBounds.right, rectBounds.bottom);

                        drawTriangle(canvas, point1, point2, point3, paint);
                    } else {
                        if (isLeftSegment) {
                            rectBounds.set(segmentLeft + barRoundingRadius + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                            //Draw left triangle
                            point1.set(rectBounds.left - barRoundingRadius, rectBounds.top + barRoundingRadius);
                            point2.set(rectBounds.left, rectBounds.top);
                            point3.set(rectBounds.left, rectBounds.bottom);

                            drawTriangle(canvas, point1, point2, point3, paint);
                        } else {
                            rectBounds.set(segmentLeft + getPaddingLeft(), valueSignSpaceHeight() + getPaddingTop(), segmentRight - barRoundingRadius + getPaddingLeft(), barHeight + valueSignSpaceHeight() + getPaddingTop());
                            canvas.drawRect(
                                    rectBounds,
                                    paint
                            );
                            //Draw right triangle
                            point1.set(rectBounds.right + barRoundingRadius, rectBounds.top + barRoundingRadius);
                            point2.set(rectBounds.right, rectBounds.top);
                            point3.set(rectBounds.right, rectBounds.bottom);

                            drawTriangle(canvas, point1, point2, point3, paint);
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

            segmentTextPaint.setTextSize(segmentTextSize);
            segmentTextPaint.setColor(segmentTextColor);
            drawTextCentredInRect(canvas, segmentTextPaint, textToShow, segmentRect);
        }

        //Drawing segment description text
        if (showDescriptionText) {
            descriptionTextPaint.setTextSize(descriptionTextSize);
            descriptionTextPaint.setColor(descriptionTextColor);
            drawTextCentredInRectWithSides(canvas, descriptionTextPaint, segment.getDescriptionText(), segmentRect.left, segmentRect.top + rectBounds.height(), segmentRect.right, segmentRect.bottom + rectBounds.height());
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

        // Draw arrow
        if (!valueNotInSegments) {
            int difference = 0;
            if (valueSignCenter - arrowWidth / 2 < barRoundingRadius + getPaddingLeft()) {
                difference = barRoundingRadius - valueSignCenter + getPaddingLeft();
            } else if (valueSignCenter + arrowWidth / 2 > getMeasuredWidth() - barRoundingRadius - getPaddingRight()) {
                difference = (getMeasuredWidth() - barRoundingRadius) - valueSignCenter - getPaddingRight();
            }

            point1.set(valueSignCenter - arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight + getPaddingTop());
            point2.set(valueSignCenter + arrowWidth / 2 + difference, valueSignSpaceHeight - arrowHeight + getPaddingTop());
            point3.set(valueSignCenter + difference, valueSignSpaceHeight + getPaddingTop());

            drawTriangle(canvas, point1, point2, point3, paint);
        }

        // Draw value text
        canvas.translate(roundRectangleBounds.left, roundRectangleBounds.top + roundRectangleBounds.height() / 2 - valueTextLayout.getHeight() / 2);
        valueTextLayout.draw(canvas);
    }

    private void drawTriangle(Canvas canvas, Point point1, Point point2, Point point3, Paint paint) {
        trianglePath.reset();
        trianglePath.moveTo(point1.x, point1.y);
        trianglePath.lineTo(point2.x, point2.y);
        trianglePath.lineTo(point3.x, point3.y);
        trianglePath.lineTo(point1.x, point1.y);
        trianglePath.close();

        canvas.drawPath(trianglePath, paint);
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

    private int valueSignSpaceHeight() {
        if (valueIsEmpty()) return 0;
        return valueSignHeight;
    }

    private boolean valueIsEmpty() {
        return value == null;
    }

    public void drawTextCentredInRect(Canvas canvas, Paint paint, String text, Rect outsideRect) {
        drawTextCentredInRectWithSides(canvas, paint, text, outsideRect.left, outsideRect.top, outsideRect.right, outsideRect.bottom);
    }

    public void drawTextCentredInRectWithSides(Canvas canvas, Paint paint, String text, float left, float top, float right, float bottom) {
        paint.getTextBounds(text, 0, text.length(), textBounds);
        paint.setTextAlign(Paint.Align.CENTER);

        float textHeight = paint.descent() - paint.ascent();
        float textOffset = (textHeight / 2) - paint.descent();

        canvas.drawText(text, (left + right) / 2, (top + bottom) / 2 + textOffset, paint);
    }

    private void createValueTextLayout() {
        String text = String.valueOf(value);
        if (unit != null && !unit.isEmpty()) text += String.format(" <small>%s</small>", unit);
        Spanned spanned = Html.fromHtml(text);

        valueTextLayout = new StaticLayout(spanned, valueTextPaint, valueSignWidth, Layout.Alignment.ALIGN_CENTER, 1, 0, false);
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        createValueTextLayout();
    }

    public void setValue(float value) {
        this.value = value;
        createValueTextLayout();
    }

    public void setValueWithUnit(float value, String unitHtml) {
        this.value = value;
        this.unit = unitHtml;
        if (!valueIsEmpty()) createValueTextLayout();
    }

    public void setGapWidth(int gapWidth) {
        this.gapWidth = gapWidth;
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
        if (!valueIsEmpty()) createValueTextLayout();
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

    public void setDescriptionTextSize(int descriptionTextSize) {
        this.descriptionTextSize = descriptionTextSize;
    }

    public void setSegmentTextSize(int segmentTextSize) {
        this.segmentTextSize = segmentTextSize;
    }

    public void setValueTextSize(int valueTextSize) {
        this.valueTextSize = valueTextSize;
        valueTextPaint.setTextSize(valueTextSize);
    }

    public void setDescriptionTextColor(int descriptionTextColor) {
        this.descriptionTextColor = descriptionTextColor;
    }

    public void setSegmentTextColor(int segmentTextColor) {
        this.segmentTextColor = segmentTextColor;
    }

    public void setValueTextColor(int valueTextColor) {
        this.valueTextColor = valueTextColor;
        valueTextPaint.setColor(valueTextColor);
    }
}
