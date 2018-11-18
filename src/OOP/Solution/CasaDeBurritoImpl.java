package OOP.Solution;


import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

public class CasaDeBurritoImpl implements CasaDeBurrito {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int distance() {
        return 0;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        return false;
    }

    @Override
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        return null;
    }

    @Override
    public int numberOfRates() {
        return 0;
    }

    @Override
    public double averageRating() {
        return 0;
    }

    @Override
    public int compareTo(CasaDeBurrito o) {
        return 0;
    }
}
