import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.min;

/**
 * Created by dusky on 3/30/17.
 */
public class Streams {

    public static void main(String[] args){
        uloha1(new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4,4,2,3,4,9})));
        uloha2(new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4,4,2,3,4,5})));
        uloha2(new ArrayList<>(Arrays.asList(new Integer[] {1,2,1,3,4,4,1,2,3,4,5})));
        uloha3(10);

        ArrayList<Integer> i1 = new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4,5,6}));
        ArrayList<Integer> i2 = new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,8,9,10}));
        ArrayList<Integer> i3 = new ArrayList<>(Arrays.asList(new Integer[] {1,3,4,5}));
        ArrayList<Integer> i4 = new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9,10}));
        ArrayList<List<Integer>> arrays = new ArrayList<List<Integer>>();
        arrays.add(i1);
        arrays.add(i2);
        arrays.add(i3);
        arrays.add(i4);

        uloha4(arrays);

        int[] ii1 = new int[] {1,2,3,4,5,6};
        int[] ii2 = new int[] {1,2,3,8,9,10};
        int[] ii3 = new int[] {1,3,4,5,3,7};
        int[] ii4 = new int[] {1,2,3,4,5,6};
        int[][] iii = new int[][] {ii1, ii2, ii3, ii4};

        uloha5(iii);
    }

    private static void uloha1(List<Integer> numbers){
        //https://www.sitepoint.com/java-8-streams-filter-map-reduce/
        //rozdiel parnych neparnych
        int rozdiel = numbers.stream().reduce(0,  (a, b) -> (b % 2 == 0 ? a + b : a - b));
        System.out.println("Array " + numbers);
        System.out.println("Rozdiel parnych neparnych " + rozdiel);
    }

    private static void uloha2(List<Integer> numbers){
        //http://stackoverflow.com/questions/22561614/java-8-streams-min-and-max-why-does-this-compile
        //pocet min prvkov
        int minimum = numbers.stream().max(Integer::min).get();
        long count = numbers.stream().filter(i -> i == minimum).count();
        System.out.println("Array " + numbers);
        System.out.println("Minimum " + minimum);
        System.out.println("Pocet " + count);
    }

    private static void uloha3(int n){
        //https://groups.google.com/forum/#!topic/comp.lang.java.programmer/-Oqq4CGqBtk
        System.out.println("Primes " + n);
        List<Integer> primes = new ArrayList<>();
        Stream<Integer> s = Stream.iterate(2, x -> x + 1);
        for (int i = 0; i < n; i++) {
            int k = i;
            Consumer<Integer> kthPrime = kthPrime(k, primes);
            s = s.peek(kthPrime).filter(x -> {int p = currPrime(k,primes);
                return x == p || x % p != 0;});
        }
        assert primes.isEmpty();
        s.limit(n).forEach(x -> System.out.print(x + " "));
        assert primes.size() == n;
        System.out.println();
    }

    private static Integer currPrime(int k, List<Integer> primes) {
        return primes.get(min(k,primes.size()-1));
    }

    private static Consumer<Integer> kthPrime(int k, List<Integer> primes) {
        return new Consumer<Integer>() {
            private int idx;

            @Override
            public void accept(Integer p) {
                if (idx > k) return;
                if (idx == k) primes.add(p);
                idx++;
            }
        };
    }

    private static void uloha4(ArrayList<List<Integer>> arrays) {
        //Prienik poli
        //http://stackoverflow.com/questions/31683375/java-8-lambda-intersection-of-two-lists
        System.out.println("Prienik of " + arrays);
        List<Integer> intersection = arrays.stream().skip(1).reduce(arrays.get(0), (a, b) -> {
            return a.stream().filter(b::contains).collect(Collectors.toList());
        });
        System.out.println("is " + intersection);
    }


    private static void uloha5(int[][] arrays) {
        //Transponovanie
        //http://stackoverflow.com/questions/34861469/compact-stream-expression-for-transposing-double-matrix
        System.out.print("Transponovanie of " );
        System.out.print("[");
        for (int i = 0; i < arrays.length; i++){
            System.out.print("[");
            int[] pole = arrays[i];
            for (int j = 0; j < pole.length; j++){
                System.out.print(pole[j]);
                if (j < pole.length - 1) {
                    System.out.print(",");
                } else {
                    System.out.print("]");
                }
            }
            if (i < arrays.length - 1) {
                System.out.print(",");
            } else {
                System.out.print("]");
            }
        }

        System.out.println();

        int[][] vysledok = IntStream.range(0, arrays[0].length).mapToObj(j -> IntStream.range(0, arrays.length).map(i -> arrays[i][j]).toArray()).toArray(int[][]::new);

        System.out.print("Transponovana je " );
        System.out.print("[");
        for (int i = 0; i < vysledok.length; i++){
            System.out.print("[");
            int[] pole = vysledok[i];
            for (int j = 0; j < pole.length; j++){
                System.out.print(pole[j]);
                if (j < pole.length - 1) {
                    System.out.print(",");
                } else {
                    System.out.print("]");
                }
            }
            if (i < vysledok.length - 1) {
                System.out.print(",");
            } else {
                System.out.print("]");
            }
        }
        System.out.print("]");
    }
}
