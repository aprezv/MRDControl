/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRDControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Armando
 */
public class Config {
    public static Properties propiedades = new Properties();
    public static OutputStream out ;
    public static InputStream inputStream;

    static{

        try {
            inputStream = new FileInputStream("C:\\MRDControl\\properties.properties");
            //inputStream =  Config.class.getClassLoader().getResourceAsStream("properties.properties");
            propiedades.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean save(){
        try {
            out = new FileOutputStream( new File("C:\\MRDControl\\properties.properties"));
            propiedades.store(out,"");
            out.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
