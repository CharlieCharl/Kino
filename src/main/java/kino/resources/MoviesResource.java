package kino.resources;

import jersey.repackaged.com.google.common.collect.Lists;
import kino.Main;
import kino.model.Movie;
import kino.model.Titles;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
    @GET
    @Path("movie/{title}")
    public Response getMovie(@PathParam("title") String title){
        Movie movie;
        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
            Root<Movie> root = criteriaQuery.from(Movie.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("title"), title));
            movie = entityManager.createQuery(criteriaQuery).getSingleResult();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }finally {
            entityManager.close();
        }

        return Response.ok(movie).build();
    }

    @GET
    @Path("titles")
    public Response getTitles(){
        List<String> titles;

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Movie> root = criteriaQuery.from(Movie.class);
            criteriaQuery.select(root.get("title"));
            titles = entityManager.createQuery(criteriaQuery).getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }finally {
            entityManager.close();
        }
        Titles titles1 = new Titles();
        titles1.setTitles(titles);
        return Response.ok(titles1).build();
    }
}
