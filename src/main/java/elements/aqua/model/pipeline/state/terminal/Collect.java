package elements.aqua.model.pipeline.state.terminal;

import elements.aqua.model.pipeline.IModel;

public class Collect<T> implements IModel, ITerminal<T> {

    @Override
    public Type type() { return Type.TERMINAL_STATE; }

    @Override
    public void consume(T data) {
    }
}
