package elements.aqua.core;

import elements.aqua.model.pipeline.operation.Filter;
import elements.aqua.model.pipeline.operation.Transform;
import elements.aqua.model.pipeline.state.initial.InitialState;
import elements.aqua.model.pipeline.state.terminal.ITerminal;

import java.util.Optional;
import java.util.function.Function;

public class ComplexPipeline<T> {

    private final InitialState<T> initialState;

    private ComplexPipeline(InitialState<T> initialState) {
        this.initialState = initialState;
    }

    public static <X> ComplexPipeline<X> createPipe(InitialState<X> initialState) {
        return new ComplexPipeline<>(initialState);
    }

    public OperationalPipe<T, T> next(Filter<T> filter) {
        OperationalPipe<T, T> pipe = OperationalPipe.filter(filter, this);
        this.next = pipe;
        return pipe;
    }

    public <S> OperationalPipe<T, S> next(Transform<T, S> transform) {
        OperationalPipe<T, S> pipe = OperationalPipe.transform(transform, this);
        this.next = pipe;
        return pipe;
    }

    private OperationalPipe<T, ?> next;
    public void evaluate() {
        if (next != null) {
            while (initialState.hasNext()) {
                next.evaluate(initialState.next());
            }
        }
    }

    /**
     * Internal Pipe useful for building a pipeline
     *
     * @param <L>
     * @param <R>
     */
    public static class OperationalPipe<L, R> {

        private ComplexPipeline<?> parent;
        private final Function<L, Optional<R>> function;
        private OperationalPipe (Function<L, Optional<R>> function, ComplexPipeline<?> parent) {
            this.function = function;
            this.parent = parent;
        }

        private static <X> OperationalPipe<X, X> filter(Filter<X> filter, ComplexPipeline<?> parent) {
            Function<X, Optional<X>> function = (x) -> {
                if (filter.matches(x)) {
                    return Optional.of(x);
                } else {
                    return Optional.empty();
                }
            };

            return new OperationalPipe<>(function, parent);
        }

        public OperationalPipe<R, R> next(Filter<R> filter) {
            OperationalPipe<R, R> pipe = OperationalPipe.filter(filter, parent);
            this.next = pipe;
            return pipe;
        }

        public static <T, S> OperationalPipe<T, S> transform(Transform<T, S> transform, ComplexPipeline<?> parent) {
            Function<T, Optional<S>> function = (x) -> {
                try {
                    return Optional.of(transform.convert(x));
                } catch (Exception e) {
                    return Optional.empty();
                }
            };

            return new OperationalPipe<>(function, parent);
        }

        public <X> OperationalPipe<R, X> next(Transform<R, X> transform) {
            OperationalPipe<R, X> pipe = OperationalPipe.transform(transform, parent);
            this.next = pipe;
            return pipe;
        }

        public ComplexPipeline<?> consume(ITerminal<R> terminal) {
            Function<R, Optional<Void>> function = (x) -> {
                terminal.consume(x);
                return Optional.empty();
            };

            this.next = new OperationalPipe<>(function, parent);
            return parent;
        }

        private OperationalPipe<R, ?> next;
        void evaluate(L input) {
            Optional<R> optional = function.apply(input);
            if (optional.isPresent() && next != null) {
                next.evaluate(optional.get());
            }
        }
    }
}
