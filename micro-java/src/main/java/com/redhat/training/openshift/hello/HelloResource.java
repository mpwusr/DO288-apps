package com.redhat.training.openshift.hello;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.net.MalformedURLException;
import org.apache.http.Header; 
import org.apache.http.HttpEntity; 
import org.apache.http.HttpHeaders; 
import org.apache.http.NameValuePair; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.CloseableHttpResponse; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.impl.client.CloseableHttpClient; 
import org.apache.http.impl.client.HttpClients; 
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.util.EntityUtils;  
import java.io.IOException;

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
    @Path("/geturi/{urlURI}/{method}/{ssl}")
    @Produces("text/html")
    public Response GetUriService(@PathParam("urlURI") String urlURI, @PathParam("method") String method,                            @PathParam("ssl") String ssl) throws Exception {
        System.out.println("TESTING - HTTP GET URI SERVICE");
        HttpClientExample obj = new HttpClientExample();
        String msg = "";
        try {
           System.out.println("Testing Case 1 - Send Http GET request");
           if (method == "GET") {
               msg = obj.sendGet(urlURI, ssl);
           } else {
               msg = obj.sendGet(urlURI, ssl);
           }
        } finally {
           obj.close();
        }
        return Response.status(200).entity("getUrlUri is called, URL : " + urlURI + "\n" + "\n" + msg).build();
    }

   private static class HttpClientExample {      
       // one instance, reuse     
       private final CloseableHttpClient httpClient = HttpClients.createDefault();      

    private void close() throws IOException {         
        httpClient.close();     
    } 

    private String sendGet(String URL, String ssl) throws Exception {   
        String protocol = "";
        if (ssl == "Y") {
            protocol = "https://";
        } else {
            protocol = "http://";
        }      
        HttpGet request = new HttpGet(protocol + URL + "/search?q=mkyong");          
        
        // add request headers         
        request.addHeader("custom-key", "mkyong");         
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
        String result = "";  
        try (CloseableHttpResponse response = httpClient.execute(request)) {              
            // Get HttpResponse Status             
            System.out.println(response.getStatusLine().toString());              
            
            HttpEntity entity = response.getEntity();             
            Header headers = entity.getContentType();             
            System.out.println(headers);              
            if (entity != null) {                 
                // return it as a String                 
                result = EntityUtils.toString(entity);                 
                System.out.println(result);             
            } 
        }
      return result;
    }
  }
}
