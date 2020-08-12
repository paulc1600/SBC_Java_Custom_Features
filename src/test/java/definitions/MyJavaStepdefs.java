package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyJavaStepdefs {
    @Given("I say {string} test name {string}")
    public void iSaytestname(String message, String name) {
        System.out.println("=============================================");
        System.out.println("     " + message);
        System.out.println("     Test Name:" + name);
        System.out.println("=============================================");
    }

    @And("I perform {string} actions with {string} and {string}")
    public void iPerformActionsWithAnd(String strFunc, String str1, String str2) throws IllegalStateException {
        switch (strFunc) {
            case "display":
                System.out.println("     " + "str1: "+ str1 + "  str2: "+ str2);
                break;
            case "upper":
                System.out.println("     " + "str1: "+ str1.toUpperCase() + "  str2: "+ str2.toUpperCase());
                break;
            case "measure":
                System.out.println("     " + "str1: "+ str1.length() + "  str2: "+ str2.length());
                break;
            case "compare":
                if (str1.equals(str2)) {
                    System.out.println("     " + str1 + " is exactly equal to "+ str2);
                } else {
                    System.out.println("     " + str1 + " is NOT exactly equal to "+ str2);
                }
                break;
            case "compareIC":
                if (str1.equalsIgnoreCase(str2)) {
                    System.out.println("     " + str1 + " is equal to "+ str2);
                } else {
                    System.out.println("     " + str1 + " is NOT equal to "+ str2);
                }
                break;
            case "contains":
                if (str1.contains(str2)) {
                    System.out.println("     " + str1 + " contains "+ str2);
                } else {
                    System.out.println("     " + str1 + " does NOT contain "+ str2);
                }
                break;
            default:
                throw new IllegalStateException("**** This function is unsupported: " + strFunc + " ****");
        }
    }

    @And("I work with Arrays")
    public void iWorkWithArrays() {
        String[] fruits = {"kiwi", "strawberry", "plum", "pear", "apple", "apple"};
        int[] nums = {5, 3, 5, 8, 10, 12};
        nums[0] = 7;
        int i = 0;
        System.out.println("     " + "=============================================");
        System.out.println("     " + "     Dump Arrays");
        System.out.println("     " + "=============================================");
        for (i=0; i<6; i++) {
            System.out.println("     " +"Array nums index " + i + " is " + nums[i] + "     " +"Array fruits index " + i + " is " + fruits[i]);
        }

        System.out.println("     " + "=============================================");
        System.out.println("     " + "     Sort Arrays");
        System.out.println("     " + "=============================================");
        Arrays.sort(fruits);
        Arrays.sort(nums);
        for (i=0; i<6; i++) {
            System.out.println("     " +"Array nums index " + i + " is " + nums[i] + "     " +"Array fruits index " + i + " is " + fruits[i]);
        }

        List<Integer> listOfNums = new ArrayList<>();
        listOfNums.add(99);
        listOfNums.add(8);
        listOfNums.add(20);
        System.out.println("     " + "=============================================");
        System.out.println("     " + "     List");
        System.out.println("     " + "=============================================");
        for (int j : listOfNums) {
            System.out.println("     " + "List has " + j);
        }
    }
}
