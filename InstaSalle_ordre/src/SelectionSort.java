import Json.Posts;

import java.util.ArrayList;


public class SelectionSort {

    public void sort(ArrayList<Posts> inputArr) {

        Posts[] arr = new Posts[inputArr.size()];
        arr = inputArr.toArray(arr);

        for (int i = 0; i < arr.length - 1; i++) {

            int index = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].getOrdre() < arr[index].getOrdre()) {
                    index = j;
                }
            }

            Posts aux = arr[index];
            arr[index] = arr[i];
            arr[i] = aux;

        }
    }

}
