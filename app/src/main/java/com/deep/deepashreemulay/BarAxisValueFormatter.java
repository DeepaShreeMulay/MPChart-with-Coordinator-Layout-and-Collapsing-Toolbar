package com.deep.deepashreemulay;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.DecimalFormat;

public class BarAxisValueFormatter implements IAxisValueFormatter {
    private DecimalFormat mformat;

    public BarAxisValueFormatter(){
        mformat = new DecimalFormat("##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mformat.format(value);
    }
}
