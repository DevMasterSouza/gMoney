package me.gamestdai.gMoney.Eventos;

import me.gamestdai.gMoney.Enums.ConverterType;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class ConverterFinishEvent extends Event{
    
    private CommandSender sender;
    private ConverterType type;

    public ConverterFinishEvent(ConverterType type, CommandSender sender) {
        this.type = type;
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
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
