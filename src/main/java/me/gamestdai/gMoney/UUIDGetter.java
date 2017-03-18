package me.gamestdai.gMoney;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 *
 * @author GamesTdai
 */
public class UUIDGetter {
    
    public static UUID getUUID(OfflinePlayer op) {
        try{
            Method m = OfflinePlayer.class.getMethod("getUniqueId");
            return (UUID)m.invoke(op);
        }catch(Exception e) {
            return null;
        }
    }
    
    public static OfflinePlayer getPlayer(UUID uuid) {
        try{
            Method m = Bukkit.class.getMethod("getOfflinePlayer", UUID.class);
            return (OfflinePlayer)m.invoke(null, uuid);
        }catch(Exception e) {
            return null;
        }
    }
}
