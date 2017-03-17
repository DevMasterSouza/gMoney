package me.gamestdai.gMoney.Skript.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.gamestdai.gMoney.gMoney;
import org.bukkit.event.Event;

/**
 *
 * @author Games Tdai
 */
public class EffGiveMoney extends Effect{
    
    private Expression<String> player;
    private Expression<Integer> money;

    @Override
    protected void execute(Event event) {
        String playernick = this.player.getSingle(event);
        Integer money = this.money.getSingle(event);
        if(playernick != null && money != null) {
            gMoney.getInstance().economia.addMoney(playernick, money);
        } 
    }

    @Override
    public String toString(Event event, boolean bln) {
        return "gmoney give";
    }

    @Override
    public boolean init(Expression<?>[] exprsns, int i, Kleenean kln, SkriptParser.ParseResult pr) {
        player = (Expression<String>) exprsns[1];
        money  = (Expression<Integer>) exprsns[0];
        return true;
    }
    
}
