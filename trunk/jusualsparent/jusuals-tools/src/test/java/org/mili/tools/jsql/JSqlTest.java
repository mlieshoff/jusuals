package org.mili.tools.jsql;

import org.junit.*;


public class JSqlTest {

    @Test
    public void shouldStart() throws Exception {
        JSql.main(new String[]{"--profile="});
    }

}
