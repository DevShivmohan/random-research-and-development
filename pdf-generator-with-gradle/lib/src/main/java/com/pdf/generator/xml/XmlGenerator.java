package com.pdf.generator.xml;

import java.io.IOException;

public class XmlGenerator {
    /**
     * Generate xml within given writer,
     * Need to flush the writer at the end of execution
     * @param obj
     * @param xmlWriter
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void generateXML(Object obj, XmlWriter xmlWriter) throws IllegalAccessException, IOException {
        xmlWriter.writeXML(obj);
    }
}
