package elements.aqua.model.pipeline.state.initial;

import elements.aqua.core.ComplexPipeline;
import elements.aqua.model.pipeline.IModel;

import java.util.Iterator;

public class Stream<K> implements IModel, InitialState<K> {

    private final Iterator<K> iterator;
    private Stream (Iterator<K> iterator) {
        this.iterator = iterator;
    }

    public static <F, T extends Iterable<F>> Stream<F> of(T o) {
        return new Stream<>(o.iterator());
    }

    public static <F, T extends Iterable<F>> ComplexPipeline<F> toPipeline(T o) {
        return ComplexPipeline.createPipe(new Stream<>(o.iterator()));
    }

    @Override
    public Type type() { return Type.INIT_STATE; }

    @Override
    public boolean hasNext() { return iterator.hasNext(); }

    @Override
    public K next() { return iterator.next(); }
}
