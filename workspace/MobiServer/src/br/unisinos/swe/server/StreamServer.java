package br.unisinos.swe.server;

import org.restlet.Component;
import org.restlet.data.Protocol;

import br.unisinos.swe.server.admin.ChannelResource;
import br.unisinos.swe.server.admin.ServiceResource;
import br.unisinos.swe.server.services.ServiceExecutionResource;
import br.unisinos.swe.server.stream.ChannelStreamResource;

public class StreamServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		 // Create a component
        Component component = new Component();
        component.getClients().add(Protocol.FILE);
        component.getServers().add(Protocol.HTTP, 8100);
       
        component.getDefaultHost().attach("/channels", ChannelResource.class);
        component.getDefaultHost().attach("/channels/{channel}", ChannelStreamResource.class);
        component.getDefaultHost().attach("/channels/{channel}/services", ServiceResource.class);
        component.getDefaultHost().attach("/services/{service}", ServiceExecutionResource.class);
        component.getDefaultHost().attach("/services/{service}/{action}", ServiceExecutionResource.class);
        

        // Attach the application to the component and start it
        component.start();
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
