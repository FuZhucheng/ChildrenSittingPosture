package com.android.administrator.childrensittingposture.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.bean.CultivateDb;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends Activity implements View.OnClickListener {

    private TextView tv_history_RestHours;
    private TextView tv_history_StutyHours;
    private ImageView img_history_back;




    //BarChart的相关数据
    public BarDataSet dataset;      //图表中的数据
    private BarChart mChart;
    private XAxis xAxis_bar;            //X轴的相关设置
    private YAxis yAxis_left_bar;       //Y轴左边
    private YAxis yAxis_right_bar;      //Y右边
    private ArrayList<BarEntry> yVals1;        //图表数据对应Y
    private ArrayList<String> labels;       //图表数据对应X
    private BarData data;                       //图表数据

    //LineBar相关
    private LineChart lineChart;
    XAxis xAxis;
    YAxis yAxis_left;
    YAxis yAxis_right;
    ArrayList<String> xVals;
    ArrayList<Entry> yVals;
    ArrayList<ILineDataSet> dataSets;
    LineDataSet linbarDataSet;

    //linebar颜色
    private int mColorText = 0xFFFFFF;

    //barchart颜色
    public static final int[] BAR_COLOR = {
            Color.rgb(79, 193, 233), Color.rgb(79, 193, 233), Color.rgb(79, 193, 233),
            Color.rgb(79, 193, 233), Color.rgb(79, 193, 233)
    };
    private int Gray=0xaaa;

    private List<CultivateDb> allCultivate = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReport.initCrashReport(getApplicationContext(), "b9188325fd", false);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_history);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_history);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String compareDay=String.valueOf(year)+"0"+String.valueOf(month+1)+String.valueOf(day-7);

        Log.e("nowaday", compareDay);
//        Log.e("year", String.valueOf(month+1));
//        Log.e("year", String.valueOf(day));
        List<CultivateDb>sevenData= DataSupport.where("thatDayTime>?",compareDay).limit(7).find(CultivateDb.class);

//        Log.e("sevenData", String.valueOf(sevenData.get(0).getThatDayTime()));
//        Log.e("sevenData", String.valueOf(sevenData.get(1).getThatDayTime()));
//        Log.e("sevenData", String.valueOf(sevenData.get(2).getThatDayTime()));
        initView();
        initListener();
        // 加载图表数据
        setLineBarData(8, sevenData);
        setBarData(sevenData);
        //加载其他数据
        setOtherData(sevenData);
    }
    private void initView(){
        tv_history_StutyHours=(TextView)findViewById(R.id.tv_history_StutyHours);
        tv_history_RestHours=(TextView)findViewById(R.id.tv_history_RestHours);
        img_history_back=(ImageView)findViewById(R.id.img_history_back);

    }
    private void initListener(){
        img_history_back.setOnClickListener(this);
    }

    private void setLineBarData(int count, List<CultivateDb> sevenData) {
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setPinchZoom(true);                         //是否只能根据X,Y轴放大缩小
        lineChart.setDescription("近七日坐姿矫正次数");
        lineChart.setDescriptionColor(Gray);
        lineChart.setDescriptionColor(Gray);
        //设置是否可以拖拽，缩放
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);

        //X轴属性
        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(mColorText);
        //Y轴属性
        yAxis_left = lineChart.getAxisLeft();
//        yAxis_left.setLabelCount(20, true);
        yAxis_left.setStartAtZero(true);
        yAxis_left.setTextSize(15f);
        yAxis_left.setTextColor(Color.WHITE);
        yAxis_left.setAxisLineColor(mColorText);
//        yAxis_left.setAxisMaxValue(15);
        yAxis_right = lineChart.getAxisRight();
//        yAxis_right.setLabelCount(20, true);
        yAxis_right.setStartAtZero(true);
        yAxis_right.setTextSize(15f);
        yAxis_right.setAxisLineColor(mColorText);
        yAxis_right.setTextColor(Color.WHITE);
//        yAxis_right.setAxisMaxValue(15);


        //图表数据
        xVals = new ArrayList<>();
        yVals = new ArrayList<Entry>();


        for (int i = 1; i < count; i++) {
            xVals.add((i) + "");
        }

        for (int i =1;i<sevenData.size();i++){
            float val=(float) sevenData.get(i).getRemindFrequency();
            yVals.add(new Entry(val, i));
//            Log.e("sevenData", String.valueOf(sevenData.get(i).getRemindFrequency()));
        }

        linbarDataSet = new LineDataSet(yVals, "次数");
        linbarDataSet.setDrawCircles(true);  //设置曲线为圆滑的线
        linbarDataSet.setDrawFilled(false);  //设置包括的范围区域填充颜色
        linbarDataSet.setLineWidth(3f);    //设置线的宽度
        linbarDataSet.setCircleSize(0f);   //设置小圆的大小
        lineChart.setDescriptionTextSize(0f);
        linbarDataSet.setColor(Color.WHITE);    //设置曲线的颜色



        dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(linbarDataSet);

        LineData data = new LineData(xVals, dataSets);

        lineChart.setData(data);
        lineChart.animateY(2000);
        lineChart.invalidate();
    }

    private void setBarData(List<CultivateDb> sevenData) {
        mChart = (BarChart) findViewById(R.id.barChart);
        mChart.setDescription("近七日坐姿矫正次数");
        mChart.setMaxVisibleValueCount(60);             //一屏超过25列时不显示具体数值，设置超过60无效
        mChart.setPinchZoom(true);                         //是否只能根据X,Y轴放大缩小
        mChart.setDrawBarShadow(false);       //设置该列空白部分是否用灰色补全
        mChart.setDescriptionColor(Gray);
        //设置是否可以拖拽，缩放
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        xAxis_bar = mChart.getXAxis();
        xAxis_bar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis_bar.setSpaceBetweenLabels(1);
        xAxis_bar.setDrawGridLines(false);
        xAxis_bar.setTextColor(Color.GRAY);

        yAxis_left_bar = mChart.getAxisLeft();
        yAxis_left_bar.setTextColor(Color.GRAY);
        yAxis_right_bar = mChart.getAxisRight();
        yAxis_left_bar.setStartAtZero(true);
        yAxis_right_bar.setStartAtZero(true);
        yAxis_right_bar.setTextColor(Color.GRAY);

        yVals1 = new ArrayList<BarEntry>();
        labels = new ArrayList<String>();

//
//
//        for (int i = 1; i < count; i++) {
//            xVals.add((i) + "");
//        }
//
//        for (int i =1;i<sevenData.size();i++){
//            float val=(float) sevenData.get(i).getRemindFrequency();
//            yVals.add(new Entry(val, i));
////            Log.e("sevenData", String.valueOf(sevenData.get(i).getRemindFrequency()));
//        }
        for (int i=1;i<8;i++){
            labels.add((i)+"");
        }
        for (int i =1;i<sevenData.size();i++){
            float sum=(float)sevenData.get(i).getRemindFrequency();
            yVals1.add(new BarEntry(sum,i));
        }
//        int index = 0;
//        for (int i = 0; i < 9 + 1; i++) {
//            double sum = 20;
//            yVals1.add(new BarEntry((float) sum, index));
//            index++;
//            labels.add("10");
//        }
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawHighlightArrow(false);

        dataset = new BarDataSet(yVals1, "");

//        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
//        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataset.setColors(BAR_COLOR);

        data = new BarData(labels, dataset);
        mChart.setData(data);
        mChart.animateY(2000);
        mChart.invalidate();
    }
    private void setOtherData(List<CultivateDb> sevenData){
//        Log.e("sevenData", String.valueOf(sevenData.get(0).getThatDayTime()));
        if (sevenData!=null) {
            tv_history_StutyHours.setText(String.valueOf(sevenData.get(6).getCultivateTime()));
            tv_history_RestHours.setText("3");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_history_back :
                Intent intent_historyBack=new Intent();
                intent_historyBack.setClass(HistoryActivity.this,MainActivity.class);
                startActivity(intent_historyBack);
                break;
        }
    }
}
