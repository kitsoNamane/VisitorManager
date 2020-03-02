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
            val person = visitor?.person
            cell = row?.createCell(0)!!
            cell.setCellValue(person?.firstName)
            cell = row.createCell(1)
            cell?.setCellValue(person?.lastName)
            cell = row.createCell(2)
            cell?.setCellValue(person?.nationalId)
            cell = row.createCell(2)
            cell?.setCellValue(person?.birthDate?.toDouble()!!)
            cell = row.createCell(2)
            cell?.setCellValue(person?.sex)
            cell = row.createCell(3)
            cell?.setCellValue(visitor?.timeIn?.toDouble()!!)
            cell = row.createCell(4)
            cell?.setCellValue(visitor?.purpose)
            cell = row.createCell(5)
            cell?.setCellValue("Plate Number")
            cell = row.createCell(6)
            cell?.setCellValue(visitor?.timeOut?.toDouble()!!)
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