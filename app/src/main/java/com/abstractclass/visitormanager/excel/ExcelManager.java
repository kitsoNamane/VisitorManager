package com.abstractclass.visitormanager.excel;

import android.content.Context;

import com.abstractclass.visitormanager.models.Person;
import com.abstractclass.visitormanager.models.Visitor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExcelManager
{
    private Workbook wb;
    private Sheet sheet;
    private String file_name;

    private File file;

    private Context context;

    public ExcelManager(Context context)
    {
        this.wb = new HSSFWorkbook();
        this.sheet = null;
        this.file = null;
        this.createSheet();
        this.context = context;
    }

    private void createSheet()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String today = dateFormat.format(c.getTime());
        this.sheet = this.wb.createSheet(today);

        CellStyle cellStyle = this.wb.createCellStyle();
        Row row = this.sheet.createRow(0);

        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue("First Name");

        cell = row.createCell(1);
        cell.setCellValue("Last Name");

        cell = row.createCell(2);
        cell.setCellValue("National ID");

        cell = row.createCell(2);
        cell.setCellValue("Birth Date");

        cell = row.createCell(2);
        cell.setCellValue("Gender");

        cell = row.createCell(3);
        cell.setCellValue("Time In");

        cell = row.createCell(4);
        cell.setCellValue("Purpose");

        cell = row.createCell(5);
        cell.setCellValue("Plate Number");

        cell = row.createCell(6);
        cell.setCellValue("Time Out");

        this.file = new File(this.context.getExternalFilesDir(null), today + ".xls");

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(this.file);
            this.wb.write(outputStream);
        }

         catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRowsToSheet(List<Visitor> visitors)
    {
        int row_counter = 1;
        Cell cell;
        Row row;
        for (Visitor visitor: visitors)
        {
            row = this.sheet.createRow(row_counter);

            Person person = visitor.getPerson();
            cell = row.createCell(0);
            cell.setCellValue(person.getFirstName());

            cell = row.createCell(1);
            cell.setCellValue(person.getLastName());

            cell = row.createCell(2);
            cell.setCellValue(person.getNationalId());

            cell = row.createCell(2);
            cell.setCellValue(person.getBirthDate());

            cell = row.createCell(2);
            cell.setCellValue(person.getSex());

            cell = row.createCell(3);
            cell.setCellValue(visitor.getTimeIn());

            cell = row.createCell(4);
            cell.setCellValue(visitor.getPurpose());

            cell = row.createCell(5);
            cell.setCellValue("Plate Number");

            cell = row.createCell(6);
            cell.setCellValue(visitor.getTimeOut());
        }
    }
}
