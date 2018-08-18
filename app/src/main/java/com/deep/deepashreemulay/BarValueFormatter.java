package com.deep.deepashreemulay;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class BarValueFormatter implements IValueFormatter {
    private DecimalFormat mformat;

    public BarValueFormatter(){
        mformat = new DecimalFormat("##");
    }
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mformat.format(value);
    }
}
