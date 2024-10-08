package com.konlamp.rallyepulse.model;

import com.google.zxing.WriterException;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.text.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
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
            PdfWriter.getInstance(document, new FileOutputStream("PDF/OverallClassifications/OverallClassification.pdf"));
            document.open();
            Image img = Image.getInstance("Images/RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Font headerTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.WHITE);
            Font tableFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Font diffF = FontFactory.getFont(FontFactory.COURIER, 7, new BaseColor(0,150,0));
            Font posistion = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Overall Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(7);
            Paragraph orderP=new Paragraph("Position", headerTable);
            PdfPCell order = new PdfPCell(orderP);
            Paragraph numberP=new Paragraph("Entry No.", headerTable);
            PdfPCell number = new PdfPCell(numberP);
            Paragraph nameP=new Paragraph("Driver",headerTable);
            nameP.add(Chunk.NEWLINE);
            nameP.add("Co-Driver");
            PdfPCell name = new PdfPCell(nameP);
            Paragraph carP=new Paragraph("Vehicle", headerTable);
            PdfPCell car = new PdfPCell(carP);
            Paragraph catP=new Paragraph("Cat/Class", headerTable);
            PdfPCell cat = new PdfPCell(catP);
            Paragraph timeP=new Paragraph("Overall Time", headerTable);
            PdfPCell time = new PdfPCell(timeP);
            Paragraph diffP=new Paragraph("Diff", headerTable);
            PdfPCell diff = new PdfPCell(diffP);
            order.setBackgroundColor(new BaseColor(79,75,239));
            order.setBorder(Rectangle.NO_BORDER);
            order.setUseAscender(true);
            order.setVerticalAlignment(Element.ALIGN_MIDDLE);
            number.setBackgroundColor(new BaseColor(79,75,239));
            number.setBorder(Rectangle.NO_BORDER);
            number.setUseAscender(true);
            number.setVerticalAlignment(Element.ALIGN_MIDDLE);
            name.setBackgroundColor(new BaseColor(79,75,239));
            name.setBorder(Rectangle.NO_BORDER);
            name.setUseAscender(true);
            name.setVerticalAlignment(Element.ALIGN_MIDDLE);
            time.setBackgroundColor(new BaseColor(79,75,239));
            time.setBorder(Rectangle.NO_BORDER);
            time.setUseAscender(true);
            time.setVerticalAlignment(Element.ALIGN_MIDDLE);
            car.setBackgroundColor(new BaseColor(79,75,239));
            car.setBorder(Rectangle.NO_BORDER);
            car.setUseAscender(true);
            car.setVerticalAlignment(Element.ALIGN_MIDDLE);
            diff.setBackgroundColor(new BaseColor(79,75,239));
            diff.setBorder(Rectangle.NO_BORDER);
            diff.setUseAscender(true);
            diff.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cat.setBackgroundColor(new BaseColor(79,75,239));
            cat.setBorder(Rectangle.NO_BORDER);
            cat.setUseAscender(true);
            cat.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(order);
            table.addCell(number);
            table.addCell(name);
            table.addCell(car);
            table.addCell(cat);
            table.addCell(time);
            table.addCell(diff);

            int i=0;
            LocalTime prev=LocalTime.now();
            LocalTime first=LocalTime.now();
            for (Overall temp : overall) {

                Competitor competitor = competitorservice.getCompetitorbyid(temp.getCo_number()).get();
                Paragraph par=new Paragraph(competitor.getDriver().split(" ")[1],tableFont);
                par.add(Chunk.NEWLINE);
                par.add(competitor.getCodriver().split(" ")[1]);

                PdfPCell entry = new PdfPCell(new Paragraph(String.valueOf(i+1),posistion));
                PdfPCell entry2= new PdfPCell(new Paragraph(String.valueOf(competitor.getCo_number()),tableFont));
                PdfPCell entry3= new PdfPCell(par);
                PdfPCell entry4 = new PdfPCell(new Paragraph(String.valueOf(competitor.getVehicle()),tableFont));
                PdfPCell entry5 = new PdfPCell(new Paragraph(competitor.getCategory()+"/"+competitor.getCar_class(),tableFont));
                String timeS;
                if(temp.getTime().getHour()==0){
                    int nano=temp.getTime().getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    timeS=temp.getTime().getMinute()+":"+temp.getTime().getSecond()+"."+nano;
                }
                else{
                    timeS=temp.getTime().toString();
                }
                PdfPCell entry6 = new PdfPCell(new Paragraph(String.valueOf(timeS),posistion));
                PdfPCell entry7;
                if(i!=0) {
                    LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                    LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                    difftoPrev=difftoPrev.plusNanos( temp.getTime().toNanoOfDay()-prev.toNanoOfDay());
                    difftoFirst=difftoFirst.plusNanos( temp.getTime().toNanoOfDay()-first.toNanoOfDay());
                    prev=temp.getTime();
                    String difftoPrevS;
                    if(difftoPrev.getHour()==0){
                        int nano=difftoPrev.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoPrev.getMinute()==0){
                            difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoPrevS=difftoPrev.toString();
                    }
                    String difftoFirstS;
                    if(difftoFirst.getHour()==0){
                        int nano=difftoFirst.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoFirst.getMinute()==0){
                            difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoFirstS=difftoFirst.toString();
                    }
                    Paragraph diffs=new Paragraph("+"+difftoPrevS,diffF);
                    diffs.add(Chunk.NEWLINE);
                    diffs.add("+"+difftoFirstS);
                    entry7 = new PdfPCell(diffs);
                }
                else{
                    first=temp.getTime();
                    prev=first;
                    entry7 = new PdfPCell(new Paragraph("+00:00",diffF));
                }
                if((i+1)%2==0){
                    entry.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry6.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry7.setBackgroundColor(BaseColor.LIGHT_GRAY);

                }
                entry.setBorder(Rectangle.NO_BORDER);
                entry.setUseAscender(true);
                entry.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry2.setBorder(Rectangle.NO_BORDER);
                entry2.setUseAscender(true);
                entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry3.setBorder(Rectangle.NO_BORDER);
                entry3.setUseAscender(true);
                entry3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry4.setBorder(Rectangle.NO_BORDER);
                entry4.setUseAscender(true);
                entry4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry5.setBorder(Rectangle.NO_BORDER);
                entry5.setUseAscender(true);
                entry5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry6.setBorder(Rectangle.NO_BORDER);
                entry6.setUseAscender(true);
                entry6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry7.setBorder(Rectangle.NO_BORDER);
                entry7.setUseAscender(true);
                entry7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(entry);
                table.addCell(entry2);
                table.addCell(entry3);
                table.addCell(entry4);
                table.addCell(entry5);
                table.addCell(entry6);
                table.addCell(entry7);

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

    public void generatebycategoryclass(List<Overall> overall, CompetitorService competitorservice, String type, String nameC) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("PDF/OverallClassificationsByCategory/OverallClassification"+type+nameC+".pdf"));
            document.open();
            Image img = Image.getInstance("Images/RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Font headerTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.WHITE);
            Font tableFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Font diffF = FontFactory.getFont(FontFactory.COURIER, 7, new BaseColor(0,150,0));
            Font posistion = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Overall Classification Of "+type+" :"+nameC,  header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(7);
            Paragraph orderP=new Paragraph("Position", headerTable);
            PdfPCell order = new PdfPCell(orderP);
            Paragraph numberP=new Paragraph("Entry No.", headerTable);
            PdfPCell number = new PdfPCell(numberP);
            Paragraph nameP=new Paragraph("Driver",headerTable);
            nameP.add(Chunk.NEWLINE);
            nameP.add("Co-Driver");
            PdfPCell name = new PdfPCell(nameP);
            Paragraph carP=new Paragraph("Vehicle", headerTable);
            PdfPCell car = new PdfPCell(carP);
            Paragraph catP=new Paragraph("Cat/Class", headerTable);
            PdfPCell cat = new PdfPCell(catP);
            Paragraph timeP=new Paragraph("Overall Time", headerTable);
            PdfPCell time = new PdfPCell(timeP);
            Paragraph diffP=new Paragraph("Diff", headerTable);
            PdfPCell diff = new PdfPCell(diffP);
            order.setBackgroundColor(new BaseColor(79,75,239));
            order.setBorder(Rectangle.NO_BORDER);
            order.setUseAscender(true);
            order.setVerticalAlignment(Element.ALIGN_MIDDLE);
            number.setBackgroundColor(new BaseColor(79,75,239));
            number.setBorder(Rectangle.NO_BORDER);
            number.setUseAscender(true);
            number.setVerticalAlignment(Element.ALIGN_MIDDLE);
            name.setBackgroundColor(new BaseColor(79,75,239));
            name.setBorder(Rectangle.NO_BORDER);
            name.setUseAscender(true);
            name.setVerticalAlignment(Element.ALIGN_MIDDLE);
            time.setBackgroundColor(new BaseColor(79,75,239));
            time.setBorder(Rectangle.NO_BORDER);
            time.setUseAscender(true);
            time.setVerticalAlignment(Element.ALIGN_MIDDLE);
            car.setBackgroundColor(new BaseColor(79,75,239));
            car.setBorder(Rectangle.NO_BORDER);
            car.setUseAscender(true);
            car.setVerticalAlignment(Element.ALIGN_MIDDLE);
            diff.setBackgroundColor(new BaseColor(79,75,239));
            diff.setBorder(Rectangle.NO_BORDER);
            diff.setUseAscender(true);
            diff.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cat.setBackgroundColor(new BaseColor(79,75,239));
            cat.setBorder(Rectangle.NO_BORDER);
            cat.setUseAscender(true);
            cat.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(order);
            table.addCell(number);
            table.addCell(name);
            table.addCell(car);
            table.addCell(cat);
            table.addCell(time);
            table.addCell(diff);

            int i=0;
            LocalTime prev=LocalTime.now();
            LocalTime first=LocalTime.now();
            for (Overall temp : overall) {

                Competitor competitor = competitorservice.getCompetitorbyid(temp.getCo_number()).get();
                Paragraph par=new Paragraph(competitor.getDriver().split(" ")[1],tableFont);
                par.add(Chunk.NEWLINE);
                par.add(competitor.getCodriver().split(" ")[1]);

                PdfPCell entry = new PdfPCell(new Paragraph(String.valueOf(i+1),posistion));
                PdfPCell entry2= new PdfPCell(new Paragraph(String.valueOf(competitor.getCo_number()),tableFont));
                PdfPCell entry3= new PdfPCell(par);
                PdfPCell entry4 = new PdfPCell(new Paragraph(String.valueOf(competitor.getVehicle()),tableFont));
                PdfPCell entry5 = new PdfPCell(new Paragraph(competitor.getCategory()+"/"+competitor.getCar_class(),tableFont));
                String timeS;
                if(temp.getTime().getHour()==0){
                    int nano=temp.getTime().getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    timeS=temp.getTime().getMinute()+":"+temp.getTime().getSecond()+"."+nano;
                }
                else{
                    timeS=temp.getTime().toString();
                }
                PdfPCell entry6 = new PdfPCell(new Paragraph(String.valueOf(timeS),posistion));
                PdfPCell entry7;
                if(i!=0) {
                    LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                    LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                    difftoPrev=difftoPrev.plusNanos( temp.getTime().toNanoOfDay()-prev.toNanoOfDay());
                    difftoFirst=difftoFirst.plusNanos( temp.getTime().toNanoOfDay()-first.toNanoOfDay());
                    prev=temp.getTime();
                    String difftoPrevS;
                    if(difftoPrev.getHour()==0){
                        int nano=difftoPrev.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoPrev.getMinute()==0){
                            difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoPrevS=difftoPrev.toString();
                    }
                    String difftoFirstS;
                    if(difftoFirst.getHour()==0){
                        int nano=difftoFirst.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoFirst.getMinute()==0){
                            difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoFirstS=difftoFirst.toString();
                    }
                    Paragraph diffs=new Paragraph("+"+difftoPrevS,diffF);
                    diffs.add(Chunk.NEWLINE);
                    diffs.add("+"+difftoFirstS);
                    entry7 = new PdfPCell(diffs);
                }
                else{
                    first=temp.getTime();
                    prev=first;
                    entry7 = new PdfPCell(new Paragraph("+00:00",diffF));
                }
                if((i+1)%2==0){
                    entry.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry6.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry7.setBackgroundColor(BaseColor.LIGHT_GRAY);

                }
                entry.setBorder(Rectangle.NO_BORDER);
                entry.setUseAscender(true);
                entry.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry2.setBorder(Rectangle.NO_BORDER);
                entry2.setUseAscender(true);
                entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry3.setBorder(Rectangle.NO_BORDER);
                entry3.setUseAscender(true);
                entry3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry4.setBorder(Rectangle.NO_BORDER);
                entry4.setUseAscender(true);
                entry4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry5.setBorder(Rectangle.NO_BORDER);
                entry5.setUseAscender(true);
                entry5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry6.setBorder(Rectangle.NO_BORDER);
                entry6.setUseAscender(true);
                entry6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry7.setBorder(Rectangle.NO_BORDER);
                entry7.setUseAscender(true);
                entry7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(entry);
                table.addCell(entry2);
                table.addCell(entry3);
                table.addCell(entry4);
                table.addCell(entry5);
                table.addCell(entry6);
                table.addCell(entry7);

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
            PdfWriter.getInstance(document, new FileOutputStream("PDF/OverallByStageClassifications/Overall "+specialStage.getName()+".pdf"));
            document.open();
            Image img = Image.getInstance("Images/RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Font headerTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.WHITE);
            Font tableFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Font diffF = FontFactory.getFont(FontFactory.COURIER, 7, new BaseColor(0,150,0));
            Font posistion = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Overall Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph("Until Stage "+specialStage.getName(),header1);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(7);
            Paragraph orderP=new Paragraph("Position", headerTable);
            PdfPCell order = new PdfPCell(orderP);
            Paragraph numberP=new Paragraph("Entry No.", headerTable);
            PdfPCell number = new PdfPCell(numberP);
            Paragraph nameP=new Paragraph("Driver",headerTable);
            nameP.add(Chunk.NEWLINE);
            nameP.add("Co-Driver");
            PdfPCell name = new PdfPCell(nameP);
            Paragraph carP=new Paragraph("Vehicle", headerTable);
            PdfPCell car = new PdfPCell(carP);
            Paragraph catP=new Paragraph("Cat/Class", headerTable);
            PdfPCell cat = new PdfPCell(catP);
            Paragraph timeP=new Paragraph("Overall Time", headerTable);
            PdfPCell time = new PdfPCell(timeP);
            Paragraph diffP=new Paragraph("Diff", headerTable);
            PdfPCell diff = new PdfPCell(diffP);
            order.setBackgroundColor(new BaseColor(79,75,239));
            order.setBorder(Rectangle.NO_BORDER);
            order.setUseAscender(true);
            order.setVerticalAlignment(Element.ALIGN_MIDDLE);
            number.setBackgroundColor(new BaseColor(79,75,239));
            number.setBorder(Rectangle.NO_BORDER);
            number.setUseAscender(true);
            number.setVerticalAlignment(Element.ALIGN_MIDDLE);
            name.setBackgroundColor(new BaseColor(79,75,239));
            name.setBorder(Rectangle.NO_BORDER);
            name.setUseAscender(true);
            name.setVerticalAlignment(Element.ALIGN_MIDDLE);
            time.setBackgroundColor(new BaseColor(79,75,239));
            time.setBorder(Rectangle.NO_BORDER);
            time.setUseAscender(true);
            time.setVerticalAlignment(Element.ALIGN_MIDDLE);
            car.setBackgroundColor(new BaseColor(79,75,239));
            car.setBorder(Rectangle.NO_BORDER);
            car.setUseAscender(true);
            car.setVerticalAlignment(Element.ALIGN_MIDDLE);
            diff.setBackgroundColor(new BaseColor(79,75,239));
            diff.setBorder(Rectangle.NO_BORDER);
            diff.setUseAscender(true);
            diff.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cat.setBackgroundColor(new BaseColor(79,75,239));
            cat.setBorder(Rectangle.NO_BORDER);
            cat.setUseAscender(true);
            cat.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(order);
            table.addCell(number);
            table.addCell(name);
            table.addCell(car);
            table.addCell(cat);
            table.addCell(time);
            table.addCell(diff);
            int i=0;
            LocalTime prev=LocalTime.now();
            LocalTime first=LocalTime.now();
            for (Overall temp : overall) {

                Competitor competitor = competitorservice.getCompetitorbyid(temp.getCo_number()).get();
                Paragraph par=new Paragraph(competitor.getDriver().split(" ")[1],tableFont);
                par.add(Chunk.NEWLINE);
                par.add(competitor.getCodriver().split(" ")[1]);

                PdfPCell entry = new PdfPCell(new Paragraph(String.valueOf(i+1),posistion));
                PdfPCell entry2= new PdfPCell(new Paragraph(String.valueOf(competitor.getCo_number()),tableFont));
                PdfPCell entry3= new PdfPCell(par);
                PdfPCell entry4 = new PdfPCell(new Paragraph(String.valueOf(competitor.getVehicle()),tableFont));
                PdfPCell entry5 = new PdfPCell(new Paragraph(competitor.getCategory()+"/"+competitor.getCar_class(),tableFont));
                String timeS;
                if(temp.getTime().getHour()==0){
                    int nano=temp.getTime().getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    timeS=temp.getTime().getMinute()+":"+temp.getTime().getSecond()+"."+nano;
                }
                else{
                    timeS=temp.getTime().toString();
                }
                PdfPCell entry6 = new PdfPCell(new Paragraph(timeS,posistion));
                PdfPCell entry7;
                if(i!=0) {
                    LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                    LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                    difftoPrev=difftoPrev.plusNanos( temp.getTime().toNanoOfDay()-prev.toNanoOfDay());
                    difftoFirst=difftoFirst.plusNanos( temp.getTime().toNanoOfDay()-first.toNanoOfDay());
                    prev=temp.getTime();
                    String difftoPrevS;
                    if(difftoPrev.getHour()==0){
                        int nano=difftoPrev.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoPrev.getMinute()==0){
                            difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoPrevS=difftoPrev.toString();
                    }
                    String difftoFirstS;
                    if(difftoFirst.getHour()==0){
                        int nano=difftoFirst.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoFirst.getMinute()==0){
                            difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoFirstS=difftoFirst.toString();
                    }
                    Paragraph diffs=new Paragraph("+"+difftoPrevS,diffF);

                    diffs.add(Chunk.NEWLINE);
                    diffs.add("+"+difftoFirstS);
                    entry7 = new PdfPCell(diffs);
                }
                else{
                    first=temp.getTime();
                    prev=first;
                    entry7 = new PdfPCell(new Paragraph("+00:00",diffF));
                }
                if((i+1)%2==0){
                    entry.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry6.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry7.setBackgroundColor(BaseColor.LIGHT_GRAY);

                }
                entry.setBorder(Rectangle.NO_BORDER);
                entry.setUseAscender(true);
                entry.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry2.setBorder(Rectangle.NO_BORDER);
                entry2.setUseAscender(true);
                entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry3.setBorder(Rectangle.NO_BORDER);
                entry3.setUseAscender(true);
                entry3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry4.setBorder(Rectangle.NO_BORDER);
                entry4.setUseAscender(true);
                entry4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry5.setBorder(Rectangle.NO_BORDER);
                entry5.setUseAscender(true);
                entry5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry6.setBorder(Rectangle.NO_BORDER);
                entry6.setUseAscender(true);
                entry6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry7.setBorder(Rectangle.NO_BORDER);
                entry7.setUseAscender(true);
                entry7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(entry);
                table.addCell(entry2);
                table.addCell(entry3);
                table.addCell(entry4);
                table.addCell(entry5);
                table.addCell(entry6);
                table.addCell(entry7);
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
            PdfWriter.getInstance(document, new FileOutputStream("PDF/StageClassifications/"+specialStage.getName()+".pdf"));
            document.open();
            Image img = Image.getInstance("Images/RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Font headerTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.WHITE);
            Font tableFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Font posistion = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
            Font diffF = FontFactory.getFont(FontFactory.COURIER, 7, new BaseColor(0,150,0));

            Paragraph paragraph = new Paragraph("Stage Classification", header1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraph2 = new Paragraph(""+specialStage.getName(),header1);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(paragraph2);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(8);
            Paragraph orderP=new Paragraph("Position", headerTable);
            PdfPCell order = new PdfPCell(orderP);
            Paragraph numberP=new Paragraph("Entry No.", headerTable);
            PdfPCell number = new PdfPCell(numberP);
            Paragraph nameP=new Paragraph("Driver",headerTable);
            nameP.add(Chunk.NEWLINE);
            nameP.add("Co-Driver");
            PdfPCell name = new PdfPCell(nameP);
            Paragraph carP=new Paragraph("Vehicle", headerTable);
            PdfPCell car = new PdfPCell(carP);
            Paragraph catP=new Paragraph("Cat/Class", headerTable);
            PdfPCell cat = new PdfPCell(catP);
            Paragraph s_ftimesP=new Paragraph("Start Time", headerTable);
            s_ftimesP.add(Chunk.NEWLINE);
            s_ftimesP.add("Finish Time");
            PdfPCell s_ftimes = new PdfPCell(s_ftimesP);
            Paragraph TtimeP=new Paragraph("Total Time", headerTable);
            PdfPCell Ttime = new PdfPCell(TtimeP);
            Paragraph diffP=new Paragraph("Diff", headerTable);
            PdfPCell diff = new PdfPCell(diffP);
            order.setBackgroundColor(new BaseColor(79,75,239));
            order.setBorder(Rectangle.NO_BORDER);
            order.setUseAscender(true);
            order.setVerticalAlignment(Element.ALIGN_MIDDLE);
            number.setBackgroundColor(new BaseColor(79,75,239));
            number.setBorder(Rectangle.NO_BORDER);
            number.setUseAscender(true);
            number.setVerticalAlignment(Element.ALIGN_MIDDLE);
            name.setBackgroundColor(new BaseColor(79,75,239));
            name.setBorder(Rectangle.NO_BORDER);
            name.setUseAscender(true);
            name.setVerticalAlignment(Element.ALIGN_MIDDLE);
            s_ftimes.setBackgroundColor(new BaseColor(79,75,239));
            s_ftimes.setBorder(Rectangle.NO_BORDER);
            s_ftimes.setUseAscender(true);
            s_ftimes.setVerticalAlignment(Element.ALIGN_MIDDLE);
            Ttime.setBackgroundColor(new BaseColor(79,75,239));
            Ttime.setBorder(Rectangle.NO_BORDER);
            Ttime.setUseAscender(true);
            Ttime.setVerticalAlignment(Element.ALIGN_MIDDLE);
            car.setBackgroundColor(new BaseColor(79,75,239));
            car.setBorder(Rectangle.NO_BORDER);
            car.setUseAscender(true);
            car.setVerticalAlignment(Element.ALIGN_MIDDLE);
            diff.setBackgroundColor(new BaseColor(79,75,239));
            diff.setBorder(Rectangle.NO_BORDER);
            diff.setUseAscender(true);
            diff.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cat.setBackgroundColor(new BaseColor(79,75,239));
            cat.setBorder(Rectangle.NO_BORDER);
            cat.setUseAscender(true);
            cat.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.setWidthPercentage(100);
            table.addCell(order);
            table.addCell(number);
            table.addCell(name);
            table.addCell(car);
            table.addCell(cat);
            table.addCell(s_ftimes);
            table.addCell(Ttime);
            table.addCell(diff);


            int i =0;
            LocalTime prev=LocalTime.now();
            LocalTime first=LocalTime.now();
            for (TimeKeeping temp : overall) {

                Competitor competitor = competitorservice.getCompetitorbyid(temp.getId().getCompetitorid()).get();
                Paragraph par=new Paragraph(competitor.getDriver().split(" ")[1],tableFont);
                par.add(Chunk.NEWLINE);
                par.add(competitor.getCodriver().split(" ")[1]);
                PdfPCell entry = new PdfPCell(new Paragraph(String.valueOf(i+1),posistion));
                PdfPCell entry2= new PdfPCell(new Paragraph(String.valueOf(competitor.getCo_number()),tableFont));
                PdfPCell entry3= new PdfPCell(par);
                PdfPCell entry4 = new PdfPCell(new Paragraph(String.valueOf(competitor.getVehicle()),tableFont));
                PdfPCell entry5 = new PdfPCell(new Paragraph(competitor.getCategory()+"/"+competitor.getCar_class(),tableFont));
                Paragraph entry6P=new Paragraph(String.valueOf(temp.getStart_time()),tableFont);
                entry6P.add(Chunk.NEWLINE);
                entry6P.add(String.valueOf(temp.getFinish_time()));
                PdfPCell entry6 = new PdfPCell(entry6P);
                String timeS;
                if(temp.getTotal_time().getHour()==0){
                    int nano=temp.getTotal_time().getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    timeS=temp.getTotal_time().getMinute()+":"+temp.getTotal_time().getSecond()+"."+nano;
                }
                else{
                    timeS=temp.getTotal_time().toString();
                }
                PdfPCell entry7 = new PdfPCell(new Paragraph(timeS,posistion));
                PdfPCell entry8;
                if(i!=0) {
                    LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                    LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                    difftoPrev=temp.getTotal_time().minusNanos(prev.toNanoOfDay());
                    difftoFirst=temp.getTotal_time().minusNanos(first.toNanoOfDay());
                    String difftoPrevS;
                    if(difftoPrev.getHour()==0){
                        int nano=difftoPrev.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoPrev.getMinute()==0){
                            difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoPrevS=difftoPrev.toString();
                    }
                    String difftoFirstS;
                    if(difftoFirst.getHour()==0){
                        int nano=difftoFirst.getNano();
                        while(nano>1000){
                            nano=nano/10;
                        }
                        if(difftoFirst.getMinute()==0){
                            difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                        }
                        else{
                            difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                        }
                    }
                    else{
                        difftoFirstS=difftoFirst.toString();
                    }
                    prev=temp.getTotal_time();
                    Paragraph diffs=new Paragraph("+"+difftoPrevS,diffF);
                    diffs.add(Chunk.NEWLINE);
                    diffs.add("+"+difftoFirstS);
                    entry8 = new PdfPCell(diffs);
                }
                else{
                    first=temp.getTotal_time();
                    prev=first;
                    entry8 = new PdfPCell(new Paragraph("+00:00",diffF));
                }
                if((i+1)%2==0){
                    entry.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry6.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry7.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry8.setBackgroundColor(BaseColor.LIGHT_GRAY);

                }
                entry.setBorder(Rectangle.NO_BORDER);
                entry.setUseAscender(true);
                entry.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry2.setBorder(Rectangle.NO_BORDER);
                entry2.setUseAscender(true);
                entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry3.setBorder(Rectangle.NO_BORDER);
                entry3.setUseAscender(true);
                entry3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry4.setBorder(Rectangle.NO_BORDER);
                entry4.setUseAscender(true);
                entry4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry5.setBorder(Rectangle.NO_BORDER);
                entry5.setUseAscender(true);
                entry5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry6.setBorder(Rectangle.NO_BORDER);
                entry6.setUseAscender(true);
                entry6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry7.setBorder(Rectangle.NO_BORDER);
                entry7.setUseAscender(true);
                entry7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry8.setBorder(Rectangle.NO_BORDER);
                entry8.setUseAscender(true);
                entry8.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(entry);
                table.addCell(entry2);
                table.addCell(entry3);
                table.addCell(entry4);
                table.addCell(entry5);
                table.addCell(entry6);
                table.addCell(entry7);
                table.addCell(entry8);
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


    public void generateStartList(List<StartList> startLists, String text, LocalDate date) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("PDF/StartLists/StartList_"+date+".pdf"));
            document.open();
            Image img = Image.getInstance("Images/RallyePulse3 1.png");
            img.scaleAbsoluteHeight(150F);
            img.scaleAbsoluteWidth(200F);
            img.setAlignment(Element.ALIGN_CENTER);
            Font header1 = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.BLACK);
            Font headerTable = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.WHITE);
            Font tableFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Font diffF = FontFactory.getFont(FontFactory.COURIER, 7, new BaseColor(0,150,0));
            Font posistion = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Start List", header1);
            Paragraph paragraph2 = new Paragraph(date+" "+text, tableFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(paragraph);
            document.add(paragraph2);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(7);
            Paragraph orderP=new Paragraph("Position", headerTable);
            PdfPCell order = new PdfPCell(orderP);
            Paragraph numberP=new Paragraph("Entry No.", headerTable);
            PdfPCell number = new PdfPCell(numberP);
            Paragraph nameP=new Paragraph("Driver",headerTable);
            nameP.add(Chunk.NEWLINE);
            nameP.add("Co-Driver");
            PdfPCell name = new PdfPCell(nameP);
            Paragraph carP=new Paragraph("Vehicle", headerTable);
            PdfPCell car = new PdfPCell(carP);
            Paragraph catP=new Paragraph("Cat/Class", headerTable);
            PdfPCell cat = new PdfPCell(catP);
            Paragraph timeP=new Paragraph("Start Time", headerTable);
            PdfPCell time = new PdfPCell(timeP);
            Paragraph diffP=new Paragraph("Diff", headerTable);
            PdfPCell diff = new PdfPCell(diffP);
            order.setBackgroundColor(new BaseColor(79,75,239));
            order.setBorder(Rectangle.NO_BORDER);
            order.setUseAscender(true);
            order.setVerticalAlignment(Element.ALIGN_MIDDLE);
            number.setBackgroundColor(new BaseColor(79,75,239));
            number.setBorder(Rectangle.NO_BORDER);
            number.setUseAscender(true);
            number.setVerticalAlignment(Element.ALIGN_MIDDLE);
            name.setBackgroundColor(new BaseColor(79,75,239));
            name.setBorder(Rectangle.NO_BORDER);
            name.setUseAscender(true);
            name.setVerticalAlignment(Element.ALIGN_MIDDLE);
            time.setBackgroundColor(new BaseColor(79,75,239));
            time.setBorder(Rectangle.NO_BORDER);
            time.setUseAscender(true);
            time.setVerticalAlignment(Element.ALIGN_MIDDLE);
            car.setBackgroundColor(new BaseColor(79,75,239));
            car.setBorder(Rectangle.NO_BORDER);
            car.setUseAscender(true);
            car.setVerticalAlignment(Element.ALIGN_MIDDLE);
            diff.setBackgroundColor(new BaseColor(79,75,239));
            diff.setBorder(Rectangle.NO_BORDER);
            diff.setUseAscender(true);
            diff.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cat.setBackgroundColor(new BaseColor(79,75,239));
            cat.setBorder(Rectangle.NO_BORDER);
            cat.setUseAscender(true);
            cat.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(order);
            table.addCell(number);
            table.addCell(name);
            table.addCell(car);
            table.addCell(cat);
            table.addCell(time);
            table.addCell(diff);

            int i=0;
            LocalTime prev=LocalTime.now();
            LocalTime first=LocalTime.now();
            for (StartList temp : startLists) {

                Competitor competitor = temp.getCompetitor();
                Paragraph par=new Paragraph(competitor.getDriver().split(" ")[1],tableFont);
                par.add(Chunk.NEWLINE);
                par.add(competitor.getCodriver().split(" ")[1]);

                PdfPCell entry = new PdfPCell(new Paragraph(String.valueOf(temp.getPosition()),posistion));
                PdfPCell entry2= new PdfPCell(new Paragraph(String.valueOf(competitor.getCo_number()),tableFont));
                PdfPCell entry3= new PdfPCell(par);
                PdfPCell entry4 = new PdfPCell(new Paragraph(String.valueOf(competitor.getVehicle()),tableFont));
                PdfPCell entry5 = new PdfPCell(new Paragraph(competitor.getCategory()+"/"+competitor.getCar_class(),tableFont));
                String timeS;
                timeS=temp.getTime().toString();
                PdfPCell entry6 = new PdfPCell(new Paragraph(String.valueOf(timeS),posistion));
                PdfPCell entry7;
                if(i!=0) {
                    LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                    difftoPrev=difftoPrev.plusNanos( temp.getTime().toNanoOfDay()-prev.toNanoOfDay());
                    prev=temp.getTime();
                    String difftoPrevS=difftoPrev.toString();
                    Paragraph diffs=new Paragraph("+"+difftoPrevS,diffF);
                    entry7 = new PdfPCell(diffs);
                }
                else{
                    first=temp.getTime();
                    prev=first;
                    entry7 = new PdfPCell(new Paragraph("+00:00",diffF));
                }
                if((i+1)%2==0){
                    entry.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry6.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    entry7.setBackgroundColor(BaseColor.LIGHT_GRAY);

                }
                entry.setBorder(Rectangle.NO_BORDER);
                entry.setUseAscender(true);
                entry.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry2.setBorder(Rectangle.NO_BORDER);
                entry2.setUseAscender(true);
                entry2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry3.setBorder(Rectangle.NO_BORDER);
                entry3.setUseAscender(true);
                entry3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry4.setBorder(Rectangle.NO_BORDER);
                entry4.setUseAscender(true);
                entry4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry5.setBorder(Rectangle.NO_BORDER);
                entry5.setUseAscender(true);
                entry5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry6.setBorder(Rectangle.NO_BORDER);
                entry6.setUseAscender(true);
                entry6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                entry7.setBorder(Rectangle.NO_BORDER);
                entry7.setUseAscender(true);
                entry7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(entry);
                table.addCell(entry2);
                table.addCell(entry3);
                table.addCell(entry4);
                table.addCell(entry5);
                table.addCell(entry6);
                table.addCell(entry7);

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




