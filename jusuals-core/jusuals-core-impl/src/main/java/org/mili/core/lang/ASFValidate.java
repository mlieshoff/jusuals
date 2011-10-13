/*
 * ASFValidateTest.java
 *
 * 21.09.2010
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

package org.mili.core.lang;

import org.apache.commons.lang.*;
import org.apache.commons.lang.math.*;


/**
 * This class extends functionality of class org.apache.commons.Validate.
 *
 * @author Michael Lieshoff
 */
public class ASFValidate extends Validate {

    /**
     * Constructor. This class should not normally be instantiated.
     */
    public ASFValidate() {
        super();
    }

    /**
     * <p>
     * Checks if a number is in range and throws <code>IllegalArgumentException</code>, if
     * number is out of range.
     * </p>
     *
     * <p>The method is used to check numbers in range or not. As example if a port parameter is
     * checked.</p>
     *
     * <pre>
     * ASFValidate.isInRange(13, new IntegerRange(1, 10), "Not in range!");
     * </pre>
     *
     * @param n number.
     * @param r range.
     * @param message message if number is not in range.
     * @throws IllegalArgumentException if number is not in range.
     */
    public static void isInRange(Number n, Range r, String message) {
        Validate.notNull(n, "number can't be null!");
        Validate.notNull(r, "range can't be null!");
        if (message == null) {
            message = "The validated number[" + n + "] is not in range[" + r.getMinimumNumber()
                + ".." + r.getMaximumNumber()+ "]";
        }
        Validate.isTrue(r.containsNumber(n), message);
    }

    /**
     * <p>
     * Checks if a number is in range and throws <code>IllegalArgumentException</code>, if
     * number is out of range.
     * </p>
     *
     * <p>The method is used to check numbers in range or not. As example if a port parameter is
     * checked.</p>
     *
     * <pre>
     * ASFValidate.isInRange(13, new IntegerRange(1, 10));
     * </pre>
     *
     * @param n number.
     * @param r range.
     * @throws IllegalArgumentException if number is not in range.
     */
    public static void isInRange(Number n, Range r) {
        isInRange(n, r, null);
    }

}
