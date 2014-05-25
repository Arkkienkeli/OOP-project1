package com.webcontroller;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
public class App {

    public static String CONFIG_LOCATION = "com.webcontroller.config";

    public static void main(String[] args) throws Exception {

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.addServlet(new ServletHolder("dispatcher", createDispatcherServlet()), "/*");
        context.setResourceBase("controller");

        Server server = new Server(8081);
        server.setHandler(context);

        server.start();
        server.join();
    }

    public static Servlet createDispatcherServlet() {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setContextClass(AnnotationConfigWebApplicationContext.class);
        servlet.setContextConfigLocation(CONFIG_LOCATION);
        return servlet;
    }
}
