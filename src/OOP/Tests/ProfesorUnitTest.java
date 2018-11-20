package OOP.Tests;


import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CartelDeNachos;
import OOP.Provided.CartelDeNachos.ImpossibleConnectionException;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Provided.CasaDeBurrito.*;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ProfesorUnitTest {

    @Test public void unitTests() {
        Profesor p1 = new ProfesorImpl(1, "p1");
        assertTrue(p1.getId() == 1);
        Profesor p2 = new ProfesorImpl(2, "p2");
        Profesor p3 = new ProfesorImpl(3, "p3");
        Profesor p4 = new ProfesorImpl(4, "p4");
        Profesor p5 = new ProfesorImpl(5, "p5");
        Profesor p6 = new ProfesorImpl(6, "p6");
        boolean error_thrown = false;
        try {
            p1.addFriend(p2);
            p1.addFriend(p3);
            p1.addFriend(p5);
            p2.addFriend(p3);
            p4.addFriend(p5);
        } catch (SameProfesorException | ConnectionAlreadyExistsException e) {
            fail();
        }
        try{
            p1.addFriend(p1);
        }catch (SameProfesorException e){
            error_thrown = true;
        }catch (ConnectionAlreadyExistsException e){fail();}
        assertTrue(error_thrown);
        error_thrown = false;
        try{
            p1.addFriend(p3);
        }catch (ConnectionAlreadyExistsException e){
            error_thrown = true;
        }catch (SameProfesorException e){fail();}
        assertTrue(error_thrown);
        error_thrown = false;
        CasaDeBurrito c1 = new CasaDeBurritoTst();
        CasaDeBurrito c2 = new CasaDeBurritoTst(){
            @Override public boolean isRatedBy(Profesor p){return true;}
        };
        try {
            p1.favorite(c1);
        }catch (UnratedFavoriteCasaDeBurritoException e){
            error_thrown = true;
        }
        assertTrue(error_thrown);
        error_thrown = false;
        try{
            p1.favorite(c2);
        }catch (UnratedFavoriteCasaDeBurritoException e){fail();}
        Collection<CasaDeBurrito> f = p1.favorites();
        assertTrue(f.contains(c2));
        assertFalse(f.contains(c1));
        Set<Profesor> friends = p1.getFriends();
        assertTrue(friends.contains(p2));
        assertTrue(friends.contains(p5));
        assertFalse(friends.contains(p1));
        assertFalse(friends.contains(p4));
    }

    @Test public void unitStreamTests() {
        Profesor p1 = new ProfesorImpl(1, "p1");
        CasaDeBurrito c1 = new CasaDeBurritoTst(){

            @Override
            public int getId() {
                return 11;
            }
            @Override
            public int distance(){
                return 3;
            }

            @Override
            public double averageRating(){
                return 2;
            }

            @Override
            public boolean isRatedBy(Profesor p){
                return true;
            }
            @Override
            public String toString(){return "\n i:11 d:3 r:2 ";}
        };
        CasaDeBurrito c2 = new CasaDeBurritoTst(){

            @Override
            public int getId() {
                return 12;
            }
            @Override
            public int distance(){
                return 3;
            }

            @Override
            public double averageRating(){
                return 2;
            }

            @Override
            public boolean isRatedBy(Profesor p){
                return true;
            }
            @Override
            public String toString(){return "\n i:12 d:3 r:2 ";}
        };
        CasaDeBurrito c3 = new CasaDeBurritoTst(){

            @Override
            public int getId() {
                return 14;
            }
            @Override
            public int distance(){
                return 3;
            }

            @Override
            public double averageRating(){
                return 4;
            }

            @Override
            public boolean isRatedBy(Profesor p){
                return true;
            }
            @Override
            public String toString(){return "\n i:14 d:3 r:4 ";}
        };
        CasaDeBurrito c4 = new CasaDeBurritoTst(){

            @Override
            public int getId() {
                return 15;
            }
            @Override
            public int distance(){
                return 4;
            }

            @Override
            public double averageRating(){
                return 3;
            }

            @Override
            public boolean isRatedBy(Profesor p){
                return true;
            }
            @Override
            public String toString(){return "\n i:15 d:4 r:3 ";}
        };
        CasaDeBurrito c5 = new CasaDeBurritoTst(){

            @Override
            public int getId() {
                return 16;
            }
            @Override
            public int distance(){
                return 5;
            }

            @Override
            public double averageRating(){
                return 3;
            }

            @Override
            public boolean isRatedBy(Profesor p){
                return true;
            }
            @Override
            public String toString(){return "\n i:16 d:5 r:3 ";}
        };
        CasaDeBurrito c6 = new CasaDeBurritoTst(){

            @Override
            public int getId() {
                return 17;
            }
            @Override
            public int distance(){
                return 4;
            }

            @Override
            public double averageRating(){
                return 2;
            }

            @Override
            public boolean isRatedBy(Profesor p){
                return true;
            }
            @Override
            public String toString(){return "\n i:17 d:4 r:2 ";}
        };
        try {
            p1.favorite(c1).favorite(c2).favorite(c3).favorite(c4).favorite(c5).favorite(c6);
        }catch (UnratedFavoriteCasaDeBurritoException e){fail();}
        assertTrue(p1.favoritesByDist(2).size()==0);
        assertTrue(p1.favoritesByRating(5).size()==0);
        Collection<CasaDeBurrito> rate =  p1.favoritesByRating(0);
        Collection<CasaDeBurrito> dist = p1.favoritesByDist(20);
        rate.forEach((i->System.out.print(i.toString())));
        System.out.print("\n\n");
        dist.forEach((i->System.out.print(i.toString())));



    }


        private class CasaDeBurritoTst implements CasaDeBurrito{
            CasaDeBurritoTst() {
            }

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
}
