package br.unisinos.swe.server;

import java.io.File;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import br.unisinos.swe.server.admin.ChannelResource;
import br.unisinos.swe.server.admin.ServiceResource;
import br.unisinos.swe.server.services.ServiceExecutionResource;
import br.unisinos.swe.server.stream.ChannelStreamResource;

public class StreamServer extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		StreamServerNode.getInstance().setRunningMode(StreamServerNode.NodeRunningMode.STANDALONE);
		// Para stand-alone: Compilar utilizando Restlet Android edition, e definir o diret—rio correto para
		// as p‡ginas administrativas
		
		 // Create a component
        Component component = new Component();
        component.getClients().add(Protocol.FILE);
        component.getClients().add(Protocol.WAR);
        component.getClients().add(Protocol.CLAP);
        component.getServers().add(Protocol.HTTP, 8100);
       
        component.getDefaultHost().attach("/channels", ChannelResource.class);
        component.getDefaultHost().attach("/channels/{channel}", ChannelStreamResource.class);
        component.getDefaultHost().attach("/channels/{channel}/services", ServiceResource.class);
        component.getDefaultHost().attach("/services/{service}", ServiceExecutionResource.class);
        component.getDefaultHost().attach("/services/{service}/{action}", ServiceExecutionResource.class);
        
        // redirecionar o default para o diret—rio WAR
        File warDir = new File("");
        if (!"war".equals(warDir.getName())) {
            warDir = new File(warDir, "war/");
        }

        System.out.println("path::" + LocalReference.createFileReference(warDir).getFile().getCanonicalPath());
        Directory dir = new Directory(component.getContext().createChildContext(), LocalReference.createFileReference(warDir));
        component.getDefaultHost().attach("/", dir);
        
        

        // Attach the application to the component and start it
        component.start();
	}
	

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
    	StreamServerNode.getInstance().setRunningMode(StreamServerNode.NodeRunningMode.GAE);
    	
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attach("/channels", ChannelResource.class);
        router.attach("/channels/{channel}", ChannelStreamResource.class);
        router.attach("/channels/{channel}/services", ServiceResource.class);
        router.attach("/services/{service}", ServiceExecutionResource.class);
        router.attach("/services/{service}/{action}", ServiceExecutionResource.class);
        
        // redirecionar o default para o diret—rio WAR
        File warDir = new File("");
        if (!"war".equals(warDir.getName())) {
            warDir = new File(warDir, "war/");
        }

        Directory dir = new Directory(router.getContext().createChildContext(), LocalReference.createFileReference(warDir));
        router.attach("/", dir);

        return router;
    }
	
	
	/*
	public StreamServer(){
		
	}
	
	public StreamServer(Context context) {
		super(context);
	}
	
	@Override
    public Restlet createRoot() {

        Router router = new Router(getContext());
        router.attach("/channels", ChannelResource.class);
        router.attach("/channels/{id}", ChannelResource.class);
               
        Restlet mainpage = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<html>");
                stringBuilder
                        .append("<head><title>Sample Application Servlet Page</title></head>");
                stringBuilder.append("<body bgcolor=white>");

                stringBuilder.append("<table border=\"0\">");
                stringBuilder.append("<tr>");
                stringBuilder.append("<td>");
                stringBuilder.append("<h1>2048Bits.com example - REST</h1>");
                stringBuilder.append("</td>");
                stringBuilder.append("</tr>");
                stringBuilder.append("</table>");
                stringBuilder.append("</body>");
                stringBuilder.append("</html>");

                response.setEntity(new StringRepresentation(stringBuilder
                        .toString(), MediaType.TEXT_HTML));

            }
        };        
        router.attach("", mainpage);
        return router;
    }*/

}
