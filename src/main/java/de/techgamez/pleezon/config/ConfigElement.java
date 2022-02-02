package de.techgamez.pleezon.config;


import java.io.*;
import java.util.Properties;


public class ConfigElement {
    String entryname;
    static String cfg = "./PresenceListener";
    ConfigElement(String entryname){
        this.entryname = entryname;
    }

    public static void init() {
        try {
            new File(cfg).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String value) throws IOException {
        Properties prop = new Properties();
        prop.load(new BufferedInputStream(new FileInputStream(cfg)));
        prop.setProperty(entryname,value);
        prop.store(new FileOutputStream(cfg),"");
    }

    public String load() throws IOException {
        Properties prop = new Properties();
        prop.load(new BufferedInputStream(new FileInputStream(cfg)));
        return prop.getProperty(entryname);
    }
    boolean isRegisteredInConfig() {
        try{
            Properties prop = new Properties();
            prop.load(new BufferedInputStream(new FileInputStream(cfg)));
            return prop.containsKey(entryname);
        }catch (Exception e){return false;}
    }
}
