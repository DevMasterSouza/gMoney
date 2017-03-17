package me.gamestdai.gMoney.Eventos;

import me.gamestdai.gMoney.Enums.ConverterType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class ConverterFinishEvent extends Event{
    
    private Player player;
    private ConverterType type;

    public ConverterFinishEvent(ConverterType type, Player player) {
        this.type = type;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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
