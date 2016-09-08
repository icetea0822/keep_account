package com.shengzidong.keepacounts.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.shengzidong.keepacounts.R;
import com.shengzidong.keepacounts.entity.Receipt;
import com.shengzidong.keepacounts.utils.MyDataBaseUtils;

import java.util.ArrayList;


public class CountFragment extends Fragment {
    PieChart pieChart;
    ArrayList<Receipt> receipts;
    private MyDataBaseUtils db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, null);
        pieChart = (PieChart) view.findViewById(R.id.pcAccount);

        db = new MyDataBaseUtils(getContext());
        receipts = db.queryFromDataBase();


        setPieChart();
        return view;
    }

    private void setPieChart() {
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        colors.add(Color.rgb(20, 5, 111));
        colors.add(Color.rgb(31, 76, 32));
        colors.add(Color.rgb(7, 3, 122));
        colors.add(Color.rgb(186, 135, 44));


        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        for (int i = 0; i < receipts.size(); i++) {
            yVals.add(new PieEntry(receipts.get(i).getMoney(), receipts.get(i).getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        dataSet.setValueTextSize(14);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);


        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(true);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad); //设置动画


        pieChart.invalidate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        db.closeDataBase();
    }
}
