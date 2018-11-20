package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CartelDeNachos;
import OOP.Provided.CartelDeNachos.ImpossibleConnectionException;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Provided.CasaDeBurrito.*;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CasaDeBurritoUnitTest {

    @Test
    public void unitTests() {
        HashSet<String> menu = new HashSet<String>();
        menu.add("item1");
        menu.add("item2");
        menu.add("aitem1");
        CasaDeBurritoImpl c1 = new CasaDeBurritoImpl(1,"c1",2,menu);
        assertEquals(c1.distance(),2);
        assertEquals(c1.getId(),1);
        assertEquals(c1.getName(),"c1");
        Profesor p = new ProfesorTest(){
            @Override
            public int getId(){ return 10;}
        };
        Profesor q = new ProfesorTest(){
            @Override
            public int getId(){ return 11;}
        };
        boolean error_thrown = false;
        try{
            c1.rate(p,3).rate(p,2).rate(p,4).rate(q,1);
        }catch (RateRangeException e){
            fail();
        }
        try{
            c1.rate(p,7);
        }catch (RateRangeException e){
           error_thrown = true;
        }
        assertTrue(error_thrown);
        error_thrown = false;
        assertTrue(c1.isRatedBy(p));
        assertTrue(c1.isRatedBy(q));
        Profesor f = new ProfesorTest(){
            @Override
            public int getId(){ return 12;}
        };
        assertFalse(c1.isRatedBy(f));
        assertTrue(c1.numberOfRates()==2);
        assertTrue(c1.averageRating()==(4+1)/2);
        assertTrue(c1.compareTo(c1)==0);
        CasaDeBurrito c2 = new CasaDeBurritoImpl(2,"c2",3,menu);
        assertTrue(c1.compareTo(c2)==-1);
        assertTrue(c2.compareTo(c1)==1);
    }


    private class ProfesorTest implements Profesor{

        ProfesorTest() {
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
            return null;
        }

        @Override
        public Collection<CasaDeBurrito> favorites() {
            return null;
        }

        @Override
        public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
            return null;
        }

        @Override
        public Set<Profesor> getFriends() {
            return null;
        }

        @Override
        public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
            return null;
        }

        @Override
        public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
            return null;
        }

        @Override
        public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
            return null;
        }

        @Override
        public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
            return null;
        }

        @Override
        public int compareTo(Profesor o) {
            return 0;
        }
    }











}
