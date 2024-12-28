package com.productstar.sortmethod;

import java.util.Arrays;
import java.util.Random;


public class SortMethod {


    public static void main(String[] args) {

        int length = 10000;
        int [] array = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(10000);
        }
        System.out.print(Arrays.toString(array));
        System.out.println("");
        System.out.println("");



        long t_1_1 = System.currentTimeMillis();
        bubbleSort(array);
        long t_1_2 = System.currentTimeMillis();
        System.out.println(t_1_2 - t_1_1);
        System.out.println(Arrays.toString(array));
        System.out.println("");



        long t_2_1 = System.currentTimeMillis();
        insertionSearch(array);
        long t_2_2 = System.currentTimeMillis();
        System.out.println(t_2_2 - t_2_1);
        System.out.println(Arrays.toString(array));
        System.out.println("");

    }

    public static void bubbleSort (int [] array) {
        for (int i = array.length - 1; i > 1; i --) {
            for (int j = 0; j < i; j++) {
              if(array[j] > array[j + 1]) {
                  int swap = array[j];
                  array[j] = array[j + 1];
                  array[j + 1] = swap;
              }
            }
        }
    }
    public static void insertionSearch(int [] array) {
        for (int i = 0; i < array.length - 1; i++) {
           int min = i;
            for (int j = i + 1; j < array.length ; j++)
                if (array [j] < array [min] )
                    min = j;
            int swap = array[i];
            array [i] = array[min];
            array [min] = swap;
        }
    }

}

