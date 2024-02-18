package com.bloomtech.localdatetime.meetingscheduler.models;

import java.time.LocalTime;
import java.util.TimeZone;

public class Employee {
    private String name;
    private TimeZone timeZone;
    private LocalTime startTime;
    private LocalTime endTime;

    private Employee(EmployeeBuilder builder) {
        name = builder.name;
        timeZone = builder.timeZone;
        startTime = builder.startTime;
        endTime = builder.endTime;
    }

    public String getName() {
        return name;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public static class EmployeeBuilder {
        private String name;
        private TimeZone timeZone;
        private LocalTime startTime;
        private LocalTime endTime;

        public EmployeeBuilder() {
            timeZone = TimeZone.getDefault();
            startTime = LocalTime.of(9, 0);
            endTime = LocalTime.of(17, 0);
        }

        public EmployeeBuilder withName(String nameToUse) {
            name = nameToUse;
            return this;
        }

        public EmployeeBuilder withTimeZone(TimeZone timeZoneToUse) {
            timeZone = timeZoneToUse;
            return this;
        }

        public EmployeeBuilder withStartTime(LocalTime startTimeToUse) {
            startTime = startTimeToUse;
            return this;
        }

        public EmployeeBuilder withEndTime(LocalTime endTimeToUse) {
            endTime = endTimeToUse;
            return this;
        }

        public Employee build() {
            if (name == null) {
                throw new RuntimeException("Employee name must not be null!");
            }
            return new Employee(this);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", timeZone=" + timeZone.getDisplayName() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "}";
    }
}
