package me.gamestdai.gMoney.Eventos;

import me.gamestdai.gMoney.Enums.ConverterType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class ConverterInProgressEvent extends Event{
    
    private ConverterType type;
    private int time;
    private Player player;
    private int missingAccounts;

    public ConverterInProgressEvent(ConverterType type, int time, Player player, int missingAccounts) {
        this.type = type;
        this.time = time;
        this.player = player;
        this.missingAccounts = missingAccounts;
    }

    public int getMissingAccounts() {
        return missingAccounts;
    }

    public ConverterType getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public Player getPlayer() {
        return player;
    }
    
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
