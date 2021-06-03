package elements.aqua.model.pipeline.transformer;

import elements.aqua.model.pipeline.IModel;

import java.util.function.Function;

public class Convert<I> implements IModel {

    private final Function<I, I> function;
    private Convert (Function<I, I> function) { this.function = function; }

    public static <T> Convert<T> createConvertor(Function<T, T> function) { return new Convert<>(function); }
    public I convert(I obj) { return function.apply(obj); }

    @Override
    public Type type() { return Type.CONVERTOR; }
}
