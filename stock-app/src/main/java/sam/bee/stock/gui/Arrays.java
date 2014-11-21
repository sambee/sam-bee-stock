// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Arrays.java

package sam.bee.stock.gui;

import java.io.PrintStream;

import sam.bee.stock.vo.ProductDataVO;


class Arrays {

    Arrays() {
    }

    public static void sort(ProductDataVO aproductdatavo[], String s) {
    }

    private static void quickSort(int a[], int left, int right) {
        if(left < right) {
            int tmp = a[left];
            int i = left;
            for(int j = right; i < j;) {
                while(i < j && a[j] < tmp) 
                    j--;
                if(i < j)
                    a[i++] = a[j];
                for(; i < j && a[i] >= tmp; i++);
                if(i < j)
                    a[j--] = a[i];
            }

            a[i] = tmp;
            quickSort(a, left, i - 1);
            quickSort(a, i + 1, right);
        }
    }

    public static void main(String args[]) {
        int a[] = {
            1, 2, 3, 4, 5
        };
        int b[] = {
            5, 4, 3, 2, 1
        };
        int c[] = {
            3, 2, 1, 4, 5
        };
        int d[] = {
            0x927c2, 0x927c1, 0x7a121
        };
        quickSort(a, 0, a.length - 1);
        quickSort(b, 0, b.length - 1);
        quickSort(c, 0, c.length - 1);
        System.out.println("before sort...");
        for(int i = 0; i < d.length; i++)
            System.out.print(d[i] + "\t");

        System.out.println("\n");
        quickSort(d, 0, d.length - 1);
        reverse(d);
        System.out.println("after sort...");
        for(int i = 0; i < d.length; i++)
            System.out.print(d[i] + "\t");

        System.out.println("\n");
    }

    static void reverse(int a[]) {
        int size = a.length;
        int count = size / 2;
        for(int i = 0; i < count; i++) {
            int tmp = a[i];
            a[i] = a[size - i - 1];
            a[size - i - 1] = tmp;
        }

    }
}
