package elements.aqua.core;

import elements.aqua.model.pipeline.operation.Filter;
import elements.aqua.model.pipeline.operation.Transform;
import elements.aqua.model.pipeline.state.initial.Stream;
import elements.aqua.model.pipeline.state.terminal.Print;

import java.util.ArrayList;
import java.util.List;

public class SampleComplex {

    public static void main(String[] args) {

        List<String> constants = new ArrayList<>();
        constants.add("EVEN");
        constants.add("ODD");

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            list.add(i);
        }

        // Create a stream for INIT state
        ComplexPipeline<?> pipeline = Stream.toPipeline(list)
                .next(Filter.createFilter(i -> i < 20))
                .next(Transform.createConversion(i -> i%2))
                .next(Transform.createConversion(constants::get))
                .consume(Print.printer(System.out));

        pipeline.evaluate();
    }
}
