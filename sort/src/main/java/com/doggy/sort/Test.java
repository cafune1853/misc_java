package com.doggy.sort;

/**
 * @author doggy1853
 */
public class Test {
    public static void main(String[] args) {
        testArraySort(new InsertSortStrategy());
        testArraySort(new BubbleSortStrategy());
        testArraySort(new SelectSortStrategy());
        testArraySort(new MergeSortStrategy());
    }

    public static void testArraySort(SortStrategy sortStrategy){
        testOneArraySort(new int[]{1, 3, 7, 5, 6, 4, 9}, sortStrategy, new int[]{1, 3, 4, 5, 6, 7, 9});
        testOneArraySort(new int[]{}, sortStrategy, new int[]{});
        testOneArraySort(new int[]{1}, sortStrategy, new int[]{1});
        testOneArraySort(new int[]{2, 1}, sortStrategy, new int[]{1, 2});
    }

    private static void testOneArraySort(int[] array, SortStrategy sortStrategy, int[] expectArray) {
        System.out.printf("Test start at sort strategy -- %s", sortStrategy.getClass().getSimpleName());
        String originalArray = arrayToString(array);
        sortStrategy.sort(array);
        boolean isFail = false;
        if(array.length != expectArray.length){
            isFail = true;
        }
        for (int i = 0; i < expectArray.length; i++) {
            if(expectArray[i] != array[i]){
                isFail = true;
            }
        }
        if(isFail){
            System.out.printf("Test failed\n");
            System.out.printf("Original array: %s\n", originalArray);
            System.out.printf("Sorted   array: %s\n", arrayToString(array));
            System.out.printf("Expected array: %s\n", arrayToString(expectArray));
        }else{
            System.out.println("Test success!");
        }
        System.out.println("----------------------------------------------------------------------");
    }

    public static String arrayToString(int[] array){
        StringBuilder sb = new StringBuilder();
        for (int e : array) {
            sb.append(e);
            sb.append(" ");
        }
        return sb.toString();
    }
}
