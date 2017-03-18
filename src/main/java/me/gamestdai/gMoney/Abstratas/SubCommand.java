package me.gamestdai.gMoney.Abstratas;

import org.bukkit.entity.Player;

/**
 *
 * @author GamesTdai
 */
public abstract class SubCommand {
    
    private String comando;
    private String descricao;
    private String Forma_De_Uso;
    private String permission;
    
    public SubCommand(String comando, String descricao, String Forma_De_Uso, String permission) {
        this.comando = comando;
        this.descricao = descricao;
        this.Forma_De_Uso = Forma_De_Uso;
        this.permission = permission;
    }
    
    public abstract boolean onCommand(Player player, String[] args);

    public String getComando() {
        return comando;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getForma_De_Uso() {
        return Forma_De_Uso;
    }

    public String getPermission() {
        return permission;
    }
}
