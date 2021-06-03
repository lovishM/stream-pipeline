package elements.aqua.model.pipeline.state.initial;

import elements.aqua.model.pipeline.IModel;

public class DataSource implements IModel, InitialState {

    @Override
    public Type type() { return Type.INIT_STATE; }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
