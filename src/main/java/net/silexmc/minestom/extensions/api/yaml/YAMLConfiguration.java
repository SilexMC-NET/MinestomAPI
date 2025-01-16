package net.silexmc.minestom.extensions.api.yaml;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class YAMLConfiguration {
    private final Map<String, Object> values = Maps.newHashMap();

    private YAMLConfiguration(File file) {
        Yaml sneakyYaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(file)) {
            Map<String, Object> var = sneakyYaml.load(inputStream);
            if (var != null)
                this.values.putAll(var);
        } catch (IOException ignored) {}
    }

    public boolean isList(String path) {
        return this.get(path, List.class) != null;
    }

    public List<String> getList(String path) {
        List<?> value = this.get(path, List.class);
        if (value == null)
            return Lists.newArrayList();

        List<String> values = Lists.newArrayList();
        for (Object entry : value) {
            if (entry instanceof String)
                values.add((String) entry);
        }
        return values;
    }

    public boolean isString(String path) {
        return this.get(path, String.class) != null;
    }

    public String getString(String path) {
        String value = this.get(path, String.class);
        return value == null ? "" : value;
    }

    public boolean isBoolean(String path) {
        return this.get(path, Boolean.class) != null;
    }

    public boolean getBoolean(String path) {
        return Boolean.TRUE.equals(this.get(path, Boolean.class));
    }

    public boolean isInteger(String path) {
        return this.get(path, Integer.class) != null;
    }

    public Integer getInteger(String path) {
        Integer value = this.get(path, Integer.class);
        return value == null ? -1 : value;
    }

    public boolean isDouble(String path) {
        return this.get(path, Double.class) != null;
    }

    public Double getDouble(String path) {
        Double value = this.get(path, Double.class);
        return value == null ? -1 : value;
    }

    public boolean isFloat(String path) {
        return this.get(path, Float.class) != null;
    }

    public Float getFloat(String path) {
        Float value = this.get(path, Float.class);
        return value == null ? -1 : value;
    }

    private <T> T get(String path, Class<T> clazz) {
        Object object = this.get(path);
        if (clazz.isInstance(object))
            return (T) object;
        return null;
    }

    private Object get(String path) {
        String[] keys = path.split("\\."); Map<String, Object> current = this.values;

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (i == keys.length - 1)
                return current.get(key);

            if (!(current.get(key) instanceof Map)) {
                return null;
            }
            current = (Map<String, Object>) current.get(key);
        }
        return null;
    }

    public static YAMLConfiguration of(File file) {
        return new YAMLConfiguration(file);
    }
}
