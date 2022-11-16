package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Bills;
import com.spring.cinemaproject.Models.Chairs;
import com.spring.cinemaproject.Models.Tickets;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class BillExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Bills> listBill;

    public BillExcelExporter(List<Bills> listBill) {
        this.listBill = listBill;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Bills");
    }

    private void writeHeaderRow(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Bills");
        Row row =sheet.createRow(0);

        CellStyle style=workbook.createCellStyle();
        XSSFFont font= workbook.createFont();

        font.setBold(true);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        font.setFontHeight(20);
        style.setFont(font);

        Cell cell =row.createCell(0);
        cell.setCellValue("BillID");
        cell.setCellStyle(style);

        cell =row.createCell(1);
        cell.setCellValue("User");
        cell.setCellStyle(style);

        cell =row.createCell(2);
        cell.setCellValue("Voucher");
        cell.setCellStyle(style);

        cell =row.createCell(3);
        cell.setCellValue("Payment");
        cell.setCellStyle(style);

        cell =row.createCell(4);
        cell.setCellValue("Tickets");
        cell.setCellStyle(style);

        cell =row.createCell(5);
        cell.setCellValue("Create Date");
        cell.setCellStyle(style);

        cell =row.createCell(6);
        cell.setCellValue("Status");
        cell.setCellStyle(style);

        cell =row.createCell(7);
        cell.setCellValue("Total Price ");
        cell.setCellStyle(style);

    }

    private void writeDataRow(){
        int rowCount = 1;

        CellStyle style=workbook.createCellStyle();
        XSSFFont font= workbook.createFont();

        font.setFontHeight(16);
        style.setFont(font);

        for(Bills item : listBill){
            Set<Tickets> billTicket = item.getTickets();
            List<String> ticketInfo = new ArrayList<>();
            for(Tickets ticket : billTicket){
                for(Chairs chair : ticket.getChairs()){
                    ticketInfo.add(chair.getChairName());
                }
            }


            Row row =sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(item.getBillID());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(0);

             cell = row.createCell(1);
            cell.setCellValue(item.getUsers().getEmail());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(1);

             cell = row.createCell(2);
             if(item.getVouchers() != null){
                 cell.setCellValue(item.getVouchers().getVoucherName());
             }else{
                 cell.setCellValue("NO VOUCHER");
             }
            cell.setCellStyle(style);
            sheet.autoSizeColumn(2);

             cell = row.createCell(3);
            cell.setCellValue(item.getPayments().getPaymentName());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(3);

             cell = row.createCell(4);
             if(ticketInfo!= null){
                 cell.setCellValue(ticketInfo.toString());
             }
            cell.setCellStyle(style);
            sheet.autoSizeColumn(4);

             cell = row.createCell(5);
            cell.setCellValue(item.getCreateDate().toString());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            if(item.getStatus() == 2){
                cell.setCellValue("RECEIVED");
            }else
            {
                cell.setCellValue("NOT RECEIVED");
            }
            cell.setCellStyle(style);
            sheet.autoSizeColumn(6);

             cell = row.createCell(7);
            cell.setCellValue(item.getBillTotal());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(7);


        }
    }

    public void export(HttpServletResponse response)throws IOException {
        writeHeaderRow();
        writeDataRow();

        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
