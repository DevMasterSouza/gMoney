package me.gamestdai.gMoney.Comandos.SubComandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.Eventos.PlayerPayMoneyEvent;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author GamesTdai
 */
public class CmdPay extends SubCommand{

    public CmdPay() {
        super("pay", "pay to player", "<player> <amount>", "pay");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(gMoney.getInstance().Msgs.get("ONLY_PLAYER_USE_COMMANDS"));
            return true;
        }
        Player player = (Player)sender;
        try {
            Economia eco = gMoney.getInstance().economia;
            Player target = Bukkit.getPlayer(args[0]);
            double money = Double.parseDouble(args[1]);
            if (target != null) {
                PlayerPayMoneyEvent ppm = new PlayerPayMoneyEvent(player, target, money, false);
                Bukkit.getPluginManager().callEvent(ppm);
                if (!ppm.isCancelled()) {
                    if (eco.removeMoney(player.getName(), money)) {
                        ppm.setFailedTransfer(false);
                        target.sendMessage(gMoney.getInstance().Msgs.get("Received_Money".toUpperCase()).replaceAll("\\{player\\}", player.getName()).replaceAll("\\{money\\}", money + ""));
                        eco.addMoney(target.getName(), money);
                        player.sendMessage(gMoney.getInstance().Msgs.get("Send_Money".toUpperCase()).replaceAll("\\{player\\}", target.getName()).replaceAll("\\{money\\}", money + ""));
                    } else {
                        ppm.setFailedTransfer(true);
                        player.sendMessage(gMoney.getInstance().Msgs.get("Dont_Have_Money".toUpperCase()));
                    }
                }
            } else {
                player.sendMessage(gMoney.getInstance().Msgs.get("Player_No_Online".toUpperCase()));
            }
        } catch (NumberFormatException e) {
            player.sendMessage(gMoney.getInstance().Msgs.get("Only_Number".toUpperCase()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}
