package com.algosenpai.app.utility;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PdfDocumentWriterUtility {

    private int size;
    private BaseColor color;
    private String fontFactory;

    /**
     * Initialize the text styling for pdf document.
     */
    public PdfDocumentWriterUtility() {
        size = 16;
        color = BaseColor.BLACK;
        fontFactory = FontFactory.COURIER;
    }

    /**
     * Save content to PDF.
     * @param paragraphs content for the document.
     * @param documentName name of the document.
     * @throws FileNotFoundException file not found.
     * @throws DocumentException document error.
     */
    public void saveToPdf(ArrayList<String> paragraphs, String documentName)
            throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(documentName));
        document.open();
        Font font = FontFactory.getFont(fontFactory, size, color);
        for (String paragraph: paragraphs) {
            Paragraph text = new Paragraph(paragraph, font);
            document.add(text);
        }
        document.close();
    }
}
