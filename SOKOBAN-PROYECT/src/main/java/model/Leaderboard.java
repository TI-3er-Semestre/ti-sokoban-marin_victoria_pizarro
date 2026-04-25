package model;
import structures.LinkedList;

public class Leaderboard {

    private LinkedList<GameStats> results;

    public Leaderboard() {
            results = new LinkedList<>();


    }
    public void add(GameStats stat){
        results.add(stat);

    }
    public LinkedList<GameStats> getResults(){
        return results;
    }
    public void displayRanking(){
        System.out.println(results.display());
    }



}
