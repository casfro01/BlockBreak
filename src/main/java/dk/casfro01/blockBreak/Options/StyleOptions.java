package dk.casfro01.blockBreak.Options;

import dk.casfro01.blockBreak.Configs.IConfig;
import dk.casfro01.blockBreak.Configs.StyleConfig;
import org.bukkit.configuration.file.FileConfiguration;

public class StyleOptions {
    private String ErrorColor = "&c";
    private String SuccessColor = "&a";
    private String PrimaryColor = "&c";
    private String SecondaryColor = "&c";
    private String AdminPrefix = "&c";
    private String Prefix = "&c";
    private String PermissionMessage = "&c";
    private IConfig styleConfig;

    public StyleOptions(StyleConfig styleConfig) {
        this.styleConfig = styleConfig;
        reload();
    }


    public void reload(){
        FileConfiguration config = styleConfig.getConfig();
        ErrorColor = config.getString("ErrorColor");
        PrimaryColor = config.getString("PrimaryColor");
        SecondaryColor = config.getString("SecondaryColor");
        AdminPrefix = config.getString("AdminCommandsPrefix");
        PermissionMessage = config.getString("PermissionMessage");
        SuccessColor = config.getString("SuccessColor");
        Prefix = config.getString("Prefix");
    }


    public String getErrorColor() {
        return ErrorColor;
    }

    public String getPrimaryColor() {
        return PrimaryColor;
    }

    public String getSecondaryColor() {
        return SecondaryColor;
    }

    public String getAdminPrefix() {
        return AdminPrefix;
    }

    public String getSuccessColor(){
        return SuccessColor;
    }

    public String getPermissionMessage() {
        return PermissionMessage;
    }

    public String getPrefix() {
        return Prefix;
    }
}
