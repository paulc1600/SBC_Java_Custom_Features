package aut;

public class TopTwoIntegers {
    static public Integer[] myArray;

    static public Integer[] reSortArray(Integer[] arrProvided) {
        System.out.printf("TTI Original Array:  ");
        aut.AnyObjectPrinter.printIt(arrProvided);
        int temp = 0;
        for (int i = 0; i < arrProvided.length; i++) {
            for (int j = i; j < arrProvided.length; j++) {
                if (arrProvided[i] > arrProvided[j]) {
                    temp = arrProvided[i];
                    arrProvided[i] = arrProvided[j];
                    arrProvided[j] = temp;
                }
            }
            // System.out.printf("--- Debug at i = " + i + "  :  ");
            // aut.AnyObjectPrinter.printIt(arrProvided);
        }
        System.out.printf("TTI Sorted Array:    ");
        aut.AnyObjectPrinter.printIt(arrProvided);
        return arrProvided;
    }

    public static Integer[] main() {
        Integer[] retValues = new Integer[2];
        reSortArray(myArray);
        retValues[0] = myArray[myArray.length - 1];
        retValues[1] = myArray[myArray.length - 2];

        // System.out.println("TTI #1: " + retValues[0]);
        // System.out.println("TTI #2: " + retValues[1]);
        // Can return a car with trunk full of luggage. Cannot return 5 pieces of luggage separately
        return retValues;
    }
}
