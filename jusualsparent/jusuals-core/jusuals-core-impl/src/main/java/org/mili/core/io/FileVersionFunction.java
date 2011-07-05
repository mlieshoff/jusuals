/*
 * FileVersionFunction.java
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
import java.util.regex.*;

import org.apache.commons.functor.*;
import org.mili.core.regex.*;

/**
 * This class is a implentation of an {@link UnaryFunction} and is used to detect file versions
 * in a filename.
 *
 * @author Michael Lieshoff
 */
public class FileVersionFunction implements UnaryFunction<File, FileInfo> {

    /**
     * Instantiates a new file version function.
     */
    public FileVersionFunction() {
        super();
    }

    @Override
    public FileInfo evaluate(File f) {
        Matcher m = RegExLib.VersionNumberOfFiles.getMatcher(f.getName());
        FileInfo fi = new FileInfo();
        fi.setFile(f);
        if (m.find()) {
            String name = m.group(1);
            String version = m.group(2);
            String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1,
                    f.getName().length());
            if (version.endsWith(".")) {
                version = version.substring(0, version.length() - 1);
            }
            fi.setName(name);
            fi.setExtension(ext);
            fi.setVersion(version);
            fi.setHasVersion(true);
        }
        return fi;
    }

}
