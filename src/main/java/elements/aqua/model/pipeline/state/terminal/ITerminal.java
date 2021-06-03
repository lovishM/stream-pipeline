package elements.aqua.model.pipeline.state.terminal;

import java.io.IOException;

public interface ITerminal<T> {
    void consume(T data) throws IOException;
}
