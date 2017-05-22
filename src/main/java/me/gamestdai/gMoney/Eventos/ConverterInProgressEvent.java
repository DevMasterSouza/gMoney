package me.gamestdai.gMoney.Eventos;

import me.gamestdai.gMoney.Enums.ConverterType;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class ConverterInProgressEvent extends Event{
    
    private ConverterType type;
    private int time;
    private CommandSender sender;
    private int missingAccounts;

    public ConverterInProgressEvent(ConverterType type, int time, CommandSender sender, int missingAccounts) {
        this.type = type;
        this.time = time;
        this.sender = sender;
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

    public CommandSender getSender() {
        return sender;
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
