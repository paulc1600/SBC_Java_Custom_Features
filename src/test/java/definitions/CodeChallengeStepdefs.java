package definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.*;

public class CodeChallengeStepdefs {
    @Then("I make a killing on the stock market")
    public void iMakeAKillingOnTheStockMarket() {
        int[] sPrices = {7, 1, 5, 3, 6, 4, 4};

    }

    @Then("I make a killing on the stock market with {int}, {int}, {int}, {int}, {int}, {int}, and {int}")
    public void iMakeAKillingOnTheStockMarketWithAnd(int p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        int[] sPrices = new int[]{0, 0, 0, 0, 0, 0, 0};
        sPrices[0] = p0;
        sPrices[1] = p1;
        sPrices[2] = p2;
        sPrices[3] = p3;
        sPrices[4] = p4;
        sPrices[5] = p5;
        sPrices[6] = p6;

        int buyDay = 0;
        int sellDay = 0;
        int profitLoss = 0;

        // Find best day to buy
        int lowPrice = 99999;
        for (int d = 0; d < sPrices.length; d++) {
            if (sPrices[d] <= lowPrice) {
                buyDay = d;
                lowPrice = sPrices[d];
            }
        }
        // Crazy case where stock values are all in millions or low day is last day so cannot sell
        if ((lowPrice != 999999) && (buyDay != sPrices.length - 1)) {
            // Was good day to buy. Find sell day with max profit
            for (int s = buyDay+1; s < sPrices.length; s++) {
                if (sPrices[s] - sPrices[buyDay] >= profitLoss) {
                    sellDay = s;
                    profitLoss = sPrices[s] - sPrices[buyDay];
                }
            }
            // Did we have any profit
            if (profitLoss > 0) {
                System.out.println("============================================");
                System.out.println("        Stock Report this Week              ");
                System.out.println("============================================");
                System.out.println(" Daily Prices: " + Arrays.toString(sPrices));
                System.out.println(" You bought on day " + (buyDay+1) + " for $" + sPrices[buyDay]);
                System.out.println(" You sold on day " + (sellDay+1) + " for $" + sPrices[sellDay]);
                System.out.println(" Your profit / loss was $" + profitLoss);
            } else {
                System.out.println("============================================");
                System.out.println("        Stock Report this Week              ");
                System.out.println("============================================");
                System.out.println(" Daily Prices: " + Arrays.toString(sPrices));
                System.out.println("***** Error: Bad luck. No profit. Try again another week!");
            }
        } else {
            System.out.println("============================================");
            System.out.println("        Stock Report this Week              ");
            System.out.println("============================================");
            System.out.println(" Daily Prices: " + Arrays.toString(sPrices));
            System.out.println("***** Error: No good day to buy stocks. Try Bonds!");
        }
    }

    @Given("I code challenge 3")
    public void iCodeChallenge3() {
        System.out.println("Coding Challenge 3");
        // But one method can call another method
        iSwap(45, 2);
        int var1 = 22;
        int var2 = 978;
        iSwapFancy(var1, var2);

        Map<String, String> admin = new LinkedHashMap<>();
        admin.put("userName", "bigAdmin");
        admin.put("firstName", "John");
        admin.put("middleName", "George");
        admin.put("lastName", "Geek");
        admin.put("email", "admin@nonet.com");
        admin.put("password", "admin123456");
        SwapMap(admin, "firstName", "middleName");
        SwapMap(admin, "lastName", "middleName");
        SwapMap(admin, "lastName", "firstName");
    }

    // ------------------------------------------
    void iSwap(int a, int b) {
        System.out.println("a b = " + a + " " + b);
        int temp = 0;
        temp = a;
        a = b;
        b = temp;
        System.out.println("a b = " + a + " " + b);
    }

    // ------------------------------------------
    void iSwapFancy(int a, int b) {
        System.out.println("a b = " + a + " " + b);
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.println("a b = " + a + " " + b);
    }

    // ------------------------------------------
    void SwapMap(Map<String, String> myMap, String key1, String key2) {
        System.out.println(myMap);
        String tempSwap;
        tempSwap = myMap.get(key2);
        myMap.put(key2, myMap.get(key1));
        myMap.put(key1, tempSwap);
        System.out.println(myMap);
    }

    // ------------------------------------------
    @Given("I code fourth coding challenge")
    public void iCodeFourthCodingChallenge() {
        char clow;
        int high;
        char chigh;
        for (int low = 65; low <= 90; low++) {
            clow = (char) low;
            high = low + 32;
            chigh = (char) high;
            System.out.println(low + "  " + clow + "    " + high + "  " + chigh);
        }
    }

    // --------------------------------------------------------------------------
    //  Scenario: Swap Array Elements
    //    When I swap the 3 and 5 element from this array "5, 2, 9, 7, 3" I get
    // --------------------------------------------------------------------------
    @When("I swap the {int} and {int} element from this array {string} I get")
    public void iSwapTheAndElementFromThisArrayIGet(int element1, int element2, String gherkinArray) {
        List<String> arrElement = new ArrayList<String>();
        String tString = "";
        // splits the string on a delimiter defined as: zero or more whitespace,
        //  a literal comma, zero or more whitespace
        arrElement = Arrays.asList(gherkinArray.split("\\s*,\\s*"));
        System.out.println("String from Gherkin: " + gherkinArray);
        System.out.println("Resulting Array Before: " + arrElement);
        if (element1 <= arrElement.size() && element2 <= arrElement.size()) {
            tString = arrElement.get(element1 - 1);
            arrElement.set(element1 - 1, arrElement.get(element2 - 1));
            arrElement.set(element2 - 1, tString);
            System.out.println("Resulting Array After: " + arrElement + "\n");
        } else {
            System.out.println("One or both desired Gherkin Array elements invalid! " + element1 + " " + element2);
        }
    }

    // --------------------------------------------------------------------------
    //  Scenario: Find If Divisible
    //    When I check if 3 is divisible by 5 then I get
    // --------------------------------------------------------------------------
    @When("I check if {int} is divisible by {int} and {int} then I get")
    public String iCheckIfIsDivisibleByThenIGet(int targetNbr, int divByNbr1, int divByNbr2) {
        boolean yesBy1 = false;
        boolean yesBy2 = false;
        String rsltString = "";

        if (divByNbr1 != 0 && targetNbr % divByNbr1 == 0) {
            yesBy1 = true;
        }
        if (divByNbr2 != 0 && targetNbr % divByNbr2 == 0) {
            yesBy2 = true;
        }

        if (yesBy1 && ! yesBy2) {
            System.out.println("Yes, " + targetNbr + " is divisible by " + divByNbr1);
            rsltString = " is divisible by " + divByNbr1;
        }
        if (! yesBy1 && yesBy2) {
            System.out.println("Yes, " + targetNbr + " is divisible by " + divByNbr2);
            rsltString = " is divisible by " + divByNbr2;
        }
        if (yesBy1 && yesBy2) {
            System.out.println("Yes, " + targetNbr + " is divisible by " + divByNbr1 + " and " + divByNbr2 + "\n");
            rsltString = " is divisible by " + divByNbr1 + " and " + divByNbr2;
        }
        if (! yesBy1 && ! yesBy2) {
            System.out.println("No, " + targetNbr + " is NOT divisible by " + divByNbr1 + " or " + divByNbr2 + "\n");
        }
        return rsltString;
    }

    @Then("I test if {int} is divisible by {int} and {int} then I get")
    public void iTestIfIsDivisibleByAndThenIGet(int arg0, int arg1, int arg2) {
        System.out.println(iCheckIfIsDivisibleByThenIGet(arg0, arg1, arg2));
    }


    @Then("I print numbers from {int} to {int}")
    public void iPrintNumbersFromTo(int start, int end) {
        int prtIndex = 1;
        String display = "";
        for (int n = start; n <= end; n++) {
            if (n != end && prtIndex <= 10) {
                display = display + n + ", ";
                prtIndex++;
            } else if (n != end && prtIndex > 10) {
                display = display + n;
                System.out.println(display);
                prtIndex = 2;
                display = "";
            } else if (n == end) {
                display = display + n + "\n";
                System.out.println(display);
            }
        }
    }

    public void iPrintIntArrays(int[] arrayProvided, String myCmd, int oneValue) {
        StringBuilder display = new StringBuilder();

        if(arrayProvided.length != 0) {
            for (int oneInt : arrayProvided) {
                // System.out.println("Number : " + oneInt + " " + oneInt % 2 + " Cmd: " + myCmd);
                if (myCmd.equals("even") && oneInt % 2 == 0) {
                    display.append(oneInt).append(" ");
                } else if (!myCmd.equals("even")) {
                    display.append(oneInt).append(" ");
                }
            }
            System.out.println(display);
        } else {
            System.out.println("................");
        }

        if (myCmd.equals("size")) {
            if(arrayProvided.length == 0) {
                System.out.println("array empty\n");
            } else {
                System.out.println("array not empty\n");
            }
        }
    }

    @Then("I print an integer array")
    public void iPrintAnIntegerArray() {
        System.out.println("I print an integer array");
        int[] arrayOfInts = {-12, -14, -9, 0, 1, 2, 3, 4, 5, 21, 22, 23, 24, 25};
        iPrintIntArrays(arrayOfInts, "all", 0);
    }

    @Then("I print all even numbers from integer array")
    public void iPrintAllEvenNumbersFromIntegerArray() {
        System.out.println("I print all even numbers from an integer array");
        int[] arrayOfInts = {-12, -14, -9, 0, 1, 2, 3, 4, 5, 21, 22, 23, 24, 25};
        iPrintIntArrays(arrayOfInts, "even", 0);
    }

    @Then("I check if an array is empty")
    public void iCheckIfAnArrayIsEmpty() {
        System.out.println("I check if an array is empty");
        int[] arrayOfInts = {-12, -14, -9, 0, 1, 2, 3, 4, 5, 21, 22, 23, 24, 25};
        iPrintIntArrays(arrayOfInts, "size", 0);
        System.out.println("I check if an array is empty");
        int[] arrayOfAir = {};
        iPrintIntArrays(arrayOfAir, "size", 0);
    }

    @Then("I check if array contains {int}")
    public void iCheckIfArrayContains(int intProvided) {
        System.out.println("I check if a array contains " + intProvided);
        Integer[] arrayOfInts = {-12, -14, -9, 0, 1, 2, 3, 4, 5, 21, 22, 23, 24, 25};
        iPrintIntegerArrays(arrayOfInts, "find", intProvided);
    }

    public void iPrintIntegerArrays(Integer[] arrayProvided, String myCmd, int end) {
        StringBuilder display = new StringBuilder();
        List<Integer> listArray = Arrays.asList(arrayProvided);
        int endPrint = 0;

        if (arrayProvided.length != 0) {
            if (myCmd.equalsIgnoreCase("fizz")) {
                System.out.println("\n" + "I print out Fizz Buzz list");
                endPrint = end;
            } else {
                System.out.println("\n" + "I print out array and look for element "+ end);
                endPrint = arrayProvided.length;
            }
            int oneInt = 0;
            for (int i = 0; i < endPrint; i++) {
                // System.out.println("Number : " + oneInt + " " + oneInt % 2 + " Cmd: " + myCmd);
                oneInt = arrayProvided[i];

                boolean b3 = false;
                boolean b5 = false;
                boolean b35 = false;
                boolean not35 = false;
                if (myCmd.equalsIgnoreCase("fizz")) {
                    if (oneInt % 3 == 0) b3 = true;
                    if (oneInt % 5 == 0) b5 = true;
                    if (b3 && b5) b35 = true;
                    if (!b3 && !b5) not35 = true;

                    if (not35) display.append(oneInt).append(" ");
                    if (b3 && !b5) display.append("Fizz ");
                    if (!b3 && b5) display.append("Buzz ");
                    if (b35) display.append("FizzBuzz ");
                    // System.out.println("Index i = " + i + " ==> " + display);
                } else {
                    display.append(oneInt).append(" ");
                }
            }
            System.out.println(display);

            if (myCmd.equalsIgnoreCase("find")) {
                if(listArray.contains(end)){
                    System.out.println("Array does contain " + end);
                } else {
                    System.out.println("Array does not contain " + end);
                }
            }
        }
    }

    @Then("I build an array with numbers up to {int}")
    public void iBuildAnArrayWithNumbersUpTo(int end) {
        Integer [] myarray = new Integer[40];
        for (int i = 1; i <= end; i++) {
            myarray[i - 1] = i;
        }
        iPrintIntegerArrays(myarray, "fizz", end);
    }
}
