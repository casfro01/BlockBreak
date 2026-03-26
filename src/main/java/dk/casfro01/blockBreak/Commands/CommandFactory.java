package dk.casfro01.blockBreak.Commands;


import dk.casfro01.blockBreak.Commands.Abstractions.AbstractCommand;
import dk.casfro01.blockBreak.Commands.Abstractions.CommandWithService;
import dk.casfro01.blockBreak.Commands.Abstractions.ICommand;
import dk.casfro01.blockBreak.Commands.Admin.NotAnAdminCommand;
import dk.casfro01.blockBreak.Commands.Registry.CommandAdminRegistry;
import dk.casfro01.blockBreak.Commands.Registry.CommandRegistry;
import dk.casfro01.blockBreak.Commands.Registry.IRegistry;
import dk.casfro01.blockBreak.Options.ConfigOptions;
import dk.casfro01.blockBreak.Options.StyleOptions;
import dk.casfro01.blockBreak.Service.Service;

public class CommandFactory {
    private static Service service;
    private static StyleOptions styleOptions;
    private static ConfigOptions configOptions;

    public static AbstractCommand newCommand(String command, CommandType type) {
        IRegistry<ICommand> registry = type.getRegistry();
        try {
            ICommand c = registry.getCommands().get(command).getDeclaredConstructor().newInstance();
            if (c instanceof CommandWithService) {
                if (((CommandWithService<?>) c).getServiceType().equals(Service.class)) {
                    ((CommandWithService<Service>) c).setService(service);
                }
//                else if (((CommandWithService<?>) c).getServiceType().equals(......class)) {
//                    ((CommandWithService<K.....>) c).setService(adminService);
//                }
            }
            if (c instanceof AbstractCommand){
                AbstractCommand abs = (AbstractCommand) c;
                abs.setStyle(styleOptions);
                return abs;
            }

        } catch (Exception e) {
            // Logger?
            //throw new NotACommandException("Invalid command: " + command + "\n" + e.getMessage());
        }
        AbstractCommand NotACommand = type == CommandType.USER ? new NotACommand() : new NotAnAdminCommand();
        NotACommand.setStyle(styleOptions);
        return NotACommand;
    }


    public static void setService(Service service) {
        CommandFactory.service = service;
    }

    public static void setStyleOptions(StyleOptions styleOptions) {
        CommandFactory.styleOptions = styleOptions;
    }

    public static void setConfigOptions(ConfigOptions configOptions) {
        if (configOptions != null){
            CommandFactory.configOptions = configOptions;
            service.setConfig(configOptions);
        }
    }


    public static enum CommandType{
        ADMIN(CommandAdminRegistry.getInstance()),
        USER(CommandRegistry.getInstance());

        private IRegistry<ICommand> registry;

        CommandType(IRegistry<ICommand> reg){
            registry = reg;
        }

        IRegistry<ICommand> getRegistry(){
            return registry;
        }
    }
}
