package de.techgamez.pleezon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UUIDHelper {
    public static String getUuid(String name) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/"+name).openStream()));
            String line = bufferedReader.readLine();
            return line.split("\"id\":\"")[1].replace("\"}","").replaceAll("-","");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getName(String uuid) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names").openStream()));
            String line = bufferedReader.readLine();
            return line.split("\"name\":\"")[1].split("\"")[0];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
