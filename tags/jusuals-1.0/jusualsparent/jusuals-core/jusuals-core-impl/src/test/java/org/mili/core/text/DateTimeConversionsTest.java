package org.mili.core.text;

import org.junit.*;

import static org.junit.Assert.*;


public class DateTimeConversionsTest {

    @Test
    public void shouldTest() {
        for(DateTimeConversions dateTimeConversion : DateTimeConversions.values()) {
            assertEquals(dateTimeConversion, DateTimeConversions.valueOf(dateTimeConversion
                    .name()));
            assertTrue(dateTimeConversion.getToken().length() > 0);
        }
    }

}
