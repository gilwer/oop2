package OOP.Solution;


import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor {

    private int Id;
    private String name;
    private HashSet<CasaDeBurrito> favorites;
    private HashSet<Profesor> friends;

    public ProfesorImpl(int id, String name) {
        Id = id;
        this.name = name;
        favorites = new HashSet<CasaDeBurrito>();
        friends = new HashSet<Profesor>();
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if(!c.isRatedBy(this)) throw new  UnratedFavoriteCasaDeBurritoException();
        favorites.add(c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return new HashSet<CasaDeBurrito>(favorites);
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
      if(p.equals(this))throw new SameProfesorException();
      if(!friends.add(p))throw new ConnectionAlreadyExistsException();
      return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        return new HashSet<Profesor>(friends);
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        return getFriends().stream().filter(p).collect(Collectors.toSet());
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        return favorites.stream().filter(p).sorted(comp).collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        return filterAndSortFavorites(Comparator.comparingDouble(CasaDeBurrito::averageRating).reversed()
                        .thenComparing(Comparator.comparingInt(CasaDeBurrito::distance))
                        .thenComparing(Comparator.comparingInt(CasaDeBurrito::getId)),
                (c -> c.averageRating() >= rLimit));
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        return filterAndSortFavorites(Comparator.comparingInt(CasaDeBurrito::distance)
                        .thenComparing(Comparator.comparingDouble(CasaDeBurrito::averageRating).reversed())
                        .thenComparing(Comparator.comparingInt(CasaDeBurrito::getId)),
                (c -> c.distance() <= dLimit));
    }

    @Override
    public int compareTo(Profesor p) {
        return Id-p.getId();
    }

    @Override
    public String toString(){
        return "Profesor: " + name
                + ".\nId: " + Integer.toString(Id)
                + ".\nFavorites: " + favorites.stream()
                .map(CasaDeBurrito::getName).sorted().collect(Collectors.joining(",")) +".";}

    @Override
    public boolean equals(Object o){
        return o instanceof Profesor ? Id == ((Profesor) o).getId() : false;
    }

    @Override
    public int hashCode(){
        return getId();
    }
}
