package net.silexmc.minestom.extensions.api;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.silexmc.minestom.extensions.api.listeners.IListener;
import net.silexmc.minestom.extensions.api.loaders.IExtensionLoader;
import net.silexmc.minestom.extensions.api.results.ResourceResult;
import net.silexmc.minestom.extensions.api.services.IServiceManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public abstract class Extension {
    public abstract void init();

    public abstract void shutdown();

    public Path getDirectory() {
        return this.getLoader().getDirectory();
    }

    public ComponentLogger getLogger() {
        return this.getLoader().getLogger();
    }

    public IServiceManager getServiceManager() {
        return this.getLoader().getServiceManager();
    }

    public ResourceResult saveResource(String resource, File target) {
        File directory = this.getDirectory().toFile();
        if (!directory.exists())
            directory.mkdirs();

        if (target.exists())
            return ResourceResult.FILE_ALREADY_EXIST;

        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(resource)) {
            if (stream == null)
                return ResourceResult.FILE_NOT_FOUND;
            Files.copy(stream, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            return ResourceResult.FILE_NOT_FOUND;
        }
        return ResourceResult.FILE_CREATED;
    }

    protected void registerCommands(Command... commands) {
        MinecraftServer.getCommandManager().register(commands);
    }

    protected void registerListeners(IListener<?>... listeners) {
        Arrays.stream(listeners).forEach(IListener::register);
    }

    private @NotNull IExtensionLoader getLoader() {
        IExtensionLoader loader = (IExtensionLoader) this.getClass().getClassLoader();;
        return loader;
    }
}
