package elements.aqua.model.pipeline;

public interface IModel {

    Type type();

    default String name() {
        return this.getClass().getSimpleName();
    }

    enum Type {
        INIT_STATE, FILTER, TRANSFORM, TERMINAL_STATE
    }
}
