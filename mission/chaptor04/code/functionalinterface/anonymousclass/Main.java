package functionalinterface.anonymousclass;

public class Main {

    public static void main(String[] args) {
        // given
        Integer value = 3;
        String expected = "3";

        Converter converter = new Converter() {
            @Override
            public String apply(Integer value) {
                return String.valueOf(value);
            }
        };

        // when
        String result = converter.apply(value);

        // then
        boolean output = result.equals(expected);
        System.out.println("output = " + output);
    }

}
