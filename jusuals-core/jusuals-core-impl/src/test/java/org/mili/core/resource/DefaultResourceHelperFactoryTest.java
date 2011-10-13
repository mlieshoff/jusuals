/*
 * DefaultResourceHelperFactoryTest.java
 *
 * 05.03.2010
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
package org.mili.core.resource;

import java.util.*;

import org.junit.*;


/**
 * @author Michael Lieshoff
 *
 */
public class DefaultResourceHelperFactoryTest {

    @Test
    public void test_createBasename_Class() {
        DefaultResourceManagerFactory drhf = DefaultResourceManagerFactory.getInstance();
        Assert.assertNotNull(drhf);
        // negativ: null
        try {
            drhf.createBasename(null);
            Assert.fail("exception expected !");
        } catch (IllegalArgumentException e) {
        }
        // negativ: default package
        try {
            drhf.createBasename("");
            Assert.fail("exception expected !");
        } catch (IllegalArgumentException e) {
        }
        // positiv: one package element
        Assert.assertEquals("java", drhf.createBasename("java"));
        // positiv: two package elements
        Assert.assertEquals("java-lang", drhf.createBasename("java.lang"));
        // positiv: three or more package elements
        Assert.assertEquals("java-lang", drhf.createBasename("java.lang.core"));
        Assert.assertEquals("java-lang", drhf.createBasename("java.lang.core.test.xyz"));
    }

    @Test
    public void test_create_Class_Locale() {
        DefaultResourceManagerFactory drhf = DefaultResourceManagerFactory.getInstance();
        Assert.assertNotNull(drhf);
        // negativ: null
        try {
            drhf.create(null, null);
            Assert.fail("exception expected !");
        } catch (IllegalArgumentException e) {
        }
        try {
            drhf.create(null, Locale.GERMAN);
            Assert.fail("exception expected !");
        } catch (IllegalArgumentException e) {
        }
        try {
            drhf.create(String.class, null);
            Assert.fail("exception expected !");
        } catch (IllegalArgumentException e) {
        }
    }

}
