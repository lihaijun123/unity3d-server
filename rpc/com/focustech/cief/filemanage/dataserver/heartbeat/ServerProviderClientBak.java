package com.focustech.cief.filemanage.dataserver.heartbeat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focustech.common.utils.Bytes;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
public class ServerProviderClientBak {
	private static final Logger log = LoggerFactory.getLogger(ServerProviderClientBak.class);
	private static final int BUFFER_SIZE = 1;

	private DataServerNode serverNode;

	private ExecutorService pool;

	private static boolean isConnnect = false;

	/**
	 * 初始化
	 * *
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void start() {
		pool = Executors.newFixedThreadPool(2);
		log.info("=========================DataServer服务提供者服务启动=========================");
		new Thread(new HeartbeatTask(serverNode)).start();
		//重连注册中心
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(true){
						Thread.sleep(2 * 1000);
						if(!isConnnect){
								pool.execute(new HeartbeatTask(serverNode));
								Thread.sleep(1 * 1000);
								int activeCount = ((ThreadPoolExecutor)pool).getActiveCount();
								log.debug("重连接线程池数量：" + activeCount);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 *
	 * *
	 * @author lihaijun
	 *
	 */
	class HeartbeatTask implements Runnable {

		private DataServerNode dataServerNode;

		public HeartbeatTask(DataServerNode serverNode){
			this.dataServerNode = serverNode;
		}

		@Override
		public void run() {
			int retry = 3;
			String address = "";
			SocketIO socket = null;
			try {
				socket = new SocketIO(dataServerNode);
				byte[] registryData = socket.getMsg().getBytes();
				byte[] length = new byte[4];
				length = Bytes.int2bytes(registryData.length);
				address = socket.getInetAddress().toString();
				OutputStream out = socket.getOutputStream();
				InputStream in = socket.getInputStream();
				while(true){
					Thread.sleep(3 * 1000);
					try {
						log.info("向注册中心注册");
						out.write(length);
						out.write(registryData);
					} catch (Exception e1) {
						log.info("向注册中心写数据异常，注册中心可能离线:" + address);
					}
					byte[] recData = new byte[BUFFER_SIZE];
					try {
						in.read(recData);
						isConnnect = true;
					} catch (Exception e) {
						while(true){
							Thread.sleep(3 * 1000);
							retry --;
							log.info("从注册中心读数据异常，客户端重试：" + retry);
							try {
								in.read(recData);
								isConnnect = true;
								break;
							} catch (Exception e1) {
								if(retry == 0){
									socket.close();
									socket = null;
									isConnnect = false;
									break;
								}
							}
						}
					} finally {
						if(socket == null){
							log.info("服务提供者主动关闭跟注册中心的连接，注册中心可能离线:" + address);
							break;
						}
					}
					String recMsg = new String(recData);
					log.debug("注册中心返回信息[" + address + "]：" + recMsg);
				}
			} catch (Exception e) {
				if(socket != null){
					try {
						socket.close();
						socket = null;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				isConnnect = false;
				log.info("注册中心服务器离线");
			}
		}
	}
	/**
	 *
	 * *
	 * @author lihaijun
	 *
	 */
	class SocketIO {
		private Socket socket;
		private DataServerNode serverNode;
		public SocketIO(){

		}
		public SocketIO(DataServerNode serverNode) throws UnknownHostException, IOException{
			this.serverNode = serverNode;
			this.socket = new Socket(serverNode.getServerIp(), serverNode.getRegistryCenterPort());
		}
		public Socket getSocket() {
			return socket;
		}
		public DataServerNode getServerNode() {

			return serverNode;
		}
		public void close() throws IOException {
			 this.socket.close();
		}
		public InetAddress getInetAddress() {
			return socket.getInetAddress();
		}
		public InputStream getInputStream() throws IOException {
			return socket.getInputStream();
		}
		public OutputStream getOutputStream() throws IOException {
			return socket.getOutputStream();
		}
		public String getMsg(){
			return serverNode.getServerIp() + ":" + serverNode.getServerPort() + ":" + EncryptUtil.encode(serverNode.getSn());
		}
		public String getKey(){
			return serverNode.getServerIp() + ":" + serverNode.getRegistryCenterPort();
		}
		@Override
		public String toString() {
			return serverNode.getServerIp() + ":" + serverNode.getRegistryCenterPort();
		}
	}

	public DataServerNode getServerNode() {
		return serverNode;
	}

	public void setServerNode(DataServerNode serverNode) {
		this.serverNode = serverNode;
	}

	public static void main(String[] arg) throws UnknownHostException, IOException, InterruptedException{
		ServerProviderClientBak client = new ServerProviderClientBak();

		client.start();
	}
}
