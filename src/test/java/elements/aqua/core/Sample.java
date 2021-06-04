package elements.aqua.core;

import elements.aqua.model.pipeline.operation.Filter;
import elements.aqua.model.pipeline.operation.Transform;
import elements.aqua.model.pipeline.state.initial.Stream;
import elements.aqua.model.pipeline.state.terminal.Print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sample {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            list.add(i);
        }

        // Create a stream for INIT state
        Stream<Integer> stream = Stream.of(list);

        // Create a printer for TERMINAL state
        Print<Integer> print = Print.printer(System.out);

        // Create Pipeline
        SimplePipeline<Integer> tree = SimplePipeline.create(stream, print);

        // Filter perfect squares
        Filter<Integer> filter = Filter.createFilter(i -> {
            long x = Math.round(Math.sqrt(i));
            return x*x == i;
        });
        tree.nextLink(filter);

        // Square root of the numbers
        Transform<Integer, Integer> convertor = Transform.createConversion(i -> (int) Math.round(Math.sqrt(i)));
        tree.nextLink(convertor);

        tree.execute();
    }
}
