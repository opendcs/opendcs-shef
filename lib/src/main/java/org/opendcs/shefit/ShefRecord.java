package org.opendcs.shefit;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ShefRecord {
    private String station;
    private ZonedDateTime observationTime;
    private ZonedDateTime creationTime;
    private String parameterCode;
    private String durationCode;
    private String typeCode;
    private String sourceCode;
    private String extremumCode;
    private String probabilityCode;
    private Double value;
    private String comment;
    private boolean replace;

    private ShefRecord(String station, ZonedDateTime observationTime, ZonedDateTime creationTime,
                       String parameterCode, Double value, String comment,
                       String durationCode, String typeCode, String sourceCode,
                       String extremumCode, String probabilityCode, boolean replace) {
        this.station = station;
        this.observationTime = observationTime;
        this.creationTime = creationTime;
        this.parameterCode = parameterCode;
        this.value = value;
        this.comment = comment;
        this.durationCode = durationCode;
        this.typeCode = typeCode;
        this.sourceCode = sourceCode;
        this.extremumCode = extremumCode;
        this.probabilityCode = probabilityCode;
        this.replace = replace;
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

    public String getDurationCode() {
        return durationCode;
    }

    public String getTypeCode() { 
        return typeCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public String getExtremumCode() {
        return extremumCode;
    }

    public boolean getReplace() {
        return replace;
    }

    public String getProbabilityCode() {
        return probabilityCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(station).append(("{"));
        sb.append("observationTime=").append(observationTime.format(DateTimeFormatter.ISO_DATE_TIME)).append(",");
        sb.append("peCode=").append(this.parameterCode).append(",");
        sb.append("value=").append(value).append(",");
        sb.append("creationTime=").append(this.creationTime.format(DateTimeFormatter.ISO_DATE_TIME)).append(",");
        sb.append("durationCode=").append(this.durationCode).append(",");
        sb.append("typeCode=").append(this.typeCode).append(",");
        sb.append("sourceCode=").append(this.sourceCode).append(",");
        sb.append("extremumCode").append(this.extremumCode).append(",");
        sb.append("probabilityCode=").append(this.probabilityCode).append(",");
        sb.append("revised=").append(this.replace).append(",");
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
        private String durationCode = "Z";
        private String typeCode = "Z";
        private String sourceCode = "Z";
        private String extremumCode = "Z";
        private String probabilityCode = "Z";
        private boolean replace = false;

        public Builder(String station) {
            this.station = station;
        }

        public Builder withObservationTime(ZonedDateTime observationTime) {
            this.observationTime = observationTime;
            return this;
        }

        public ShefRecord build() {            
            ShefRecord v = new ShefRecord(
                                station,observationTime,creationTime,parameterCode,
                                value,comment,durationCode,typeCode,sourceCode,
                                extremumCode,probabilityCode,replace);
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

        public Builder withDurationCode(String durationCode) {
            this.durationCode = durationCode;
            return this;
        }

        public Builder withTypeCode(String typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        public Builder withSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
            return this;
        }

        public Builder withExtremumCode(String extremumCode) {
            this.extremumCode = extremumCode;
            return this;
        }

        public Builder withProbabilityCode(String probabilityCode) {
            this.probabilityCode = probabilityCode;
            return this;
        }

        public Builder withRevisionStatus(boolean replace) {
            this.replace = replace;
            return this;
        }
    }
}
