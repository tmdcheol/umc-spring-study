package functionalinterface.methodreference;

import java.util.function.Function;

@FunctionalInterface
public interface Converter extends Function<Integer, String> {

    String apply(Integer t);

}
