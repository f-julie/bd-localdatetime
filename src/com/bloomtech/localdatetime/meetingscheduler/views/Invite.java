package com.bloomtech.localdatetime.meetingscheduler.views;

import com.bloomtech.localdatetime.meetingscheduler.models.Employee;

import java.util.Objects;

public class Invite {
    private Employee employee;
    private boolean available;

    public Invite(Employee employee, boolean available) {
        this.employee = employee;
        this.available = available;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invite invite = (Invite) o;
        return available == invite.available &&
                Objects.equals(employee, invite.employee);
    }

    @Override
    public String toString() {
        return "Invite{" +
                "employee=" + employee +
                ", available=" + available +
                '}';
    }
}
