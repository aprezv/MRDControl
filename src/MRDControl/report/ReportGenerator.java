/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl.report;

import MRDControl.Config;
import MRDControl.mail.Report;
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
import java.util.stream.Collectors;

/**
 * @author armando
 */
public class ReportGenerator {
    public ReportGenerator() {

    }

    public String generateReport(Report report) {
        Calendar calendar;
        Date start = null;
        Date end = null;
        switch (report) {
            case SEVEN_AM:
                calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),
                             calendar.get(Calendar.MONTH),
                             calendar.get(Calendar.DATE), 6, 59);
                end = calendar.getTime();
                calendar.add(Calendar.DATE, -1);
                calendar.set(calendar.get(Calendar.YEAR),
                             calendar.get(Calendar.MONTH),
                             calendar.get(Calendar.DATE), 7, 0);
                start = calendar.getTime();
                break;
            case TEN_AM:
                calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),
                             calendar.get(Calendar.MONTH),
                             calendar.get(Calendar.DATE), 7, 0);
                start = calendar.getTime();
                end = start;
                break;
        }

        return generateReport(report,start, end);
    }

    public String generateReport(Report report, Date start, Date end) {
        String fileName = "reporte.pdf";
        try {

            byte[] byteArray = buildReport(report, start, end);
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

    public byte[] buildReport(Report report, Date start, Date end) throws FileNotFoundException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);

        List<ReportRecord> records;
        try {
            records = new ReportData().getData(report, start, end);
        } catch (SQLException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }

        List<ReportRecord> moreThan315Hours = records.stream()
                .filter(ReportRecord::isGreaterThan3hours)
                .collect(Collectors.toList());

        List<ReportRecord> rest = records.stream()
                .filter(reportRecord -> !reportRecord.isGreaterThan3hours())
                .collect(Collectors.toList());


        try (Document document = new Document(pdf)) {
            document.add(header());

            document.add(summary(rest.size()));
            Table table = table();
            addRecords(table, rest);
            document.add(table);

            document.add(
                    new Paragraph("Salidas Mayores de 3:15 Horas")
                            .setMarginTop(20)
                            .setFontColor(new DeviceRgb(60, 141, 188))
                            .setBold()
                            .setFontSize(14));

            document.add(summary(moreThan315Hours.size()));
            Table table2 = table();
            addRecords(table2, moreThan315Hours);
            document.add(table2);

            document.close();
            return out.toByteArray();
        }
    }

    private Paragraph header() {
        Paragraph header = new Paragraph(
                Config.propiedades.getProperty("empresa", "nombre de empresa") +
                        "\nREPORTE OCUPACION HABITACIONES"
        );
        header.setFontSize(18);
        header.setFontColor(new DeviceRgb(60, 141, 188));
        header.setBold();
        return header;
    }

    private Table summary(int recordCOunt) {
        Table table = new Table(
                new UnitValue[]{
                        new UnitValue(UnitValue.PERCENT, 100)

                }).setBorder(Border.NO_BORDER);
        table.addCell(new Cell().add(new Paragraph("Total de rentas: " +recordCOunt)).setBorder(Border.NO_BORDER));
        return table;
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

    private void addRecords(Table table, List<ReportRecord> records) {
        boolean odd = true;
        for (ReportRecord record : records) {
            odd = !odd;
            table.addCell(contentCell(record.getIdRoom(), odd));
            table.addCell(contentCell(record.getDate(), odd));
            table.addCell(contentCell(record.getStartTime(), odd));
            table.addCell(contentCell(record.getEndTime(), odd));
            table.addCell(contentCell(record.getDuration(), odd));
        }

    }
}
