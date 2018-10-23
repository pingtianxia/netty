package com.hyp.netty.day1.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @包 名: com.hyp.netty.day1.bio
 * @描 述: 传统Socket阻塞多线程版本
 * @作 者: hyp
 * @邮 箱: henanyunpingearl@163.com
 * @创建日期: 2018/10/23 8:40
 * @修改日期: 2018/10/23 8:40
 * 解决了只能一个客户端服务的问题
 *
 * 客户端的数量：线程数= 1:1（N:N）
 *
 * 服务器的资源是有限的每new一个线程都是要消耗资源的
 * 当系统的连接数增大（客户端数量增多），线程数会增多。
 * 系统慢慢的资源枯竭，会导致系统资源不足，系统会非常缓慢，终止导致服务挂掉
 */
public class TraditionalSocketPromoteDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("服务端启动，端口为：7777");

        while (true){
            /**
             * 获取socket套接字
             * 第一个 accept()阻塞点
             */
            final Socket socket = serverSocket.accept();
            System.out.println("有新客户端连接上来了...");
            new Thread(new Runnable() {
                public void run() {
                    try {
                        // 获取客户端输入流
                        InputStream is = socket.getInputStream();
                        byte[] bytes = new byte[1024];
                        while(true){
                            /**
                             * 循环读取数据
                             * 第二个 read() 阻塞点
                             */
                            int count = is.read(bytes);
                            if(count != -1){
                                String info = new String(bytes,0,count,"UTF-8");
                                System.out.println(info);
                            }else{
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
