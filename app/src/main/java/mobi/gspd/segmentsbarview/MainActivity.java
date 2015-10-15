package mobi.gspd.segmentsbarview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mobi.gspd.segmentedbarview.Segment;
import mobi.gspd.segmentedbarview.SegmentedBarView;
import mobi.gspd.segmentedbarview.SegmentedBarViewSideStyle;
import mobi.gspd.segmentedbarview.SegmentedBarViewSideTextStyle;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        initUi();
    }

    private void initUi() {

        final SegmentedBarView barView = (SegmentedBarView) findViewById(R.id.bar_view);
        final ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(20, 40, "Normal", Color.BLUE));
        segments.add(new Segment(40, 60, "Worse", Color.LTGRAY));
        barView.setValue(30f);
        barView.setSegments(segments);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barView.setValue(50f);
                barView.setShowDescriptionText(false);
            }
        });

        createNormalBarView();
        createBarViewWithoutNumericValue();
        createNormalBarViewSideStyleNormal();
        createNormalBarViewSideStyleAngle();
        createBarViewInMinPosition();
        createBarViewInMaxPosition();
        createBarViewWithManySegments();
        createBarViewWithOnSegmentAngles();
        createBarViewWithTwoSegments();
        createBarViewWithNoSegments();
        createNormalBarViewWithTwoSidedSideText();
        createBarViewWithCustomSettings();


        createBarViewWithBuilder();
        createNormalBarViewWithPadding();
        createBarViewWithoutValueSign();
        createBarViewInMinPosition();
        createBarViewInMinPositionWithPadding();
        createBarViewInMinPositionNormalSides();
        createBarViewInMaxPosition();
        createBarViewInMaxPositionWithPadding();
        createBarViewWithCustomSettings();
        createBarViewWithManySegments();
        createBarViewWithOneSegment();
        createBarViewWithOnSegmentAngles();
        createBarViewWithTwoSegments();
        createBarViewWithNoSegments();
        createBarViewWithValueOutOfSegments();
        createNormalBarViewWithTwoSidedSideText();
        createNormalBarViewSideStyleNormal();
        createNormalBarViewSideStyleAngle();
    }

    private void createBarViewWithoutNumericValue() {
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment("Negative Left", null, Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment("Positive", null, Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment("Negative Right", null, Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        SegmentedBarView barView = SegmentedBarView.builder(this)
                .segments(segments)
                .valueSegment(1)
                .valueSegmentText("Your result")
                .unit("ml<sup>2</sup>")
                .sideStyle(SegmentedBarViewSideStyle.ANGLE)
                .build();
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createNormalBarView() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValueWithUnit(4.96f, "10<sup>12</sup>/l");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithBuilder() {
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        SegmentedBarView barView = SegmentedBarView.builder(this)
                .segments(segments)
                .value(5.25f)
                .unit("ml<sup>2</sup>")
                .showDescriptionText(true)
                .sideStyle(SegmentedBarViewSideStyle.ANGLE)
                .build();
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createNormalBarViewWithPadding() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Some text", "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(4.96f);
        barView.setUnit("m<sup>5</sup>/s<sup>2</sup>");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(1000, 500));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        barView.setShowDescriptionText(true);
        barView.setSideStyle(SegmentedBarViewSideStyle.ANGLE);
        mainLayout.addView(barView);
    }

    private void createBarViewWithoutValueSign() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 0.01f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(0.01f, 0.1f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(0.1f, 1f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setSegments(segments);
        barView.setShowDescriptionText(true);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewInMinPosition() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(0f);
        barView.setUnit("μmol/l");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewInMinPositionWithPadding() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(0f);
        barView.setUnit("μ/l");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewInMinPositionNormalSides() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(0f);
        barView.setUnit("μ/l");
        barView.setSegments(segments);
        barView.setSideStyle(SegmentedBarViewSideStyle.NORMAL);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewInMaxPosition() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(100f);
        barView.setUnit("μ/l");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewInMaxPositionWithPadding() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(100f);
        barView.setUnit("μ/l");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(100, 100, 100, 100);
        mainLayout.addView(barView);
    }

    private void createBarViewWithCustomSettings() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Bad", Color.parseColor("#2C3E50"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Ok", Color.parseColor("#5F91A6"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "Good", Color.parseColor("#ED8C2B"));
        segments.add(segment3);
        Segment segment4 = new Segment(100f, 150f, "Perfect", Color.parseColor("#911146"));
        segments.add(segment4);
        barView.setValueSignColor(Color.parseColor("#1A5717"));
        barView.setValue(90f);
        barView.setBarHeight(140);
        barView.setValueSignSize(220, 130);
        barView.setSegments(segments);
        barView.setValueTextSize(80);
        barView.setDescriptionTextSize(30);
        barView.setSegmentTextSize(60);
        barView.setValueTextColor(Color.parseColor("#aaffaa"));
        barView.setSegmentTextColor(Color.parseColor("#999999"));
        barView.setDescriptionTextColor(Color.parseColor("#aa5555"));
        barView.setGapWidth(0);
        barView.setShowDescriptionText(true);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithManySegments() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment2);
        segments.add(segment3);
        barView.setValue(13f);
        barView.setShowSegmentText(false);
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithOneSegment() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        barView.setValue(13f);
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithTwoSegments() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment);
        segments.add(segment2);
        barView.setValue(34.1234f);
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithNoSegments() {
        SegmentedBarView barView = new SegmentedBarView(this);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithOnSegmentAngles() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        barView.setValue(13f);
        barView.setSegments(segments);
        barView.setSideStyle(SegmentedBarViewSideStyle.ANGLE);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createBarViewWithValueOutOfSegments() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 10f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(30, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(55f, 70f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(20f);
        barView.setUnit("m");
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createNormalBarViewWithTwoSidedSideText() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(4.96f);
        barView.setUnit("m");
        barView.setSideTextStyle(SegmentedBarViewSideTextStyle.TWO_SIDED);
        barView.setSegments(segments);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createNormalBarViewSideStyleNormal() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(4.96f);
        barView.setUnit("m");
        barView.setSideTextStyle(SegmentedBarViewSideTextStyle.TWO_SIDED);
        barView.setSideStyle(SegmentedBarViewSideStyle.NORMAL);
        barView.setSegments(segments);
        barView.setShowDescriptionText(true);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }

    private void createNormalBarViewSideStyleAngle() {
        SegmentedBarView barView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        barView.setValue(4.96f);
        barView.setUnit("m");
        barView.setSideTextStyle(SegmentedBarViewSideTextStyle.TWO_SIDED);
        barView.setSideStyle(SegmentedBarViewSideStyle.ANGLE);
        barView.setSegments(segments);
        barView.setShowDescriptionText(true);
        barView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        barView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.vertical_padding), 0, 0);
        mainLayout.addView(barView);
    }
}
