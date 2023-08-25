package com.pdf.generator.pdf;

import com.pdf.generator.constants.LibraryConstants;
import org.apache.fop.apps.*;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class PdfGenerator {
    private final FopFactory fopFactory;
    public PdfGenerator() {
        fopFactory = FopFactory.newInstance(new File(LibraryConstants.DOT).toURI());
    }

    /**
     * This method used as a engine which can take xsl and xml as a input
     * and based on these files it will generate pdf
     * @param xmlFile take valid xml file
     * @param xslFile valid xsl file
     * @param outputDir destination directory where pdf will be created, make sure have write access
     * @return generated pdf file name upon successful execution
     * @throws IOException
     * @throws TransformerException
     * @throws FOPException
     */
    public String convertToPDF(File xmlFile, File xslFile, File outputDir) throws IOException, TransformerException, FOPException {
        if(!xmlFile.exists() || !xslFile.exists() || !outputDir.isDirectory() || !outputDir.canWrite())
            throw new IllegalArgumentException(LibraryConstants.ERROR_MESSAGE);
        File pdfFile=new File(outputDir, UUID.randomUUID()+ LibraryConstants.DOUBLE_UNDERSCORE+System.currentTimeMillis()+ LibraryConstants.PDF_EXTENSION);
        StreamSource xmlStreamSource = new StreamSource(xmlFile);
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream pdfOutputStream=new FileOutputStream(pdfFile);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,pdfOutputStream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
        Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(xmlStreamSource, res);
        pdfOutputStream.close();
        return pdfFile.getName();
    }
}
