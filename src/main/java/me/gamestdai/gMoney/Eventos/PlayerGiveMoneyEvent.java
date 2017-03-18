package me.gamestdai.gMoney.Eventos;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Games Tdai
 */
public class PlayerGiveMoneyEvent extends Event{
    
    private Player player;
    private OfflinePlayer target;
    private double money;

    public PlayerGiveMoneyEvent(Player player, OfflinePlayer target, double money) {
        this.player = player;
        this.target = target;
        this.money = money;
    }

    public Player getPlayer() {
        return player;
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
