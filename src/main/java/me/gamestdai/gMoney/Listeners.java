package me.gamestdai.gMoney;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 *
 * @author GamesTdai
 */
public class Listeners implements Listener{
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if(e.getMessage().startsWith("/")) {
            String command = e.getMessage().substring(1).split(" ")[0];
            if(gMoney.getInstance().aliases.contains(command.toLowerCase())){
                String[] args = (String[]) ArrayUtils.removeElement(e.getMessage().split(" "), e.getMessage().split(" ")[0]);
                e.setCancelled(true);
                gMoney.getInstance().cmdmoney.onCommand(e.getPlayer(), (Command)gMoney.getInstance().getCommand("gmoney"), e.getMessage().substring(1), args);
            }
        }
    }
}
