package de.flightright.task.web;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author Andrei Kukharau
 * @since 1.0
 */
record UserWithSource(String email, String phone, String source) {

    public boolean isValid() {
        return isNotEmpty(this.email) && isNotEmpty(this.phone) && isNotEmpty(this.source);
    }
}
