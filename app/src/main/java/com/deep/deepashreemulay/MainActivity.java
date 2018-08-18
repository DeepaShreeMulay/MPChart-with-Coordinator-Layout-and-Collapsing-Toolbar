package com.deep.deepashreemulay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class MainActivity extends AppCompatActivity {

    private Context context;
    DatabaseHelper dataHelper;
    TableLayout tableLayout;
    AppBarLayout mAppBarLayout;
    BarChart barChart;
    PieChart pieChart;
    Button btnpieChart;
    Button btnbargraph;
    StudentMarks studentMarks1;
    ArrayList<StudentMarks> MarkList;
    int[] My_COLORS = {
            rgb("#F90BB8"), rgb("#0FF30F"), rgb("#FEFE02"), rgb("#0247FE"), rgb("#FE8402")
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        InitView();
        AddData();
        DisplayTable();
        SetListener();
    }

    private void SetListener() {
        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                mAppBarLayout.setExpanded(true,true);
                btnbargraph.setVisibility(View.VISIBLE);
                btnpieChart.setVisibility(View.VISIBLE);
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
            }
        });

        btnbargraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                mAppBarLayout.setExpanded(false,true);
                btnbargraph.setVisibility(View.GONE);
                btnpieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                DisplayBarGraph();
            }
        });

        btnpieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                btnbargraph.setVisibility(View.GONE);
                btnpieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                mAppBarLayout.setExpanded(false,true);
                DisplayPieChart();
            }
        });

        barChart.setOnChartValueSelectedListener( new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(context,"This is Enty :" + h.getStackIndex(),Toast.LENGTH_SHORT).show();
                int index = (int)h.getX();
                int sindex = (int)h.getStackIndex();
                ShowDialog(index,sindex);
            }

            @Override
            public void onNothingSelected() {

            }
        });


        pieChart.setOnChartValueSelectedListener( new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(context,"This is Enty :" + h.getX(),Toast.LENGTH_SHORT).show();
                int index = (int)h.getX();

                ShowDialog(index);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void ShowDialog(int i) {
        final Dialog myDialog = new Dialog(MainActivity.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.dialogdetails);
        myDialog.setCancelable(true);

       	final TextView desc_text = (TextView) myDialog.findViewById(R.id.desc_text);
        final TextView dm1 = (TextView) myDialog.findViewById(R.id.dmarks1);
        final TextView dm2 = (TextView) myDialog.findViewById(R.id.dmarks2);
        final TextView dm3 = (TextView) myDialog.findViewById(R.id.dmarks3);
        final TextView dm4 = (TextView) myDialog.findViewById(R.id.dmarks4);
        final TextView dm5 = (TextView) myDialog.findViewById(R.id.dmarks5);
		desc_text.setText(MarkList.get(i).getCity() +" is represented by "
                +MarkList.get(i).getName()+" and he scored marks as follows :");
        dm1.setText(String.valueOf(MarkList.get(i).getS1()));
        dm2.setText(String.valueOf(MarkList.get(i).getS2()));
        dm3.setText(String.valueOf(MarkList.get(i).getS3()));
        dm4.setText(String.valueOf(MarkList.get(i).getS4()));
        dm5.setText(String.valueOf(MarkList.get(i).getS5()));

        myDialog.show();
    }
    private void ShowDialog(int i,int stack) {
        final Dialog myDialog = new Dialog(MainActivity.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.dialogdetails);
        myDialog.setCancelable(true);

        final TextView desc_text = (TextView) myDialog.findViewById(R.id.desc_text);
        final TextView dm1 = (TextView) myDialog.findViewById(R.id.dmarks1);
        final TextView dm2 = (TextView) myDialog.findViewById(R.id.dmarks2);
        final TextView dm3 = (TextView) myDialog.findViewById(R.id.dmarks3);
        final TextView dm4 = (TextView) myDialog.findViewById(R.id.dmarks4);
        final TextView dm5 = (TextView) myDialog.findViewById(R.id.dmarks5);
        desc_text.setText(MarkList.get(i).getCity() +" is represented by "
                +MarkList.get(i).getName()+" and he scored marks as follows :");
        dm1.setText(String.valueOf(MarkList.get(i).getS1()));
        dm2.setText(String.valueOf(MarkList.get(i).getS2()));
        dm3.setText(String.valueOf(MarkList.get(i).getS3()));
        dm4.setText(String.valueOf(MarkList.get(i).getS4()));
        dm5.setText(String.valueOf(MarkList.get(i).getS5()));
        if(stack==0){ dm1.setTextColor(My_COLORS[0]); }
        else if(stack==1){ dm2.setTextColor(My_COLORS[1]); }
        else if(stack==2){ dm3.setTextColor(My_COLORS[2]); }
        else if(stack==3){ dm4.setTextColor(My_COLORS[3]); }
        else if(stack==4){ dm5.setTextColor(My_COLORS[4]); }
        myDialog.show();
    }

    private void DisplayPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(15f);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.animateY(1000,Easing.EasingOption.EaseInCubic);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        for (int i = 0; i<MarkList.size();i++){
            yValues.add(new PieEntry(GrandTotal(i),MarkList.get(i).getCity()));
        }

        PieDataSet dataSet = new PieDataSet(yValues,"City");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(My_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.LTGRAY);

        pieChart.setData(data);

    }

    private float GrandTotal(int i) {
        return (float) (MarkList.get(i).getS1()+MarkList.get(i).getS2()+MarkList.get(i).getS3()+
                MarkList.get(i).getS4()+MarkList.get(i).getS5());
    }

    private void DisplayBarGraph() {
        barChart.setMaxVisibleValueCount(500);
        SetBarData(MarkList.size());
    }

    private void SetBarData(int count) {
        ArrayList<BarEntry> yVal = new ArrayList<>();
        for(int i=0; i<count ; i++){
            yVal.add(new BarEntry(i,new float[]{(float)(MarkList.get(i).getS1()),(float)(MarkList.get(i).getS2()),
                    (float)(MarkList.get(i).getS3()), (float)(MarkList.get(i).getS4()), (float)(MarkList.get(i).getS5())}));
        }

        BarDataSet set1 = new BarDataSet(yVal, "Students Marks");
        set1.setDrawIcons(false);
        set1.setStackLabels( new String[] {"M1", "M2", "M3", "M4", "M5"} );
        set1.setColors(My_COLORS);

        BarData data = new BarData(set1);
        data.setValueFormatter(new BarValueFormatter());

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(
                new BarAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return MarkList.get((int) value).getName();
                    }
                });


        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
        barChart.getDescription().setEnabled(false);


    }

    private void DisplayTable() {
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"NAME","CITY","M1", "M2", "M3", "M4", "M5"};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try {
            MarkList.clear();
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_STUDENT;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    // Read columns data
                    StudentMarks studentMarks = new StudentMarks();
                    String Student_name = cursor.getString(cursor.getColumnIndex("Student_name"));
                    String city = cursor.getString(cursor.getColumnIndex("Student_address"));
                    int M1 = cursor.getInt(cursor.getColumnIndex("M1"));
                    int M2 = cursor.getInt(cursor.getColumnIndex("M2"));
                    int M3 = cursor.getInt(cursor.getColumnIndex("M3"));
                    int M4 = cursor.getInt(cursor.getColumnIndex("M4"));
                    int M5 = cursor.getInt(cursor.getColumnIndex("M5"));
                    studentMarks.setName(Student_name);
                    studentMarks.setCity(city);
                    studentMarks.setS1(M1);
                    studentMarks.setS2(M2);
                    studentMarks.setS3(M3);
                    studentMarks.setS4(M4);
                    studentMarks.setS5(M5);
                    MarkList.add(studentMarks);



                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {Student_name,city, "" + M1, "" + M2, "" + M3, "" + M4, "" + M5};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);
                }
            }
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }

    private void InitView() {

        context = MainActivity.this;
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        barChart = (BarChart) findViewById(R.id.barchart);
        barChart.setVisibility(View.GONE);
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setVisibility(View.GONE);
        btnpieChart = (Button) findViewById(R.id.btnPieChart);
        btnbargraph = (Button) findViewById(R.id.btnbargraph);
        MarkList = new ArrayList<>();
    }

    private void AddData() {
        // Create DatabaseHelper instance
        dataHelper = new DatabaseHelper(context);
        SQLiteDatabase db1 = dataHelper.getWritableDatabase();
        db1.execSQL(dataHelper.DELETE_TABLE_STUDENT);
        db1.execSQL(dataHelper.CREATE_TABLE_STUDENT);
        // Insert sample data
        dataHelper.insertData("Ram", "Pune", 89, 68, 84, 86, 73);
        dataHelper.insertData("Raj", "Bangalore", 67, 95, 99, 72, 94);
        dataHelper.insertData("Rahul", "Mumbai", 89, 98, 74, 68, 81);
        dataHelper.insertData("Prem", "Chennai", 72, 77, 66, 65, 75);
        dataHelper.insertData("Kumar", "Delhi", 66, 88, 78, 76, 83);

    }
}
