/*
 * PropertiesReplacerImpl.java
 *
 * 02.11.2009
 *
 * Copyright 2010 Michael Lieshoff
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

package org.mili.ant;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.xml.bind.*;

import org.apache.commons.io.*;
import org.apache.commons.lang.*;
import org.mili.ant.generated.jaxb.propertiesdescription.*;
import org.mili.core.xml.*;


/**
 * This interface reads a properties file and a specified xml configuration, to replace other
 * file contents.
 *
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplacerImpl implements PropertiesReplacer {
    private Properties p = new Properties();
    private Map<String, Property> cachedProperties = new Hashtable<String, Property>();
    private Map<File, String> cachedFiles = new Hashtable<File, String>();
    private PropertiesDescription pd = null;
    private ActualParameters actuals = null;
    private final File BASE_DIR = new File("");
    private final static String NAMESPACE = "org.mili.ant.generated.jaxb.propertiesdescription";

    /**
     * Replace.
     *
     * @param propFile the prop file
     * @param propDescFile the prop desc file
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JAXBException the jAXB exception
     */
    @Override
    public void replace(File propFile, File propDescFile) throws IOException, JAXBException  {
        checkParameters(propFile, propDescFile);
        loadProperties(propFile);
        pd = (PropertiesDescription) XmlAccess.read(propDescFile, NAMESPACE);
        cacheProperties();
        iterateOverFiles();
        writeCachedFiles();
        return;
    }

    private void checkParameters(File propFile, File propDescFile)
            throws FileNotFoundException {
        Validate.notNull(propFile);
        Validate.notNull(propDescFile);
        if (!propFile.exists()) {
            throw new FileNotFoundException("propFile not found ! "
                    + BASE_DIR.getAbsolutePath());
        }
        if (!propDescFile.exists()) {
            throw new FileNotFoundException("propDescFile not found ! "
                    + BASE_DIR.getAbsolutePath());
        }
    }

    private void loadProperties(File propFile) throws IOException {
        InputStream is = new FileInputStream(propFile);
        if (propFile.getAbsolutePath().endsWith(".xml")) {
            p.loadFromXML(is);
        } else {
            p.load(is);
        }
        is.close();
    }

    private void cacheProperties() {
        for (int i = 0, n = pd.getProperty().size(); i < n; i++) {
            Property prop = pd.getProperty().get(i);
            if (cachedProperties.get(prop.getName()) != null) {
                throw new IllegalStateException(
                        "Properties can't be declared more then one times["
                        + prop.getName() + "].");
            }
            cachedProperties.put(prop.getName(), prop);
        }
    }

    private void iterateOverFiles() throws IOException {
        for (int i = 0, n = pd.getReplaceFile().size(); i < n; i++) {
            actuals = new ActualParameters();
            actuals.replaceFile = pd.getReplaceFile().get(i);
            actuals.file = new File(actuals.replaceFile.getName());
            if (actuals.file.exists()) {
                replaceFile();
            } else if (actuals.replaceFile.isHaltOnError()) {
                throw new FileNotFoundException("Replace-file "
                        + actuals.replaceFile.getName() + " not found !");
            }
        }
    }

    private void replaceFile() throws IOException {
        String data = getContentFromCacheOrFile(actuals.file);
        String orig_data = data;
        for (int i = 0, n = actuals.replaceFile.getReplace().size(); i < n; i++) {
            this.setActualParameters(actuals.replaceFile.getReplace().get(i));
            Property prop = getProperty();
            String value = getValue(prop, getDefault(prop));
            Converter converter = null;
            if (!StringUtils.isEmpty(actuals.replace.getConverterClass())) {
                converter = this.createConverter(actuals.replace.getConverterClass());
            }
            if (converter != null) {
                value = converter.convert(value);
            }
            data = replace(getReplace(value), data);
        }
        if (mustCached(data, orig_data)) {
            cachedFiles.put(actuals.file, data);
        }
    }

    private void setActualParameters(Replace r) {
        actuals.replace = r;
        actuals.what = actuals.replace.getWhat();
        actuals.to = actuals.replace.getTo();
    }

    private Property getProperty() {
        Property prop = cachedProperties.get(actuals.replace.getProperty());
        if (prop == null) {
            throw new IllegalStateException(
                    "Property for replacement not defined ![" + actuals.replace.getProperty()
                    + ", " + actuals.what.getContent() + "--> " + actuals.to.getContent()
                    + "] in replace-file[" + actuals.file.getAbsolutePath() + "].");
        }
        return prop;
    }

    private String getDefault(Property p) {
        return p.getDefault().size() > 0 ? p.getDefault().get(0).getContent() : null;
    }

    private String getValue(Property prop, String def) {
        String value = p.getProperty(prop.getName(), def);
        if (value == null || value.length() == 0) {
            value = def;
        }
        if (value == null) {
            throw new NoSuchElementException("No property value and no default "
                    + "value for replacement found ![" + prop.getName() + ", "
                    + actuals.what.getContent() + " --> " + actuals.to.getContent()
                    + "] in replace-file[" + actuals.file.getAbsolutePath() + "].");
        }
        return value;
    }

    private String getReplace(String value) {
        return actuals.to.getContent()
                .replace("$1", value)
                .replace("$!LS", "\n")
                .replace("$!BD", BASE_DIR.getAbsolutePath());
    }

    private String replace(String rep, String data) {
        String what = actuals.what.getContent();
        if (actuals.replace.isRegexp()) {
            rep = rep.replace("$", "\\$");
            Matcher m = Pattern.compile(what, Pattern.MULTILINE).matcher(data);
            if (m.find()) {
                data = m.replaceAll(rep);
            } else if (actuals.replaceFile.isHaltOnError()) {
                throwNothingToReplaceException();
            }
        } else {
            int index = data.indexOf(what);
            if (index >= 0) {
                data = data.replace(what, rep);
            } else if (index < 0 && actuals.replaceFile.isHaltOnError()) {
                throwNothingToReplaceException();
            }
        }
        return data;
    }

    private Converter createConverter(String clsname) {
        try {
            return (Converter) Class.forName(clsname).newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void throwNothingToReplaceException() {
        throw new NoSuchElementException(
                "Nothing to replace found ! [" + actuals.replace.getProperty()
                + ", " + actuals.what.getContent() + "--> "
                + actuals.to.getContent()
                + "] in replace-file[" + actuals.file.getAbsolutePath()
                + "].");
    }

    private String getContentFromCacheOrFile(File f) throws IOException {
        String data = cachedFiles.get(f);
        return data == null ? FileUtils.readFileToString(f) : data;
    }

    private boolean mustCached(String content, String orig) {
        return orig.hashCode() != content.hashCode();
    }

    private void writeCachedFiles() throws IOException {
        if (cachedFiles.size() > 0) {
            for (Iterator<File> i = cachedFiles.keySet().iterator(); i.hasNext();) {
                File f = i.next();
                String data = cachedFiles.get(f);
                FileUtils.writeStringToFile(f, data);
            }
        }
    }

    /**
     * The Class ActualParameters.
     */
    class ActualParameters {

        /** The replace file. */
        ReplaceFile replaceFile = null;

        /** The replace. */
        Replace replace = null;

        /** The what. */
        What what = null;

        /** The to. */
        To to = null;

        /** The file. */
        File file = null;

        /**
         * Instantiates a new actual parameters.
         */
        ActualParameters() {
            super();
        }
    }

}
