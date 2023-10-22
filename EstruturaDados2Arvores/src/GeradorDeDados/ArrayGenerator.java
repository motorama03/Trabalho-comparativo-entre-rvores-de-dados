package GeradorDeDados;

import java.util.Random;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayGenerator {
    private Random random = new Random();

    public int[] generateRandomArray(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Tamanho deve ser maior que zero.");
        }

        Set<Integer> uniqueKeys = new HashSet<>();
        int[] array = new int[size];
        int index = 0;

        while (index < size) {
            int randomKey = random.nextInt(1000000);
            if (uniqueKeys.add(randomKey)) {
                array[index] = randomKey;
                index++;
            }
            System.out.println(randomKey);
        }

        return array;
    }

    public int[] generateSortedArray(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Tamanho deve ser maior que zero.");
        }

        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    public static void main(String[] args) {
        ArrayGenerator generator = new ArrayGenerator();

        int[] randomArray = generator.generateRandomArray(10);
        System.out.println("Array Aleatório: " + Arrays.toString(randomArray));

        int[] sortedArray = generator.generateSortedArray(10);
        System.out.println("Array Ordenado: " + Arrays.toString(sortedArray));
    }
}
