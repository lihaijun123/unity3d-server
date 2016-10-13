package com.focustech.cief.filemanage.dataserver.heartbeat;

import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

public class ServerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataServerNode serverNode = new DataServerNode();
		serverNode.setRegistryCenterPort(8901);
		HeartBeatServer client = new HeartBeatServer();
		client.setServerNode(serverNode);
		client.start();
	}

}
