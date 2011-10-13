/*
 * ClassVersionCheckerImpl.java
 *
 * 09.05.2010
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
import java.util.jar.*;
import java.util.regex.*;
import java.util.zip.*;

import org.apache.tools.ant.*;

/**
 * This class is a default implementation of a class version checker. It checks the Java version
 * of classes and classes in jar files of same version.
 *
 * @author Michael Lieshoff
 */
public class ClassVersionCheckerImpl implements ClassVersionChecker {
    private Pattern pattern_jar = Pattern.compile("(.*)\\.(jar)$", Pattern.CASE_INSENSITIVE);
    private Pattern pattern_class = Pattern
            .compile("(.*)\\.(class)$", Pattern.CASE_INSENSITIVE);
    private Pattern pattern_jar_or_class = Pattern.compile("(.*)\\.(jar||class)$",
            Pattern.CASE_INSENSITIVE);
    private File root = null;
    private Type version = null;

    /**
     * Instantiates a new class version checker impl.
     *
     * @param root the root
     * @param version the version
     */
    public ClassVersionCheckerImpl(File root, Type version) {
        super();
        this.root = root;
        this.version = version;
    }

    private List<File> searchJars(File f) {
        List<File> l = new ArrayList<File>();
        File[] fl = f.listFiles();
        for (int i = 0; i < fl.length; i++) {
            File f0 = fl[i];
            if (f0.isDirectory()) {
                l.addAll(this.searchJars(f0));
            } else {
                Matcher m = pattern_jar_or_class.matcher(f0.getAbsolutePath());
                if (m.find()) {
                    l.add(f0);
                }
            }
        }
        return l;
    }

    private Type checkClass(InputStream is, Type version) {
        byte[] ar = new byte[8];
        try {
            is.read(ar);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        int v = ar[6] + ar[7];
        return Type.fromInt(v);
    }

    private Map<String, List<String>> checkJars(List<File> l, Type version) {
        Map<String, List<String>> result = new TreeMap<String, List<String>>();
        for (int i = 0, n = l.size(); i < n; i++) {
            File f = l.get(i);
            if (pattern_class.matcher(f.getAbsolutePath()).matches()) {
                List<String> l0 = result.get(f.getAbsolutePath());
                if (l0 == null) {
                    l0 = new ArrayList<String>();
                }
                FileInputStream is = null;
                try {
                    is = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    throw new IllegalStateException(e);
                }
                //System.out.print("check class ... " + f.getAbsolutePath() + "\n");
                Type t = checkClass(is, version);
                if (t.version > version.version) {
                    l0.add(t.name() + " : " + f.getAbsolutePath());
                    //System.out.println(" Bad version number[" + t.name() + "] ! ");
                } else {
                    //System.out.println(" Ok.");
                }
                if (l0.size() > 0) {
                    result.put(f.getAbsolutePath(), l0);
                }
                try {
                    is.close();
                } catch (IOException e1) {
                    throw new IllegalStateException(e1);
                }
            } else if (pattern_jar.matcher(f.getAbsolutePath()).matches()) {
                JarFile jf = null;
                try {
                    jf = new JarFile(f, false);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                //System.out.println("check jar ... " + f.getAbsolutePath());
                List<String> l0 = result.get(jf.getName());
                if (l0 == null) {
                    l0 = new ArrayList<String>();
                }
                for (Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements();) {
                    JarEntry je = e.nextElement();
                    ZipEntry ze = jf.getEntry(je.getName());
                    if (!je.isDirectory()) {
                        InputStream is = null;
                        try {
                            is = jf.getInputStream(ze);
                        } catch (IOException e1) {
                            throw new IllegalStateException(e1);
                        }
                        Matcher m = pattern_class.matcher(je.getName());
                        if (m.find()) {
                            //System.out.print("    " + je.getName());
                            Type t = checkClass(is, version);
                            if (t.version > version.version) {
                                l0.add(t.name() + " : " + je.getName());
                                //System.out.println(" Bad version number[" + t.name() + "] ! ");
                            } else {
                                //System.out.println(" Ok.");
                            }
                        }
                        try {
                            is.close();
                        } catch (IOException e1) {
                            throw new IllegalStateException(e1);
                        }
                    }
                }
                if (l0.size() > 0) {
                    result.put(jf.getName(), l0);
                }
                try {
                    jf.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return result;
    }

    @Override
    public void execute() throws BuildException {
        if (!this.root.exists()) {
            throw new BuildException("Dir not found : " + this.root);
        } else if (!this.root.isDirectory()) {
            throw new BuildException("Dir not a directory : " + this.root);
        }
        List<File> l = this.searchJars(this.root);
        Map<String, List<String>> m = this.checkJars(l, version);
        //System.out.println("\n\n---\n\n");
        if (m.size() == 0) {
            System.out.println("All founded jars/classes are OK !");
        }
        for (Iterator<String> i = m.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            List<String> l0 = m.get(k);
            System.out.println(k + " - " + l0.size() + " bad class files.");
            for (int j = 0, n = l0.size(); j < n; j++) {
                String s = l0.get(j);
                System.out.println("    " + s);
            }
        }
        return;
    }

    enum Type {
        J2SE_60(50), J2SE_50(49), JDK_14(48), JDK_13(47), JDK_12(46), JDK_11(45), UNKNOWN(-1);
        int version = 0;

        Type(int v) {
            version = v;
        }

        static Type fromInt(int i) {
            if (i == 50) {
                return J2SE_60;
            } else if (i == 49) {
                return J2SE_50;
            } else if (i == 48) {
                return JDK_14;
            } else if (i == 47) {
                return JDK_13;
            } else if (i == 46) {
                return JDK_12;
            } else if (i == 45) {
                return JDK_11;
            }
            return UNKNOWN;
        }
    }

}
