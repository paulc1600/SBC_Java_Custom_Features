package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.time.DayOfWeek;
import java.util.*;

public class MyJavaStepdefs {
    // Global Variable Section for Java Steps
    //  Max 10 unique lists
    //  Max 20 items in each list
    Integer gArrayCnt = -1;
    String[][] gArray = new String[10][20];
    String[] gArrayName = new String[20];
    Integer[] gArrayLastItem = new Integer[20];
    ArrayList<Integer> gArrList = new ArrayList<Integer>(Arrays.asList(3,9,14,8,3,10));

    @Given("I say {string} test name {string}")
    public void iSaytestname(String message, String name) {
        System.out.println("     ");
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
        System.out.println("     ");
        System.out.println("     ");
    }

    @And("I work with Arrays")
    public void iWorkWithArrays() {
        String[] fruits = {"kiwi", "strawberry", "plum", "pear", "apple", "apple"};
        int[] nums = {5, 3, 5, 8, 10, 12};
        nums[0] = 7;
        int i = 0;
        System.out.println("     ");
        System.out.println("     " + "=============================================");
        System.out.println("     " + "     Dump Arrays");
        System.out.println("     " + "=============================================");
        for (i=0; i<6; i++) {
            System.out.println("     " +"Array nums index " + i + " is " + nums[i] + "     " +"Array fruits index " + i + " is " + fruits[i]);
        }

        System.out.println("     ");
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
        System.out.println("     ");
        System.out.println("     " + "=============================================");
        System.out.println("     " + "     List");
        System.out.println("     " + "=============================================");
        for (int j : listOfNums) {
            System.out.println("     " + "List has " + j);
        }
        System.out.println("     ");
        System.out.println("     ");
    }

    @And("I create shopping list {string} with {int} items")
    public void iCreateShoppingListWithItems(String arrayName, int itemCnt) {
        int i;
        gArrayCnt = gArrayCnt + 1;               // First list created is list 0
        if (gArrayCnt < 10) {
            gArrayName[gArrayCnt] = arrayName;       // Store user's list name here
            gArrayLastItem[gArrayCnt] = 0;           // New Array so last item stored is at index 0

            // Initialize Array for N items
            System.out.println(itemCnt + " item array " + gArrayCnt + " with name: " + gArrayName[gArrayCnt] + " created.");
            for (i = 1; i <= itemCnt; i++) {
                gArray[gArrayCnt][itemCnt] = "";     // not null
            }
        } else {
            // oops, code only supports 10 unique list names
            System.out.println("**** Error: Only 10 unique lists can be created! ****");
        }
    }

    @When("I add item {string} to list {string}")
    public void iAddItemToListSafeway(String itemName, String lArrayName) {
        int lArrayIndex = 0;
        int lLastItemIndex = 0;
        lArrayIndex = findArrayNumberFromName(lArrayName);

        // Found it! Add the item
        if (lArrayIndex >= 0) {
            lLastItemIndex = gArrayLastItem[lArrayIndex];      // Starts at 0, so this is where you store
            gArray[lArrayIndex][lLastItemIndex] = itemName;
            gArrayLastItem[lArrayIndex] = lLastItemIndex + 1;  // Increment Item Index for this array
        } else {
            // Cannot Continue!!!!
            System.out.println("**** Error: No list " + lArrayName + " found! No item added! ****");
        }
    }

    public int findArrayNumberFromName(String ArrayName) {
        int lArrayIndex = -1;       // If not found, passes -1 as index.  8-(, how handle?
        int li = 0;

        // Only Search Arrays already created
        for (li = 0; li <= gArrayCnt; li++) {
            if (gArrayName[li].equals(ArrayName)) {
                lArrayIndex = li;
                break;
            }
        }
        return lArrayIndex;
    }

    @Then("I check the items in list {string}")
    public void iCheckTheItemsInList(String lArrayName) {
        int li = 0;
        int lArrayIndex = 0;
        int lLastItemIndex = 0;
        lArrayIndex = findArrayNumberFromName(lArrayName);

        // Found array! Show the items
        if (lArrayIndex >= 0) {
            lLastItemIndex = gArrayLastItem[lArrayIndex];
            System.out.println("     ");
            System.out.println("     " + "=============================================");
            System.out.println("     " + "     " + lArrayName + " List Contents");
            System.out.println("     " + "=============================================");
            for (li = 0; li < lLastItemIndex; li++) {
                System.out.println("     " +"Item " + (li + 1) + ": " + gArray[lArrayIndex][li]);
            }
        } else {
            // Cannot Continue!!!!
            System.out.println("     ");
            System.out.println("**** Error: No list " + lArrayName + " found! ****");
        }
    }

    @Then("I check the {int} item in list {string}")
    public void iCheckTheItemInList(int litemIndex, String lArrayName) {
        int lArrayIndex = 0;
        int lLastItemIndex = 0;
        lArrayIndex = findArrayNumberFromName(lArrayName);

        // Found array! Show the selected item
        if (lArrayIndex >= 0) {
            lLastItemIndex = gArrayLastItem[lArrayIndex];

            if (litemIndex - 1 <= lLastItemIndex) {
                System.out.println("     " + "=============================================");
                System.out.println("     " + lArrayName + " item " + litemIndex + ": " + gArray[lArrayIndex][litemIndex-1]);
            } else {
                System.out.println("**** Error: list " + lArrayName + " does not contain this item! ****");
            }
        } else {
            // Cannot Continue!!!!
            System.out.println("**** Error: No list " + lArrayName + " found! ****");
        }
    }

    @Given("I display dynamic list")
    public void iCreateDynamicList() {
        int litemIndex = 0;

        System.out.println("     ");
        System.out.println("     -----------------------------------------");
        for (int element : gArrList) {
            litemIndex++;
            System.out.println("     Dynamic list item " + litemIndex + ": " + element);
        }
        System.out.println("     -----------------------------------------");
        System.out.println("     ");
    }

    @When("I get item {int} from the dynamic list")
    public void iGetItemFromTheDynamicList(int litemIndex) {
        int dispIndex = 0;
        // for (int element : gArrList) {
        //    dispIndex++;
        //    System.out.println("     Dynamic list item " + dispIndex + ": " + element);
        // }
        System.out.println("     -----------------------------------------");
        System.out.println("     Dynamic list item " + litemIndex + ": " + gArrList.get(litemIndex-1));
        System.out.println("     -----------------------------------------");
        System.out.println("     ");
    }

    @Given("I add new item {int} to the dynamic list")
    public void iAddNewItemToTheDynamicList(int elementValue) {
        gArrList.add(elementValue);
    }

    @Then("I check if number {int} is positive")
    public void iCheckIfNumberIsPositive(Integer userNumber) {
        String dispAnswer = "unknown";
        if (userNumber > 0) {
            dispAnswer = "positive";
        } else {
            if (userNumber < 0) {
                dispAnswer = "negative";
            } else {
                dispAnswer = "exactly zero";
            }
        }
        System.out.println("     " + userNumber + " is " + dispAnswer);
    }

    @Then("I display day of the week number {int}")
    public void iDisplayDayOfTheWeekNumber(int userDOW) {
        System.out.println("     Day number " + userDOW + " is " + DayOfWeek.of(userDOW).toString());
    }

    @Given("I work with maps")
    public void iWorkWithMaps() {
        Map<String, String> user = new HashMap<>();
        user.put("username", "jdoe");
        user.put("email", "jdoe@nonet.com");
        user.put("password", "123456");
        System.out.println(user);
        String newEmail = user.get("email");
        newEmail = newEmail + "goober";
        user.put("email", newEmail);
        System.out.println(user);

        Map<String, String> admin = new LinkedHashMap<>();
        admin.put("userName", "bigAdmin");
        admin.put("firstName", "John");
        admin.put("middleName", "George");
        admin.put("lastName", "Geek");
        admin.put("email", "admin@nonet.com");
        admin.put("password", "admin123456");
        System.out.println(admin);
        // Map Swap for homework 8/17/2020
        String tempSwap;
        tempSwap =  admin.get("middleName");
        admin.put("middleName", admin.get("firstName"));
        admin.put("firstName", tempSwap);
        System.out.println(admin);
    }

    //  Write a method that returns true
    //    if integer even and divisible by 5 or
    //    if integer odd and divisible by 3.
    @Then("I write the Team {int} coding challenge")
    public void iWriteTheTeamCodingChallenge(int userInt) {
        boolean result = false;
        if (((userInt % 2 == 0) && (userInt % 5 == 0)) || ((userInt%2 == 1) && (userInt%3 == 0))) {
            result = true;
        }
        System.out.println(result);
    }

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
}
