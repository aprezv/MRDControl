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
public class ParamsDao {

    private Connection con;
    private Statement stm;

    public ParamsDao(Connection con) {
        this.con = con;
    }

    public Params retrieve() {
        Params params = new Params();

        String query = "select * from parametros";

        try {
            ResultSet rs = getStatement().executeQuery(query);
            if (rs.next()) {
                params.setDoorDown(rs.getInt("doordown"));
                params.setDoorUp(rs.getInt("doorup"));
            }else{
                params.setDoorDown(0);
                params.setDoorUp(0);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParamsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return params;

    }

    private Statement getStatement() {
        try {
            stm = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ParamsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stm;
    }

}
