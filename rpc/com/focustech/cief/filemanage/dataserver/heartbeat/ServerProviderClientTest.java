package com.focustech.cief.filemanage.dataserver.heartbeat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.focustech.common.utils.TCUtil;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
public class ServerProviderClientTest {
	private static final int BUFFER_SIZE = 1;
	private static Map<String, Socket> availChannels = new ConcurrentHashMap<String, Socket>();
	private static Map<String, Node> deadNodes = new ConcurrentHashMap<String, Node>();

	private ExecutorService pool;

	private static List<Node> servers = new ArrayList<Node>();

	public static void main(String[] arg) throws UnknownHostException, IOException, InterruptedException{
		ServerProviderClient client = new ServerProviderClient();
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("192.168.4.197", 8900));
		servers.add(new Node("127.0.0.1", 8901));
		servers.add(new Node("127.0.0.1", 8901));
		servers.add(new Node("127.0.0.1", 8901));
		servers.add(new Node("127.0.0.1", 8901));
		servers.add(new Node("127.0.0.1", 8901));
		servers.add(new Node("127.0.0.1", 8901));
		client.start();
	}
	/**
	 * 初始化
	 * *
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void init() throws UnknownHostException, IOException, InterruptedException{
		pool = Executors.newFixedThreadPool(servers.size() + 1);
		for (int i = 0; i < servers.size(); i ++) {
			Node server = servers.get(i);
			Thread thread1 = new Thread(new HeartbeatTask(server.getIp(), server.getPort(), TCUtil.sv(i), availChannels));
			thread1.start();
		}

		while(true){
			for(Map.Entry<String, Node> nodes : deadNodes.entrySet()){
				Node node = nodes.getValue();
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pool.execute(new HeartbeatTask(node.getIp(), node.getPort(), "2", availChannels));
				Thread.sleep(3 * 1000);
				int activeCount = ((ThreadPoolExecutor)pool).getActiveCount();
				System.out.println("重连接线程池数量：" + activeCount);
			}
		}
	}
	class HeartbeatTask implements Runnable {
		private String ip;
		private int port;
		private String msg;
		private Map<String, Socket> channels;
		public HeartbeatTask(String ip, int port, String msg, Map<String, Socket> channels){
			this.ip = ip;
			this.port = port;
			this.msg = msg;
			this.channels = channels;
		}
		public void start(String msg){
			int retry = 3;
			String key = ip + ":" + port;
			Socket socket = null;
			try {
				if(!channels.containsKey(key)){
					socket = new Socket(ip, port);
					channels.put(key, socket);
					System.out.println("添加链接对象到channels中");
				} else {
					socket = channels.get(key);
				}
				while(true){
					OutputStream outputStream = socket.getOutputStream();
					try {
						outputStream.write(msg.getBytes());
					} catch (Exception e1) {
						System.out.println("写数据异常，可能服务端离线");
					}
					System.out.println("client:in0");
					InputStream in = socket.getInputStream();
					byte[] recData = new byte[BUFFER_SIZE];
					System.out.println("client:in1");
					Thread.sleep(1000);
					try {
						in.read(recData);
						deadNodes.remove(key);
					} catch (Exception e) {
						while(true){
							retry --;
							System.out.println("读数据异常，客户端重试：" + retry);
							try {
								in.read(recData);
								deadNodes.remove(key);
								break;
							} catch (Exception e1) {
								if(retry == 0){
									socket.close();
									socket = null;
									break;
								}
							}
						}
					} finally {
						if(socket == null){
							System.out.println("客户端关闭跟服务器的连接，服务器可能离线");
							if(channels.containsKey(key)){
								System.out.println("从channels中去除连接对象：" + key);
								channels.remove(key);
								deadNodes.put(key, new Node(ip, port));
							}
							break;
						}
					}
					System.out.println("client:in2");
					String recMsg = new String(recData);
					System.out.println(recMsg);
				}
			} catch (Exception e) {
				deadNodes.put(key, new Node(ip, port));
				if(socket != null){
					try {
						socket.close();
						socket = null;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				System.out.println(key + " 添加到deadNodes");
				System.out.println("服务器离线");
			}
		}
		@Override
		public void run() {
			start(msg);
		}
	}
}


class Node {
	private String ip;
	private int port;

	public Node(String ip, int port){
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return ip + ":" + port;
	}


}
