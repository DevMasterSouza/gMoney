package me.gamestdai.gMoney.Comandos.SubComandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.Eventos.PlayerGiveMoneyEvent;
import me.gamestdai.gMoney.Eventos.PlayerSetMoneyEvent;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 *
 * @author GamesTdai
 */
public class CmdSet extends SubCommand {

    public CmdSet() {
        super("set", "set money to player", "<player> <amount>", "admin.set");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        try {
            Economia eco = gMoney.getInstance().economia;
            String target = args[0];
            if (Bukkit.getOfflinePlayer(target).hasPlayedBefore()) {
                double money = Double.parseDouble(args[1]);
                eco.setMoney(target, money);
                Bukkit.getPluginManager().callEvent(new PlayerSetMoneyEvent(sender, Bukkit.getOfflinePlayer(target), money));
                sender.sendMessage(gMoney.getInstance().Msgs.get("Set_Money".toUpperCase()).replaceAll("\\{player\\}", target).replaceAll("\\{money\\}", money + ""));
            } else {
                sender.sendMessage(gMoney.getInstance().Msgs.get("Player_Dont_Join_Before".toUpperCase()));
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(gMoney.getInstance().Msgs.get("Only_Number".toUpperCase()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
