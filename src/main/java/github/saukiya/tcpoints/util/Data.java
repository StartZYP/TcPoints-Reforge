package github.saukiya.tcpoints.util;


import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import static github.saukiya.tcpoints.TcPoints.getPlugin;


public class Data {
    private static PlayerPoints playerPoints;
    public static void loadData() {
        Plugin plugin = getPlugin().getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        System.out.println(playerPoints != null);
        System.out.println("加载中……………………………………………………");
    }


    public static Boolean isPlayerPoints(String playerName, int points) {
        return getPlayerPoints(playerName) >= points;
    }

    public static int getPlayerPoints(String playerName) {
        //System.out.println("gegegege"+playerName);
        //System.out.println(playerPoints.getAPI().look(Bukkit.getPlayer(playerName).getUniqueId()));
        //System.out.println(playerPoints.getAPI().nicklook(playerName));
        try{
            return playerPoints.getAPI().look(playerName);
        }catch (NullPointerException e){
            return 0;
        }
    }

    public static void addPlayerPoints(String playerName, int addPoints) {
        playerPoints.getAPI().give(playerName,addPoints);
        if (Bukkit.getOfflinePlayer(playerName).isOnline()) {
            Bukkit.getPlayer(playerName).sendMessage(Message.getMsg("Player.Add", new String[]{String.valueOf(addPoints), String.valueOf(playerPoints.getAPI().look(playerName))}));
        }

    }

    public static void takePlayerPoints(String playerName, int takePoints) {
        playerPoints.getAPI().take(playerName,takePoints);
        if (Bukkit.getOfflinePlayer(playerName).isOnline()) {
            Bukkit.getPlayer(playerName).sendMessage(Message.getMsg("Player.Take", new String[]{String.valueOf(takePoints), String.valueOf(playerPoints.getAPI().look(playerName))}));
        }

    }

    public static void setPlayerPoints(String playerName, int setPoints) {
        playerPoints.getAPI().set(playerName,setPoints);
    }

    public static void sendPointsTop(CommandSender player, int page) {
        Bukkit.getServer().dispatchCommand(player,"points lead");
    }

}
