package de.techgamez.pleezon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UUIDHelper {
    public static String getUuid(String name) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            String line;
            while((line = bufferedReader.readLine())!= null){
                if(line.trim().startsWith("\"id\" : \"")){
                    return line.trim().replaceFirst("\"id\" : \"","").replaceAll("\",","");
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getName(String uuid) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.replace("-", "")).openStream()));
            String line;
            while((line = bufferedReader.readLine())!= null){
                if(line.trim().startsWith("\"name\" : \"")){
                    return line.trim().replaceFirst("\"name\" : \"","").replaceAll("\",","");
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
