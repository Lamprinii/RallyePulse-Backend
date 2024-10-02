package com.konlamp.rallyepulse.model;

import com.google.zxing.WriterException;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.text.*;
import java.util.List;
import java.util.ArrayList;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.PdfPTable;

import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.service.CompetitorService;
import com.konlamp.rallyepulse.service.SpecialStageService;
import org.hibernate.sql.ast.tree.expression.Over;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PdfGenerator {



    public void generate(List<Overall> overall, CompetitorService competitorservice) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("overall.pdf"));
            document.open();
            Image img = Image.getInstance("RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Overall Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(5);
            table.addCell("Order");
            table.addCell("Number");
            table.addCell("Name");
            table.addCell("Car");
            table.addCell("Time");

            int i=0;
            for (Overall temp : overall) {
                Competitor competitor = competitorservice.getCompetitorbyid(temp.getCo_number()).get();
                Paragraph entry = new Paragraph((i+1)+" | "+competitor.getCo_number()+" | "+competitor.getDriver()+" | "+temp.getTime().toString());
                table.addCell(String.valueOf(i+1));
                table.addCell(String.valueOf(competitor.getCo_number()));
                table.addCell(competitor.getDriver());
                table.addCell(String.valueOf(competitor.getVehicle()));
                table.addCell(temp.getTime().toString());
                //document.add(entry);
                //document.add(Chunk.NEWLINE);
                i++;
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public void generateoverallbystage(List<Overall> overall, CompetitorService competitorservice,SpecialStage specialStage) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("overall "+specialStage.getName()+".pdf"));
            document.open();
            Image img = Image.getInstance("RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Overall Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph("Until Stage "+specialStage.getName());
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(paragraph2);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(5);
            table.addCell("Order");
            table.addCell("Number");
            table.addCell("Name");
            table.addCell("Car");
            table.addCell("Time");

            int i=0;
            for (Overall temp : overall) {
                Competitor competitor = competitorservice.getCompetitorbyid(temp.getCo_number()).get();
                table.addCell(String.valueOf(i+1));
                table.addCell(String.valueOf(competitor.getCo_number()));
                table.addCell(competitor.getDriver());
                table.addCell(String.valueOf(competitor.getVehicle()));
                table.addCell(temp.getTime().toString());
                //document.add(entry);
                //document.add(Chunk.NEWLINE);
                i++;
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public void generatestage(List<TimeKeeping> overall, CompetitorService competitorservice, SpecialStage specialStage) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(specialStage.getName()+".pdf"));
            document.open();
            Image img = Image.getInstance("RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Stage Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph("Stage :"+specialStage.getName());
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(paragraph2);

            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(7);
            table.addCell("Order");
            table.addCell("Number");
            table.addCell("Name");
            table.addCell("Car");
            table.addCell("Start Time");
            table.addCell("Finish Time");
            table.addCell("Total Time");


            int i=0;
            for (TimeKeeping temp : overall) {
                Competitor competitor = competitorservice.getCompetitorbyid(temp.getId().getCompetitorid()).get();
                table.addCell(String.valueOf(i+1));
                table.addCell(String.valueOf(competitor.getCo_number()));
                table.addCell(competitor.getDriver());
                table.addCell(String.valueOf(competitor.getVehicle()));
                table.addCell(temp.getStart_time().toString());
                table.addCell(temp.getFinish_time().toString());
                table.addCell(temp.getTotal_time().toString());

                //document.add(entry);
                //document.add(Chunk.NEWLINE);
                i++;
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}




