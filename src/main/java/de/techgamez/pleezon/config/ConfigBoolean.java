package de.techgamez.pleezon.config;

import java.io.IOException;
import java.util.ArrayList;

public class ConfigBoolean extends ConfigElement{
    private boolean value;

    public ConfigBoolean(String entryname, boolean defaultvalue) {
        super(entryname);
        if(this.isRegisteredInConfig()){
            try{
                this.value = Boolean.parseBoolean(this.load());
            }catch (Exception e){
                System.out.println("Failed whilst loading: " + entryname);
                e.printStackTrace();
            }
        }else{
            try{
                this.save(String.valueOf(defaultvalue));
            }catch (Exception e){
                System.out.println("Failed whilst saving: " + entryname);
                e.printStackTrace();
            }

        }
    }

    public boolean getValue(){
        return this.value;
    }

    public void setValue(boolean value){
        this.value = value;
        try{
            this.save(String.valueOf(value));
        }catch (Exception e){
            System.out.println("Failed whilst saving: " + entryname);
            e.printStackTrace();
        }
    }


}
