// Code from filename: MyClass.java
class MyExponent {
    public static void main(String[] args) {
        // Calculate exp(1). End when the term is less than 0.00001
        double sum = 0.0;
        double term = 1.0;
        int k = 1;

        while (term >= 0.00001) {
            sum = sum + term;
            term = term / k;
            k++;                 // Shortcut for ‘k = k + 1’
        }
        System.out.println("sum: " + sum);
    }
}
