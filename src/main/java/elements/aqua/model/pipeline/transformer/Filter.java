package elements.aqua.model.pipeline.transformer;

import elements.aqua.model.pipeline.IModel;

import java.util.function.Predicate;

public class Filter<T> implements IModel {

    private final Predicate<T> predicate;
    private Filter (Predicate<T> predicate) { this.predicate = predicate; }

    public static <T> Filter<T> createFilter(Predicate<T> predicate) { return new Filter<>(predicate); }
    public boolean matches(T data) { return predicate.test(data); }

    @Override
    public Type type() { return Type.FILTER; }
}
