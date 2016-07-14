package com.android.administrator.childrensittingposture.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.adapter.MyAdapter;
import com.android.administrator.childrensittingposture.db.CultivateDb;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;

    public BarDataSet dataset;


    private List<CultivateDb> listContacts = new ArrayList<>();  //存储联系人数据

    private MyAdapter adapter;

    public ArrayList<String> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BarChart mChart = (BarChart) findViewById(R.id.chart);

        mChart.setDescription("近七日坐姿矫正次数");
        mChart.setMaxVisibleValueCount(60);             //一屏超过25列时不显示具体数值，设置超过60无效
        mChart.setPinchZoom(true);                         //是否只能根据X,Y轴放大缩小
        mChart.setDrawBarShadow(false);       //设置该列空白部分是否用灰色补全

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(1);
        xAxis.setDrawGridLines(false);

        YAxis yAxis_left=mChart.getAxisLeft();
        YAxis yAxis_right=mChart.getAxisRight();
        yAxis_left.setLabelCount(20,true);
        yAxis_right.setLabelCount(20,true);
        yAxis_left.setStartAtZero(true);
        yAxis_right.setStartAtZero(true);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        labels = new ArrayList<String>();
        int index=0;

        for (int i = 0; i < 9 + 1; i++) {
            double sum=20;
            yVals1.add(new BarEntry((float)sum,index));
            index++;
            labels.add("40");
        }


        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawHighlightArrow(false);

        dataset = new BarDataSet(yVals1, "");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData data = new BarData(labels, dataset);
        mChart.setData(data);
        mChart.animateY(2000);


        listView=(ListView)findViewById(R.id.listView);

        adapter = new MyAdapter(HistoryActivity.this);

        adapter.setData(readContacts());

        listView.setAdapter(adapter);

    }


    public List<CultivateDb> readContacts() {


        CultivateDb db=new CultivateDb();
        db.setThatDayTime("2016.01.07");
        db.setCultivateTime("3:10:10");
        db.setRemindFrequency("8");
//        db.saveThrows();
        Log.d("TAG", "news id is " + db.getId());
        db.save();
        Log.d("TAG", "news id is " + db.getId());
        listContacts.add(db);

        return listContacts;
    }


}
