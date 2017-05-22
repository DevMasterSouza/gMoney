package me.gamestdai.gMoney.Comandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.Comandos.SubComandos.*;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CmdMoney implements CommandExecutor{
    
    private ArrayList<SubCommand> comandos = new ArrayList<>();
    
    public CmdMoney() {
        registerCommand(new CmdPermission());
        registerCommand(new CmdHelp());
        registerCommand(new CmdTop());
        registerCommand(new CmdGive());
        registerCommand(new CmdPay());
        registerCommand(new CmdReset());
        registerCommand(new CmdSet());
        registerCommand(new CmdConverter());
    }
    
    private HashMap<String, Long> players = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        Player player = (Player) cs;
        Economia eco = gMoney.getInstance().economia;
        if(strings.length == 0) {
            if (eco.hasAccount(player.getName())) {
                player.sendMessage(gMoney.getInstance().Msgs.get("PLAYER_MONEY").replaceAll("\\{money\\}", String.valueOf(gMoney.getInstance().formatar(eco.getMoney(player.getName())))));
            } else {
                player.sendMessage(gMoney.getInstance().Msgs.get("NO_ACCOUNT"));
                eco.createAccount(player.getName());
            }
            return true;
        }
        for(SubCommand cmd : comandos) {
            if(strings[0].equalsIgnoreCase(cmd.getComando())) {
                if(player.hasPermission("gMoney.cmd." + cmd.getPermission())) {
                    if(gMoney.getInstance().commandsWithCooldown) {
                        if(players.containsKey(player.getName().toLowerCase())) {
                            Long l = players.get(player.getName().toLowerCase());
                            if(((System.currentTimeMillis() - l) /1000) - gMoney.getInstance().cooldownTime >= 0) {
                                players.put(player.getName().toLowerCase(), System.currentTimeMillis());
                            }else{
                                int time = Math.abs((int) (((System.currentTimeMillis() - l) /1000) - gMoney.getInstance().cooldownTime));
                                player.sendMessage(gMoney.getInstance().Msgs.get("WFUTCA").replaceAll("\\{time\\}", time + ""));
                                return true;
                            }
                        }else{
                            players.put(player.getName().toLowerCase(), System.currentTimeMillis());
                        }
                    }
                    List<String> list = new ArrayList<String>();
                    Collections.addAll(list, strings);
                    list.remove(0);
                    String[] args = list.toArray(new String[list.size()]);
                    if(!cmd.onCommand(player, args)) {
                        player.sendMessage(gMoney.getInstance().Msgs.get("COMMAND_CORRECT_FORM").replaceAll("\\{command\\}",cmd.getComando()).replaceAll("\\{useform\\}", cmd.getForma_De_Uso()));
                    }
                }else{
                    player.sendMessage(gMoney.getInstance().Msgs.get("NO_PERMISSION"));
                }
                return true;
            }
        }
        String name = strings[0];
        if (eco.hasAccount(name)) {
            player.sendMessage(gMoney.getInstance().Msgs.get("OTHER_PLAYER_MONEY").replaceAll("\\{player\\}", name).replaceAll("\\{money\\}", gMoney.getInstance().formatar(eco.getMoney(name))));
        } else {
            player.sendMessage(gMoney.getInstance().Msgs.get("PLAYER_DONT_HAVA_ACCOUNT"));
        }
        return true;
    }
    
    public void registerCommand(SubCommand cmd) {
        comandos.add(cmd);
    }
    
    public ArrayList<SubCommand> getCommands() {
        return comandos;
    }

    /*public CmdMoney() {
        super("money");
        setAliases(new String[]{"dinheiro", "reais", "econ", "bal", "balance", "baltop"});
    }*/

    /*public void onCommand(Player player, String string, String[] strings) {
        Economia eco = gMoney.getInstance().economia;
        if (strings.length == 1) {
            if (eco.hasAccount(player.getName())) {
                player.sendMessage(ChatColor.GREEN + "Seu Dinheiro: " + eco.getMoney(player.getName()));
            } else {
                player.sendMessage(ChatColor.YELLOW + "Voce nao tinha uma conta antes, agora foi criada");
                eco.createAccount(player.getName());
            }
        }
        if (strings.length == 2) {
            if (strings[1].equalsIgnoreCase("top")) {
                player.sendMessage(ChatColor.GREEN + "=-=-= Top 10 mais ricos do servidor =-=-=");

                Map<String, Double> map = eco.getTop(10);
                for (String name : map.keySet()) {
                    player.sendMessage(ChatColor.GREEN + name + " - " + map.get(name));
                }
                return;
            }
            String name = strings[1];
            if (eco.hasAccount(name)) {
                player.sendMessage(ChatColor.GREEN + "Money de " + name + ": " + eco.getMoney(name));
            } else {
                player.sendMessage(ChatColor.RED + "Este jogador nao tem uma conta.");
            }
        }
        if ((strings.length == 3)
                && (strings[1].equalsIgnoreCase("reset"))) {
            if (player.hasPermission("gMoney.give")) {
                String target = strings[2];
                eco.resetAccount(target);
                player.sendMessage(ChatColor.YELLOW + "Voce resetou a conta de " + target);
            } else {
                player.sendMessage(ChatColor.RED + "Voce nao tem permissao para usar este comando!");
            }
        }
        if (strings.length == 4) {
            if (strings[1].equalsIgnoreCase("give")) {
                if (player.hasPermission("gMoney.give")) {
                    try {
                        String target = strings[2];
                        double money = Double.parseDouble(strings[3]);
                        eco.addMoney(target, money);
                        player.sendMessage(ChatColor.YELLOW + "Voce adicionou ao " + player + " R$" + money);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Somente Numeros.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Voce nao tem permissao para usar este comando!");
                }
            }
            if (strings[1].equalsIgnoreCase("pay")) {
                if (player.hasPermission("gMoney.pay")) {
                    try {
                        Player target = Bukkit.getPlayer(strings[2]);
                        double money = Double.parseDouble(strings[3]);
                        if (player != null) {
                            if (eco.removeMoney(player.getName(), money)) {
                                target.sendMessage(ChatColor.GREEN + "Voce recebeu de " + player.getName() + " R$" + money);
                                eco.addMoney(target.getName(), money);
                                player.sendMessage(ChatColor.GREEN + "Voce deu R$" + money + " para " + target.getName());
                            } else {
                                player.sendMessage(ChatColor.RED + "Voce nao tem dinheiro suficiente.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "O jogador nao esta online no momento.");
                        }
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Somente Numeros.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Voce nao tem permissao para usar este comando!");
                }
            }
            if (strings[1].equalsIgnoreCase("set")) {
                if (player.hasPermission("gMoney.set")) {
                    try {
                        String target = strings[2];
                        double money = Double.parseDouble(strings[3]);
                        eco.setMoney(target, money);
                        player.sendMessage(ChatColor.YELLOW + "Voce setou ao " + player + " R$" + money);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Somente Numeros.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Voce nao tem permissao para usar este comando!");
                }
            }
        }
    }*/
}
