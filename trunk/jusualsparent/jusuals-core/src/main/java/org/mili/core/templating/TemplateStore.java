/*
 * TemplateStore.java
 *
 * 21.06.2005
 *
 * Copyright 2005 Michael Lieshoff
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
package org.mili.core.templating;


import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.apache.log4j.*;


/**
 * This class provides methods handles generic templates. It stores templates via Map and
 * provides methods to access templates from filesystem and put them into internally template
 * store.
 *
 * The tricky one is the static replace method. It's function is simply defined to replace all
 * given replacements in a specified template. A replace is defined as something like this:
 *
 * ${...}
 *
 * You know, it's Ant syntax using.
 *
 * The store provides a hot deploy able cache system, every time the read method is called, the
 * templates source file will be up-to-date checked. Then it will be re-deployed or not.
 *
 * The store enables usage of component sheets. This is custom method to replace some
 * placeholders in template with their component sheet values. You can specify default component
 * sheet which be used for default value replacing and by example user component sheets which
 * overrides the default values.
 *
 * @author Michael Lieshoff
 *
 */
public class TemplateStore {
    private static final Logger log = Logger.getLogger(TemplateStore.class);
    private static final String BEGINNING = "<!-- @";
    private static final String ENDING = "<!-- /@";
    private static final String CLOSE = "-->";
    private Map<String, StoreData> templates = new Hashtable<String, StoreData>();
    private Map<String, StoreData> componentSheets = new Hashtable<String, StoreData>();
    private String path = "";
    private boolean cache = true;

    /**
     * Creates new empty TemplateStore.
     */
    public TemplateStore() {
        super();
    }

    /**
     * Reads a template from filesystem and places it in holder under specially key.
     *
     * @param filename Name of file contents template.
     * @param key Key to store template in holder.
     */
    public void read(String filename, String key) {
        String fn = this.path.length() > 0 ? this.path + "/" + filename : filename;
        File file = new File(fn);
        if (this.cache) {
            StoreData sd = this.templates.get(key);
            if (sd != null) {
                if (this.uptodate(file, sd.getModified())) {
                    log.debug("Caching enabled, so i don't load again !");
                    return;
                } else {
                    log.debug("Re-Deploying[" + file.lastModified() + " don't matches with "
                            + sd.getModified() + "]");
                }
            }
        }
        log.debug("Read template[" + fn + "].");
        try {
            String s = FileUtils.readFileToString(file);
            log.debug("Store template[" + fn + "] under [" + key + "].");
            this.filterTemplates(key, s, file.lastModified());
        } catch (Exception e) {
            log.debug("Read Template[" + fn + "] causes an error." + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Stores a template from filesystem and places it in holder under specially key.
     *
     * @param data Template content.
     * @param key Key to store template in holder.
     */
    public void store(String data, String key) {
        try {
            log.debug("Store template data under [" + key + "].");
            this.filterTemplates(key, data, System.currentTimeMillis());
        } catch (Exception e) {
            log.debug("Read Template data[" + key + "] causes an error." + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Getting a plain template by its key from holder.
     *
     * @param key The templates key.
     * @return String contents plain template.
     */
    public String get(String key) {
        StoreData sd = this.templates.get(key);
        if (sd != null) {
            log.debug("Template[" + key + "] requested.");
            return sd.getData().toString();
        } else {
            log.debug("Template[" + key + "] not in holder.");
            return "";
        }
    }

    /**
     * Getting a required plain template by its key from holder.
     *
     * @param key The templates key.
     * @return String contents plain template.
     */
    public String getRequired(String key) {
        StoreData sd = this.templates.get(key);
        if (sd != null) {
            log.debug("Template[" + key + "] requested.");
            return sd.getData().toString();
        } else {
            throw new IllegalArgumentException("Template[" + key + "] not in holder.");
        }
    }

    /**
     * Putting a template into holder with specified unified key.
     *
     * @param key The templates key.
     * @param template The template represented by String object.
     */
    public void put(String key, String template) {
        if (key != null && key.length() > 0) {
            if (template != null && template.length() > 0) {
                log.debug("Put templates[" + key + "] in store.");
                this.templates.put(key, new StoreData(template, 0));
            } else {
                log.debug("Dont store empty templates[key=" + key + "].");
            }
        } else {
            log.debug("Dont store templates with not legal key[" + key + "].");
        }
    }

    /**
     * Sets the path.
     *
     * @param path The path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns the path.
     *
     * @return The path;
     */
    public String getPath() {
        return this.path;
    }

    /**
     * List all included templates by their names.
     *
     * @return List with all template names.
     */
    public String[] listTemplates() {
        Vector<String> v = new Vector<String>();
        v.addAll(this.templates.keySet());
        return (String[]) v.toArray(new String[] {});
    }

    /**
     * Reads a component sheet from filesystem and places it in holder under specially key.
     *
     * @param filename Name of file contents template.
     * @param key Key to store template in holder.
     */
    public void readComponentSheet(String filename, String key) {
        String fn = this.path.length() > 0 ? this.path + "/" + filename : filename;
        File file = new File(fn);
        if (this.cache) {
            StoreData sd = this.templates.get(key);
            if (sd != null) {
                if (this.uptodate(file, sd.getModified())) {
                    log.debug("Caching enabled, so i don't load again !");
                    return;
                } else {
                    log.debug("Re-Deploying[" + file.lastModified() + " don't matches with "
                            + sd.getModified() + "]");
                }
            }
        }
        log.debug("Read component sheet[" + fn + "].");
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(fn));
            log.debug("Store component sheet[" + fn + "] under [" + key + "].");
            this.componentSheets.put(key, new StoreData(p, file.lastModified()));
        } catch (Exception e) {
            log.debug("Read component sheet[" + fn + "] causes an error." + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Putting a component sheet into holder with specified unified key.
     *
     * @param key The component sheet key.
     * @param template The component sheet represented by properties.
     */
    public void putComponentSheet(String key, Properties componentSheet) {
        if (key != null && key.length() > 0) {
            if (componentSheet != null) {
                log.debug("Put component sheet[" + key + "] in store.");
                this.componentSheets.put(key, new StoreData(componentSheet, 0));
            } else {
                log.debug("Dont store empty component sheet[key=" + key + "].");
            }
        } else {
            log.debug("Dont store component sheet with not legal key[" + key + "].");
        }
    }

    /**
     * Getting a plain component sheet by its key from holder.
     *
     * @param key The component sheets key.
     * @return String contents plain component sheets.
     */
    public Properties getComponentSheet(String key) {
        Properties componentSheet = (Properties) this.componentSheets.get(key).getData();
        if (componentSheet != null) {
            log.debug("Component sheet[" + key + "] requested.");
            return componentSheet;
        } else {
            log.debug("Component sheet[" + key + "] not in holder.");
            return new Properties();
        }
    }

    /**
     * Filter templates by their matching sequences.
     *
     * @param mainname The main template name.
     * @param template The templates source data.
     * @param modified Last modified file stamp.
     */
    private void filterTemplates(String mainname, String template, long modified) {
        long ms = -System.currentTimeMillis();
        Map<String, StoreData> temp = new Hashtable<String, StoreData>();
        Map<String, StoreData> temp2 = new Hashtable<String, StoreData>();
        int keystart = template.indexOf(BEGINNING);
        if (keystart >= 0) {
            keystart += BEGINNING.length();
        } else {
            this.templates.put(mainname, new StoreData(template, modified));
            return;
        }
        int keyend = template.indexOf(CLOSE) - 1;
        String key = template.substring(keystart, keyend);
        int contentend = template.indexOf(ENDING + key + " ");
        String content = template.substring(keyend + CLOSE.length() + 1, contentend);
        temp.put(mainname + "_" + key, new StoreData(content, modified));
        log.debug("Put[" + mainname + "_" + key + "].");
        boolean salter = false;
        while (!salter) {
            boolean salter2 = true;
            for (Iterator<String> i = temp.keySet().iterator(); i.hasNext();) {
                String name = i.next();
                String elem = (String) temp.get(name).getData();
                log.debug("Get[" + name + "].");
                int keystart1 = elem.indexOf(BEGINNING);
                if (keystart1 >= 0) {
                    keystart1 += BEGINNING.length();
                    int keyend1 = elem.indexOf(CLOSE) - 1;
                    String key1 = elem.substring(keystart1, keyend1);
                    log.debug("Search " + key1);
                    int contentend1 = elem.indexOf(ENDING + key1 + " ");
                    if (contentend1 < 0) {
                        log.error("Cant find content end[" + ENDING + key1 + "] index["
                                + contentend1 + "] for [" + key1 + "].");
                        log.debug(elem);
                    }
                    if (keyend1 < 0) {
                        log.error("Cant find key end index[" + key1 + "].");
                    }
                    String content1 = elem.substring(keyend1 + CLOSE.length() + 1, contentend1);
                    temp2.put(mainname + "_" + key1, new StoreData(content1, modified));
                    log.debug("Put[" + mainname + "_" + key1 + "].");
                    salter2 = false;
                    elem = elem.substring(0, keystart1 - BEGINNING.length())
                            + "${"
                            + key1
                            + "}"
                            + elem.substring(contentend1 + ENDING.length() + key1.length()
                                    + CLOSE.length() + 1, elem.length());
                    temp2.put(name, new StoreData(elem, modified));
                    log.debug("Update[" + name + "].");
                } else {
                    this.templates.put(name, new StoreData(elem, modified));
                    temp2.remove(name);
                    log.debug("Store[" + name + "].");
                }
            }
            for (Iterator<String> i = temp2.keySet().iterator(); i.hasNext();) {
                String name = i.next();
                log.debug("Swap[" + name + "].");
                temp.put(name, temp2.get(name));
            }
            if (salter2) {
                salter = true;
            }
        }
        for (Iterator<String> i = temp.keySet().iterator(); i.hasNext();) {
            String name = i.next();
            this.templates.put(name, temp.get(name));
        }
        ms += System.currentTimeMillis();
        log.debug("Storing tooks[" + ms + "] ms.");
        return;
    }

    /* checks up-to-date. */
    private boolean uptodate(File file, long modified) {
        if (file.lastModified() <= modified) {
            return true;
        }
        return false;
    }

    /**
     * Replaces a template with given component sheets.
     *
     * @param template The template.
     * @param defaultComponentSheet The default component Sheet.
     * @param givenComponentSheet Component sheet.
     * @return Replaced template.
     */
    public static String replaceWithComponentSheets(String template,
            Properties defaultComponentSheet, Properties givenComponentSheet) {
        String s = template;
        try {
            for (Enumeration<?> e = defaultComponentSheet.propertyNames(); e.hasMoreElements();) {
                String name = e.nextElement().toString();
                log.debug("check: " + name);
                String prop = givenComponentSheet.getProperty(name, defaultComponentSheet
                        .getProperty(name));
                log.debug("prop: " + prop);
                if (prop != null && prop.length() > 0) {
                    s = s.replace("${" + name + "}", prop);
                } else {
                    log.debug("Component sheet style[" + name + "] not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * This method returns a new template String instance replaced by given replacements. Beware
     * that this methods do not any change at the original template in holder, its immutable.
     *
     * Remember that replacements are specified through something like this :
     * {{"${abc/abc/abd}", "xyz"}, {...}, ...}
     *
     * Inner patterns looks like something this : ${...}
     *
     * @param template The template wants to replace something in.
     * @param replacements Array holds template replacements.
     * @return A new instance of the template replaced all the replacements.
     */
    public static String replace(String template, String[][] replacements) {
        long m = -System.currentTimeMillis();
        String s = template;
        if (s != null) {
            try {
                for (int i = 0; i < replacements.length; i++) {
                    s = s.replace(replacements[i][0], replacements[i][1]);
                }
            } catch (Exception e) {
                log.debug("Templates[" + template
                        + "] either ressource or label not legal setted.");
            }
        } else {
            log.debug("No template given.");
            s = "";
        }
        m += System.currentTimeMillis();
        log.debug("Replacement(no ressources) tooks[" + m + "] ms.");
        return s;
    }

    /* class to represent stored data. */
    class StoreData {

        Object data = null;
        long modified = 0;

        StoreData() {
            super();
        }

        StoreData(Object data, long modified) {
            super();
            this.data = data;
            this.modified = modified;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void setModified(long modified) {
            this.modified = modified;
        }

        public long getModified() {
            return modified;
        }

    }

}