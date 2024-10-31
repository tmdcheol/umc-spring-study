package inheritanceclass;

import functionalinterface.inheritance.Converter;

public class Main {

    public static void main(String[] args) {
        // given
        Integer value = 3;
        String expected = "3";

        Converter converter = new Converter();

        // when
        String result = converter.apply(value);

        // then
        boolean output = result.equals(expected);
        System.out.println("output = " + output);
    }

}
