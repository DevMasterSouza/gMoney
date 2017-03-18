package me.gamestdai.gMoney.Comandos.SubComandos;

import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author GamesTdai
 */
public class CmdPermission extends SubCommand{
    
    public CmdPermission() {
        super("permissions", "show all permissions", "", "admin.permissions");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GREEN).append("- gMoney Permissions -").append("\n");
        for(SubCommand cmd : gMoney.getInstance().cmdmoney.getCommands()) {
            sb.append(ChatColor.GREEN).append("money ").append(cmd.getComando()).append(" ").append(cmd.getForma_De_Uso()).append(" - gMoney.cmd.").append(cmd.getPermission()).append("\n");
        }
        sb.append(ChatColor.GREEN).append("- gMoney Permissions -").append("\n");
        player.sendMessage(sb.toString());
        return true;
    }
}
