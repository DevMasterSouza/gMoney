package me.gamestdai.gMoney.Eventos;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class PlayerGiveMoneyEvent extends Event{
    
    private CommandSender sender;
    private OfflinePlayer target;
    private double money;

    public PlayerGiveMoneyEvent(CommandSender sender, OfflinePlayer target, double money) {
        this.sender = sender;
        this.target = target;
        this.money = money;
    }

    public CommandSender getSender() {
        return sender;
    }

    public OfflinePlayer getTarget() {
        return target;
    }

    public double getMoney() {
        return money;
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
