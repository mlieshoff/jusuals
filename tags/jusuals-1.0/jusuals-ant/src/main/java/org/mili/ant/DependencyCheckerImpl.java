/*
 * DependencyCheckerImpl.java
 *
 * 10.05.2010
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
import java.text.*;
import java.util.*;

import org.apache.commons.functor.*;
import org.mili.core.io.*;

/**
 * This class is a default implementation of a dependency checker. It checks same named files on
 * their version number and/or file sizes and will handle a pattern for ignore some directories.
 *
 * @author Michael Lieshoff
 */
public class DependencyCheckerImpl implements DependencyChecker {
    /* name -> version -> list of filenames */
    private Map<String, Map<String, List<File>>> model = new TreeMap<String,
            Map<String, List<File>>>();
    private final FileVersionFunction fvf = new FileVersionFunction();
    private File root = null;
    private String patternToIgnore = null;

    /**
     * Instantiates a new dependency checker impl.
     *
     * @param root the root
     * @param patternToIgnore the pattern to ignore
     */
    protected DependencyCheckerImpl(File root, String patternToIgnore) {
        super();
        this.root = root;
        this.patternToIgnore = patternToIgnore;
    }

    @Override
    public void execute() {
        int z = 0;
        final String t0 = "WARNING: MULTIPLE VERSIONS OF {0} DETECTED:";
        final String t1 = "WARNING: DIFFERENT FILE SIZE DETECTED! ({0} != {1}) --> {3} != {2}";
        final String t2 = "WARNING: DIFFERENT FILE TIME DETECTED! ({0} != {1}) --> {2}";
        FileWalker fw = DefaultFileWalker.create(root);
        fw.walk(new DCFunction());
        List<String> problems = new ArrayList<String>();
        for (Iterator<String> i = model.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            Map<String, List<File>> vm = model.get(k);
            if (vm.size() > 1) {
                z ++;
                problems.add(MessageFormat.format(t0, k));
                this.addToProblems(problems, vm);
            }
            // System.out.println(k);
            for (Iterator<String> ii = vm.keySet().iterator(); ii.hasNext();) {
                String kk = ii.next();
                List<File> l = vm.get(kk);
                // System.out.println("    - " + kk);
                long size = 0;
                long mt = 0;
                File last = null;
                for (int iii = 0, nnn = l.size(); iii < nnn; iii++) {
                    File f = l.get(iii);
                    if (iii == 0) {
                        size = f.length();
                        mt = f.lastModified();
                        last = f;
                    } else if (size != f.length()) {
                        z ++;
                        problems.add(MessageFormat.format(t1, size, f.length(), f
                                .getAbsolutePath(), last.getAbsolutePath()));
                    } else if (mt != f.lastModified()) {
                        // problems.add(MessageFormat.format(t2, mt, f.lastModified(),
                        // f.getAbsolutePath()));
                    }
                    // System.out.println("        * " + f.getAbsolutePath());
                }
            }
        }
        if (problems.size() > 0) {
            System.out.println("ERROR: Problems in " + root.getAbsolutePath() + " found!\n");
            for (int i = 0, n = problems.size(); i < n; i++) {
                String s = problems.get(i);
                System.out.println(s);
            }
            System.out.println("\nFound " + z + " problems!");
        } else {
            System.out.println("No problems in " + root.getAbsolutePath() + " found.");
        }
    }

    private void addToProblems(List<String> l0, Map<String, List<File>> m) {
        final String t0 = "    * {0} {1}";
        for (Iterator<String> ii = m.keySet().iterator(); ii.hasNext();) {
            String kk = ii.next();
            List<File> l = m.get(kk);
            for (int iii = 0, nnn = l.size(); iii < nnn; iii++) {
                File f = l.get(iii);
                l0.add(MessageFormat.format(t0, kk, f.getAbsolutePath()));
            }
        }
    }

    private void update(FileInfo fi) {
        if ((this.patternToIgnore != null && fi.getFile().getAbsolutePath().matches(
                this.patternToIgnore)) || !fi.isHasVersion()) {
            return;
        }
        // get versions
        Map<String, List<File>> vs = this.model.get(fi.getName());
        if (vs == null) {
            vs = new Hashtable<String, List<File>>();
        }
        // get version
        List<File> l = vs.get(fi.getVersion());
        if (l == null) {
            l = new ArrayList<File>();
        }
        l.add(fi.getFile());
        vs.put(fi.getVersion(), l);
        // put together
        this.model.put(fi.getName(), vs);
    }

    class DCFunction implements UnaryProcedure<File> {
        @Override
        public void run(File f) {
            FileInfo fi = fvf.evaluate(f);
            update(fi);
        }
    }

}