package kino.resources;

import kino.Main;
import kino.model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
    @GET
    @Path("movie/{title}")
    public Response getMovie(@PathParam("title") String title){
        Movie movie = null;
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

        return Response.accepted(movie).build();
    }
}
