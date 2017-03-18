package me.gamestdai.gMoney.Comandos.SubComandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author GamesTdai
 */
public class CmdHelp extends SubCommand{

    public CmdHelp() {
        super("help", "show all commands", "", "help");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GREEN).append("- gMoney Help -").append("\n");
        for(SubCommand cmd : gMoney.getInstance().cmdmoney.getCommands()) {
            sb.append(ChatColor.GREEN).append("money ").append(cmd.getComando()).append(" ").append(cmd.getForma_De_Uso()).append(" - ").append(cmd.getDescricao()).append("\n");
        }
        sb.append(ChatColor.GREEN).append("- gMoney Help -").append("\n");
        player.sendMessage(sb.toString());
        return true;
    }
}
