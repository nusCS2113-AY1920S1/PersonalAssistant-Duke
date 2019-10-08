package com.algosenpai.app.utility;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfDocumentWriterUtility {

    private int size;
    private BaseColor color;
    private String fontFactory;

    private PdfDocumentWriterUtility() {
        size = 16;
        color = BaseColor.BLACK;
        fontFactory = FontFactory.COURIER;
    }

    /**
     * Save content to PDF.
     * @param documentContent content for the document.
     * @param documentName name of the document.
     * @throws FileNotFoundException file not found.
     * @throws DocumentException document error.
     */
    public void saveToPdf(String documentContent, String documentName) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(documentName));
        document.open();
        Font font = FontFactory.getFont(fontFactory, size, color);
        Chunk chunk = new Chunk(documentContent, font);
        document.add(chunk);
        document.close();
    }

    /**
     * Change color of text in PDF.
     * @param color text color.
     */
    public void setColor(BaseColor color) {
        this.color = color;
    }

    /**
     * Change font of text.
     * @param fontFactory text font.
     */
    public void setFontFactory(String fontFactory) {
        this.fontFactory = fontFactory;
    }

    /**
     * Change size of text.
     * @param size text size.
     */
    public void setSize(int size) {
        this.size = size;
    }
}
