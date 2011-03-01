/*
 * FolderUtils.java
 *
 * 01.03.2011
 *
 * Copyright 2011 Michael Lieshoff
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

package org.mili.test;

import java.io.*;

/**
 * This utility class provides some useful methods for queries.
 * 
 * @author Michael Lieshoff
 *
 */
public class FolderUtils {
    /** The Constant TMP_FOLDER. */
    static final String TMP_FOLDER = "./tmp";
    /** The Constant TMP_DIR. */
    static final File TMP_DIR = new File(TMP_FOLDER);
    /** The Constant DB_FOLDER. */
    static final String DB_FOLDER = TMP_FOLDER + "/hsql";
    /** The Constant DB_DIR. */
    static final File DB_DIR = new File(DB_FOLDER);

    /**
     * Creates a new folder util.
     */
    public FolderUtils() {
        super();
    }

    /**
     * Gets the tmp folder.
     *
     * @param cls the class based for tmp folder creation
     * @return the tmp folder
     * @throws Exception if errors occurs
     */
    static File getTmpFolder(Class<?> cls) {
        return new File(TMP_DIR, cls.getName().replace(".", "/"));
    }

}
