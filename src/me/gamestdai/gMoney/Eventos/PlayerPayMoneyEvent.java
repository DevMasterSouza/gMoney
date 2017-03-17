package me.gamestdai.gMoney.Eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class PlayerPayMoneyEvent extends Event implements Cancellable{
    
    private Player player;
    private Player target;
    private double money;
    private boolean failedTransfer;
    
    private boolean cancel = false;

    public PlayerPayMoneyEvent(Player player, Player target, double money, boolean failedTransfer) {
        this.player = player;
        this.target = target;
        this.money = money;
        this.failedTransfer = failedTransfer;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getTarget() {
        return target;
    }

    public double getMoney() {
        return money;
    }

    public boolean isFailedTransfer() {
        return failedTransfer;
    }

    public void setFailedTransfer(boolean failedTransfer) {
        this.failedTransfer = failedTransfer;
    }
    
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancel = bln;
    }
}
