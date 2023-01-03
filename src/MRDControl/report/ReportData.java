/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl.report;

import MRDControl.ConnectionManager;
import MRDControl.OccupancyRecord;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author armando
 */
public class ReportData {

    private SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d hh:mmaa");
    private String query = "select "
            + "                idroom, "
            + "                starttime, "
            + "                date_format(starttime,'%%M %%d, %%Y') as fecha, "
            + "                date_format(starttime,'%%h:%%i:%%s %%p') as inicio, "
            + "                date_format(endtime,'%%h:%%i:%%s %%p') as fin, "
            + "                timediff(endtime, starttime) as duracion, "
            + "                case when TIMESTAMPDIFF(MINUTE, starttime, endtime) >= 195 then true else false end as greater"
            + "            from "
            + "                occupancy_record "
            + "                where starttime >= str_to_date('%s','%%Y-%%m-%%d %%h:%%i%%p') "
            + "                and   starttime <= str_to_date('%s','%%Y-%%m-%%d %%h:%%i%%p')"
            + "                order by starttime";

    public List<ReportRecord> getData(Date start, Date end) throws SQLException {
        String finalQuery = String.format(
                query,
                sdf.format(start),
                sdf.format(end));
        System.out.println("Query: " + finalQuery);

        Connection con = ConnectionManager.getConnection();

        con.createStatement().executeQuery("SET lc_time_names = 'es_MX'; ");
        ResultSet rs = con.createStatement().executeQuery(finalQuery);
        List<ReportRecord> list = new ArrayList();
        System.out.println("List Size: " + list.size());

        while (rs.next()) {
            ReportRecord record = new ReportRecord();
            record.setIdRoom(rs.getString("idroom"));
            record.setStartTime(rs.getString("inicio"));
            record.setEndTime(rs.getString("fin"));
            record.setDuration(rs.getString("duracion"));
            record.setDate(StringUtils.capitalize(rs.getString("fecha")));
            record.setGreaterThan3hours(rs.getBoolean("greater"));
            list.add(record);
        }
        return list;
    }
}
