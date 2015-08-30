package mobi.gspd.segmentedbarview;

public class Segment {
    private String customText;
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

    public Segment(float minValue, float maxValue, int color) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.color = color;
    }

    public Segment(float minValue, float maxValue, String customText, String descriptionText, int color) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.customText = customText;
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

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
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
