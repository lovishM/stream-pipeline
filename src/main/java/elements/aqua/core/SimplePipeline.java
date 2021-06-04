package elements.aqua.core;

import elements.aqua.model.pipeline.IModel;
import elements.aqua.model.pipeline.operation.Filter;
import elements.aqua.model.pipeline.operation.Transform;
import elements.aqua.model.pipeline.state.initial.InitialState;
import elements.aqua.model.pipeline.state.terminal.ITerminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplePipeline<I> {

    private final InitialState<I> initialState;
    private final ITerminal<I> terminalState;

    private final List<Signature> sequence = new ArrayList<>();
    private final Map<Integer, Filter<I>> filters = new HashMap<>();
    private final Map<Integer, Transform<I, I>> convertors = new HashMap<>();

    private boolean executionComplete = false;

    private SimplePipeline(InitialState<I> initialState, ITerminal<I> terminalState) {
       this.initialState = initialState;
       this.terminalState = terminalState;
    }

    public static <F, T extends InitialState<F>, H extends ITerminal<F>> SimplePipeline<F> create(
            T initialState,
            H terminalState) {

        return new SimplePipeline<>(initialState, terminalState);
    }

    public <F extends Filter<I>> void nextLink(F obj2) {
        Signature signature = new Signature(obj2.type(), obj2.hashCode());
        sequence.add(signature);
        filters.put(signature.hashCode, obj2);
    }

    public <F extends Transform<I, I>> void nextLink(F obj2) {
        Signature signature = new Signature(obj2.type(), obj2.hashCode());
        sequence.add(signature);
        convertors.put(signature.hashCode, obj2);
    }

    public synchronized void execute() {

        if (executionComplete) throw new IllegalStateException("Pipeline already executed");

        try {
            while (initialState.hasNext()) {
                I obj = initialState.next();
                boolean matched = false;

                for (Signature signature : sequence) {
                    if (signature.type == IModel.Type.FILTER) {
                        Filter<I> filter = filters.get(signature.hashCode);
                        if (!filter.matches(obj)) {
                            matched = false;
                            break;
                        }
                        matched = true;
                    } else if (signature.type == IModel.Type.TRANSFORM) {
                        Transform<I, I> convertor = convertors.get(signature.hashCode);
                        obj = convertor.convert(obj);
                    }
                }

                if (matched) {
                    terminalState.consume(obj);
                }
            }
        } finally {
            executionComplete = true;
        }
    }

    private static class Signature {

        final IModel.Type type;
        final int hashCode;

        public Signature(IModel.Type type, int hashCode) {
            this.type = type;
            this.hashCode = hashCode;
        }
    }
}
