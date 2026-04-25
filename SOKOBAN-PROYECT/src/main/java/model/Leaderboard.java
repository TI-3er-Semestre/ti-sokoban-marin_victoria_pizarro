package model;
import structures.LinkedList;

public class Leaderboard {

    private LinkedList<GameStats> results;

    public Leaderboard() {
        results = new LinkedList<>();


    }

    public void add(GameStats stat) {
        results.add(stat);

    }

    public LinkedList<GameStats> getResults() {
        return results;
    }

    public void displayRanking() {
        System.out.println(results.display());
    }

    private GameStats[] toArray() {

        GameStats[] arr = new GameStats[results.getLength()];
        int i = 0;

        structures.CustomIterator<GameStats> it = results.iterator();

        while (it.hasNext()) {
            arr[i++] = it.next().getData();
        }

        return arr;
    }
    public GameStats[] sortByMoves(){
        GameStats[] arr = toArray();
        mergeSort(arr, 0,arr.length - 1);
        return arr;
    }
    private void mergeSort(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }
    private void merge(GameStats[]arr, int left, int mid, int right){
        // calcular el tamano en subArreglos que voy a unir
        int n1 = mid - left + 1;
        int n2 = right - mid;

        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];


        for  (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j=0, k = left;

        while (i<n1 && j<n2) {
            if (L[i].getMoves() <= R[j].getMoves()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];


            }
        }
        while(i < n1) {
                arr[k++] = L[i++];
            }
        while (j < n2) arr[k++] = R[j++];
        }




    }








