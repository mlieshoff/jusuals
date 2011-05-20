/*
 * RevisionImpl.java
 *
 * 20.05.2011
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
package org.mili.core.ant;

import java.io.*;

import org.mili.core.io.*;

/**
 * This class defines an implementation of the revision logic.
 *
 * @author Michael Lieshoff
 */
public class RevisionImpl implements Revision {

    @Override
    public String start(String filenameWithoutRevision, String revisionPrefix)
            throws Exception {
        File file = new File(filenameWithoutRevision);
        File revFile = RevisionUtil.getLastRevisionOfFile(file, revisionPrefix);
        if (revFile == null) {
            revFile = RevisionUtil.createNewRevisionFromFile(file, false, revisionPrefix);
        } else {
            revFile = RevisionUtil.createNextRevisionFromFile(file, false, revisionPrefix);
        }
        return revisionPrefix + String.valueOf(RevisionUtil.extractRevisionNumber(revFile,
                revisionPrefix));
    }

}
