package me.gamestdai.gMoney;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class VaultConexao
        implements Economy {

    public boolean isEnabled() {
        return gMoney.getInstance().isEnabled();
    }

    public String getName() {
        return "gMoney";
    }

    public boolean hasBankSupport() {
        return false;
    }

    public int fractionalDigits() {
        return 2;
    }

    public String format(double quantidade) {
        return "R$" + String.valueOf(quantidade);
    }

    public String currencyNamePlural() {
        return "Reais";
    }

    public String currencyNameSingular() {
        return "Real";
    }

    public boolean hasAccount(String string) {
        return false;
    }

    public boolean hasAccount(String string, String string1) {
        return false;
    }

    public double getBalance(String name) {
        return gMoney.getInstance().economia.getMoney(name);
    }

    public double getBalance(String name, String world) {
        return getBalance(name);
    }

    public boolean has(String name, double d) {
        return gMoney.getInstance().economia.getMoney(name) >= d;
    }

    public boolean has(String string, String string1, double d) {
        return has(string, d);
    }

    public EconomyResponse withdrawPlayer(String player, double d) {
        return new EconomyResponse(d, gMoney.getInstance().economia.getMoney(player) - d, gMoney.getInstance().economia.removeMoney(player, d) ? EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, "Insufficient funds.");
    }

    public EconomyResponse withdrawPlayer(String string, String string1, double d) {
        return withdrawPlayer(string, d);
    }

    public EconomyResponse depositPlayer(String name, double d) {
        gMoney.getInstance().economia.addMoney(name, d);
        return new EconomyResponse(d, gMoney.getInstance().economia.getMoney(name), EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse depositPlayer(String string, String string1, double d) {
        depositPlayer(string, d);
        return new EconomyResponse(d, gMoney.getInstance().economia.getMoney(string), EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse createBank(String string, String string1) {
        return null;
    }

    public EconomyResponse deleteBank(String string) {
        return null;
    }

    public EconomyResponse bankBalance(String string) {
        return null;
    }

    public EconomyResponse bankHas(String string, double d) {
        return null;
    }

    public EconomyResponse bankWithdraw(String string, double d) {
        return null;
    }

    public EconomyResponse bankDeposit(String string, double d) {
        return null;
    }

    public EconomyResponse isBankOwner(String string, String string1) {
        return null;
    }

    public EconomyResponse isBankMember(String string, String string1) {
        return null;
    }

    public List<String> getBanks() {
        return null;
    }

    public boolean createPlayerAccount(String string) {
        return gMoney.getInstance().economia.createAccount(string);
    }

    public boolean createPlayerAccount(String string, String string1) {
        return createPlayerAccount(string);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return createPlayerAccount(offlinePlayer.getName());
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer.getName());
    }
}
