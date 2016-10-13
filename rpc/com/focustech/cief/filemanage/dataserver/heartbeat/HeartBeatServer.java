package com.focustech.cief.filemanage.dataserver.heartbeat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * 心跳检测，服务端
 * *
 * @author lihaijun
 *
 */
public class HeartBeatServer {
	private static final Logger log = LoggerFactory.getLogger(HeartBeatServer.class);
	@Autowired
	private DataServerNode serverNode;

	private static final int BUFFER_SIZE = 1;
	/**
	 * 心跳检测服务端启动
	 * *
	 */
	public void start(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					log.info("心跳检测服务端启动");
					ServerSocket serverSocket = new ServerSocket(serverNode.getRegistryCenterPort());
					while(true){
						Socket socket = serverSocket.accept();
						log.info("客户端:" + socket.getInetAddress().toString() + "连接");
						new Thread(new HeartTask(socket)).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	class HeartTask implements Runnable{

		private Socket socket;

		public HeartTask(Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			byte[] recData = new byte[BUFFER_SIZE];
			int retry = 3;
			String address = "";
			while(true){
				try {
					InputStream in = socket.getInputStream();
					address = socket.getInetAddress().toString();
					try {
						in.read(recData);
					} catch (Exception e) {
						log.info("服务端读超时，客户端可能离线：" + address);
						while(true){
							retry --;
							log.info("服务端读重试：" + retry);
							try {
								in.read(recData);
							} catch (Exception e1) {
								if(retry == 0){
									if(socket != null){
										socket.close();
										socket = null;
										break;
									}
								}
							}
						}
					}
					log.debug("心跳包-接收[" + address + "]：" + new String(recData));
					OutputStream out = socket.getOutputStream();
					out.write(recData);
				} catch (Exception e) {

				} finally {
					if(socket == null){
						log.info("服务端和客户端连接关闭：" + address);
						break;
					}
				}
			}
		}
	}

	/**
	 *
	 * *
	 * @param arg
	 */
	public static void main(String[] arg){
		DataServerNode serverNode = new DataServerNode();
		serverNode.setRegistryCenterPort(8900);
		HeartBeatServer client = new HeartBeatServer();
		client.setServerNode(serverNode);
		client.start();
	}
	/**
	 *
	 * *
	 * @return
	 */
	public DataServerNode getServerNode() {
		return serverNode;
	}
	/**
	 *
	 * *
	 * @param serverNode
	 */
	public void setServerNode(DataServerNode serverNode) {
		this.serverNode = serverNode;
	}
}
