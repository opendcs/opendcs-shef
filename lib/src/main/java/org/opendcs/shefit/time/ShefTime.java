package org.opendcs.shefit.time;

import java.time.LocalTime;

public class ShefTime {
    private LocalTime time;

    private ShefTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

/**
 * 
EXPANSION TABLE EXPANSION TABLE
DSss DRS±ss
DNnnss DRN±nn <--- hour should be 2400, need to return a full date/time here
DHhhnnss DRH±hh
DDddhhnnss DRD±dd
DMmmddhhnnss DRM±mm
DYyymmddhhnnss DRY±yy
DTccyymmddhhnnss DRE±xx
DJyyddd
DJccyyddd
 * @param timeStr
 * @return
 */
    public static ShefTime from(String timeStr) {
        LocalTime ret = LocalTime.now();
        if (timeStr.startsWith("DS")) {
            ret = LocalTime.of(0, 0, Integer.parseInt(timeStr.substring(2)));
        } else if (timeStr.startsWith("DN")) {
            ret = LocalTime.of(0,
                               Integer.parseInt(timeStr.substring(2,4)),
                               timeStr.length() >=6 ? Integer.parseInt(timeStr.substring(4)) : 0
                               );
        } else if (timeStr.startsWith("DH")) {
            ret = LocalTime.of(Integer.parseInt(timeStr.substring(2,4)),
                               timeStr.length() >=6 ? Integer.parseInt(timeStr.substring(4,6)) : 0,
                               timeStr.length() >=8 ? Integer.parseInt(timeStr.substring(6)) : 0
                               );
        }
        return new ShefTime(ret);
    }
}