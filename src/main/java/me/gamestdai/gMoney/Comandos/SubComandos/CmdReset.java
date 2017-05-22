package me.gamestdai.gMoney.Comandos.SubComandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 *
 * @author GamesTdai
 */
public class CmdReset extends SubCommand{

    public CmdReset() {
        super("reset", "reset account of player", "<player>", "admin.reset");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Economia eco = gMoney.getInstance().economia;
        String target = args[0];
        if (Bukkit.getOfflinePlayer(target).hasPlayedBefore()) {
            eco.resetAccount(target);
            sender.sendMessage(gMoney.getInstance().Msgs.get("Reset_Account".toUpperCase()).replaceAll("\\{player\\}", target));
        } else {
            sender.sendMessage(gMoney.getInstance().Msgs.get("Player_Dont_Join_Before".toUpperCase()));
        }
        return true;
    }
    
}
