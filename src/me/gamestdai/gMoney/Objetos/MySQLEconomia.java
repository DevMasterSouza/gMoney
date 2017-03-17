package me.gamestdai.gMoney.Objetos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.UUIDGetter;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.Bukkit;

public class MySQLEconomia
        implements Economia {

    private MySQL mysql;
    private String table;

    public MySQLEconomia(MySQL mysql, String table)
            throws SQLException, ClassNotFoundException {
        this.mysql = mysql;
        this.table = table;
        getMysql().openConnection();
        getMysql().getConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS " + getTable() + " (player VARCHAR(40) PRIMARY KEY, money DOUBLE);");
    }

    public MySQL getMysql() {
        return this.mysql;
    }

    public String getTable() {
        return this.table;
    }

    public double getMoney(String player) {
        try {
            Statement statement = getMysql().getConnection().createStatement();
            ResultSet res = null;
            if(gMoney.getInstance().usingUUID) {
                 res = statement.executeQuery("SELECT * FROM " + getTable() + " WHERE player = '" + UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString() + "';");
            }else{
                res = statement.executeQuery("SELECT * FROM " + getTable() + " WHERE player = '" + player.toLowerCase() + "';");
            }
            if (res.next()) {
                double money = res.getDouble("money");
                res.close();
                statement.close();
                return money;
            }
            res.close();
            statement.close();
            return 0.0D;
        } catch (Exception ex) {
        }
        return 0.0D;
    }

    public double setMoney(String player, double quantidade) {
        try {
            quantidade = Math.abs(quantidade);
            Statement statement = getMysql().getConnection().createStatement();
            ResultSet res = null;
            if(gMoney.getInstance().usingUUID) {
                res = statement.executeQuery("SELECT * FROM " + getTable() + " WHERE player = '" + UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString() + "';");
            }else{
                res = statement.executeQuery("SELECT * FROM " + getTable() + " WHERE player = '" + player.toLowerCase() + "';");
            }
            if (res.next()) {
                statement.executeUpdate("UPDATE " + getTable() + " SET money='" + quantidade + "' WHERE player= '" + (gMoney.getInstance().usingUUID ? UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString() : player.toLowerCase()) + "';");
                res.close();
                statement.close();
                return quantidade;
            }
            statement.executeUpdate("INSERT INTO " + getTable() + " (`player`, `money`) VALUES ('" + (gMoney.getInstance().usingUUID ? UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString() : player.toLowerCase()) + "','" + quantidade + "');");
            res.close();
            statement.close();
            return quantidade;
        } catch (Exception ex) {
        }
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
        double money = getMoney(player);
        return setMoney(player, money + quantidade);
    }

    public Map<String, Double> getTop(int startin, int stopin) {
        Map<String, Double> top = new LinkedHashMap(stopin);
        try {
            Statement s = getMysql().getConnection().createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM " + getTable() + " ORDER BY CONVERT(money, UNSIGNED INTEGER) DESC LIMIT " + stopin);
            while (r.next()) {
                if(gMoney.getInstance().usingUUID) {
                    top.put(UUIDGetter.getPlayer(UUID.fromString(r.getString("player"))).getName(), Double.valueOf(r.getDouble("money")));
                }else{
                    top.put(r.getString("player"), Double.valueOf(r.getDouble("money")));
                }
            }
            return top;
        } catch (SQLException e) {
        }
        return top;
    }

    public boolean hasAccount(String player) {
        try {
            Statement statement = getMysql().getConnection().createStatement();
            ResultSet res = null;
            if(gMoney.getInstance().usingUUID) {
                res = statement.executeQuery("SELECT * FROM " + getTable() + " WHERE player = '" + UUIDGetter.getUUID(Bukkit.getOfflinePlayer(player)).toString() + "';");
            }else{
                res = statement.executeQuery("SELECT * FROM " + getTable() + " WHERE player = '" + player.toLowerCase() + "';");
            }
            if (res.next()) {
                return true;
            }
            return false;
        } catch (Exception ex) {
        }
        return false;
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
        try{
            ArrayList<Account> contas = new ArrayList<>();
            Statement statement = getMysql().getConnection().createStatement();
            ResultSet r = statement.executeQuery("SELECT * FROM " + getTable() + ";");
            while (r.next()) {                
                if(gMoney.getInstance().usingUUID) {
                    contas.add(new Account(UUIDGetter.getPlayer(UUID.fromString(r.getString("player"))).getName(), Double.valueOf(r.getDouble("money"))));
                }else{
                    contas.add(new Account(r.getString("player"), Double.valueOf(r.getDouble("money"))));
                }
            }
            return contas;
        }catch(Exception e) {
            return null;
        }
    }
}