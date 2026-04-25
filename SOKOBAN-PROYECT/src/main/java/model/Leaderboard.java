package model;
import structures.LinkedList;

public class Leaderboard {

    private LinkedList<GameStats> results;

    /**
     * construye una leaderboard vacia.
     */

    public Leaderboard() {
        results = new LinkedList<>();


    }

    /**
     * agrega una nueva estadistica al final de la lista
     *
     * @param stat la estadistica a agregar
     */

    public void add(GameStats stat) {
        results.add(stat);

    }

    public LinkedList<GameStats> getResults() {
        return results;
    }

    public void displayRanking() {
        System.out.println(results.display());
    }

    /**
     * convierte la lista interna a un arreglo de Gamestats
     *
     * @return arreglo con elementos en el mismo orden que la Lista
     */

    private GameStats[] toArray() {

        GameStats[] arr = new GameStats[results.getLength()];
        int i = 0;

        structures.CustomIterator<GameStats> it = results.iterator();

        while (it.hasNext()) {
            arr[i++] = it.next().getData();
        }

        return arr;
    }

    /**
     * ordena la estadistica por numero de movimientos usando el algoritno merge sort
     *
     * @return un arreglo ordenado de game stats segn getmoves
     */
    public GameStats[] sortByMoves() {
        GameStats[] arr = toArray();
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    public GameStats[] sortByLevel() {
        GameStats[] arr = toArray();
        mergeSortByLevel(arr, 0, arr.length - 1);
        return arr;
    }

    public GameStats[] sortByScore() {
        GameStats[] arr = toArray();
        mergeSortByScore(arr, 0, arr.length - 1);
        return arr;
    }

    public GameStats[] sortByPushes() {
        GameStats[] arr = toArray();
        mergeSortByPushes(arr, 0, arr.length - 1);
        return arr;
    }

    public GameStats[] sortByTime() {
        GameStats[] arr = toArray();
        mergeSortByTime(arr, 0, arr.length - 1);
        return arr;
    }

    private void mergeSortByTime(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortByTime(arr, left, mid);
            mergeSortByTime(arr, mid + 1, right);

            mergeByTime(arr, left, mid, right);
        }
    }

    private void mergeByTime(GameStats[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].getTime() <= R[j].getTime()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    private void mergeSortByPushes(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortByPushes(arr, left, mid);
            mergeSortByPushes(arr, mid + 1, right);

            mergeByPushes(arr, left, mid, right);
        }
    }

    private void mergeByPushes(GameStats[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].getPushes() <= R[j].getPushes()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }


    private void mergeSortByScore(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortByScore(arr, left, mid);
            mergeSortByScore(arr, mid + 1, right);

            mergeByScore(arr, left, mid, right);
        }
    }

    private void mergeByScore(GameStats[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];
        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];


        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].calculateScore() >= R[j].calculateScore()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }

        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];


    }

    private void mergeSortByLevel(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortByLevel(arr, left, mid);
            mergeSortByLevel(arr, mid + 1, right);

            mergeByLevel(arr, left, mid, right);
        }
    }

    private void mergeByLevel(GameStats[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].getLevelId() <= R[j].getLevelId()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }


    /**
     * metodo recursivo de ordenaminto por mezcla
     *
     * @param arr   arreglo a ordenar
     * @param left  indice izquierd
     * @param right indice derecho
     */
    private void mergeSort(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    /**
     * fusiona 2 subarreglos ordenados en un solo arreglo orden dado
     *
     * @param arr   arreglo que contiene los subarreglos
     * @param left  inicio del primer subarreglo
     * @param mid   fin del primer subaarrelgo
     * @param right fin del segundo subarreglo
     */
    private void merge(GameStats[] arr, int left, int mid, int right) {
        // calcular el tamano en subArreglos que voy a unir
        int n1 = mid - left + 1;
        int n2 = right - mid;

        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];


        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].getMoves() <= R[j].getMoves()) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];


            }
        }
        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) arr[k++] = R[j++];
    }
    public GameStats binarySearchByScore(double score) {
        GameStats[] arr = sortByScore();

        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            double midScore = arr[mid].calculateScore();

            if (Math.abs(midScore - score) < 0.0001)
                return arr[mid];

            if (midScore < score)
                right = mid - 1;
            else
                left = mid + 1;
        }

        return null;
    }
    public GameStats binarySearchByTime(double time) {
        GameStats[] arr = sortByTime();
        // bisqueda binaria por tiempo
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (arr[mid].getTime() == time)
                return arr[mid];

            if (arr[mid].getTime() < time)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return null;
    }
    public GameStats binarySearchByLevel(int levelId) {
        GameStats[] arr = sortByLevel();
        // busqueda binaria por nivel

        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (arr[mid].getLevelId() == levelId)
                return arr[mid];

            if (arr[mid].getLevelId() < levelId)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return null;
    }



}










