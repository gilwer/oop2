package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.CasaDeBurrito.CasaDeBurritoAlreadyInSystemException;
import OOP.Provided.CasaDeBurrito.CasaDeBurritoNotInSystemException;
import OOP.Provided.CasaDeBurrito.RateRangeException;
import OOP.Provided.Profesor;
import OOP.Provided.Profesor.*;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

public class CartelDeNachosUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void EmptyCartelTest() {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        assertNotNull(cartel);
        ArrayList<CasaDeBurrito> restaurants = (ArrayList<CasaDeBurrito>) cartel.registeredCasasDeBurrito();
        ArrayList<Profesor> profesors = (ArrayList<Profesor>) cartel.registeredProfesores();
        assertNotNull(restaurants);
        assertNotNull(profesors);
        assertTrue(restaurants.isEmpty());
        assertTrue(profesors.isEmpty());
    }

    @Test
    public void ProfesorFlowTest() throws ProfesorAlreadyInSystemException, ProfesorNotInSystemException {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        assertNotNull(cartel);
        Profesor p1, p2;
        try {
            p1 = cartel.joinCartel(1, "Danny");
            p2 = cartel.joinCartel(2, "Moshe");
            ArrayList<Profesor> profesors = (ArrayList<Profesor>) cartel.registeredProfesores();
            assertNotNull(profesors);
            assertEquals(2, profesors.size());
            Profesor p3 = cartel.getProfesor(1);
            assertSame(p1, p3);
            assertEquals(p1, p3);
            assertNotSame(p2, p3);
            assertNotEquals(p2, p3);
        } catch (ProfesorAlreadyInSystemException | ProfesorNotInSystemException e) {
            fail();
        }

        exceptionRule.expect(ProfesorAlreadyInSystemException.class);
        cartel.joinCartel(1, "Jacob");
        exceptionRule.expect(ProfesorNotInSystemException.class);
        cartel.getProfesor(3);
    }

    @Test
    public void CasaDeBurritoFlowTest() throws CasaDeBurritoAlreadyInSystemException, CasaDeBurritoNotInSystemException {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        assertNotNull(cartel);
        CasaDeBurrito c1, c2;
        try {
            HashSet<String> menu1 = new HashSet<>();
            menu1.add("item1");
            HashSet<String> menu2 = new HashSet<>();
            menu2.add("item2");
            c1 = cartel.addCasaDeBurrito(1, "Pollo Loco", 10, menu1);
            c2 = cartel.addCasaDeBurrito(2, "Taco Bell", 20, menu2);
            ArrayList<CasaDeBurrito> restaurants = (ArrayList<CasaDeBurrito>) cartel.registeredCasasDeBurrito();
            assertNotNull(restaurants);
            assertEquals(2, restaurants.size());
            CasaDeBurrito c3 = cartel.getCasaDeBurrito(1);
            assertSame(c1, c3);
            assertEquals(c1, c3);
            assertNotSame(c2, c3);
            assertNotEquals(c2, c3);
        } catch (CasaDeBurritoNotInSystemException | CasaDeBurritoAlreadyInSystemException e) {
            fail();
        }

        exceptionRule.expect(CasaDeBurritoAlreadyInSystemException.class);
        HashSet<String> menu3 = new HashSet<>();
        menu3.add("item2");
        cartel.addCasaDeBurrito(1, "Casa Orginal", 30, menu3);
        exceptionRule.expect(CasaDeBurritoNotInSystemException.class);
        cartel.getCasaDeBurrito(3);
    }

    @Test
    public void addConnectionTest() throws ConnectionAlreadyExistsException, SameProfesorException, ProfesorNotInSystemException {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        assertNotNull(cartel);
        Profesor p1 = null, p2 = null;
        try {
            p1 = cartel.joinCartel(1, "Danny");
            p2 = cartel.joinCartel(2, "Moshe");

        } catch (ProfesorAlreadyInSystemException e) {
            fail();
        }
        Profesor p3 = new ProfesorImpl(3, "Jacob");
        try {
            cartel.addConnection(p1, p2);
            assertTrue(p1.getFriends().contains(p2));
            assertTrue(p2.getFriends().contains(p1));
        } catch (ProfesorNotInSystemException | ConnectionAlreadyExistsException | SameProfesorException e) {
            fail();
        }
        exceptionRule.expect(ConnectionAlreadyExistsException.class);
        cartel.addConnection(p2, p1);
        Profesor p4 = null;
        try {
            p4 = cartel.getProfesor(1);
        } catch (ProfesorNotInSystemException e) {
            fail();
        }
        exceptionRule.expect(SameProfesorException.class);
        cartel.addConnection(p1, p4);
        exceptionRule.expect(ProfesorNotInSystemException.class);
        cartel.addConnection(p1, p3);
    }

    @Test
    public void favoritesTest() throws ProfesorNotInSystemException, UnratedFavoriteCasaDeBurritoException, CartelDeNachos.ImpossibleConnectionException, CasaDeBurritoNotInSystemException {
        CartelDeNachos cartel = new CartelDeNachosImpl();
        assertNotNull(cartel);
        CasaDeBurrito[] casaDeBurritos = new CasaDeBurrito[7];
        Profesor[] profesors = new Profesor[5];
        HashSet[] menus = new HashSet[7];

        try {
            profesors[0] = cartel.joinCartel(1, "Danny");
            profesors[1] = cartel.joinCartel(2, "Moshe");
            profesors[2] = cartel.joinCartel(3, "Jacob");
            profesors[3] = cartel.joinCartel(4, "Ella");
            profesors[4] = cartel.joinCartel(5, "Robin");

            for (int i = 0; i < menus.length; i++) {
                menus[i] = new HashSet<String>();
                menus[i].add("item" + String.valueOf(i));
            }
            casaDeBurritos[0] = cartel.addCasaDeBurrito(1, "McDonald's", 20, menus[0]);
            casaDeBurritos[1] = cartel.addCasaDeBurrito(2, "KFC", 40, menus[1]);
            casaDeBurritos[2] = cartel.addCasaDeBurrito(3, "Dunkin' Donuts", 60, menus[2]);
            casaDeBurritos[3] = cartel.addCasaDeBurrito(4, "Pizza Hut", 70, menus[3]);
            casaDeBurritos[4] = cartel.addCasaDeBurrito(5, "Subway", 50, menus[4]);
            casaDeBurritos[5] = cartel.addCasaDeBurrito(6, "Starbucks", 30, menus[5]);
            casaDeBurritos[6] = cartel.addCasaDeBurrito(7, "Taco Bell", 10, menus[6]);

            casaDeBurritos[0].rate(profesors[1], 2);
            profesors[1].favorite(casaDeBurritos[0]);
            casaDeBurritos[0].rate(profesors[4], 4);
            profesors[4].favorite(casaDeBurritos[0]);
            casaDeBurritos[1].rate(profesors[2], 3);
            profesors[2].favorite(casaDeBurritos[1]);
            casaDeBurritos[1].rate(profesors[3], 1);
            profesors[3].favorite(casaDeBurritos[1]);
            casaDeBurritos[2].rate(profesors[2], 5);
            profesors[2].favorite(casaDeBurritos[2]);
            casaDeBurritos[3].rate(profesors[1], 4);
            profesors[1].favorite(casaDeBurritos[3]);
            casaDeBurritos[4].rate(profesors[0], 1);
            profesors[0].favorite(casaDeBurritos[4]);
            casaDeBurritos[4].rate(profesors[3], 4);
            profesors[3].favorite(casaDeBurritos[4]);
            casaDeBurritos[5].rate(profesors[0], 3);
            profesors[0].favorite(casaDeBurritos[5]);
            casaDeBurritos[6].rate(profesors[2], 4);
            profesors[2].favorite(casaDeBurritos[6]);
            casaDeBurritos[6].rate(profesors[4], 5);
            profesors[4].favorite(casaDeBurritos[6]);

            cartel.addConnection(profesors[0], profesors[3]);
            cartel.addConnection(profesors[1], profesors[2]);
            cartel.addConnection(profesors[1], profesors[3]);
            cartel.addConnection(profesors[1], profesors[4]);
            cartel.addConnection(profesors[2], profesors[3]);
            cartel.addConnection(profesors[3], profesors[4]);

            ArrayList<CasaDeBurrito> expResByRate1 = new ArrayList<>();
            expResByRate1.add(casaDeBurritos[4]);
            expResByRate1.add(casaDeBurritos[1]);
            assertEquals(cartel.favoritesByRating(profesors[0]),expResByRate1);
            ArrayList<CasaDeBurrito> expResByRate2 = new ArrayList<>();
            expResByRate2.add(casaDeBurritos[2]);
            expResByRate2.add(casaDeBurritos[6]);
            expResByRate2.add(casaDeBurritos[1]);
            expResByRate2.add(casaDeBurritos[4]);
            expResByRate2.add(casaDeBurritos[0]);
            assertEquals(cartel.favoritesByRating(profesors[1]),expResByRate2);
            ArrayList<CasaDeBurrito> expResByRate3 = new ArrayList<>();
            expResByRate3.add(casaDeBurritos[3]);
            expResByRate3.add(casaDeBurritos[0]);
            expResByRate3.add(casaDeBurritos[4]);
            expResByRate3.add(casaDeBurritos[1]);
            assertEquals(cartel.favoritesByRating(profesors[2]),expResByRate3);
            ArrayList<CasaDeBurrito> expResByRate4 = new ArrayList<>();
            expResByRate4.add(casaDeBurritos[5]);
            expResByRate4.add(casaDeBurritos[4]);
            expResByRate4.add(casaDeBurritos[3]);
            expResByRate4.add(casaDeBurritos[0]);
            expResByRate4.add(casaDeBurritos[2]);
            expResByRate4.add(casaDeBurritos[6]);
            expResByRate4.add(casaDeBurritos[1]);
            assertEquals(cartel.favoritesByRating(profesors[3]),expResByRate4);
            ArrayList<CasaDeBurrito> expResByRate5 = new ArrayList<>();
            expResByRate5.add(casaDeBurritos[3]);
            expResByRate5.add(casaDeBurritos[0]);
            expResByRate5.add(casaDeBurritos[4]);
            expResByRate5.add(casaDeBurritos[1]);
            assertEquals(cartel.favoritesByRating(profesors[4]),expResByRate5);

            ArrayList<CasaDeBurrito> expResByDist1 = new ArrayList<>();
            expResByDist1.add(casaDeBurritos[1]);
            expResByDist1.add(casaDeBurritos[4]);
            assertEquals(cartel.favoritesByDist(profesors[0]), expResByDist1);
            ArrayList<CasaDeBurrito> expResByDist2 = new ArrayList<>();
            expResByDist2.add(casaDeBurritos[6]);
            expResByDist2.add(casaDeBurritos[1]);
            expResByDist2.add(casaDeBurritos[2]);
            expResByDist2.add(casaDeBurritos[4]);
            expResByDist2.add(casaDeBurritos[0]);
            assertEquals(cartel.favoritesByDist(profesors[1]), expResByDist2);
            ArrayList<CasaDeBurrito> expResByDist3 = new ArrayList<>();
            expResByDist3.add(casaDeBurritos[0]);
            expResByDist3.add(casaDeBurritos[3]);
            expResByDist3.add(casaDeBurritos[1]);
            expResByDist3.add(casaDeBurritos[4]);
            assertEquals(cartel.favoritesByDist(profesors[2]), expResByDist3);
            ArrayList<CasaDeBurrito> expResByDist4 = new ArrayList<>();
            expResByDist4.add(casaDeBurritos[5]);
            expResByDist4.add(casaDeBurritos[4]);
            expResByDist4.add(casaDeBurritos[0]);
            expResByDist4.add(casaDeBurritos[3]);
            expResByDist4.add(casaDeBurritos[6]);
            expResByDist4.add(casaDeBurritos[1]);
            expResByDist4.add(casaDeBurritos[2]);
            assertEquals(cartel.favoritesByDist(profesors[3]), expResByDist4);
            ArrayList<CasaDeBurrito> expResByDist5 = new ArrayList<>();
            expResByDist5.add(casaDeBurritos[0]);
            expResByDist5.add(casaDeBurritos[3]);
            expResByDist5.add(casaDeBurritos[1]);
            expResByDist5.add(casaDeBurritos[4]);
            assertEquals(cartel.favoritesByDist(profesors[4]), expResByDist5);

            assertFalse(cartel.getRecommendation(profesors[0], casaDeBurritos[1], 0));
            assertTrue(cartel.getRecommendation(profesors[0], casaDeBurritos[1], 1));
            assertTrue(cartel.getRecommendation(profesors[0], casaDeBurritos[6], 2));
            assertTrue(cartel.getRecommendation(profesors[4], casaDeBurritos[6], 0));
            assertFalse(cartel.getRecommendation(profesors[4], casaDeBurritos[2], 1));
            assertTrue(cartel.getRecommendation(profesors[4], casaDeBurritos[2], 2));
            assertTrue(cartel.getRecommendation(profesors[2], casaDeBurritos[5], 2));

        } catch (ProfesorAlreadyInSystemException | CasaDeBurritoAlreadyInSystemException | RateRangeException | SameProfesorException | ConnectionAlreadyExistsException | CasaDeBurritoNotInSystemException | CartelDeNachos.ImpossibleConnectionException e) {
            fail();
        }
        System.out.print(cartel);

        Profesor p = new ProfesorImpl(10, "David");
        exceptionRule.expect(ProfesorNotInSystemException.class);
        cartel.favoritesByRating(p);
        cartel.favoritesByDist(p);
        cartel.getRecommendation(p, casaDeBurritos[0], 0);
        CasaDeBurrito c = new CasaDeBurritoImpl(10, "McDavid", 100, null);
        exceptionRule.expect(CasaDeBurritoNotInSystemException.class);
        cartel.getRecommendation(profesors[0], c, 0);
        exceptionRule.expect(CartelDeNachos.ImpossibleConnectionException.class);
        cartel.getRecommendation(profesors[0], casaDeBurritos[0], -4);

    }
}