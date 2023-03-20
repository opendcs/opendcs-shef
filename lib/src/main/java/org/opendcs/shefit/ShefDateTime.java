package org.opendcs.shefit;

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
        return ZonedDateTime.of(localDate,localTime,ZoneId.of("UTC"));
    }

    public static class ShefDate {
        private String dateStr;

        public ShefDate(String dateStr) {
            this.dateStr = dateStr;
        }

        public LocalDate getLocalDate() {
            int day = Integer.parseInt(dateStr.substring(dateStr.length()-2));
            String monthStr = dateStr.substring(dateStr.length()-4,dateStr.length()-2);
            int month = Integer.parseInt(monthStr,10);
            int year = LocalDate.now().getYear();
            switch (dateStr.length()) {
                case 8: { // full yyyymmdd
                    year = Integer.parseInt(dateStr.substring(0, 4));
                    break;
                }
                case 6: { // decade only yymmdd
                    year = (year%100) + Integer.parseInt(dateStr.substring(0,2));
                    break;
                }
                case 4: { // month day mmdd
                    break;   
                }
                default: {
                    throw new RuntimeException("Date must be 4,6, or 8 digits.");
                }
            }
            return LocalDate.of(year,month,day);
        }
    }

    public static class ShefTime {
        private boolean isFixed;
        private String  type;
        private String  time;

        public ShefTime(boolean isFixed, String type, String time) {
            this.isFixed = isFixed;
            this.type = type;
            this.time = time;
        }

        public boolean getIsFixed() {
            return isFixed;
        }

        public String getType() {
            return type;
        }

        public String getTimeStr() {
            return time;
        }

        public LocalTime getTime() {
            int hours = Integer.parseInt(time.substring(0, 2),10);
            int minutes = Integer.parseInt(time.substring(2, 4),10);
            int seconds = Integer.parseInt(time.substring(4),10);
            return LocalTime.of(hours,minutes,seconds);
        }
    }
}
