package com.pdf.generator.xml;

import com.pdf.generator.constants.LibraryConstants;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

public class ObjectFieldWriter implements XmlWriter {
    private final Writer writer;

    /**
     * Initialize the XML header with writer
     * @param writer
     * @throws IOException
     */
    public ObjectFieldWriter(Writer writer) throws IOException {
        this.writer=writer;
        writer.write(LibraryConstants.XML_HEADER);
    }
    @Override
    public void writeXML(Object object) throws IllegalAccessException, IOException {
        Class<?> objClass = object.getClass();
        writer.write("<" + objClass.getSimpleName() + ">\n");
        for (Field field : objClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(object);
            writer.write("<" + field.getName() + ">" + value + "</" + field.getName() + ">\n");
        }
        writer.write("</" + objClass.getSimpleName() + ">\n");
    }
}
