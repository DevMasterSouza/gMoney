package me.gamestdai.gMoney.Objetos;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author GamesTdai
 */
public class Config extends YamlConfiguration {

    private File file;

    public Config(File file) {
        this.file = file;
        file.getParentFile().mkdirs();
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.err.println("Ocorreu um erro em criar a " + file.getPath());
            }
        }
        try {
            load(file);
        } catch (Exception ex) {
            System.err.println("Ocorreu um erro carregar a " + getFile().getPath());
        }
    }

    public void save() {
        try {
            save(getFile());
        } catch (IOException ex) {
            System.err.println("Ocorreu um erro ao salvar a " + getFile().getPath());
        }
    }

    public File getFile() {
        return file;
    }

    public void reload() {
        try {
            load(getFile());
        } catch (Exception ex) {
            System.err.println("Ocorreu um erro ao dar reload na " + getFile().getPath());
        }
    }

    public void adicionarDefault(String lugar, Object value) {
        if (!contains(lugar)) {
            set(lugar, value);
        }
    }
}
