package dk.casfro01.blockBreak.Commands.Abstractions;

public interface ICommand {
    void execute();

    String getName();
    String getDescription();
    String getPermission();
}
