package com.abstractclass.visitormanager.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExcelManager
{
    private Workbook wb;
    private Sheet sheet;

    public ExcelManager()
    {
        this.wb = new HSSFWorkbook();
        this.sheet = null;
        this.createSheet();
    }

    private void createSheet()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String today = dateFormat.format(c.getTime());
        this.sheet = this.wb.createSheet(today);
    }

    public void addRowsToSheet()
    {

    }
}
