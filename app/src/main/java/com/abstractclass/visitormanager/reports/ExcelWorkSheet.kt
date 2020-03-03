package com.abstractclass.visitormanager.reports

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import androidx.core.net.toUri
import com.abstractclass.visitormanager.models.Visitor
import com.abstractclass.visitormanager.utils.Utils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

class ExcelWorkSheet(context: Context?) {
    private var workbook: Workbook? = null
    private var sheet: Sheet? = null
    private var context: Context?
    private var workSheetCells: List<String>? = null

    private var filename: String? = null

    private fun initSheet() {
        workbook = HSSFWorkbook()
        filename = Utils.getISODate()
        val outputStream = context?.openFileOutput(filename, MODE_PRIVATE)
        sheet = workbook?.createSheet("Visitor_Log_$filename")
    }

    private fun createSheet() {
        val row = sheet?.createRow(0)
        workSheetCells?.forEachIndexed {index, element ->
            row?.createCell(index)?.setCellValue(element)
        }
    }

    fun addVisitors(visitors: List<Visitor?>?) {
        var row: Row?
        visitors?.forEachIndexed { index, visitor ->
            // get the last row of worksheet
            row = sheet?.createRow(index + 1)
            visitor?.toList()?.forEachIndexed {index, s ->
                row?.createCell(index)?.setCellValue(s)
            }
        }
        saveWorkSheet()
    }

    private fun saveWorkSheet() {
        val output = context?.openFileOutput(filename, MODE_PRIVATE)
        workbook?.write(output)
        output?.close()
    }

    fun getFile() : Uri {
        return filename?.toUri()!!
    }

    init {
        this.context = context
        workSheetCells = listOf(
                "First Name", "Last Name", "National ID", "Phone Number", "Birthdate", "Gender",
                "Time In", "Purpose", "Plate Number", "Time Out"
        )
        initSheet()
        createSheet()
    }
}