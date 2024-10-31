package functionalinterface.anonymousclass;

import java.util.function.Function;

public interface Converter extends Function<Integer, String> {

    @Override
    String apply(Integer t);

}