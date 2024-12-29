package com.bloomtech.localdatetime.meetingscheduler;

import com.bloomtech.localdatetime.meetingscheduler.models.*;
import com.bloomtech.localdatetime.meetingscheduler.views.Invite;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MeetingScheduler {

    public static void main(String[] args) {
        Employee owner = new Employee.EmployeeBuilder()
                .withName("Owner")
                .withTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
                .build();

        Employee invitee = new Employee.EmployeeBuilder()
                .withName("Invited Employee")
                .withTimeZone(TimeZone.getTimeZone("America/New_York"))
                .withStartTime(LocalTime.of(12,0))
                .withEndTime(LocalTime.of(20,0))
                .build();

        Meeting meeting = new Meeting.MeetingBuilder()
                .withOwner(owner)
                .withTitle("Quick Chat")
                .withStartDateTime(LocalDateTime.now().plusHours(1))
                .withEndDateTime(LocalDateTime.now().plusHours(2))
                .build();

        meeting.invite(invitee);

        System.out.println(createInvites(meeting));
    }

    public static List<Invite> createInvites(Meeting meeting) {
        List<Invite> invites = new ArrayList<>();

        TimeZone meetingTimeZone = meeting.getOwner().getTimeZone();
        ZonedDateTime zonedMeetingStart = meeting.getStartDateTime().atZone(meetingTimeZone.toZoneId());
        ZonedDateTime zonedMeetingEnd = meeting.getEndDateTime().atZone(meetingTimeZone.toZoneId());

        for (Employee employee : meeting.getInvited()) {

            LocalDateTime employeeStartDateTime = LocalDateTime.of(meeting.getStartDateTime().toLocalDate(), employee.getStartTime());
            ZonedDateTime zonedEmployeeStart = employeeStartDateTime.atZone(employee.getTimeZone().toZoneId());

            LocalDateTime employeeEndDateTime = LocalDateTime.of(meeting.getEndDateTime().toLocalDate(), employee.getEndTime());
            ZonedDateTime zonedEmployeeEnd = employeeEndDateTime.atZone(employee.getTimeZone().toZoneId());

            // If meeting start time (LocalDateTime) is before employee start time (LocalTime)
            // Or if meeting end time is after employee end time
            if (zonedMeetingStart.isBefore(zonedEmployeeStart) || zonedMeetingEnd.isAfter(zonedEmployeeEnd)) {
                // THEN employee not available
                invites.add(new Invite(employee, false));
            } else {
                // ELSE Employee is available
                invites.add(new Invite(employee, true));
            }
        }

        return invites;
    }
}
