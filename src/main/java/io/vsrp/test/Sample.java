package io.vsrp.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by vpurohi on 29/11/18.
 */
public class Sample {

    public static void main(String ... args) {
        Function<String, Integer> computeFunc = s -> s.length();
        Function<String, Integer> nameLengthFunc = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };

        Map<String, Integer> nameMap = new HashMap();
        nameMap.put("singh", 25);
        nameMap.computeIfAbsent("vikram", s -> s.length());
        System.out.println(nameMap.computeIfAbsent("singh", computeFunc));
        System.out.println(nameMap.computeIfAbsent("vikram", nameLengthFunc));

        Function<String, Boolean> startsWithA = s -> s.startsWith("A");

        Predicate<String> nameStartsWithA = s -> s.startsWith("A");
        Predicate<String> fruitStartsWithA = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("A");
            }
        };

        Function<String, Predicate<String>> nameStartsWith = s -> fruitName -> fruitName.startsWith(s);
        Function<String, Predicate<String>> nameStartsWith2 = s -> new Predicate<String>() {
            @Override
            public boolean test(String t) {
                return t.startsWith(s);
            }
        };
        Predicate<String> fruitNameStartsWithB = nameStartsWith.apply("B");

        List<String> names = Arrays.asList("Apple", "Banana", "mango", "apricot");
        System.out.println(
                names.stream()
                     .filter(s -> s.startsWith("A"))
                     .collect(Collectors.toList())
        );

        System.out.println(
                names.stream()
                        .filter(fruitStartsWithA)  // 1 OR
                        .filter(nameStartsWithA)   // 2 OR
                        .filter(nameStartsWith.apply("A"))   // 3 OR
                        .filter(nameStartsWith2.apply("A"))  // 4
                        .collect(Collectors.toList())
        );
        names.stream().filter(fruitNameStartsWithB)
                .forEach(System.out::println);
    }

}

@FunctionalInterface
interface MyFunction {

    public int multiple(int first, int second);

    default int sum(int first, int second) {
        return first + second;
    }

    default int doubleIt(int first, int second) {
        return sum(sum(first, second),sum(first, second));
    }
}
