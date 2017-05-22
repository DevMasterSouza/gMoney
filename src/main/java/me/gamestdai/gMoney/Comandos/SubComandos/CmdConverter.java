package me.gamestdai.gMoney.Comandos.SubComandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.Enums.ConverterType;
import me.gamestdai.gMoney.Eventos.ConverterFinishEvent;
import me.gamestdai.gMoney.Eventos.ConverterInProgressEvent;
import me.gamestdai.gMoney.Eventos.ConverterStartEvent;
import me.gamestdai.gMoney.UUIDGetter;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author GamesTdai
 */
public class CmdConverter extends SubCommand {

    public CmdConverter() {
        super("converter", "Converter old plugin economy to gMoney economy", " <plugin>", "admin.converter");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        try {
            if (args.length == 0) {
                String msg = ChatColor.GOLD + "Converter Plugins:\n" + ChatColor.GOLD + "Iconomy\n" + ChatColor.GOLD + "Essentials";
                sender.sendMessage(msg);
                return true;
            }
            if (args[0].equalsIgnoreCase("iconomy")) {
                ThreadIconomy thread = new ThreadIconomy(sender);
                thread.start();
                Bukkit.getPluginManager().callEvent(new ConverterStartEvent(ConverterType.ICONOMY, sender, thread.getTime()));
                sender.sendMessage(ChatColor.GREEN + "Conversion started. DON'T STOP SERVER.");
            }else if(args[0].equalsIgnoreCase("essentials")) {
                ThreadEssentials thread = new ThreadEssentials(sender);
                thread.start();
                Bukkit.getPluginManager().callEvent(new ConverterStartEvent(ConverterType.ESSENTIALS, sender, thread.getTime()));
                sender.sendMessage(ChatColor.GREEN + "Conversion started. DON'T STOP SERVER.");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public class ThreadIconomy extends Thread implements Runnable {
        
        private CommandSender sender;
        private int time;
        
        public ThreadIconomy(CommandSender sender) {
            this.sender = sender;
        }

        public int getTime() {
            return time;
        }

        @Override
        public void run() {
            try {
                Class Queried = Class.forName("com.iCo6.system.Queried");
                Method accounts = Queried.getDeclaredMethod("accountList");
                accounts.setAccessible(true);
                List<String> lista = (List<String>) accounts.invoke(null);
                for (int i = lista.size() - 1; i >= 0; i--) {
                    String account = lista.get(i);
                    Method balanceM = Queried.getDeclaredMethod("getBalance", String.class);
                    balanceM.setAccessible(true);
                    Double balance = (Double) balanceM.invoke(null, account);
                    if (gMoney.getInstance().usingUUID == true) {
                        UUID uuid = UUIDGetter.getUUID(Bukkit.getOfflinePlayer(account));
                        gMoney.getInstance().economia.setMoney(uuid.toString(), balance);
                    } else {
                        gMoney.getInstance().economia.setMoney(account, balance);
                    }
                    time = (i / 5);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "gMoney - Converting Iconomy - Player " + account);
                    Bukkit.getPluginManager().callEvent(new ConverterInProgressEvent(ConverterType.ICONOMY, time, sender, i));
                    if(i % 10 == 0) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "gMoney - Converting Iconomy - Wait more " + (i / 5) + " " + ((i / 5) > 1 ? "seconds" : "second"));
                    }
                    if(i == 0) {
                        this.interrupt();
                        Bukkit.getPluginManager().callEvent(new ConverterFinishEvent(ConverterType.ICONOMY, this.sender));
                        if(sender instanceof Player && ((Player)sender).isOnline()) {
                            sender.sendMessage(ChatColor.RED + "Convert is finish :D");
                        }
                        continue;
                    }
                    this.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public class ThreadEssentials extends Thread implements Runnable {
        
        private CommandSender sender;
        private int time;
        
        public ThreadEssentials(CommandSender sender) {
            this.sender = sender;
        }

        public int getTime() {
            return time;
        }
        
        @Override
        public void run() {
            try {
                File directory = new File("plugins\\Essentials\\userdata\\");
                File[] files = directory.listFiles();
                for(int i = files.length - 1; i >= 0; i--) {
                    if(files[i].isDirectory()) {
                        continue;
                    }
                    YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(files[i]);
                    String playernick = null;
                    if (playerdata.contains("money")) {
                        if (Integer.parseInt(gMoney.getInstance().getServerVersion().split("\\.")[1]) >= 7) {
                            if (playerdata.contains("uuid")) {
                                String playername = files[i].getName().split("\\.")[0].trim();
                                String uuidplayer = UUIDGetter.getUUID(Bukkit.getOfflinePlayer(playername)).toString();
                                playernick = playername;
                                if (gMoney.getInstance().usingUUID) {
                                    gMoney.getInstance().economia.setMoney(playername, Double.parseDouble(playerdata.getString("money")));
                                } else {
                                    gMoney.getInstance().economia.setMoney(playername, Double.parseDouble(playerdata.getString("money")));
                                }
                            }else if(playerdata.contains("lastAccountName")) {
                                String playername = playerdata.getString("lastAccountName");
                                String uuidplayer = UUIDGetter.getUUID(Bukkit.getOfflinePlayer(playername)).toString();
                                playernick = playername;
                                if (gMoney.getInstance().usingUUID) {
                                    gMoney.getInstance().economia.setMoney(playername, Double.parseDouble(playerdata.getString("money")));
                                } else {
                                    gMoney.getInstance().economia.setMoney(playername, Double.parseDouble(playerdata.getString("money")));
                                }
                            }
                        } else {
                            String playername = files[i].getName().split("\\.")[0];
                            playernick = playername;
                            double money = Double.parseDouble(playerdata.getString("money"));
                            gMoney.getInstance().economia.setMoney(playername, money);
                        }
                    }else{
                        continue;
                    }
                    time = (i / 5);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "gMoney - Converting Essentials - Player " + playernick);
                    Bukkit.getPluginManager().callEvent(new ConverterInProgressEvent(ConverterType.ESSENTIALS, time, sender, i));
                    if(i % 10 == 0) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "gMoney - Converting Essentials - Wait more " + (i / 5) + " " + ((i / 5) > 1 ? "seconds" : "second"));
                    }
                    if(i == 0) {
                        this.interrupt();
                        Bukkit.getPluginManager().callEvent(new ConverterFinishEvent(ConverterType.ESSENTIALS, this.sender));
                        if(sender instanceof Player && ((Player)sender).isOnline()) {
                            sender.sendMessage(ChatColor.GOLD + "Convert is finish :D");
                        }
                        continue;
                    }
                    this.sleep(200);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
