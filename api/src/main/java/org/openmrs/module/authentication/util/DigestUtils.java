/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.authentication.util;

import java.security.MessageDigest;

public class DigestUtils {

    static final char[] HEX_CHAR_TABLE = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private static byte[] createChecksum(final String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.update(data.getBytes());
        return digest.digest();
    }

    private static char[] getHexString(final byte[] raw) throws Exception {
        int length = raw.length;

        char[] out = new char[length << 1];
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = HEX_CHAR_TABLE[(0xF0 & raw[i]) >>> 4];
            out[j++] = HEX_CHAR_TABLE[0x0F & raw[i]];
        }

        return out;
    }

    public static String generateChecksum(final String data) throws Exception {
        return new String(getHexString(createChecksum(data)));
    }
}