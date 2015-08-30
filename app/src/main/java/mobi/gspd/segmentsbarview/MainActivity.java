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
        barView.setValue(30);
        barView.setSegments(segments);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barView.setValue(50);
                barView.setShowDescriptionText(true);
            }
        });

        createNormalBarView();
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

    private void createNormalBarView() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValueWithUnit(4.96f, "10<sup>12</sup>/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createNormalBarViewWithPadding() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(4.96f);
        segmentedProgressView.setUnit("m<sup>5</sup>/s<sup>2</sup>");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(1000, 500));
        segmentedProgressView.setPadding(200, 70, 50, 120);
        segmentedProgressView.setShowDescriptionText(true);
        segmentedProgressView.setSideStyle(SegmentedBarViewSideStyle.ANGLE);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithoutValueSign() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 0.01f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(0.01f, 0.1f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(0.1f, 1f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setShowDescriptionText(true);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewInMinPosition() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(0);
        segmentedProgressView.setUnit("μmol/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewInMinPositionWithPadding() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(0);
        segmentedProgressView.setUnit("μ/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(300, 100, 100, 100);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewInMinPositionNormalSides() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(0);
        segmentedProgressView.setUnit("μ/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setSideStyle(SegmentedBarViewSideStyle.NORMAL);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewInMaxPosition() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(100);
        segmentedProgressView.setUnit("μ/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewInMaxPositionWithPadding() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(100);
        segmentedProgressView.setUnit("μ/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(100, 100, 100, 100);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithCustomSettings() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Bad", Color.parseColor("#2C3E50"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Ok", Color.parseColor("#5F91A6"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "Good", Color.parseColor("#ED8C2B"));
        segments.add(segment3);
        Segment segment4 = new Segment(100f, 150f, "Perfect", Color.parseColor("#911146"));
        segments.add(segment4);
        segmentedProgressView.setValueSignColor(Color.parseColor("#1A5717"));
        segmentedProgressView.setValue(90);
        segmentedProgressView.setBarHeight(140);
        segmentedProgressView.setValueSignSize(220, 130);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setValueTextSize(80);
        segmentedProgressView.setDescriptionTextSize(30);
        segmentedProgressView.setSegmentTextSize(60);
        segmentedProgressView.setValueTextColor(Color.parseColor("#aaffaa"));
        segmentedProgressView.setSegmentTextColor(Color.parseColor("#999999"));
        segmentedProgressView.setDescriptionTextColor(Color.parseColor("#aa5555"));
        segmentedProgressView.setGapWidth(0);
        segmentedProgressView.setShowDescriptionText(true);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithManySegments() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
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
        segmentedProgressView.setValue(13f);
        segmentedProgressView.setShowText(false);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithOneSegment() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        segmentedProgressView.setValue(13f);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithTwoSegments() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment);
        segments.add(segment2);
        segmentedProgressView.setValue(34.1234f);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithNoSegments() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithOnSegmentAngles() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        segmentedProgressView.setValue(13f);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setSideStyle(SegmentedBarViewSideStyle.ANGLE);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(50, 100, 30, 80);
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithValueOutOfSegments() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 10f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(30, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(55f, 70f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(20);
        segmentedProgressView.setUnit("m");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createNormalBarViewWithTwoSidedSideText() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(4.96f);
        segmentedProgressView.setUnit("m");
        segmentedProgressView.setSideTextStyle(SegmentedBarViewSideTextStyle.TWO_SIDED);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createNormalBarViewSideStyleNormal() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(4.96f);
        segmentedProgressView.setUnit("m");
        segmentedProgressView.setSideTextStyle(SegmentedBarViewSideTextStyle.TWO_SIDED);
        segmentedProgressView.setSideStyle(SegmentedBarViewSideStyle.NORMAL);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setShowDescriptionText(true);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }

    private void createNormalBarViewSideStyleAngle() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setValue(4.96f);
        segmentedProgressView.setUnit("m");
        segmentedProgressView.setSideTextStyle(SegmentedBarViewSideTextStyle.TWO_SIDED);
        segmentedProgressView.setSideStyle(SegmentedBarViewSideStyle.ANGLE);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setShowDescriptionText(true);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        segmentedProgressView.setPadding(0, 20, 0, 0);
        mainLayout.addView(segmentedProgressView);
    }
}
