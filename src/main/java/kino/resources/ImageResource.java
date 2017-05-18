package kino.resources;

import kino.Main;
import kino.model.Movie;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("image")
public class ImageResource {

    @GET
    @Path("{title}")
    @Produces("image/png")
    public Response getImage(@PathParam("title") String title){
        String path;

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Movie> root = criteriaQuery.from(Movie.class);
            criteriaQuery.select(root.get("path")).where(criteriaBuilder.equal(root.get("title"), title));
            path = entityManager.createQuery(criteriaQuery).getSingleResult();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }finally {
            entityManager.close();
        }

        File responseFile = new File(path);
        if(!responseFile.exists())
                return Response.status(Response.Status.NOT_FOUND).build();
        String mt = new MimetypesFileTypeMap().getContentType(responseFile);
        return Response.ok(responseFile, mt).build();
    }


}
