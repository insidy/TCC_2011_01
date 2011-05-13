package br.unisinos.swe.server;

public class StreamServerNode {
	
	private static StreamServerNode instance = null;
	
	private NodeRunningMode runningMode = NodeRunningMode.GAE;
	
	private StreamServerNode() {
		
	}
	
	public static StreamServerNode getInstance() {
		if(instance == null)
			instance = new StreamServerNode();
		
		return instance;
	}
	
	public void setRunningMode(NodeRunningMode mode) {
		this.runningMode = mode;
	}
	
	public NodeRunningMode getRunningMode() {
		return this.runningMode;
	}
	
	public enum NodeRunningMode {
		STANDALONE, GAE
	}
}
