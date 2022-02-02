package de.techgamez.pleezon;

import com.google.common.collect.Lists;
import de.techgamez.pleezon.config.ConfigElement;
import de.techgamez.pleezon.config.ConfigHashMap;
import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.core.LabyModCore;
import net.labymod.core.asm.LabyModCoreMod;
import net.labymod.ingamechat.IngameChatManager;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.ChatComponentText;

import java.util.HashMap;
import java.util.List;

public class PresenceListener extends LabyModAddon {

    private static ConfigHashMap trackedPlayers = new ConfigHashMap("trackedPlayers",new HashMap<>());

    public void onEnable() {
        ConfigElement.init();
        getApi().registerForgeListener(this);
        getApi().getEventManager().registerOnIncomingPacket((p)->{
            if (p instanceof S38PacketPlayerListItem){
                S38PacketPlayerListItem packet = (S38PacketPlayerListItem) p;
                S38PacketPlayerListItem.Action action = packet.func_179768_b();
                List<S38PacketPlayerListItem.AddPlayerData> players = packet.func_179767_a();
                players.forEach((playerData)->{
                    if(!trackedPlayers.containsKey(playerData.getProfile().getId().toString().replaceAll("-","")))return;
                    String playerName = trackedPlayers.get(playerData.getProfile().getId().toString().replaceAll("-",""));
                    switch (action){
                        case ADD_PLAYER:
                            notifyPlayerEvent("JOIN: " + playerName);
                            break;
                        case REMOVE_PLAYER:
                            notifyPlayerEvent("LEAVE: " + playerName);
                            break;
                    }
                });
            }
        });
        getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String s) {
                boolean ret = s.startsWith(".");
                if(s.startsWith(".add ")){
                    s = s.replaceFirst("\\.add ","");
                    if(!s.equals("")){
                        String uuid =UUIDHelper.getUuid(s);
                        if(uuid != null){
                            trackedPlayers.put(uuid,s);
                            notifySuccess("added player: " +s);
                        }else{
                            notifyError("error fetching UUID of \"" + s+"\"");
                        }
                    }else{
                        notifyError("wrong amount of arguments specified. Supplied: \""+s+"\" wanted: \"<name>\"");
                    }
                }else if(s.startsWith(".remove ")){
                    s = s.replaceFirst("\\.remove ","");
                    if(!s.equals("")){
                        String uuid =UUIDHelper.getUuid(s);
                        if(uuid != null){
                            trackedPlayers.remove(uuid);
                            notifySuccess("removed player " + s);
                        }else{
                            notifyError("error fetching UUID of \"" + s+"\"");
                        }
                    }else{
                        notifyError("wrong amount of arguments specified. Supplied: \""+s+"\" wanted: \"<name>\"");
                    }
                }else if(s.startsWith(".list")){
                    PresenceListener.notify("§f§lTracked Players:");
                    trackedPlayers.getMap().forEach((k,v)->{
                        PresenceListener.notify(v);
                    });
                }else if(s.startsWith(".help")){
                    PresenceListener.notify("&a&lCommands: .add <name> || .remove <name> || .list");
                }
                return ret;
            }
        });
    }
    private static void notifyError(String err){
        notify("§4§lERR: " + err);
    }
    private static void notifySuccess(String msg){
        notify("§a§lSuccess: " + msg);
    }
    private static void notify(String msg){
        if(Minecraft.getMinecraft().thePlayer!=null)LabyMod.getInstance().displayMessageInChat(msg);
    }
    private static void notifyPlayerEvent(String text){
        try{
            if(Minecraft.getMinecraft().thePlayer != null) IngameChatManager.INSTANCE.getSecond().addChatLine(text,true,"Global",new ChatComponentText(text), Minecraft.getMinecraft().ingameGUI.getUpdateCounter(),0,0,true);
        }catch (Exception | Error e ){
            e.printStackTrace();
        }
    }

    public void loadConfig() {

    }

    protected void fillSettings(List<SettingsElement> list) {





    }
}
