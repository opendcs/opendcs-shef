package org.opendcs.shefit;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ShefRecord {
    private String station;
    private ZonedDateTime observationTime;
    private ZonedDateTime creationTime;
    private String parameterCode;
    private Double value;
    private String comment;

    private ShefRecord(String station, ZonedDateTime observationTime, ZonedDateTime creationTime,
                       String parameterCode, Double value, String comment) {
        this.station = station;
        this.observationTime = observationTime;
        this.creationTime = creationTime;
        this.parameterCode = parameterCode;
        this.value = value;
        this.comment = comment;
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

    public Double getValue() {
        return value;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(station).append(("{"));
        sb.append("observationTime=").append(observationTime.format(DateTimeFormatter.ISO_DATE_TIME)).append(",");
        sb.append("peCode=").append(this.parameterCode).append(",");
        sb.append("value=").append(value).append(",");
        sb.append("creationTime=").append(this.creationTime.format(DateTimeFormatter.ISO_DATE_TIME)).append(",");
        if (comment != null) {
            sb.append("comment='").append(comment).append("'");
        } else {
            sb.append("comment=");
        }
        
        sb.append("}");
        return sb.toString();
    }

    public static class Builder {
        String station;
        ZonedDateTime observationTime;
        ZonedDateTime creationTime = ZonedDateTime.now();
        String parameterCode;
        Double value;
        String comment;

        public Builder(String station) {
            this.station = station;
        }

        public Builder withObservationTime(ZonedDateTime observationTime) {
            this.observationTime = observationTime;
            return this;
        }

        public ShefRecord build() {            
            ShefRecord v = new ShefRecord(station,observationTime,creationTime,parameterCode,value,comment);
            return v;
        }

        public Builder withParameterCode(String parameterCode) {
            this.parameterCode = parameterCode;
            return this;
        }

        public Builder withValue(Double v) {
            this.value = v;
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }
    }
}
