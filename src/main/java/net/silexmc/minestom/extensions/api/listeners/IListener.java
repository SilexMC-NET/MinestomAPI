package net.silexmc.minestom.extensions.api.listeners;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;

public abstract class IListener<E extends Event> {
    public abstract Class<E> getEvent();

    public abstract void execute(E event);

    public void register() {
        MinecraftServer.getGlobalEventHandler().addListener(this.getEvent(), this::execute);
    }
}
