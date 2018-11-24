package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;


public class CartelDeNachosImpl implements CartelDeNachos {
    private HashMap<Integer, Profesor> profesors;
    private HashMap<Integer, CasaDeBurrito> restaurants;

    public CartelDeNachosImpl() {
        profesors = new HashMap<>();
        restaurants = new HashMap<>();
    }

    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        if (profesors.containsKey(id)) throw new Profesor.ProfesorAlreadyInSystemException();
        Profesor profesor = new ProfesorImpl(id, name);
        profesors.put(id, profesor);
        return profesor;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu) throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        if (restaurants.containsKey(id)) throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        CasaDeBurrito restaurant = new CasaDeBurritoImpl(id, name, dist, menu);
        restaurants.put(id, restaurant);
        return restaurant;
    }

    @Override
    public Collection<Profesor> registeredProfesores() {
        return new ArrayList<>(profesors.values());
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        return new ArrayList<>(restaurants.values());
    }

    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        if (!profesors.containsKey(id)) throw new Profesor.ProfesorNotInSystemException();
        return profesors.get(id);
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id) throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        if (!restaurants.containsKey(id)) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        return restaurants.get(id);
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2) throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        if (!profesors.containsKey(p1.getId()) || !profesors.containsKey(p2.getId()))
            throw new Profesor.ProfesorNotInSystemException();
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if (!profesors.containsKey(p.getId())) throw new Profesor.ProfesorNotInSystemException();
        return p.getFriends().stream()
                .sorted()
                .map(f -> f.favoritesByRating(0))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if (!profesors.containsKey(p.getId())) throw new Profesor.ProfesorNotInSystemException();
        int maxDist = p.getFriends().stream()
                .map(Profesor::favorites)
                .flatMap(Collection::stream)
                .map(CasaDeBurrito::distance)
                .max(Comparator.comparingInt(d -> d))
                .get();
        return p.getFriends().stream()
                .sorted()
                .map(f -> f.favoritesByDist(maxDist))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t) throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        if (!profesors.containsKey(p.getId())) throw new Profesor.ProfesorNotInSystemException();
        if (!restaurants.containsKey(c.getId())) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if (t < 0) throw new ImpossibleConnectionException();
        return getRecommendationAux(p, c, t);
    }

    private boolean getRecommendationAux(Profesor p, CasaDeBurrito c, int t) {
        if (t == 0)
            return p.favorites().contains(c);
        return p.favorites().contains(c) || p.getFriends().stream().anyMatch(profesor -> this.getRecommendationAux(profesor, c, t - 1));
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        return null;
    }


    @Override
    public String toString() {
        return "Registered profesores: " + profesors.keySet().stream().sorted().map(String::valueOf).collect(Collectors.joining(", "))
                + ".\nRegistered casas de burrito: " + restaurants.keySet().stream().sorted().map(String::valueOf).collect(Collectors.joining(", "))
                + ".\nProfesores:"
                + profesors.values().stream().sorted().map(p -> "\n" + p.getId() + " -> " + p.getFriends().stream().map(Profesor::getId).sorted().collect(Collectors.toList()) + ".").collect(Collectors.joining(""));
    }
}
