package com.google.neilchen.chartdemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ArrayList<String> mList;
  private PieChart mChart;
  //protected Typeface mTfLight;
  //protected Typeface mTfRegular;
  protected String[] mParties = new String[] {
      "广裕中心dfgdsgdsgfxxx", "xx花园", "xx花园", "yy中", "其他小ghfg区", "区外"
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setLineChart();

    picChart();
  }

  private void picChart() {
    mChart = (PieChart) findViewById(R.id.chart);
    //mChart.getLegend().setEnabled(false);
    mChart.setDrawEntryLabels(false);
    mChart.setUsePercentValues(true);
    mChart.getDescription().setEnabled(false);
    mChart.getLegend().setTextSize(15);
    mChart.setHighlightPerTapEnabled(false);

    mChart.setDragDecelerationFrictionCoef(0.95f);

    mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
    mChart.setDrawHoleEnabled(true);
    mChart.setHoleColor(Color.WHITE);

    mChart.setTransparentCircleColor(Color.WHITE);
    mChart.setTransparentCircleAlpha(110);

    mChart.setHoleRadius(58f);
    mChart.setTransparentCircleRadius(61f);

    mChart.setDrawCenterText(true);

    mChart.setRotationAngle(0);
    // enable rotation of the chart by touch
    mChart.setRotationEnabled(false);
    mChart.setHighlightPerTapEnabled(true);
    setData(5, 100);

    mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    Legend l = mChart.getLegend();
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    l.setOrientation(Legend.LegendOrientation.VERTICAL);
    l.setForm(Legend.LegendForm.CIRCLE);
    l.setDrawInside(false);
    l.setXEntrySpace(5f);
    l.setYEntrySpace(5f);
    l.setYOffset(5f);

    mChart.setEntryLabelColor(Color.WHITE);
    mChart.setEntryLabelTextSize(12f);
  }

  public static final int[] COLORS = {
      Color.rgb(25, 155, 252), Color.rgb(84, 194, 115), Color.rgb(183, 243, 74),
      Color.rgb(37, 215, 251), Color.rgb(61, 95, 153)
  };
  private void setData(int count, float range) {

    float mult = range;

    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

    // NOTE: The order of the entries when being added to the entries array determines their position around the center of
    // the chart.
    for (int i = 0; i < count; i++) {
      String mParty = mParties[i % mParties.length];
      mParty = mParty.length() > 5 ? mParty.substring(0, 5)+"..." : mParty.substring(0, mParty.length());
      entries.add(new PieEntry((float) (Math.random() * mult) + mult / 5, mParty));
    }

    PieDataSet dataSet = new PieDataSet(entries, "");
    dataSet.setSliceSpace(3f);
    dataSet.setSelectionShift(5f);

    // add a lot of colors

    ArrayList<Integer> colors = new ArrayList<Integer>();

    for (int c : COLORS)
      colors.add(c);

    dataSet.setColors(colors);

    //dataSet.setValueLinePart1OffsetPercentage(80.f);
    //dataSet.setValueLinePart1Length(2f);
    //dataSet.setValueLinePart2Length(4f);
    dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

    PieData data = new PieData(dataSet);
    data.setValueFormatter(new PercentFormatter());
    data.setValueTextSize(11f);
    data.setValueTextColor(Color.WHITE);
    //data.setValueTypeface(tf);
    mChart.setData(data);

    // undo all highlights
    mChart.highlightValues(null);

    mChart.invalidate();

  }

  private void setLineChart() {
    mList = new ArrayList<>();
    mList.add("一月");
    mList.add("二月");
    mList.add("三月");
    mList.add("四月");
    mList.add("五月");
    mList.add("六月");
    mList.add("七月");
    mList.add("八月");
    mList.add("九月");
    mList.add("十月");
    mList.add("十一月");
    mList.add("十二月");
    LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
    mLineChart.getDescription().setEnabled(false);
    mLineChart.getLegend().setEnabled(false); //隐藏最后的label

    XAxis xAxis = mLineChart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setLabelCount(mList.size(), false);
    xAxis.setGranularity(2f);
    xAxis.setDrawGridLines(false);
    mLineChart.getAxisLeft().setDrawGridLines(false);
    mLineChart.getAxisRight().setDrawGridLines(false);
    xAxis.setValueFormatter(new IAxisValueFormatter() {
      @Override
      public String getFormattedValue(float value, AxisBase axis) {
        return mList.get((int) value); //mList为存有月份的集合
      }
    });
    //显示边界
    mLineChart.setDrawBorders(false);

    //设置数据
    List<Entry> entries = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      entries.add(new Entry(i, (float) (Math.random()) * 800));
    }
    //一个LineDataSet就是一条线
    LineDataSet lineDataSet = new LineDataSet(entries, "");

    //设置圆心半径
    lineDataSet.setCircleRadius(3f);
    //设置曲线值的圆点是实心还是空心
    lineDataSet.setDrawCircleHole(true);
    lineDataSet.setCircleColorHole(Color.parseColor("#73B1F6"));

    lineDataSet.setColor(Color.parseColor("#73B1F6"));
    lineDataSet.setMode(LineDataSet.Mode.LINEAR);
    lineDataSet.setDrawValues(false);//取消曲线点的刻度 setValueFormatter将无效
    lineDataSet.setValueFormatter(new IValueFormatter() {
      @Override
      public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        int IValue = (int) value;
        return String.valueOf(IValue);

      }
    });
    LineData data = new LineData(lineDataSet);
    mLineChart.setData(data);
  }
}
