package me.gamestdai.gMoney.Comandos.SubComandos;

import java.text.DecimalFormat;
import java.util.Map;
import me.gamestdai.gMoney.Abstratas.SubCommand;
import me.gamestdai.gMoney.Interfaces.Economia;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author GamesTdai
 */
public class CmdTop extends SubCommand{

    public CmdTop() {
        super("top", "show top five rich of server", "", "top");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Economia eco = gMoney.getInstance().economia;
        StringBuilder sb = new StringBuilder();
        sb.append(gMoney.getInstance().Msgs.get("Top_5_Rich".toUpperCase())).append("\n");

        Map<String, Double> map = eco.getTop(0, 5);
        for (String name : map.keySet()) {
            
            sb.append(ChatColor.GRAY).append(name).append(" - ").append(ChatColor.GREEN).append(gMoney.getInstance().formatar(map.get(name))).append("\n");
        }
        sb.append(gMoney.getInstance().Msgs.get("Top_5_Rich".toUpperCase()));
        player.sendMessage(sb.toString());
        return true;
    }
    
}
