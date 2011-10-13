/*
 * Util.java
 *
 * 01.10.2009
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
package org.mili.core.service;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;
import org.mili.core.logging.*;

/**
 * @author Michael Lieshoff
 */
public final class Util {
    private static final Class<?> ID = Util.class;
    private static final DefaultLogger log = DefaultLogger.getLogger(ID);
    /** The Constant CLASS_PARAM. */
    public static final String CLASS_PARAM = Util.class.getName() + ".Class";

    /**
     * Resolve service parameters.
     *
     * @param p the p
     * @return the list
     * @throws ClassNotFoundException the class not found exception
     */
    public static List<Map<String, String>> resolveServiceParameters(String p)
            throws ClassNotFoundException {
        log.beginDebug("resolveServiceParameters");
        List<Map<String, String>> m = new ArrayList<Map<String,String>>();
        if (p == null || p.length() == 0) {
            log.endDebug("resolveServiceParameters");
            return m;
        }
        String s = p.replaceAll("(\\s)*", "");
        String[] cblocks = s.split("[;]");
        if (cblocks == null || cblocks.length == 0) {
            throw new java.lang.IllegalStateException("param not in form : class0;...;classN");
        }
        // class:name0=value0,name1=value1, ..., nameN=valueN;
        for (int i = 0; i < cblocks.length; i++) {
            String[] cls_p_block = cblocks[i].split("[:]");
            if (cls_p_block == null || cls_p_block.length == 0) {
                throw new java.lang.IllegalStateException("param not in form : class0:...");
            }
            String cls = cls_p_block[0];
            Class<?> c = Class.forName(cls);
            Map<String, String> mm = new Hashtable<String, String>();
            if (cls_p_block.length > 1) {
                String p_block = cls_p_block[1];
                String[] ps = p_block.split("[,|]");
                for (int ii = 0; ii < ps.length; ii++) {
                    String[] pv_block = ps[ii].split("[=]");
                    if (pv_block == null || pv_block.length == 0) {
                        throw new java.lang.IllegalStateException("param not in form : name=value");
                    }
                    mm.put(pv_block[0], pv_block[1]);
                }
            }
            mm.put(CLASS_PARAM, c.getName());
            m.add(mm);
        }
        log.endDebug("resolveServiceParameters");
        return m;
    }

    /**
     * Gets the file.
     *
     * @param p the p
     * @param pfn the pfn
     * @return the file
     */
    public static File getFile(Map<String, String> p, String pfn) {
        Validate.notEmpty(p, "p");
        Validate.notEmpty(pfn, "pfn");
        String wd = p.get(Service.P_WORKDIR);
        String fn = p.get(pfn);
        Validate.notEmpty(fn, "fn");
        if (wd != null && wd.length() > 0) {
            fn = wd.concat("/").concat(fn);
        }
        return new File(fn);
    }

    /**
     * Gets the file from filename.
     *
     * @param p the p
     * @param fn the fn
     * @return the file from filename
     */
    public static File getFileFromFilename(Map<String, String> p, String fn) {
        Validate.notEmpty(p, "p");
        Validate.notEmpty(fn, "fn");
        String wd = p.get(Service.P_WORKDIR);
        if (wd != null && wd.length() > 0) {
            fn = wd.concat("/").concat(fn);
        }
        return new File(fn);
    }
}
