package com.bloomtech.localdatetime.meetingscheduler.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Meeting {
    private String title;
    private Employee owner;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<Employee> invited;

    private Meeting(MeetingBuilder builder) {
        title = builder.title;
        owner = builder.owner;
        startDateTime = builder.startDateTime;
        endDateTime = builder.endDateTime;
        invited = builder.invited;
    }

    public void invite(Employee employee) {
        invited.add(employee);
    }

    public void invite(List<Employee> employeeList) {
        invited.addAll(employeeList);
    }

    public String getTitle() {
        return title;
    }

    public Employee getOwner() {
        return owner;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public List<Employee> getInvited() {
        return invited;
    }

    public static class MeetingBuilder {
        private String title;
        private Employee owner;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private List<Employee> invited;

        public MeetingBuilder() {
            invited = new ArrayList<>();
        }

        public MeetingBuilder withTitle(String titleToUse) {
            title = titleToUse;
            return this;
        }

        public MeetingBuilder withOwner(Employee ownerToUse) {
            owner = ownerToUse;
            return this;
        }

        public MeetingBuilder withStartDateTime(LocalDateTime startDateTimeToUse) {
            startDateTime = startDateTimeToUse;
            return this;
        }

        public MeetingBuilder withEndDateTime(LocalDateTime endDateTimeToUse) {
            endDateTime = endDateTimeToUse;
            return this;
        }

        public MeetingBuilder withInvited(List<Employee> invitedToUse) {
            invited = invitedToUse;
            return this;
        }

        public Meeting build() {
            return new Meeting(this);
        }
    }
}
