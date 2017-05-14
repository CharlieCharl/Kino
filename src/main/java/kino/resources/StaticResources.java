package kino.resources;

import kino.Main;
import kino.model.Genere;
import kino.model.Movie;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class StaticResources {

    private static List<Object> objects = new ArrayList<>();

    public  static void populatedb(){
        addMovies();

        addToDatabase();
    }

    private static void addMovies() {
        Movie movie = new Movie();
        movie.setTitle("Obcy:Przymierze");
        movie.setDescription("Legendarny reżyser Ridley Scott, twórca ”Łowcy androidów” i ”Marsjanina”, powraca do świata, który stworzył w filmie ”Obcy – 8 pasażer >Nostromo<”, zaliczanym do klasyki kina science fiction. \"Obcy: Przymierze\" to drugi rozdział nowej trylogii, którą zapoczątkował film \"Prometeusz\". Statek osadniczy ”Przymierze” dociera na nieznaną planetę, która wydaje się być prawdziwym rajem dla człowieka. Członkowie ekspedycji szybko się jednak przekonują, że trafili do mrocznego, pełnego tajemnic i zagadek świata, którego jedynym mieszkańcem jest android David (nominowany do Oscara Michael Fassbender), ocalały z katastrofy ”Prometeusza”. Gdy odkrywają, że świat ten kryje w sobie niewyobrażalne zagrożenie, muszą podjąć przerażającą próbę ucieczki. W pozostałych rolach: Katherine Waterston (”Fantastyczne zwierzęta i jak je znaleźć”), Billy Crudup (”Spotlight”), Danny McBride (”W chmurach”), Demián Bichir (”Nienawistna ósemka”) i Carmen Ejogo (”Selma”).");
        movie.setDirector("Ridley Scott");
        movie.setGenere(Genere.horror);
        movie.setLanguage("Angielski");
        movie.setReleased("12/05/2017");
        movie.setLenght(122);

        objects.add(movie);

        movie = new Movie();
        movie.setTitle("Piraci z Karaibów. Zemsta Salazara");
        movie.setDescription("Szczęście nie sprzyja kapitanowi Jackowi Sparrowowi (Johnny Depp), a złe wiatry pchają go coraz silniej ku kolejnej awanturze. Duchy korsarzy pod wodzą jego dawnego wroga, przerażającego kapitana Salazara (Javier Bardem), wydostają się z Devil’s Triangle z zamiarem unicestwienia każdego pirata, jakiego noszą oceany. Jedyną nadzieją by uniknąć śmierci, jest dla Jacka odnalezienie legendarnego trójzębu Posejdona, który daje swojemu posiadaczowi całkowitą kontrolę nad morzami i oceanami.");
        movie.setDirector("Joachim Roenning, Espen Sandberg");
        movie.setGenere(Genere.fantasy);
        movie.setLanguage("Angielski");
        movie.setReleased("26/05/2017");
        movie.setLenght(129);

        objects.add(movie);

    }

    private static void addToDatabase(){
        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        objects.forEach(entityManager::persist);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
