import Json.Posts;

import java.util.ArrayList;


class RadixSort {

    Posts[] arr;
    int n;

    // Utilitat per agafar el valor maxim de arr
    public double getMax() {

        double mx = arr[0].getOrdre();

        for (int i = 1; i < n; i++) {
            if (arr[i].getOrdre() > mx) {
                mx = arr[i].getOrdre();
            }
        }

        return mx;

    }

    void countSort(Posts arr[], int exp) {

        Posts[] output = new Posts[n]; // output array
        int i;
        int[] count = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        for (i = 0; i < n; i++) {
            count[(arr[i].getOrdre().intValue() / exp) % 10]++;
            //System.out.println("N: " + n + " I: " + i + " POS: " + (arr[i].getOrdre().intValue() / exp) % 10);
        }

        // Moure totes les caselles perque el numero equivalgui al del valor
        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Construir el array output
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i].getOrdre().intValue() / exp) % 10] - 1] = arr[i];
            count[(arr[i].getOrdre().intValue() / exp) % 10]--;
        }

        // Copiar el array ouput a arr perquè arr estigui ordenat
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
        }

    }

    public void sort(ArrayList<Posts> arrp) {

        arr = new Posts[arrp.size()];
        arr = arrp.toArray(arr);
        n = arr.length;

        // Trobar el nombre màxim de digits
        double m = getMax();

        // fer el countingSort per tots els digits

        for (int exp = 1; m / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }

    }

}
