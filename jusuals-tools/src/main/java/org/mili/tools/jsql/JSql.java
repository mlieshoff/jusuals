/*
 * JSql.java
 *
 * 01.07.2010
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

package org.mili.tools.jsql;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.cli.*;
import org.mili.core.database.*;
import org.mili.core.properties.*;
import org.mili.core.text.*;

/**
 * @author Michael Lieshoff
 */
public class JSql {
    private String profile = null;
    private Connection connection = null;
    private String user = null;
    /**
     *
     */
    protected JSql(String profile) {
        super();
        this.profile = profile;
    }

    public void start() throws IOException, SQLException {
        loadSystemProperties(new File("./" + profile + "-profile.properties"));
        String host = PropUtil.getSystemRequired(JSql.class, "host");
        String user = PropUtil.getSystemRequired(JSql.class, "user");
        connection = DriverManager.getConnection(host, user, PropUtil.getSystemRequired(JSql.class,
                "password"));
        System.out.println("> connected to: " + host);
        console();
    }

    public void stop() throws SQLException {
        connection.close();
        System.exit(0);
    }

    private boolean process(String s) throws SQLException {
        if (s.equals("q") || s.equals("quit")) {
            return true;
        }
        String s0 = s.toLowerCase();
        if (s0.startsWith("update") || s0.startsWith("insert") || s0.startsWith("delete")) {
            int i = update(s);
            System.out.println("> Effected rows: " + i);
        } else {
            ResultSet rs = query(s);
            List<List<Object>> l = DumpUtil.dumpResultSet(rs, "a", null);
            Table t = DumpUtil.tableToTextTable(l);
            System.out.println(t.toString());
            if (l.size() == 2) {
                System.out.println("> No rows found!");
            } else {
                System.out.println("> " + (l.size() - 2) + " rows found.");
            }
        }
        return false;
    }

    private int update(String query) throws SQLException {
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(query);
        statement.close();
        return result;
    }

    private ResultSet query(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        statement.close();
        return resultSet;
    }

    private void console() {
        boolean quit = false;
        while (!quit) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print(user + "> ");
                if (process(br.readLine())) {
                    stop();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void loadSystemProperties(File f) throws IOException {
        InputStream is = null;
        Properties p = new Properties();
        is = new FileInputStream(f);
        p.load(is);
        System.getProperties().putAll(p);
        is.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("help", false, "print this message.");
        options.addOption("version", false, "print version.");
        options.addOption("profile", true, "the database profile.");
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(JSql.class.getName(), options);
            return;
        } else if (cmd.hasOption("version")) {
            System.out.println("1.0");
            return;
        }
        String profile = "";
        if (cmd.hasOption("profile")) {
            profile = cmd.getOptionValue("profile");
        } else {
            throw new IllegalArgumentException("No profile given !");
        }
        JSql sql = new JSql(profile);
        try {
            sql.start();
        } catch (Exception e) {
            System.out.println(new File("").getAbsolutePath());
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                sql.stop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
