package org.opendcs.shefit.time;

import java.time.LocalDate;

public class ShefDate {
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
                throw new RuntimeException("Date must be 4,6, or 8 digits. Got '" + dateStr + "'.");
            }
        }
        return LocalDate.of(year,month,day);
    }
}