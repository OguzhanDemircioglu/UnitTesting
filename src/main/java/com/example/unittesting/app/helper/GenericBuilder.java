package com.example.unittesting.app.helper;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class GenericBuilder<T> {
    private final T instance;

    private GenericBuilder(Class<T> clazz) {
        try {
            this.instance = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create instance of " + clazz.getName(), e);
        }
    }

    public static <T> GenericBuilder<T> of(Class<T> clazz) {
        return new GenericBuilder<>(clazz);
    }

    public GenericBuilder<T> from(T original) {
        try {
            Field[] fields = original.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(instance, field.get(original));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to copy properties from original object", e);
        }
        return this;
    }

    public GenericBuilder<T> with(Consumer<T> setter) {
        setter.accept(instance);
        return this;
    }

    public T build() {
        return instance;
    }
}
