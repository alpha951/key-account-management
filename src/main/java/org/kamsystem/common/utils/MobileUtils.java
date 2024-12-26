package org.kamsystem.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.kamsystem.common.exception.InvalidMobileException;

public class MobileUtils {

    private static final String ERROR_MSG_INVALID_MOBILE_NUMBER = "Invalid Mobile Number";

    /**
     * Validate and sanitize mobile number
     *
     * @param mobile
     * @return sanitized mobile number
     */
    public static String sanitizeMobile(String mobile) {
        // allowed String: "(+91)7232 874-761"
        if (StringUtils.isBlank(mobile) || !mobile.matches("^[0-9\\s(+)-]+")) {
            throw new InvalidMobileException(ERROR_MSG_INVALID_MOBILE_NUMBER);
        }

        // clean all special characters
        mobile = mobile.replaceAll("[^0-9]", "");

        // remove 0 prefix
        mobile = StringUtils.stripStart(mobile, "0");

        if (StringUtils.isBlank(mobile) || (mobile.length() != 10 && mobile.length() != 12)) {
            throw new InvalidMobileException(ERROR_MSG_INVALID_MOBILE_NUMBER);
        }

        // remove country code
        if (mobile.length() == 12) {
            if (!mobile.startsWith("91")) {
                throw new InvalidMobileException(ERROR_MSG_INVALID_MOBILE_NUMBER);
            } else {
                mobile = mobile.substring(2);
            }
        }

        return mobile;
    }
}
