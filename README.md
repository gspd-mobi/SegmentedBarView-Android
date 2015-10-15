Segmented Bar View
=============================
Custom UI control for android which is showing data as a segments and a value inside them.

### Screenshots ###
![Imgur](http://i.imgur.com/E1ivT7N.png)

## Install ##

### From repositories ###
Add jcenter to project repositories
```groovy
repositories {
    jcenter()
}
```
Add compile line to project dependencies
```groovy
compile 'mobi.gspd:segmentedbarview:1.1.5@aar'
```

### Local aar ###
[ ![Download](https://api.bintray.com/packages/gspd-mobi/segmentedbarview/mobi.gspd.segmentedbarview/images/download.svg) ](https://bintray.com/gspd-mobi/segmentedbarview/mobi.gspd.segmentedbarview/_latestVersion)

Put **aar** into ``app/libs`` folder.
Add flatDir in ``build.gradle``
```groovy
repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}
```

And add dependency 
```groovy
compile 'mobi.gspd:segmentedbarview:1.1.5@aar'
```

## Usage ##
#### Builder ####
```java
SegmentedBarView barView = SegmentedBarView.builder(this)
                           .segments(segments)
                           .value(5.25f)
                           .unit("ml<sup>2</sup>")
                           .showDescriptionText(true)
                           .sideStyle(SegmentedBarViewSideStyle.ANGLE)
                           .build();
```
#### Constructor and setters ####
```java
SegmentedBarView barView = new SegmentedBarView(this);
barView.setValueWithUnit(4.96f, "10<sup>12</sup>/l"); /* You can use Html tags here in unit to support superscript and subscript */
barView.setSegments(segments);
```
#### Within xml layout ####
```xml
<mobi.gspd.segmentedbarview.SegmentedBarView
        android:id="@+id/bar_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="20dp"
        app:sbv_empty_segment_text="No segments"
        app:sbv_segment_gap_width="10dp"
        app:sbv_segment_text_size="20sp"
        app:sbv_show_description_text="true"
        app:sbv_side_style="angle"
        app:sbv_side_text_style="twoSided"
        app:sbv_value_sign_height="30dp"
        app:sbv_value_sign_round="8dp"
        app:sbv_value_sign_width="30dp" 
/>
```
#### Create segments ####

In any case you should create segments list to put in view.
```java
List<Segment> segments = new ArrayList<>();
Segment segment = new Segment(0, 4.5f, "Low", Color.parseColor("#EF3D2F"));
segments.add(segment);
Segment segment2 = new Segment(4.5f, 6.5f, "Optimal", Color.parseColor("#8CC63E"));
segments.add(segment2);
Segment segment3 = new Segment(6.5f, 20f, "High", Color.parseColor("#EF3D2F"));
segments.add(segment3);
```
Each segment contains min and max value, color, description text (optional), custom text (optional). Value sign position is calculated automatically in these intervals.

Also there can be segments with no min and max. Then you should set `valueSegment` field of SegmentedBarView, it's just an index of segment to show value sign over.


## Customization ##

### Xml attributes ###
Here is list of xml attributes and their defaults.

* sbv_show_description_text - boolean
    * false (default)
* sbv_show_segment_text - boolean
    * true (default)
* sbv_value_sign_background - color
    * ``#7492E2`` (default)
* sbv_empty_segment_background - color
    * ``#858585`` (default)
* sbv_description_text_size - dimension
    * 14sp (default)
* sbv_value_text_size - dimension
    * 14sp (default)
* sbv_segment_text_size - dimension
    * 14sp (default)
* sbv_bar_height - dimension
    * 24dp (default)
* sbv_value_sign_height - dimension
    * 32dp (default)
* sbv_value_sign_width - dimension
    * 72dp (default)
* sbv_arrow_height - dimension
    * 5dp (default)
* sbv_arrow_width - dimension
    * 10dp (default)
* sbv_description_box_height - dimension
    * 24dp (default)
* sbv_value_sign_round - dimension
    * 4dp (default)
* sbv_side_style - enum
	* normal (default)
	* rounded
	* angle
* sbv_side_text_style - enum
	* oneSided (default)
	* twoSided

All of these also can be set via setters of **SegmentedBarView** class.

License
-------
    The MIT License (MIT)

    Copyright (c) 2015 gspd.mobi

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
