package stream;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // given
        List<Integer> collectionList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // when
        List<Integer> collectionResult = collectionList.stream()
            .map(i -> 2 * i) // Function <T,T>
            .toList();

        List<Integer> arrayResult = Arrays.stream(array)
            .map(i -> 2 * i) // Function <T,T>
            .toList();

        // then
        System.out.println("collectionList = " + collectionList);
        System.out.println("collectionResult = " + collectionResult);

        Arrays.stream(array)
            .forEach(System.out::print);
        System.out.println();

        System.out.println("arrayResult = " + arrayResult);
    }

}