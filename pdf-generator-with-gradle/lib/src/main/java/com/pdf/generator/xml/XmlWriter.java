package com.pdf.generator.xml;

import java.io.IOException;

public interface XmlWriter {
    void writeXML(Object object) throws IllegalAccessException, IOException;
}
