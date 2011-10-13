/*
 * UtilTest.java
 *
 * 29.09.2009
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

import org.apache.commons.io.*;
import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Michael Lieshoff
 */
public class UtilTest {

    @Test
    public void shouldResolve2ClassesWith2Params() throws Exception {
        String p = "java.lang.Object:filename=test,namespace=test;java.lang.String:model=test,method=test;";
        List<Map<String, String>> m = Util.resolveServiceParameters(p);
        assertNotNull("m is null !", m);
        assertEquals("m size is not 2", 2, m.size());
        Map<String, String> m0 = m.get(0);
        Map<String, String> m1 = m.get(1);
        assertTrue("dont contains Object.class !", m0.get(Util.CLASS_PARAM).equals(Object.class.getName()));
        assertTrue("dont contains String.class !", m1.get(Util.CLASS_PARAM).equals(String.class.getName()));
        assertNotNull("m0 is null !", m0);
        assertEquals("m0 size is not 2", 2, m0.size() - 1);
        assertTrue("dont contains parameter filename !", m0.containsKey("filename"));
        assertEquals("parameter filename is not test !", "test", m0.get("filename"));
        assertTrue("dont contains parameter namespace !", m0.containsKey("namespace"));
        assertEquals("parameter namespace is not test !", "test", m0.get("namespace"));
        assertNotNull("m1 is null !", m1);
        assertEquals("m1 size is not 2", 2, m1.size() - 1);
        assertTrue("dont contains parameter model !", m1.containsKey("model"));
        assertEquals("parameter model is not test !", "test", m1.get("model"));
        assertTrue("dont contains parameter method !", m1.containsKey("method"));
        assertEquals("parameter method is not test !", "test", m1.get("method"));
    }

    @Test
    public void shouldResolve2ClassesWith1Params() throws Exception {
        String p = "java.lang.Object:filename=test;java.lang.String:model=test;";
        List<Map<String, String>> m = Util.resolveServiceParameters(p);
        assertNotNull("m is null !", m);
        assertEquals("m size is not 2", 2, m.size());
        Map<String, String> m0 = m.get(0);
        Map<String, String> m1 = m.get(1);
        assertTrue("dont contains Object.class !", m0.get(Util.CLASS_PARAM).equals(Object.class.getName()));
        assertTrue("dont contains String.class !", m1.get(Util.CLASS_PARAM).equals(String.class.getName()));
        assertNotNull("m0 is null !", m0);
        assertEquals("m0 size is not 2", 1, m0.size() - 1);
        assertTrue("dont contains parameter filename !", m0.containsKey("filename"));
        assertEquals("parameter filename is not test !", "test", m0.get("filename"));
        assertNotNull("m1 is null !", m1);
        assertEquals("m1 size is not 2", 1, m1.size() - 1);
        assertTrue("dont contains parameter model !", m1.containsKey("model"));
        assertEquals("parameter model is not test !", "test", m1.get("model"));
    }

    @Test
    public void shouldResolve2ClassesWith0Params() throws Exception {
        String p = "java.lang.Object;java.lang.String;";
        List<Map<String, String>> m = Util.resolveServiceParameters(p);
        assertNotNull("m is null !", m);
        assertEquals("m size is not 2", 2, m.size());
        Map<String, String> m0 = m.get(0);
        Map<String, String> m1 = m.get(1);
        assertTrue("dont contains Object.class !", m0.get(Util.CLASS_PARAM).equals(Object.class.getName()));
        assertTrue("dont contains String.class !", m1.get(Util.CLASS_PARAM).equals(String.class.getName()));
        assertNotNull("m0 is null !", m0);
        assertNotNull("m1 is null !", m1);
    }

    @Test
    public void shouldResolve1ClassWith0Params() throws Exception {
        String p = "java.lang.Object;";
        List<Map<String, String>> m = Util.resolveServiceParameters(p);
        assertNotNull("m is null !", m);
        assertEquals("m size is not 1", 1, m.size());
        Map<String, String> m0 = m.get(0);
        assertTrue("dont contains Object.class !", m0.get(Util.CLASS_PARAM).equals(Object.class.getName()));
        assertNotNull("m0 is null !", m0);
    }

    @Test
    public void shouldResolveEmpty() throws Exception {
        assertNotNull("m is null !", Util.resolveServiceParameters(""));
    }

    @Test
    public void shouldResolveNull() throws Exception {
        assertNotNull("m is null !", Util.resolveServiceParameters(null));
    }

    @Test
    public void testGetFile_MapString() throws Exception {
        Map<String, String> p = new Hashtable<String, String>();
        p.put("File", "./tmp/test.txt");
        // negativ
        try {
            Util.getFile(null, null);
            fail("Exception awaited !");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Util.getFile(p, null);
            fail("Exception awaited !");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Util.getFile(p, "");
            fail("Exception awaited !");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
        // normal
        try {
            FileUtils.writeStringToFile(new File("./tmp/test.txt"), "lala");
            File f = Util.getFile(p, "File");
            assertTrue(f.exists());
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
        // with work dir
        try {
            FileUtils.writeStringToFile(new File("./tmp/test.txt"), "lala");
            p.put(Service.P_WORKDIR, "./tmp");
            p.put("File", "./test.txt");
            File f = Util.getFile(p, "File");
            assertTrue(f.exists());
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    @Test
    public void test_getFileFromFilename_Map_String() {
        // negativ
        try {
            Util.getFileFromFilename(null, "aaa");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Util.getFileFromFilename(new Hashtable<String, String>(), null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        // positiv
        Map<String, String> m = new Hashtable<String, String>();
        m.put(Service.P_WORKDIR, "aaa");
        File f = Util.getFileFromFilename(m, "bbb");
    }

}
