# Stream Pipeline 
> Conceptualized from JAVA's stream api, and concept of a flowchart / decision tree.

## Objective of this project
- Create a pipeline with the ability to split it at any point and continue with different pipeline
- Convert the pipeline, or transform it to create an API of decision tree.
- Ability to merge 2 or more decision trees to create a complex decision graph.

## What's done so far
- Simple Pipeline (works on a Single Type from _Creation_ to _Consumption_)
  - Provided below is a sample of `SimplePipeline`

```java
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
```
----
- Complex Pipeline (allows _Transformation_ of types, just like a JAVA stream, though currently nowhere near as powerful)
  - Provided below is a sample of `ComplexPipeline`

```java
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
```
