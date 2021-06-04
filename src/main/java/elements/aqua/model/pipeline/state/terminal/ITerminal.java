package elements.aqua.model.pipeline.state.terminal;

public interface ITerminal<T> {
    void consume(T data);
}
