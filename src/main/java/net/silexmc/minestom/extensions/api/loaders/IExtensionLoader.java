package net.silexmc.minestom.extensions.api.loaders;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.silexmc.minestom.extensions.api.services.IServiceManager;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public abstract class IExtensionLoader extends URLClassLoader {
    public IExtensionLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public abstract Path getDirectory();

    public abstract ComponentLogger getLogger();

    public abstract IServiceManager getServiceManager();
}
