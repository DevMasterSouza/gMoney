package me.gamestdai.gMoney.Interfaces;

import java.util.List;
import java.util.Map;
import me.gamestdai.gMoney.Objetos.Account;

public abstract interface Economia {

    public abstract double getMoney(String paramString);

    public abstract double setMoney(String paramString, double paramDouble);

    public abstract boolean removeMoney(String paramString, double paramDouble);

    public abstract double addMoney(String paramString, double paramDouble);

    public abstract Map<String, Double> getTop(int startin, int stopin);

    public abstract boolean hasAccount(String paramString);

    public abstract boolean createAccount(String paramString);

    public abstract boolean resetAccount(String paramString);
    
    public abstract List<Account> getAllAccounts();
}
