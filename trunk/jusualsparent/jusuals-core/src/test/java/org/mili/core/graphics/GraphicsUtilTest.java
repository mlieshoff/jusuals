/*
 * GraphicsUtilTest.java
 *
 * 27.11.2009
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

package org.mili.core.graphics;

import static org.junit.Assert.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import org.apache.commons.io.*;
import org.junit.*;

/**
 * @author Michael Lieshoff
 */
public class GraphicsUtilTest {
    private File dir = new File("./tmp/GraphicsUtilTest");

    @Before
    public void init() throws Exception {
        FileUtils.deleteDirectory(this.dir);
        this.dir.mkdirs();
        // original image
        BufferedImage outImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = outImg.getGraphics();
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 50, 50);
        g.dispose();
        ImageIO.write(outImg, "jpeg", new File(this.dir, "test.jpg"));
        // original image 2
        outImg = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        g = outImg.getGraphics();
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 200, 200);
        g.dispose();
        ImageIO.write(outImg, "jpeg", new File(this.dir, "test_big.jpg"));
        // image to set
        outImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        g = outImg.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 20, 20);
        g.dispose();
        ImageIO.write(outImg, "jpeg", new File(this.dir, "block.jpg"));
}

    @Test
    public void test_Image_readImage_File() {
        // negativ
        try {
            GraphicsUtil.readImage(null);
            fail("here was null given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (IOException e) {
            fail();
        }
        try {
            GraphicsUtil.readImage(new File(this.dir, "/xyz/test.jpg"));
            fail("file not exists here, but method works !");
        } catch (IOException e) {
            assertTrue(true);
        }
        // positiv
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            assertTrue(i != null);
            assertEquals(100, i.getHeight(null));
            assertEquals(100, i.getWidth(null));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    @Test
    public void Graphics_pointImage_Graphics_Image_int_int() {
        // negativ
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.pointImage(o, p, -1, 10);
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.pointImage(o, p, 10, -1);
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalArgumentException e) {
        }
        // positiv
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.pointImage(o, p, 50, 50);
            assertNotNull(g);
            GraphicsUtil.writeImage(new File(this.dir, "test_2.jpg"), g);
            Image o0 = GraphicsUtil.readImage(new File(this.dir, "test_2.jpg"));
            assertNotNull(o0);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    @Test
    public void Graphics_centerImage_Graphics_Image() {
        // positiv
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.centerImage(o, p);
            assertNotNull(g);
            GraphicsUtil.writeImage(new File(this.dir, "test_3.jpg"), g);
            Image o0 = GraphicsUtil.readImage(new File(this.dir, "test_3.jpg"));
            assertNotNull(o0);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    @Test
    public void Graphics_pointCenterImage_Graphics_Image_int_int() {
        // negativ
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.pointCenterImage(o, p, -1, 30);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.pointCenterImage(o, p, 30, -1);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        // positiv
        try {
            Image o = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            Image g = GraphicsUtil.pointCenterImage(o, p, 30, 30);
            assertNotNull(g);
            GraphicsUtil.writeImage(new File(this.dir, "test_4.jpg"), g);
            Image o0 = GraphicsUtil.readImage(new File(this.dir, "test_4.jpg"));
            assertNotNull(o0);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    @Test
    public void test_void_writeImage_File_Graphics() {
        // negativ
        try {
            GraphicsUtil.writeImage(null, null);
            fail("here was null given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(true);
        }
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            GraphicsUtil.writeImage(new File(this.dir, "/xyz/test.jpg"), i);
            fail("here was directory not exists, but method works !");
        } catch (IOException e) {
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GraphicsUtil.writeImage(new File(this.dir, "test.jpg"), null);
            fail("here null given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(true);
        }
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            GraphicsUtil.writeImage(null, i);
            fail("here null given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(true);
        }
        // positiv
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            GraphicsUtil.writeImage(new File(this.dir, "test_2.jpg"), i);
            assertTrue(true);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return;
    }

    @Test
    @Ignore
    public void test_Image_scaleImage_Image_int_int() {
        // negativ
        try {
            GraphicsUtil.scaleImage(null, 0, 0);
            fail("here was null given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            GraphicsUtil.scaleImage(i, -1, 10);
            fail("here was < 0 given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            GraphicsUtil.scaleImage(i, 10, -1);
            fail("here was < 0 given, but method works !");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        // positiv keine skalierung
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test.jpg"));
            int x = i.getWidth(null);
            int y = i.getHeight(null);
            i = GraphicsUtil.scaleImage(i, 0, 0);
            assertEquals(x, i.getWidth(null));
            assertEquals(y, i.getHeight(null));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        // positiv skalierung
        try {
            Image i = GraphicsUtil.readImage(new File(this.dir, "test_big.jpg"));
            Image p = GraphicsUtil.readImage(new File(this.dir, "block.jpg"));
            double fx = GraphicsUtil.getRelationFactor(i.getWidth(null), i.getHeight(null), p);
            p = GraphicsUtil.scaleImage(p, (int) fx, (int) fx);
            i = GraphicsUtil.pointCenterImage(i, p, 50, 50);
            i = GraphicsUtil.scaleImage(i, 150, 150);
            GraphicsUtil.writeImage(new File(this.dir, "test_scaled.jpg"), i);
            assertEquals(150, i.getWidth(null));
            assertEquals(150, i.getHeight(null));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void void_writeChar_File_char_int_int() {
        try {
            Font f = new Font("Lucida Sans Bold", Font.BOLD, 12);
            GraphicsUtil.writeChars(this.dir, "%.jpg", f, "0123456789", 100, 100);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return;
    }


}
