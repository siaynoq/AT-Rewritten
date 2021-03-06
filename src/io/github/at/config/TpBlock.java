package io.github.at.config;

import io.github.at.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class TpBlock {
    public static File configFile = new File(Main.getInstance().getDataFolder(),"blocklist.yml");

    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public static void save() throws IOException {
        config.save(configFile);
    }
    public static void addBlockedPlayer(Player bpl, Player target) throws IOException {
        if (getBlockedPlayers(bpl).size()>0){
            List<String> players = config.getStringList("players." + bpl.getUniqueId().toString());
            players.add(target.getUniqueId().toString());
            config.set("players." + bpl.getUniqueId().toString(), players);
        } else {
            config.set("players." + bpl.getUniqueId().toString(), new ArrayList<>(Collections.singleton(target.getUniqueId().toString())));
        }save();
    }
    public static List<Player> getBlockedPlayers(Player target){
        List<Player> players = new ArrayList<>();
        for (String uniqueID: config.getStringList("players." + target.getUniqueId().toString())){
            Player player = Bukkit.getPlayer(UUID.fromString(uniqueID));
            players.add(player);
        }
        return players;
    }
    public static void remBlockedPlayer(Player rpl, Player target) throws IOException {
        List<String> players = config.getStringList("players." + rpl.getUniqueId().toString());
        players.remove((target.getUniqueId().toString()));
        config.set("players." + rpl.getUniqueId().toString(), players);
        save();
    }

    public static void reloadBlocks() throws IOException {
        if (configFile == null) {
            configFile = new File(Main.getInstance().getDataFolder(), "blocklist.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        save();
    }

}
