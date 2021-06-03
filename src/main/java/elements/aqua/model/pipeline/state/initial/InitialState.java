package elements.aqua.model.pipeline.state.initial;

public interface InitialState<T> {
    boolean hasNext();
    T next();
}
