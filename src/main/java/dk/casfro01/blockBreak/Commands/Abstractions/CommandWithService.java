package dk.casfro01.blockBreak.Commands.Abstractions;

public interface CommandWithService<T> {

    void setService(T service);
    Class<T> getServiceType();
}
