/*
 * PropertiesReplacerCLI.java
 *
 * 02.11.2009
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

import javax.xml.bind.*;

import org.apache.commons.cli.*;
import org.mili.core.logging.*;


/**
 * CLI starter for properties replacer.
 *
 * @author Michael Lieshoff
 *
 */
public class PropertiesReplacerCLI {
    private final static DefaultLogger log = DefaultLogger.getLogger(
            PropertiesReplacerCLI.class);

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        PropertiesReplacerCLI o = new PropertiesReplacerCLI();
        PropertiesReplacer pr = new PropertiesReplacerImpl();
        try {
            o.execute(pr, args);
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
            //System.exit(1);
        }
    }

    /**
     * @param args
     * @throws IOException, JAXBException
     */
    public void execute(PropertiesReplacer pr, String[] args) throws IOException, JAXBException,
            ParseException {
        log.info("started.");
        Options options = new Options();
        options.addOption("help", false, "print this message.");
        options.addOption("version", false, "print version.");
        options.addOption("propfile", true, "properties file.");
        options.addOption("propdesc", true, "xml properties description.");
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw e;
        }
        if (cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(PropertiesReplacerCLI.class.getName(), options);
            return;
        } else if (cmd.hasOption("version")) {
            System.out.println("1.0");
            log.info("1.0");
            return;
        }
        String propFileN = "";
        if (cmd.hasOption("propfile")) {
            propFileN = cmd.getOptionValue("propfile");
        } else {
            throw new IllegalArgumentException("No properties file given !");
        }
        String propDescFileN = "";
        if (cmd.hasOption("propdesc")) {
            propDescFileN = cmd.getOptionValue("propdesc");
        } else {
            throw new IllegalArgumentException("No properties description given !");
        }
        try {
            pr.replace(new File(propFileN), new File(propDescFileN));
        } catch (IOException e) {
            throw e;
        } catch (JAXBException e) {
            throw e;
        }
        log.info("stopped.");
        return;
    }
}
