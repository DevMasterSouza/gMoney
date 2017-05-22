package me.gamestdai.gMoney.Eventos;

import me.gamestdai.gMoney.Enums.ConverterType;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class ConverterStartEvent extends Event {
    
    private CommandSender sender;
    private int time;
    private ConverterType type;

    public ConverterStartEvent(ConverterType type, CommandSender sender, int time) {
        this.type = type;
        this.sender = sender;
        this.time = time;
    }

    public CommandSender getSender() {
        return sender;
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
