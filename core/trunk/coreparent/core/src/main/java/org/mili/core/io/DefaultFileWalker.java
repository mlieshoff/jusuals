/*
 * DefaultFileWalker.java
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

package org.mili.core.io;

import java.io.*;
import java.util.*;

import org.apache.commons.functor.*;
import org.apache.commons.io.*;
import org.apache.commons.lang.*;

// TODO: Auto-generated Javadoc
/**
 * This class is a default implementation of interface {@link core.io.FileWalker}.
 *
 * @author Michael Lieshoff
 */
public class DefaultFileWalker implements FileWalker {

    /** The root. */
    private File root = null;

    /**
     * Instantiates a new default file walker.
     *
     * @param root the root
     */
    protected DefaultFileWalker(File root) {
        super();
        Validate.notNull(root, "root dir");
        this.root = root;
    }

    /**
     * Creates a new file walker.
     *
     * @param root the root
     * @return the default file walker
     */
    public static DefaultFileWalker create(File root) {
        return new DefaultFileWalker(root);
    }

    @Override
    public void walk(UnaryFunction<File, Void> f) {
        Validate.notNull(f, "walk function");
        Iterator<?> fs = FileUtils.iterateFiles(this.root, new String[]{"jar"}, true);
        while (fs.hasNext()) {
            File f0 = (File) fs.next();
            if (f0.isFile()) {
                f.evaluate(f0);
            }
        }
    }

}
