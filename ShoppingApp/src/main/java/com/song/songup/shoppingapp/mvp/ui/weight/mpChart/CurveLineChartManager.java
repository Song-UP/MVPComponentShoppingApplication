package com.song.songup.shoppingapp.mvp.ui.weight.mpChart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.song.songup.shoppingapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CurveLineChartManager {

    private LineChart lineChart;
    private YAxis leftAxis;
    private XAxis xAxis;
    private LineData lineData;//用来存放lineDataSet
    private LineDataSet lineDataSet;//表示一条线
    private List<ILineDataSet> lineDataSets = new ArrayList<>();
    private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式  
    private Context context;
    MyXYMarkerView myXYMarkerView;

    private int XLabelCount = 10;
    private int YLabelCount = 5;


    /********************** 一条线 ***********/
    //一条曲线
    public CurveLineChartManager(Context context, LineChart mLineChart) {
        this.context = context;
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();
        xAxis = lineChart.getXAxis();
        initLineChart();
        initLineDataSet();

    }

    /**
     * 初始化LineChar(除了线之外的内容)
     */
    private void initLineChart() {

        /********* 设置整体 ***********/
        lineChart.setDrawGridBackground(false);
        lineChart.setScaleEnabled(false);     //禁止缩放
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        //显示边界
        lineChart.setDrawBorders(false);
        //折线图例 标签 设置(x轴数据)
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
//        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTextSize(11f);
//        //显示位置
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(false);
        lineChart.setDragEnabled(true);                   // 不可以拖动
        lineChart.setDoubleTapToZoomEnabled(false);        // 不可以双击缩放

        /*************** 设置x轴 ******************/
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);     // 设置x轴的位置
        xAxis.setDrawGridLines(false);                     // 不需要图表内的网格线
        xAxis.setGranularity(1f); //底部的间隔(缩放时最小间隔)
        xAxis.setLabelCount(XLabelCount);  //0-1 (中间 中间有多少个间隔)
        xAxis.setEnabled(true);
        xAxis.setTextColor(Color.parseColor("#989898"));
        xAxis.setTextSize(10);
        xAxis.setDrawAxisLine(false);

        //ToDO  轴显示的格式
   IndexAxisValueFormatter indexAxisValueFormatter =  new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
//                return timeList.get((int) value % timeList.size());
                return String.valueOf(value);
            }
        };
        xAxis.setValueFormatter(indexAxisValueFormatter);

        /***************** 设置Y轴 *********************/
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setGridColor(Color.parseColor("#e1e1e1"));
        leftAxis.enableGridDashedLine(12f, 6f, 0f);

        leftAxis.setTextColor(Color.parseColor("#989898"));
        leftAxis.setTextSize(10);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        /**************** 设置展示的内容  *********************/
        myXYMarkerView = new MyXYMarkerView(context);
        lineChart.setMarker(myXYMarkerView);
        myXYMarkerView.setChartView(lineChart);



    }

    /**
     * 初始化折线(一条线)
     */
    private void initLineDataSet() {

        //label 标名
        lineDataSet = new LineDataSet(null, "");
        lineDataSet.setLineWidth(2f);
//        线的颜色
//        lineDataSet.setColor(color);

//     设置高亮样式
        lineDataSet.setCircleColors(Color.TRANSPARENT);
        lineDataSet.setCircleRadius(1f);
//        lineDataSet.setCircleColor(color);
//        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setHighLightColor(Color.TRANSPARENT);//高亮线的颜色
        //设置曲线填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //区块填充颜色
        lineDataSet.setFillDrawable(context.getResources().getDrawable(R.drawable.shape_gradient_ver_orange));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        绘制文字
        lineDataSet.setDrawValues(false);
//        lineDataSet.setValueTextSize(10f);
//        lineDataSet.setValueTextColor(Color.parseColor("#989898"));
        //添加一个空的 LineData
        lineData = new LineData();
        lineChart.setData(lineData);
        lineChart.invalidate();


        //最开始的时候才添加 lineDataSet（一个lineDataSet 代表一条线,）
        if (lineDataSet.getEntryCount() == 0) {
            lineData.addDataSet(lineDataSet);
            lineChart.setData(lineData);
        }

    }


    public void clearDataOnly(){
        lineDataSet.clear();
        max = 0;
    }

    /**
     * 初始化数据
     */
    public void addDataOnly(int number) {
        //最开始的时候才添加 lineDataSet（一个lineDataSet 代表一条线,）
        if (lineDataSet.getEntryCount() == 0) {
            lineData.addDataSet(lineDataSet);
            lineChart.setData(lineData);
        }
        Entry entry = new Entry(lineDataSet.getEntryCount(), number);//数据
        lineData.addEntry(entry, 0);
        if (max<number){
            max = number;
        }
    }
    List<Entry> firstEntryList = new ArrayList<>();
    public void setFirstEntryList(List<Integer> dataList){
        setEntrList(dataList, firstEntryList);

    }
    List<Entry> secEntryList = new ArrayList<>();
    public void setSecEntryList(List<Integer> dataList){
        setEntrList(dataList, secEntryList);
    }

    public void setEntrList(List<Integer> dataList,List<Entry> entryList){

        for (int i =0;i<dataList.size();i++){
            entryList.add(new Entry(i, dataList.get(i)));
        }

    }

    public void showFirst(){
        show(firstEntryList,context.getResources().getDrawable(R.drawable.shape_gradient_ver_orange),Color.parseColor("#ffb90b"));
    }
    public void showSec(){
        show(secEntryList,context.getResources().getDrawable(R.drawable.shape_gradient_ver_green),Color.parseColor("#3ec890"));
    }

    public void show(List<Entry> entryList,Drawable drawable, int lineColor){
        clearDataOnly();
        for (int i =0; i<entryList.size(); i++){
            Entry entry = entryList.get(i);
//            entry.setX(lineDataSet.getEntryCount());
            lineData.addEntry(entry, 0);
            if (max<entryList.get(i).getY()){
                max = entryList.get(i).getY();
            }
        }
        refreshChart(drawable, lineColor);
    }



    float max = 0;
    /**
     * 刷新单独拿出来
     */
    public void refreshChart(Drawable drawable, int lineColor){

        int spaceYCount = YLabelCount-1;
        float space = max%spaceYCount;
        max=((max/spaceYCount)+(space == 0?0:1))*spaceYCount;
        max+=4;

        //左边的线
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(YLabelCount,true);
        //区块填充颜色
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.setColor(lineColor);
//高亮的颜色
        myXYMarkerView.setCurColor(lineColor);

        //通知数据已经改变
        lineData.notifyDataChanged();//刷新线
        lineChart.notifyDataSetChanged();//刷新整个图表(除了线)
        //设置在曲线图中显示的最大数量(必须设置,不然全部都会挤在一屏里面)
        lineChart.setVisibleXRangeMaximum(XLabelCount);
        //移到某个位置
        lineChart.moveViewToX(0);

        lineChart.animateY(500);
    }


    /**
     * 动态添加数据（一条折线图）
     *
     * @param number
     */
    public void addEntry(int number) {
        //最开始的时候才添加 lineDataSet（一个lineDataSet 代表一条线）
        if (lineDataSet.getEntryCount() == 0) {
            lineData.addDataSet(lineDataSet);
        }
        lineChart.setData(lineData);
        //避免集合数据过多，及时清空（做这样的处理，并不知道有没有用，但还是这样做了）

        Entry entry = new Entry(lineDataSet.getEntryCount(), number);
        lineData.addEntry(entry, 0);
        //通知数据已经改变
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        //设置在曲线图中显示的最大数量
        lineChart.setVisibleXRangeMaximum(10);
        //移到某个位置
        lineChart.moveViewToX(lineData.getEntryCount() - 5);
    }


    /**
     * 设置Y轴值
     *
     * @param max
     */
    public void setYAxis(float max) {
        int min = 0;
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(YLabelCount, false);
        leftAxis.setEnabled(true);

        lineChart.invalidate();
    }
    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(16f);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }
    /**
     * 设置两个y轴显示不同的数据
     * 那么X轴的DATA肯定是相同，不同的Y轴数据，两个LineDataSet。
     * 那么关键的一个属性设置来了，它就是让Y轴左右两边显示两级不同的value关键，就是红色框内的setAxisDependency这个方法。
     * 默认情况下，两边都显示左边Y轴数据，而方法设置为AxisDependency.RIGHT就把Y轴左右显示不同数据的功能解决了。
     */
    public void changeYData(){
//        LineDataSet left = new LineDataSet(yVals1, "左边y轴标注");
//        LineDataSet right = new LineDataSet(yVals2, "右边y轴标注");
//        right.setAxisDependency(YAxis.AxisDependency.RIGHT);
//        LineData data = new LineData(xVals, dataSets);
//        List<ILineDataSet> dataSets =new ArrayList<>();
//        dataSets.add(left);
//        dataSets.add(right);
//        LineData data = new LineData("x轴数据", dataSets);
    }




    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        lineChart.setDescription(description);
        lineChart.invalidate();
    }

    /*********************** 多条折线图 ***********************/

    /**
     * 动态添加数据（多条折线图）
     *
     * @param numbers
     */
    public void addEntry(List<Integer> numbers) {

        if (lineDataSets.get(0).getEntryCount() == 0) {
            lineData = new LineData(lineDataSets);
            lineChart.setData(lineData);
        }
        for (int i = 0; i < numbers.size(); i++) {
            Entry entry = new Entry(lineDataSet.getEntryCount(), numbers.get(i));
            lineData.addEntry(entry, i);
            lineData.notifyDataChanged();
            lineChart.notifyDataSetChanged();
            lineChart.setVisibleXRangeMaximum(6);
            lineChart.moveViewToX(lineData.getEntryCount() - 5);
        }
    }

    //多条曲线
    public CurveLineChartManager(LineChart mLineChart, List<String> names, List<Integer> colors) {
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();
        xAxis = lineChart.getXAxis();
        initLineChart();
        initLineDataSet(names, colors);
    }

    /**
     * 初始化折线（多条线）
     *
     * @param names
     * @param colors
     */
    private void initLineDataSet(List<String> names, List<Integer> colors) {

        for (int i = 0; i < names.size(); i++) {
            lineDataSet = new LineDataSet(null, names.get(i));
            lineDataSet.setColor(colors.get(i));
            lineDataSet.setLineWidth(1.5f);
            lineDataSet.setCircleRadius(1.5f);
            lineDataSet.setColor(colors.get(i));

            lineDataSet.setDrawFilled(true);
            lineDataSet.setCircleColor(colors.get(i));
            lineDataSet.setHighLightColor(colors.get(i));
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setValueTextSize(10f);
            lineDataSets.add(lineDataSet);

        }
        //添加一个空的 LineData
        lineData = new LineData();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}