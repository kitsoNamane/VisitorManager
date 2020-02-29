package com.abstractclass.visitormanager.reports

import android.content.Context
import com.abstractclass.visitormanager.models.Visitor
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ExcelManager(context: Context?) {
    private val wb: Workbook?
    private var sheet: Sheet?
    private val file_name: String? = null
    private var file: File?
    private val context: Context?
    private fun createSheet() {
        val c = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(c.time)
        sheet = wb?.createSheet(today)
        val cellStyle = wb?.createCellStyle()
        val row = sheet?.createRow(0)
        var cell: Cell?
        cell = row?.createCell(0)
        cell?.setCellValue("First Name")
        cell = row?.createCell(1)
        cell?.setCellValue("Last Name")
        cell = row?.createCell(2)
        cell?.setCellValue("National ID")
        cell = row?.createCell(2)
        cell?.setCellValue("Birth Date")
        cell = row?.createCell(2)
        cell?.setCellValue("Gender")
        cell = row?.createCell(3)
        cell?.setCellValue("Time In")
        cell = row?.createCell(4)
        cell?.setCellValue("Purpose")
        cell = row?.createCell(5)
        cell?.setCellValue("Plate Number")
        cell = row?.createCell(6)
        cell?.setCellValue("Time Out")
        file = File(context?.getExternalFilesDir(null), "$today.xls")
        val outputStream: FileOutputStream
        try {
            outputStream = FileOutputStream(file)
            wb?.write(outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun addRowsToSheet(visitors: MutableList<Visitor?>?) {
        val row_counter = 1
        var cell: Cell
        var row: Row?
        for (visitor in visitors!!) {
            row = sheet?.createRow(row_counter)
            val person = visitor?.getPerson()
            cell = row?.createCell(0)!!
            cell.setCellValue(person?.getFirstName())
            cell = row.createCell(1)
            cell?.setCellValue(person?.getLastName())
            cell = row.createCell(2)
            cell?.setCellValue(person?.getNationalId())
            cell = row.createCell(2)
            cell?.setCellValue(person?.getBirthDate()?.toDouble()!!)
            cell = row.createCell(2)
            cell?.setCellValue(person?.getSex())
            cell = row.createCell(3)
            cell?.setCellValue(visitor?.getTimeIn()?.toDouble()!!)
            cell = row.createCell(4)
            cell?.setCellValue(visitor?.getPurpose())
            cell = row.createCell(5)
            cell?.setCellValue("Plate Number")
            cell = row.createCell(6)
            cell?.setCellValue(visitor?.getTimeOut()?.toDouble()!!)
        }
    }

    init {
        wb = HSSFWorkbook()
        sheet = null
        file = null
        createSheet()
        this.context = context
    }
}