package mobi.gspd.segmentsbarview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mobi.gspd.segmentedprogressview.Segment;
import mobi.gspd.segmentedprogressview.SegmentedBarView;

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
        createNormalBarView();
        createDivider();
        createBarViewWithoutValueSign();
        createDivider();
        createBarViewInMinPosition();
        createDivider();
        createBarViewInMaxPosition();
        createDivider();
        createBarViewWithValueInLastSegment();
        createDivider();
        createBarViewWithManySegments();
        createDivider();
        createBarViewWithOneSegment();
        createDivider();
        createBarViewWithTwoSegments();
    }

    private void createDivider() {
        LinearLayout divider = new LinearLayout(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20));
        mainLayout.addView(divider);
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
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setValue(4.96f);
        segmentedProgressView.setUnit("m");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setShowDescriptionText(true);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setValue(0);
        segmentedProgressView.setUnit("μ/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setValue(100);
        segmentedProgressView.setUnit("μ/l");
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithValueInLastSegment() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment2);
        Segment segment3 = new Segment(50f, 100f, "High", Color.parseColor("#EF3D2F"));
        segments.add(segment3);
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(100);
        segmentedProgressView.setValue(90);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setValue(13f);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithOneSegment() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        segments.add(segment);
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setValue(13f);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(segmentedProgressView);
    }

    private void createBarViewWithTwoSegments() {
        SegmentedBarView segmentedProgressView = new SegmentedBarView(this);
        ArrayList<Segment> segments = new ArrayList<>();
        Segment segment = new Segment(0, 20f, "Low", Color.parseColor("#EF3D2F"));
        Segment segment2 = new Segment(20f, 50f, "Optimal", Color.parseColor("#8CC63E"));
        segments.add(segment);
        segments.add(segment2);
        segmentedProgressView.setMinValue(0);
        segmentedProgressView.setMaxValue(20);
        segmentedProgressView.setValue(34.1234f);
        segmentedProgressView.setSegments(segments);
        segmentedProgressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(segmentedProgressView);
    }
}
