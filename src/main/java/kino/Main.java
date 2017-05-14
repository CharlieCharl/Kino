package kino;

import kino.resources.StaticResources;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/kino/";
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("KinoPersistence");
    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    public static void main(String[] args) {
        startServer();

        StaticResources.populatedb();
    }

    public static HttpServer startServer(){
        final ResourceConfig resourceConfig = new ResourceConfig().packages("kino");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),resourceConfig);
    }
}
