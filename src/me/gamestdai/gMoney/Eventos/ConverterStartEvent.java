package me.gamestdai.gMoney.Eventos;

import me.gamestdai.gMoney.Enums.ConverterType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class ConverterStartEvent extends Event {
    
    private Player player;
    private int time;
    private ConverterType type;

    public ConverterStartEvent(ConverterType type, Player player, int time) {
        this.type = type;
        this.player = player;
        this.time = time;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTime() {
        return time;
    }

    public ConverterType getType() {
        return type;
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
