package me.gamestdai.gMoney;

import me.gamestdai.gMoney.Comandos.CmdMoney;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.Objetos.Config;
import me.gamestdai.gMoney.Objetos.ConfigEconomia;
import me.gamestdai.gMoney.Objetos.MySQL;
import me.gamestdai.gMoney.Objetos.MySQLEconomia;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class gMoney extends JavaPlugin {

    private static gMoney instance;
    public Economia economia;
    private Config config;
    public CmdMoney cmdmoney;
    public boolean usingUUID;
    public boolean commandsWithCooldown;
    public int cooldownTime;
    public ArrayList<String> aliases = new ArrayList<>();

    public HashMap<String, String> Msgs = new HashMap<>();

    @Override
    public void onEnable() {

        instance = this;

        try {
            Metrics m = new Metrics(this);
            m.start();
        } catch (Exception ex) {
        }

        this.config = new Config(new File(getDataFolder(), "config.yml"));
        Config msg = new Config(new File(getDataFolder(), "messages.yml"));
        
        adicionarPadroes(this.config, msg);

        cmdmoney = new CmdMoney();

        PluginCommand pcmd = getCommand("gmoney");

        pcmd.setExecutor(cmdmoney);

        for (String str : config.getStringList("CommandAliases")) {
            aliases.add(str.toLowerCase());
        }
        
        if(config.getBoolean("CommandAliasesUseNMS")) {
            for(String cmd : aliases) {
                registrarAliase(cmd.trim());
            }
        }else {
            getServer().getPluginManager().registerEvents(new Listeners(), this);
        }

        usingUUID = config.getBoolean("UsingUUID");
        commandsWithCooldown = config.getBoolean("CommandsCooldown");
        cooldownTime = config.getInt("CooldownTime");

        if (Integer.parseInt(getServerVersion().split("\\.")[1]) < 7) {
            usingUUID = false;
            System.out.println("using UUID disable because server is version " + getServerVersion());
        }

        if (this.config.getBoolean("MySQL.Enable") == true) {
            try {
                this.economia = new MySQLEconomia(new MySQL(this.config.getString("MySQL.Ip"), "3306", this.config.getString("MySQL.DataBase"), this.config.getString("MySQL.User"), this.config.getString("MySQL.Password")), this.config.getString("MySQL.Table"));
            } catch (Exception e) {
                System.out.println("Nao foi possivel conectar no MySQL, entao o plugin foi habilitado em modo config.");
                this.economia = new ConfigEconomia(new Config(new File(getDataFolder(), "money.yml")));
            }
        } else {
            this.economia = new ConfigEconomia(new Config(new File(getDataFolder(), "money.yml")));
        }
        if (config.getBoolean("UsingVault")) {
            if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
                Bukkit.getServer().getServicesManager().register(Economy.class, new VaultConexao(), gMoney.getInstance(), ServicePriority.Highest);
            }
        }
        
        Msgs.put("NO_PERMISSION", changeColor(msg.getString("NoPermission")));
        Msgs.put("ONLY_PLAYER_USE_COMMANDS", changeColor(msg.getString("OnlyPlayerUseCommands")));
        Msgs.put("PLAYER_MONEY", changeColor(msg.getString("PlayerMoney")));
        Msgs.put("NO_ACCOUNT", changeColor(msg.getString("NoAccount")));
        Msgs.put("COMMAND_CORRECT_FORM", changeColor(msg.getString("CommandCorrectForm")));
        Msgs.put("OTHER_PLAYER_MONEY", changeColor(msg.getString("OtherPlayerMoney")));
        Msgs.put("PLAYER_DONT_HAVA_ACCOUNT", changeColor(msg.getString("PlayerDontHaveAccount")));
        Msgs.put("Player_Dont_Join_Before".toUpperCase(), changeColor(msg.getString("PlayerDontJoinBefore")));
        Msgs.put("On_Give_Money".toUpperCase(), changeColor(msg.getString("OnGiveMoney")));
        Msgs.put("Only_Number".toUpperCase(), changeColor(msg.getString("OnlyNumber")));
        Msgs.put("Player_No_Online".toUpperCase(), changeColor(msg.getString("PlayerNoOnline")));
        Msgs.put("Dont_Have_Money".toUpperCase(), changeColor(msg.getString("DontHaveMoney")));
        Msgs.put("Send_Money".toUpperCase(), changeColor(msg.getString("SendMoney")));
        Msgs.put("Received_Money".toUpperCase(), changeColor(msg.getString("ReceivedMoney")));
        Msgs.put("Reset_Account".toUpperCase(), changeColor(msg.getString("ResetAccount")));
        Msgs.put("Set_Money".toUpperCase(), changeColor(msg.getString("SetMoney")));
        Msgs.put("WFUTCA".toUpperCase(), changeColor(msg.getString("WaitToUseCommandAgain")));
        Msgs.put("Top_5_Rich".toUpperCase(), changeColor(msg.getString("Top5Rich")));
    }

    public void onDisable() {
        if (this.config.getBoolean("MySQL.Enable") == true) {
            if(economia instanceof MySQLEconomia) {
                if (((MySQLEconomia) this.economia).getMysql().checkConnection()) {
                    ((MySQLEconomia) this.economia).getMysql().closeConnection();
                }
            }
        }
    }

    public static gMoney getInstance() {
        return instance;
    }

    public void adicionarPadroes(Config config, Config msg) {
        config.adicionarDefault("MySQL.Enable", Boolean.valueOf(false));
        config.adicionarDefault("MySQL.Ip", "localhost");
        config.adicionarDefault("MySQL.User", "root");
        config.adicionarDefault("MySQL.Password", "12345");
        config.adicionarDefault("MySQL.DataBase", "gmoney");
        config.adicionarDefault("MySQL.Table", "gmoney");

        config.adicionarDefault("UsingUUID", Boolean.valueOf(false));
        config.adicionarDefault("UsingVault", Boolean.valueOf(true));
        config.adicionarDefault("CommandsCooldown", false);
        config.adicionarDefault("CooldownTime", 3);
        config.adicionarDefault("CommandAliasesUseNMS", false);
        ArrayList<String> lista = new ArrayList<String>();
        lista.add("money");
        lista.add("eco");
        lista.add("coins");
        lista.add("cash");
        lista.add("tokens");
        config.adicionarDefault("CommandAliases", lista);

        config.save();

        msg.adicionarDefault("NoPermission", "&7[&2gMoney&7] &cYou dont have permission.");
        msg.adicionarDefault("OnlyPlayerUseCommands", "&7[&2gMoney&7] &cOnly players can use this command.");
        msg.adicionarDefault("PlayerMoney", "&7[&2gMoney&7] &eYour Money is: {money}");
        msg.adicionarDefault("NoAccount", "&7[&2gMoney&7] &aYou did not have a account before, but now was created.");
        msg.adicionarDefault("CommandCorrectForm", "&7[&2gMoney&7] &cUse: {command} {useform}.");
        msg.adicionarDefault("OtherPlayerMoney", "&7[&2gMoney&7] &eMoney of {player} is {money}.");
        msg.adicionarDefault("PlayerDontHaveAccount", "&7[&2gMoney&7] &cThis player don't have account.");
        msg.adicionarDefault("PlayerDontJoinBefore", "&7[&2gMoney&7] &cThis player don't join before.");
        msg.adicionarDefault("PlayerNoOnline", "&7[&2gMoney&7] &cThis player it's offline.");
        msg.adicionarDefault("OnGiveMoney", "&7[&2gMoney&7] &eYou give to {player} $ {money}.");
        msg.adicionarDefault("OnlyNumber", "&7[&2gMoney&7] &cPlease, only numbers.");
        msg.adicionarDefault("DontHaveMoney", "&7[&2gMoney&7] &cYou don't have money.");
        msg.adicionarDefault("SendMoney", "&7[&2gMoney&7] &eYou send $ {money} to {player}.");
        msg.adicionarDefault("ReceivedMoney", "&7[&2gMoney&7] &eYou received of {player} $ {money}.");
        msg.adicionarDefault("ResetAccount", "&7[&2gMoney&7] &eYou reset account of {player}.");
        msg.adicionarDefault("SetMoney", "&7[&2gMoney&7] &eYou set to {player} $ {money}.");
        msg.adicionarDefault("WaitToUseCommandAgain", "&7[&2gMoney&7] &cWait {time} to use this command again.");
        msg.adicionarDefault("Top5Rich", "&6=-=-= Top 5 more rich of server =-=-=");

        msg.save();
    }

    public String getServerVersion() {
        return Bukkit.getServer().getVersion().split("MC: ")[1].replaceAll("\\)", "").trim();
    }
    
    private Class getCraftBukkit(String classe) {
        try {
            String packageName = getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            Class bukkitclass = Class.forName("org.bukkit.craftbukkit." + version + "." + classe);
            return bukkitclass;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
    
    private void registrarAliase(String cmd) {
        try {
            Field f = getCraftBukkit("CraftServer").getDeclaredField("commandMap");
            f.setAccessible(true);
            SimpleCommandMap scm = (SimpleCommandMap) f.get(getServer());
            Command cmdob = new Command(cmd) {

                @Override
                public boolean execute(CommandSender cs, String string, String[] strings) {
                    cmdmoney.onCommand(cs, null, string, strings);
                    return true;
                }
            };
            scm.register(getDescription().getName().toLowerCase(), cmdob);

        } catch (Exception ex) {
        }
    }
    
    public String formatar(Double numero) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        Double balance = numero;
        String formatted = formatter.format(balance);

        if (formatted.endsWith(".")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        return formatted;
    }

    private String changeColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
