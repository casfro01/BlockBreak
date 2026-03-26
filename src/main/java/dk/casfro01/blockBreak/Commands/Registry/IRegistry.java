package dk.casfro01.blockBreak.Commands.Registry;

import java.util.Map;

public interface IRegistry<T> {

    void registerCommand(T command);

    Map<String, Class<? extends T>> getCommands();
}
