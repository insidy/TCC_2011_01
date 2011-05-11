package br.unisinos.swe.server;

import org.restlet.representation.Representation;

import br.unisinos.swe.shared.serialization.MobiSerialization;

public class StreamUtil {

	public static <T> T messageFromRepresentation(Class<T> genClass, Representation representation) {
		T message = null;
		if(representation != null) {
			try {
				message = genClass.newInstance();
				message = new MobiSerialization().<T>decode(representation.getText());
			} catch(Exception ex) { }
		}
		return message;
	}
}
