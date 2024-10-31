package functionalinterface.inheritance;

import java.util.function.Function;

public class Converter implements Function<Integer, String> {

    @Override
    public String apply(Integer t) {
        return String.valueOf(t);
    }

}