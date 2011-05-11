package br.unisinos.swe.shared.serialization;

import com.thoughtworks.xstream.XStream;

//import com.thoughtworks.xstream.XStream;

public class MobiSerialization {
	// TODO code own XML encoding and decoding to guarantee GAE compatibility
	public String encode(Object object) {
		// Currently only encode to XML
		XStream stream = new XStream();
		return stream.toXML(object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T decode(String source) {
		// Currently only decode from XML
		return ((T)(new XStream().fromXML(source)));
	}

}
