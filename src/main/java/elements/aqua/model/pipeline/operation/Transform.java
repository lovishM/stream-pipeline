package elements.aqua.model.pipeline.operation;

import elements.aqua.model.pipeline.IModel;

import java.util.function.Function;

public class Transform<L, R> implements IModel {

    private final Function<L, R> function;
    private Transform(Function<L, R> function) { this.function = function; }

    public static <L0, R0> Transform<L0, R0> createConversion(Function<L0, R0> function) { return new Transform<>(function); }
    public R convert(L obj) { return function.apply(obj); }

    @Override
    public Type type() { return Type.TRANSFORM; }
}
