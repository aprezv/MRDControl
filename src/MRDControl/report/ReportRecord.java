/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl.report;

/**
 *
 * @author armando
 */
public class ReportRecord {
    private String idRoom;
    private String date;
    private String startTime;
    private String endTime;
    private String duration;
    private boolean greaterThan3hours;

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isGreaterThan3hours() {
        return greaterThan3hours;
    }

    public void setGreaterThan3hours(boolean greaterThan3hours) {
        this.greaterThan3hours = greaterThan3hours;
    }

    @Override
    public String toString() {
        return "ReportRecord{" + "idRoom=" + idRoom + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", duration=" + duration + ", greaterThan3hours=" + greaterThan3hours + '}';
    }
    
    
    
}
