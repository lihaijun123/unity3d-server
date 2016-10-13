package com.focustech.cief.filemanage.dataserver.heartbeat;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
import com.focustech.focus3d.bundle.fileserver.data.api.ZkConst;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
public class ServerProviderClient {
	private static final Logger log = LoggerFactory.getLogger(ServerProviderClient.class);

	private DataServerNode serverNode;

	/**
	 * 初始化
	 * *
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void start() {
		String rootPath = "/" + ZkConst.ZK_ROOT_NODE;

	}

	public DataServerNode getServerNode() {
		return serverNode;
	}

	public void setServerNode(DataServerNode serverNode) {
		this.serverNode = serverNode;
	}

	public static void main(String[] arg) throws UnknownHostException, IOException, InterruptedException{
		ServerProviderClient client = new ServerProviderClient();

		client.start();
	}
}
