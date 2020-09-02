package aut;

public class FindArrayDuplicates {
    static public Integer[] myArray;
    static public Integer[] results = new Integer[100];
    static int resultCnt = 0;
    static Boolean alreadyFound = false;

    static public Integer[] reArrayDuplicates(Integer[] arrProvided) {
        resultCnt = 0;
        // Initial Results Array
        for (int x = 0; x < results.length - 1; x++) {
            results[x] = -1;
        }

        System.out.printf("TTI Original Array:    ");
        aut.AnyObjectPrinter.printIt(arrProvided);

        for (int i = 0; i < arrProvided.length - 1; i++) {
            for (int j = i + 1; j < arrProvided.length; j++) {
                if (arrProvided[i] == arrProvided[j]) {
                    if (resultCnt == 0) {
                        // first found duplicate, may already be in results
                        results[resultCnt] = arrProvided[i];
                        resultCnt++;
                    } else {
                        // Aaything but first found duplicate, may already be in results
                        alreadyFound = false;
                        int lResults;
                        // Do not double-count the duplicates -- design assumption
                        // Is current duplicate already in duplicate results?
                        for (lResults = 0; lResults <= resultCnt; lResults++) {
                            if (arrProvided[i] == results[lResults]) {
                                alreadyFound = true;
                            }
                        }
                        if (alreadyFound == false) {
                            results[resultCnt] = arrProvided[i];
                            resultCnt++;
                        }
                    }
                }
            }
        }
        // resultCnt has total number of duplicates found
        System.out.printf("TTI Results Array:    ");
        for (int x = 0; x < resultCnt; x++) {
            System.out.printf("% 3d ", results[x]);
        }
        System.out.printf("\n");
        System.out.printf("TTI found " + resultCnt + " duplicates in array. \n");
        return results;
    }

    public static Integer[] main() {
        return reArrayDuplicates(myArray);
    }
}
