package com.redhat.training.openshift.hello;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class HelloResource {

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello() {
        String hostname = System.getenv().getOrDefault("HOSTNAME", "unknown");
	      String message = System.getenv().getOrDefault("APP_MSG", null);
	      String response = "";

      	if (message == null) {
      	  response = "Hello world from host "+hostname+"\n";
      	} else {
      	  response = "Hello world from host ["+hostname+"].\n";
      	  response += "Message received = "+message+"\n";
        }
        return response;
    }
    @GET
    @Path("/goodbye")
    @Produces("text/plain")
    public String goodbye() {
        String hostname = System.getenv().getOrDefault("HOSTNAME", "unknown");
              String message = System.getenv().getOrDefault("APP_MSG", null);
              String response = "";

        if (message == null) {
          response = "Goodbye from host "+hostname+"\n";
        } else {
          response = "Goodbye from host ["+hostname+"].\n";
          response += "Message received = "+message+"\n";
        }
        return response;
    }
    @GET
    @Path("/geturi")
    @Produces("text/plain")
    public String geturi() {
        URL url = new URL("https://test.domain.com/a/b/c.html?test=hello"); 
               String protocol = url.getProtocol(); 
               String host = url.getHost(); 
               int port = url.getPort();  
       // if the port is not explicitly specified in the input, it will be -1. 
       if (port == -1) {     
          response = "Hostname is " + String.format("%s://%s", protocol, host);
       } else {     
          response = "Hostname is " + String.format("%s://%s:%d", protocol, host, port);
       }
       return response;
    }
}
