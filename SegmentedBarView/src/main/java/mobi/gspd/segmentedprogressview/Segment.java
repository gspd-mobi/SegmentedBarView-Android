package mobi.gspd.segmentedprogressview;

public class Segment {
    private String descriptionText;
    private int color;
    private float minValue = -1;
    private float maxValue = -1;

    public Segment(float minValue, float maxValue, String descriptionText, int color) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.descriptionText = descriptionText;
        this.color = color;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public int getColor() {
        return color;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "descriptionText='" + descriptionText + '\'' +
                ", color=" + color +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
