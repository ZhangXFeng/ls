package com.locationService.resource;



import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class HttpServer2 {

	
	public static void main(String[] args) throws Exception {
        Server server = new Server(60000);
        ServletHolder sh=new ServletHolder(ServletContainer.class);
        sh.setInitOrder(1);
        sh.setInitParameter("com.sun.jersey.config.property."
        		+ "resourceConfigClass", "com.sun.jersey.api.core."
        				+ "PackagesResourceConfig");
        sh.setInitParameter("com.sun.jersey.config."
        		+ "property.packages", "com.locationService.resource");
        
        ContextHandler contextHandler=new ContextHandler();
        contextHandler.setContextPath("/");
        ServletHandler handler2=new ServletHandler();
        handler2.addServletWithMapping(sh, "/*");
        contextHandler.addHandler(handler2);
        server.addHandler(contextHandler);
        server.start();
        server.join();
    }
 
   
}
