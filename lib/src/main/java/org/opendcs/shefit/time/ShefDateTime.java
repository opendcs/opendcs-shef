package org.opendcs.shefit.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ShefDateTime {
    private ShefDate date;
    private ShefTime time;
    private String timeZone;

    public ShefDateTime(ShefDate date, String timeZone, ShefTime time ) {
        this.date = date;
        this.timeZone = timeZone;
        this.time = time;
    }

    public ZonedDateTime getZonedDateTime() {
        LocalDate localDate = date.getLocalDate();
        LocalTime localTime = time.getTime();
        return ZonedDateTime.of(localDate,localTime,ZoneId.of(timeZone));
    }
}
