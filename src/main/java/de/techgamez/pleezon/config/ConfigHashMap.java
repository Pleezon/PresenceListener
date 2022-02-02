package de.techgamez.pleezon.config;

import java.io.IOException;
import java.util.HashMap;

public class ConfigHashMap extends ConfigElement{
    private HashMap<String,String> map = new HashMap<>();

    public ConfigHashMap(String entryname, HashMap<String, String> defaultmap) {
        super(entryname);
        try {
            if(!isRegisteredInConfig()){
                save(hashMapToString(defaultmap));
            }
            HashMap<String,String> map = hashMapFromString(load());
            if(map==null){
                save(hashMapToString(defaultmap));
            }
            this.map=hashMapFromString(load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value){
        this.map.put(key, value);
        saveMap();
    }
    public HashMap<String,String> getMap(){
        return this.map;
    }
    public String get(String key){
        return this.map.get(key);
    }
    public boolean containsKey(String key){
        return this.map.containsKey(key);
    }
    public void remove(String key){
        this.map.remove(key);
        saveMap();
    }
    private void saveMap(){
        try {
            this.save(this.hashMapToString(this.map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String hashMapToString(HashMap<String,String> set){
        StringBuilder sb = new StringBuilder("");
        set.forEach((k,v)->{
            sb.append("["+k+":"+v+"]");
        });
        return sb.toString();
    }
    private HashMap<String,String> hashMapFromString(String s){
        if(s.equals("")){
            return new HashMap<String, String>();
        }
        HashMap<String,String> ret = new HashMap<String, String>();
        try{
            for (String s1 : s.split("]")) {
                s1=s1.replace("[","");
                String key = s1.split(":")[0];
                String value = s1.split(":")[1];
                ret.put(key, value);
            }
        }catch (Exception e){e.printStackTrace();return null;}
        return ret;
    }

}
