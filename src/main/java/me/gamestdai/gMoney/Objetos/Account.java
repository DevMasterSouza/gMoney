package me.gamestdai.gMoney.Objetos;

import org.bukkit.OfflinePlayer;

/**
 *
 * @author GamesTdai
 */
public class Account implements Comparable<Account>{

    private String player;
    private double money;

    public Account(String player, double money) {
        this.player = player;
        this.money = money;
    }
    
    public Account(OfflinePlayer player, double money) {
        this(player.getName(), money);
    }

    public String getPlayer() {
        return this.player;
    }

    public double getMoney() {
        return this.money;
    }
    
    @Override
    public int compareTo(Account outroJogador) {
        if (getMoney() < outroJogador.getMoney()) {
            return 1;
        }
        if (getMoney() > outroJogador.getMoney()) {
            return -1;
        }
        return 0;
    }
}
