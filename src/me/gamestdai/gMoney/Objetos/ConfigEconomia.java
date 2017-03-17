package me.gamestdai.gMoney.Objetos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.UUIDGetter;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.Bukkit;

public class ConfigEconomia
        implements Economia {

    private Config config;

    public ConfigEconomia(Config config) {
        this.config = config;
    }

    public double getMoney(String player) {
        if(gMoney.getInstance().usingUUID) {
            return this.config.getDouble(UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString());
        }else{
            return this.config.getDouble("money." + player.toLowerCase());
        }
    }

    public double setMoney(String player, double quantidade) {
        quantidade = Math.abs(quantidade);
        if(gMoney.getInstance().usingUUID) {
            this.config.set("money." + UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString(), Double.valueOf(quantidade));
        }else{
            this.config.set("money." + player.toLowerCase(), Double.valueOf(quantidade));
        }
        this.config.save();
        this.config.reload();
        return quantidade;
    }

    public boolean removeMoney(String player, double quantidade) {
        quantidade = Math.abs(quantidade);
        if (getMoney(player) - quantidade < 0.0D) {
            return false;
        }
        setMoney(player, getMoney(player) - quantidade);
        return true;
    }

    public double addMoney(String player, double quantidade) {
        quantidade = Math.abs(quantidade);
        return setMoney(player, getMoney(player) + quantidade);
    }

    public Map<String, Double> getTop(int startin, int stopin) {
        Map<String, Double> top = new LinkedHashMap(stopin);
        this.config.reload();
        ArrayList<Account> players = new ArrayList();
        for (String player : this.config.getConfigurationSection("money").getKeys(false)) {
            if(gMoney.getInstance().usingUUID) {
                players.add(new Account(UUIDGetter.getPlayer(UUID.fromString(player)).getName(), getMoney(player)));
            }else{
                players.add(new Account(player, getMoney(player)));
            }
        }
        Collections.sort(players);
        for (int i = 0; i < stopin; i++) {
            try {
                top.put(((Account) players.get(i)).getPlayer(), Double.valueOf(((Account) players.get(i)).getMoney()));
            } catch (Exception e) {
                break;
            }
        }
        return top;
    }

    public boolean hasAccount(String player) {
        if(gMoney.getInstance().usingUUID) {
            return this.config.contains("money." + UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString());
        }else{
            return this.config.contains("money." + player.toLowerCase());
        }
    }

    public boolean createAccount(String player) {
        if (!hasAccount(player)) {
            setMoney(player, 0.0D);
            return true;
        }
        return false;
    }

    public boolean resetAccount(String player) {
        if (hasAccount(player)) {
            setMoney(player, 0.0D);
            return true;
        }
        return createAccount(player);
    }

    @Override
    public List<Account> getAllAccounts() {
        ArrayList<Account> contas = new ArrayList<>();
        for(String player : config.getConfigurationSection("money").getKeys(false)) {
            if(gMoney.getInstance().usingUUID) {
                contas.add(new Account(UUIDGetter.getPlayer(UUID.fromString(player)), getMoney(player)));
            }else{
                contas.add(new Account(player, getMoney(player)));
            }
        }
        return contas;
    }
}