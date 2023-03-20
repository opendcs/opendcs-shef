package org.opendcs.shefit;

import java.time.ZonedDateTime;

public class ShefRecord {
    private String station;
    private ZonedDateTime observationTime;
    private ZonedDateTime creationTime;

    private ShefRecord(String station, ZonedDateTime observationTime, ZonedDateTime creationTime) {
        this.station = station;
        this.observationTime = observationTime;
        this.creationTime = creationTime;
    }

    public ZonedDateTime getObservationTime() {
        return observationTime;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public String getStation() {
        return station;
    }

    public static class Builder {
        String station;
        ZonedDateTime observationTime;
        ZonedDateTime creationTime = ZonedDateTime.now();

        public Builder(String station) {
            this.station = station;
        }

        public Builder withObservationTime(ZonedDateTime observationTime) {
            this.observationTime = observationTime;
            return this;
        }

        public ShefRecord build() {            
            ShefRecord v = new ShefRecord(station,observationTime,creationTime);
            return v;
        }

        public Builder withValue(Double v) {
            return this;
        }
    }
}
