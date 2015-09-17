package MRDControl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Armando
 */
public class RoomDAO {
    private Connection con;
    private Statement stm;
    
    
    public RoomDAO(Connection con)
    {
        this.con = con;
    }
    public void save(OccupancyRecord  or)
    {
     String query = "insert into occupancy_record (idroom,starttime,endtime) values "+
                        "("+or.getIdRoom()+",'"+or.getStartTime()+"','"+or.getEndTime()+"')";
        
        
        try {
            getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Room> findAll()
    {
        List<Room> list = new ArrayList<Room>();
        
        String query = "select * from rooms";
        
        try {
            ResultSet rs = getStatement().executeQuery(query);
            while(rs.next())
            {
                Room r = new Room();
                r.setActive(rs.getBoolean("active"));
                r.setId(rs.getInt("id"));
                r.setNumber(rs.getInt("number"));
                r.setType(rs.getString("type"));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
        
    }
    
    public List<OccupancyRecord> findByRoomId(int idRoom)
    {
        List<OccupancyRecord> list = new ArrayList<OccupancyRecord>();
        
        String query = "select * from occupancy_record where idroom = "+idRoom;
        
        try {
            ResultSet rs = getStatement().executeQuery(query);
            while(rs.next())
            {
                OccupancyRecord oc = new OccupancyRecord();
                oc.setIdRoom(rs.getInt("idroom"));
                oc.setStartTime(rs.getDate("starttime"));
                oc.setEndTime(rs.getDate("endtime"));
                list.add(oc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list; 
    }
    
     public List<OccupancyRecord> findByDateRange(Date startTime,Date endTime)
    {
        List<OccupancyRecord> list = new ArrayList<OccupancyRecord>();
        
        String query = "select * from occupancy_record where starttime between = "+startTime+" and "+endTime;
        
        try {
            ResultSet rs = getStatement().executeQuery(query);
            while(rs.next())
            {
                OccupancyRecord oc = new OccupancyRecord();
                oc.setIdRoom(rs.getInt("idroom"));
                oc.setStartTime(rs.getDate("starttime"));
                oc.setEndTime(rs.getDate("endtime"));
                list.add(oc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list; 
    }
    

    private Statement getStatement()
    {
        try {
            stm = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stm;
    }
    
}
