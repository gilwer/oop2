package OOP.Solution;


import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class CasaDeBurritoImpl implements CasaDeBurrito {

    private int Id;
    private String name;
    private int distance;
    Set<String> menu;
    HashMap<Profesor,Integer> ratings;

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu) {
        Id = id;
        this.name = name;
        this.distance = dist;
        this.menu = menu;
        ratings = new HashMap<Profesor,Integer>();
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int distance() {
        return distance;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        return ratings.containsKey(p);
    }

    @Override
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if(r>5 || r<0) throw new RateRangeException();
        ratings.put(p,r);
        return this;
    }

    @Override
    public int numberOfRates() {
        return ratings.size();
    }

    @Override
    public double averageRating() {
        return ratings.values().stream().mapToInt(i->i).sum()/numberOfRates();
    }

    @Override
    public int compareTo(CasaDeBurrito c) {
        return Id-c.getId();
    }

    @Override
    public boolean equals(Object o){
        return o instanceof CasaDeBurrito ? false : Id == ((CasaDeBurrito) o).getId();
    }

    @Override
    public String toString(){
        return "CasaDeBurrito: " + name
            + ".\nId: " + Integer.toString(Id)
            + ".\nDistance: " + Integer.toString(distance)
            + ".\nMenu: " + menu.stream().sorted().collect(Collectors.joining(",")) + ".";}
}
