package MRDControl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;

/**
 *
 * @author Armando
 */
public class OccupancyRecord {

    private int idRoom;
    private String startTime;
    private String endTime;
    private boolean ableForSave;

    public boolean isAbleForSave() {
        return ableForSave;
    }

    public void setAbleForSave(boolean ableForSave) {
        this.ableForSave = ableForSave;
    }

    
    public OccupancyRecord(int idRoom, Date startTime) {
        this.idRoom = idRoom;
        this.startTime = Util.toSqlDate(startTime);
        this.ableForSave = false;
    }

    public OccupancyRecord(int idRoom, Date startTime, Date endTime) {
        this.idRoom = idRoom;
        this.startTime = Util.toSqlDate(startTime);
        this.endTime = Util.toSqlDate(endTime);
        this.ableForSave = false;
    }

    public OccupancyRecord() {
    }

    public OccupancyRecord(int idRoom) {
        this.idRoom = idRoom;
        this.ableForSave = false;
    }

    @Override
    public String toString() {
        return "OccupancyRecord{" + "idRoom=" + idRoom + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        if (startTime == null) {
            this.startTime = null;
        } else {
            this.startTime = Util.toSqlDate(startTime);
        }

    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        if (endTime == null) {
            this.endTime = null;
        } else {
            this.endTime = Util.toSqlDate(endTime);
        }
    }

}
