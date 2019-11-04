package com.algosenpai.app.utility;

import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

class PdfDocumentWriterUtilityTest {

    @Test
    void testFileExist() throws FileNotFoundException, DocumentException {
        ArrayList<String> sample = new ArrayList<>();
        sample.add("testing");
        PdfDocumentWriterUtility pdfDocumentWriterUtility = new PdfDocumentWriterUtility();
        pdfDocumentWriterUtility.saveToPdf(sample, "test_pdf_writer.pdf");
        File tempFile = new File("test_pdf_writer.pdf");
        boolean exists = tempFile.exists();
        Assertions.assertTrue(exists);
    }
}
