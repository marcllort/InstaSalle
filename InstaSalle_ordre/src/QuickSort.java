import Json.Posts;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort {

    private ArrayList<Posts> array;
    private int length;


    public void sort(ArrayList<Posts> inputArr) {

        if (inputArr == null || inputArr.size() == 0) {
            return;
        }

        this.array = inputArr;
        length = inputArr.size();

        quickSort(0, length - 1);

    }

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;


        // calculate pivot number, I am taking pivot as middle index number
        Double pivot = array.get(lowerIndex + (higherIndex - lowerIndex) / 2).getOrdre();
        //double pivot = p.intValue();
        /*if (p-pivot > 0.5){
            pivot++;
        }*/

        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a number
             * from right side which is less then the pivot value. Once the search
             * is done, then we exchange both numbers.
             */
            while (array.get(i).getOrdre() < pivot) {
                i++;
            }

            while (array.get(j).getOrdre() > pivot) {
                j--;
                //System.out.println("ORDRE: "+array.get(j).getOrdre()+ " PIVOT: " + pivot+ " J: "+ j);

            }

            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }

        }

        // call quickSort() method recursively
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }

    }

    private void exchangeNumbers(int i, int j) {
        Collections.swap(array, i, j);
    }

}