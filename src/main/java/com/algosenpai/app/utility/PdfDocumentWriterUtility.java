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

    /**
     * Font size.
     */
    private int size;

    /**
     * Font color.
     */
    private BaseColor color;

    /**
     * Font style.
     */
    private String fontFactory;

    /**
     * Initializes the text styles.
     */
    public PdfDocumentWriterUtility() {
        size = 16;
        color = BaseColor.BLACK;
        fontFactory = FontFactory.COURIER;
    }

    /**
     * Saves content to pdf.
     * @param paragraphs document content.
     * @param documentName document name.
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
