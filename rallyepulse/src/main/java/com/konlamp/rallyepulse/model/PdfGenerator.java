package com.konlamp.rallyepulse.model;

import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import java.util.List;
import java.util.ArrayList;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.service.CompetitorService;
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
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Overall Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            int i=0;
            for (Overall temp : overall) {
                Competitor competitor = competitorservice.getCompetitorbyid(temp.getCo_number()).get();
                Paragraph entry = new Paragraph((i+1)+" | "+competitor.getCo_number()+" | "+competitor.getDriver()+" | "+temp.getTime().toString());
                document.add(entry);
                document.add(Chunk.NEWLINE);
                i++;
            }
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

}




