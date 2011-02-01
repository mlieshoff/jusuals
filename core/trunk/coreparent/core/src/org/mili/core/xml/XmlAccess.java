/*
 * XmlAccess.java
 *
 * 23.01.2009
 *
 * Copyright 2009 Michael Lieshoff
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mili.core.xml;

import com.sun.org.apache.xml.internal.serialize.*;

import java.io.*;

import javax.xml.bind.*;

import org.mili.core.io.*;
import org.mili.core.logging.*;

/**
 * This utility class provides some useful methods to operate with JAXB.
 *
 * @author Michael Lieshoff
 *
 */
public class XmlAccess {
    private final static DefaultLogger log = DefaultLogger.getLogger(XmlAccess.class);

    /**
     * Unmarshalls a XML file to an object structure.
     *
     * @param file the file object contains XML data.
     * @param namespace the namespace.
     * @return unmarshalled XML data.
     * @throws IOException if IO exceptions occurs.
     * @throws JAXBException if JAXB exceptions occurs.
     */
    public static Object read(File file, String namespace) throws IOException, JAXBException {
        return read(FileUtil.getInputStream(file.getAbsolutePath()), namespace);
    }

    /**
     * Unmarshalls a XML file to an object structure.
     *
     * @param filename the filename to a file contains XML data.
     * @param namespace the namespace.
     * @return unmarshalled XML data.
     * @throws IOException if IO exceptions occurs.
     * @throws JAXBException if JAXB exceptions occurs.
     */
    public static Object read(String filename, String namespace) throws IOException,
            JAXBException {
        return read(FileUtil.getInputStream(filename), namespace);
    }

    /**
     * Unmarshalls a XML file to an object structure.
     *
     * @param is the input stream object contains XML data.
     * @param namespace the namespace.
     * @return unmarshalled XML data.
     * @throws IOException if IO exceptions occurs.
     * @throws JAXBException if JAXB exceptions occurs.
     */
    public static Object read(InputStream is, String namespace) throws IOException,
            JAXBException {
        JAXBContext context = JAXBContext.newInstance(namespace);
        Unmarshaller u = context.createUnmarshaller();
        return u.unmarshal(is);
    }

    /**
     * Marshalls a object structure to a XML file.
     *
     * @param o an object.
     * @param filename filename of XML file.
     * @param namespace the namespace.
     * @return <tt>true</tt> if object is written, <tt>false</tt> otherwise.
     * @throws PropertyException if property exception occurs.
     * @throws JAXBException if JAXB exception occurs.
     * @throws IOException if IO exception occurs
     */
    public static boolean write(Object o, String filename, String namespace)
            throws PropertyException, JAXBException, IOException {
        return write(o, filename, namespace, null);
    }

    /**
     * Marshalls a object structure to a XML file.
     *
     * @param o an object.
     * @param filename filename of XML file.
     * @param namespace the namespace.
     * @param cdes CData elements, like "^text" etc.
     * @return <tt>true</tt> if object is written, <tt>false</tt> otherwise.
     * @throws PropertyException if property exception occurs.
     * @throws JAXBException if JAXB exception occurs.
     * @throws IOException if IO exception occurs
     */
    public static boolean write(Object o, String filename, String namespace, String[] cdes)
            throws PropertyException, JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(namespace);
        Marshaller m = context.createMarshaller();
        if (o == null) {
            log.error("There is no writing object defined !");
            return false;
        }
        File f = new File(filename);
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        XMLSerializer serializer = getXMLSerializer(new FileOutputStream(f), cdes);
        m.marshal(o, serializer.asContentHandler());
        return true;
    }

    private static XMLSerializer getXMLSerializer(FileOutputStream fos, String[] cdes) {
        OutputFormat of = new OutputFormat();
        if (cdes != null && cdes.length > 0) {
            of.setCDataElements(cdes);
        }
        of.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(of);
        serializer.setOutputByteStream(fos);
        return serializer;
    }

}
