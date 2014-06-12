package com.locationService;

import java.io.File;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class LocationService {
	private static final Log LOG=LogFactory.getLog(LocationService.class);
	public static void main(String[] args) {
		try {
			SelectChannelConnector ret = new SelectChannelConnector();
			ret.setLowResourceMaxIdleTime(10000);
			ret.setAcceptQueueSize(128);
			ret.setResolveNames(false);
			ret.setUseDirectBuffers(false);

			ret.setHost(InetAddress.getLocalHost().getHostAddress());
			ret.setPort(80);

			Server server = new Server();
			 server.addConnector(ret);

			ServletHolder sh = new ServletHolder(ServletContainer.class);
			sh.setInitOrder(1);
			sh.setInitParameter("com.sun.jersey.config.property."
					+ "resourceConfigClass", "com.sun.jersey.api.core."
					+ "PackagesResourceConfig");
			sh.setInitParameter("com.sun.jersey.config." + "property.packages",
					"com.locationService.resource");

			ContextHandler contextHandler = new ContextHandler();
			contextHandler.setContextPath("/");
			ServletHandler handler2 = new ServletHandler();
			handler2.addServletWithMapping(sh, "/*");
			contextHandler.addHandler(handler2);
			server.addHandler(contextHandler);

			server.start();
			server.join();
		} catch (Exception e) {
			LOG.error("LocationService not start successfully.", e);
			System.exit(-1);
		}

	}

}
