/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl.report;

import MRDControl.Config;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import java.awt.color.ColorSpace;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author armando
 */
public class ReportGenerator {
    private Date start;
    private Date end;

    public ReportGenerator() {

    }

    public String generateReport() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 6, 59);

        Date end = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 7, 0);

        Date start = calendar.getTime();

        System.out.println(start);
        System.out.println(end);
        return generateReport(start, end);
    }
    public String generateReport(Date start, Date end) {
       String fileName = "reporte.pdf";
        try {

            byte[] byteArray = buildReport(start, end);
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(byteArray);

            } catch (IOException ex) {
                Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileName;
    }

    public byte[] buildReport(Date start, Date end) throws FileNotFoundException {
        this.start = start;
        this.end = end;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);

        try (Document document = new Document(pdf)) {
            document.add(header());
            Table table = table();
            addRecords(table);
            document.add(table);
            document.close();
            return out.toByteArray();
        }
    }

    private Paragraph header() {
        Paragraph header = new Paragraph(
                Config.propiedades.getProperty("empresa","nombre de empresa") +
                        "\nREPORTE OCUPACION HABITACIONES"
        );
        header.setFontSize(18);
        header.setFontColor(new DeviceRgb(60, 141, 188));
        header.setBold();
        return header;
    }

    private Table table() {
        Table table = new Table(
                new UnitValue[]{
                    new UnitValue(UnitValue.PERCENT, 12),
                    new UnitValue(UnitValue.PERCENT, 22),
                    new UnitValue(UnitValue.PERCENT, 22),
                    new UnitValue(UnitValue.PERCENT, 22),
                    new UnitValue(UnitValue.PERCENT, 22)
                });
        table.addHeaderCell(headerCell("Habitación"));
        table.addHeaderCell(headerCell("Fecha"));
        table.addHeaderCell(headerCell("Hora de Inicio"));
        table.addHeaderCell(headerCell("Hora de Fin"));
        table.addHeaderCell(headerCell("Duración"));
        table.useAllAvailableWidth();

        return table;
    }

    private Cell headerCell(String text) {
        Cell cell = new Cell()
                .add(new Paragraph(text))
                .setBackgroundColor(new DeviceRgb(60, 141, 188))
                .setBold()
                .setFontColor(ColorConstants.WHITE)
                .setBorder(new SolidBorder(ColorConstants.WHITE, 2))
                .setFontSize(10f);
        return cell;
    }

    private Cell contentCell(String text, boolean odd) {
        Cell cell = new Cell()
                .add(new Paragraph(text))
                .setFontColor(new DeviceRgb(51, 51, 51))
                .setBorder(new SolidBorder(ColorConstants.WHITE, 2))
                .setFontSize(10f);
        if (odd) {
            cell.setBackgroundColor(new DeviceRgb(243, 244, 245));
        }
        return cell;
    }

    private void addRecords(Table table) {
        List<ReportRecord> records;
        try {
            records = new ReportData().getData(start, end);
            System.out.println("size" + records.size());
            boolean odd = true;
            for (ReportRecord record : records) {
                odd = !odd;
                table.addCell(contentCell(record.getIdRoom(), odd));
                table.addCell(contentCell(record.getDate(), odd));
                table.addCell(contentCell(record.getStartTime(), odd));
                table.addCell(contentCell(record.getEndTime(), odd));
                table.addCell(contentCell(record.getDuration(), odd));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
