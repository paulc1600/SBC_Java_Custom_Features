package aut;

// AnyObjectPrinter.printIt()
public class AnyObjectPrinter {
    public static void printIt(Object[] anyArr) {
        for (Object element: anyArr) {
            System.out.printf("% 3d ", element);
        }
        System.out.print("\n");
        return;
    }
}
