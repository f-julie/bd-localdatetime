package com.bloomtech.localdatetime.meetingscheduler;

import com.bloomtech.localdatetime.meetingscheduler.models.Employee;
import com.bloomtech.localdatetime.meetingscheduler.models.Meeting;
import com.bloomtech.localdatetime.meetingscheduler.views.Invite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class MeetingSchedulerTest {
    private MeetingScheduler meetingScheduler;

    @BeforeEach
    void init() {
        meetingScheduler = new MeetingScheduler();
        System.out.println();
    }

    @Test
    void createInvites_whereAnEmployeeIsUnavailableDueToTimeZoneDifference() {
        Employee owner = new Employee.EmployeeBuilder()
                .withName("Roy")
                .withTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
                .build();
        Employee timezoneAheadEmployee = new Employee.EmployeeBuilder()
                .withName("Anna")
                .withTimeZone(TimeZone.getTimeZone("America/New_York"))
                .build();
        Employee sameTimezoneEmployee = new Employee.EmployeeBuilder()
                .withName("Erin")
                .withTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
                .build();

        LocalDateTime startDateTime = LocalDateTime.of(2022, 12, 30, 14, 0, 0);
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        Meeting meeting = new Meeting.MeetingBuilder()
                .withOwner(owner)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)
                .build();

        meeting.invite(timezoneAheadEmployee);
        meeting.invite(sameTimezoneEmployee);

        List<Invite> expectedInvites = Arrays.asList(
                new Invite(timezoneAheadEmployee, false),
                new Invite(sameTimezoneEmployee, true)
        );
        List<Invite> actualInvites = meetingScheduler.createInvites(meeting);

        System.out.println("Meeting starts at " + meeting.getStartDateTime().toLocalTime() + " " + meeting.getOwner().getTimeZone().getDisplayName());
        System.out.println(actualInvites);
        assertArrayEquals(expectedInvites.toArray(), actualInvites.toArray());
    }

    @Test
    void createInvites_withAvailableEmployeeInSeparateTimeZone() {
        Employee owner = new Employee.EmployeeBuilder()
                .withName("Roy")
                .withTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
                .withStartTime(LocalTime.of(7, 0))
                .withEndTime(LocalTime.of(3, 0))
                .build();
        Employee timezoneAheadEmployee = new Employee.EmployeeBuilder()
                .withName("Anna")
                .withTimeZone(TimeZone.getTimeZone("America/New_York"))
                .build();

        LocalDateTime startDateTime = LocalDateTime.of(2022, 12, 30, 7, 0, 0);
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        Meeting meeting = new Meeting.MeetingBuilder()
                .withOwner(owner)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)
                .build();

        meeting.invite(timezoneAheadEmployee);

        List<Invite> expectedInvites = Arrays.asList(
                new Invite(timezoneAheadEmployee, true)
        );
        List<Invite> actualInvites = meetingScheduler.createInvites(meeting);

        System.out.println("Meeting starts at " + meeting.getStartDateTime().toLocalTime() + " " + meeting.getOwner().getTimeZone().getDisplayName());
        System.out.println(actualInvites);
        assertArrayEquals(expectedInvites.toArray(), actualInvites.toArray());
    }
}