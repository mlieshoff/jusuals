/*
 * GraphicsUtil.java
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

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import org.apache.commons.io.*;
import org.apache.commons.lang.*;

import sun.awt.image.*;

/**
 * This utility class provides some useful methods to operate with graphics.
 *
 * @author Michael Lieshoff
 */
public final class GraphicsUtil {

    /**
     * Reads an image from defined file. All formats specifies in ImageIO.getReaderFormatNames()
     * are supported.
     *
     * @param f file.
     * @return readed image.
     * @throws IOException if io exceptrion occurs.
     */
    public static Image readImage(File f) throws IOException {
        Validate.notNull(f, "file cannot be null!");
        if (!f.exists()) {
            throw new FileNotFoundException(f.getAbsolutePath() + " not exists !");
        }
        return (Image) ImageIO.read(f);
    }

    /**
     * Points an image at coordinates x/y in defined image.
     *
     * @param og original image.
     * @param i image to point.
     * @param x coordinate x >= 0.
     * @param y coordinate y >= 0.
     * @return new image included image to point.
     */
    public static Image pointImage(Image og, Image i, int x, int y) {
        Validate.notNull(og, "original image cannot be null!");
        Validate.notNull(i, "image to point cannot be null!");
        Validate.isTrue(x >= 0, "x can only be >= 0!");
        Validate.isTrue(y >= 0, "y can only be >= 0!");
        BufferedImage bi = new BufferedImage(og.getWidth(null), og.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        og = new ImageIcon(og).getImage();
        i = new ImageIcon(i).getImage();
        Graphics2D g = (Graphics2D) bi.createGraphics();
        g.drawImage(og, 0, 0, null);
        g.drawImage(i, x, y, null);
        g.dispose();
        return bi;
    }

    /**
     * Centers an image at in defined image.
     *
     * @param og original image.
     * @param i image to point.
     * @return new image included image to point.
     */
    public static Image centerImage(Image og, Image i) {
        Validate.notNull(og, "original image cannot be null!");
        Validate.notNull(i, "image to center cannot be null!");
        int x = (og.getWidth(null) - i.getWidth(null)) / 2;
        if (x < 0) {
            x = 0;
        }
        int y = (og.getHeight(null) - i.getHeight(null)) / 2;
        if (y < 0) {
            y = 0;
        }
        return pointImage(og, i, x, y);
    }

    /**
     * Centers an image at relative coordinates x/y in defined image.
     *
     * @param og original image.
     * @param i image to point.
     * @param x coordinate.
     * @param y coordinate.
     * @return new image included image to point.
     */
    public static Image pointCenterImage(Image og, Image i, int x, int y) {
        Validate.notNull(og, "original image cannot be null!");
        Validate.notNull(i, "image to center cannot be null!");
        int x0 = x - (i.getWidth(null) / 2);
        if (x0 < 0) {
            x0 = 0;
        }
        int y0 = y - (i.getHeight(null) / 2);
        if (y0 < 0) {
            y0 = 0;
        }
        return pointImage(og, i, x0, y0);
    }

    /**
     * Writes an image into a file. All formats specifies in ImageIO.getReaderFormatNames()
     * are supported.
     *
     * @param f file.
     * @param i image.
     * @throws IOException if io exception occurs.
     */
    public static void writeImage(File f, Image i) throws IOException {
        Validate.notNull(i, "image cannot be null!");
        Validate.notNull(f, "file cannot be null!");
        String ext = FilenameUtils.getExtension(f.getName());
        Validate.notEmpty(ext, "file extension cannot be empty!");
        if (!f.exists()) {
            f.createNewFile();
        }
        if (i instanceof ToolkitImage) {
            ToolkitImage ti = (ToolkitImage) i;
            i = ti.getBufferedImage();
        }
        ImageIO.write((RenderedImage) i, ext, f);
        return;
    }

    /**
     * Scales an image.
     *
     * @param i image.
     * @param scaleX scale to x >= 0.
     * @param scaleY scale to y >= 0.
     * @return scaled image.
     */
    public static Image scaleImage(Image i, int scaleX, int scaleY) {
        Validate.notNull(i, "image cannot be null!");
        Validate.isTrue(scaleX >= 0, "scaleX cannot be < 0!");
        Validate.isTrue(scaleY >= 0, "scaleY cannot be < 0!");
        if (scaleX == 0 && scaleY == 0) {
            return i;
        }
        double f = getRelationFactor(scaleX, scaleY, i);
        int x = (int) (i.getWidth(null) * f);
        int y = (int) (i.getHeight(null) * f);
        i = i.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(i);
        i = ii.getImage();
        if (i instanceof ToolkitImage) {
            ToolkitImage ti = (ToolkitImage) i;
            i = ti.getBufferedImage();
        }
        return i;
    }

    /**
     * Calculates relation factor for scaling.
     *
     * @param scaleX scale to x.
     * @param scaleY scale to y.
     * @param i image.
     * @return relation factor for scaling.
     */
    public static double getRelationFactor(int scaleX, int scaleY, Image i) {
        double fx = (double) scaleX / i.getWidth(null);
        double fy = (double) scaleY / i.getHeight(null);
        return fx < fy ? fx : fy;
    }

    /**
     * Prints a character image in defined font in a file.
     *
     * @param dir directory.
     * @param fn filename.
     * @param font font.
     * @param c character to print in file.
     * @param mx character size x.
     * @param my character size y.
     * @throws IOException if io exception occurs.
     */
    public static void writeChar(File dir, String fn, Font font, char c, int mx, int my)
            throws IOException {
        BufferedImage bi = new BufferedImage(mx, my, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.createGraphics();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int fh = fm.getHeight();
        int cw = fm.charWidth(c);
        int asc = g.getFontMetrics().getAscent();
        int x0 = mx/2 - cw/2;
        int y0 = my/2 - fh/2 + asc;
        g.drawString(String.valueOf(c), x0, y0);
        g.dispose();
        File f = new File(dir, fn);
        GraphicsUtil.writeImage(f, bi);
    }

    /**
     * Prints character images in defined font into files. Filename must content an "%0"
     * sequence to replace the characters value in.
     *
     * @param dir directory.
     * @param fn filename.
     * @param font font.
     * @param cs characters to print in file.
     * @param mx character size x.
     * @param my character size y.
     * @throws IOException if io exception occurs.
     */
    public static void writeChars(File dir, String fn, Font font, String cs, int mx, int my)
            throws IOException {
        for (int i = 0; i < cs.length(); i++) {
            char c = cs.charAt(i);
            writeChar(dir, fn.replace("%", String.valueOf(c)), font, c, mx, my);
        }
        return;
    }

}
