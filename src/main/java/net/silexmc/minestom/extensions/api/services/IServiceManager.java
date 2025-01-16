package net.silexmc.minestom.extensions.api.services;

public interface IServiceManager {
    <T> T get(Class<T> clazz);

    <T> void register(Class<T> clazz, T service);

    <T> T unregister(Class<T> clazz);
}
