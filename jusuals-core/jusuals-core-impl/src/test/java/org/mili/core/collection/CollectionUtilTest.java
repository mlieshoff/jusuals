/*
 * CollectionUtilTest.java
 *
 * 15.09.2009
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

package org.mili.core.collection;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Michael Lieshoff
 */
public class CollectionUtilTest {

    @Test
    public void testConstruct() {
        new CollectionUtil();
    }

    @Test
    public void test() {
        List<Integer> cli = new ArrayList<Integer>();
        cli.add(1);
        cli.add(2);
        cli.add(3);
        cli.add(4);
        String s = CollectionUtil.collectionToString(new ArrayList<Integer>(), "[%1]");
        assertEquals("", s);
        s = CollectionUtil.collectionToString(null, "[%1]");
        assertEquals("", s);
        s = CollectionUtil.collectionToString(cli);
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("1234") == 0);
        s = CollectionUtil.collectionToString(cli, "[%1]");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1234]") == 0);
        s = CollectionUtil.collectionToString(cli, "[%1]", "%1");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1234]") == 0);
        s = CollectionUtil.collectionToString(cli, "[%1]", "%1", ", ");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1, 2, 3, 4]") == 0);
        s = CollectionUtil.collectionToString(cli, "[%1]", "%1", ", ", " = ");
        assertTrue(s != null && s.length() > 0);
        assertTrue(s.compareTo("[1 = 2, 3 = 4]") == 0);
        // collection of collection
        List<Integer> cli1 = new ArrayList<Integer>();
        cli1.add(1);
        cli1.add(2);
        cli1.add(3);
        cli1.add(4);
        List<Integer> cli2 = new ArrayList<Integer>();
        cli2.add(5);
        cli2.add(6);
        cli2.add(7);
        cli2.add(8);
        List<List<Integer>> cli3 = new ArrayList<List<Integer>>();
        cli3.add(cli1);
        cli3.add(cli2);

        s = CollectionUtil.collectionOfCollectionToString(new ArrayList<List<Integer>>(), "[%1]");
        assertEquals("", s);
        s = CollectionUtil.collectionOfCollectionToString(null, "[%1]");
        assertEquals("", s);

        s = CollectionUtil.collectionOfCollectionToString(cli3);
        assertTrue(s.compareTo("12345678") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]");
        assertTrue(s.compareTo("[12345678]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "[%1]");
        assertTrue(s.compareTo("[[1234][5678]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "", "[%1]");
        assertTrue(s.compareTo("[[1234][5678]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "[%1]", "[%1]");
        assertTrue(s.compareTo("[[[1234]][[5678]]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "", "[%1]", "%1");
        assertTrue(s.compareTo("[[1234][5678]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "[%1]", "[%1]", "[%1]");
        assertTrue(s.compareTo("[[[[1][2][3][4]]][[[5][6][7][8]]]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "", "[%1]", "%1", ", ");
        assertTrue(s.compareTo("[[1234], [5678]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "[%1]", "[%1]", "[%1]",
                ", ");
        assertTrue(s.compareTo("[[[[1][2][3][4]]], [[[5][6][7][8]]]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "", "[%1]", "%1", ", ",
                ", ");
        assertTrue(s.compareTo("[[1, 2, 3, 4], [5, 6, 7, 8]]") == 0);
        s = CollectionUtil.collectionOfCollectionToString(cli3, "[%1]", "[%1]", "[%1]", "[%1]",
                ", ", ", ");
        assertTrue(s.compareTo("[[[[1], [2], [3], [4]]], [[[5], [6], [7], [8]]]]") == 0);
        return;
    }

}
