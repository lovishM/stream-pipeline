package elements.aqua.model.pipeline.state.terminal;

import elements.aqua.model.pipeline.IModel;

import java.io.IOException;

public class Print<T> implements IModel, ITerminal<T> {

    private final Appendable appendable;
    private final char recordSeparator;

    private Print(Appendable appendable, char recordSeparator) {
        this.appendable = appendable;
        this.recordSeparator = recordSeparator;
    }

    public static <X, T extends Appendable> Print<X> printer(T appendable) {
        return printer(appendable, '\n');
    }

    public static <X, T extends Appendable> Print<X> printer(T appendable, char recordSeparator) {
        return new Print<>(appendable, recordSeparator);
    }

    @Override
    public Type type() { return Type.TERMINAL_STATE; }

    @Override
    public void consume(T data) {
        try {
            appendable.append(data.toString());
            appendable.append(recordSeparator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
