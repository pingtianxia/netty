package com.hyp.netty.day1.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @包 名: com.hyp.netty.day1.bio
 * @类 名:TraditionalSocketMultiThreadDemo
 * @描 述:  传统BIO 多线程伪异步IO
 * 传统 Socket案例 （线程池） 客户端数量：服务端数量 = N > M (N>=M)
 *
 *
 * 弊端：
 * 每当一个新的客户端请求接入时，服务端必须创建一个线程来处理这条链路
 * 如果在高性能高并发场景下肯定是没法用的（可能导致资源被耗尽的问题）
 *
 * @作 者: hyp
 * @邮 箱: henanyunpingearl@163.com
 * @创建日期: 2018/10/23 10:50
 */
public class TraditionalSocketMultiThreadDemo {
    public static void main(String[] args) throws IOException {
        /**
         * ExecutorService threadPool = Executors.newCachedThreadPool();
         *  public static final int MAX_VALUE = 2147483647
         *  最大线程数 2147483647
         *  使用线程池本质还是 new 线程(只不过线程能重复利用)
         *  如果客户端的数量也很多，并且客户端还没有退出，那么 客户端：服务端还是 N:N 的
         */
//        ExecutorService threadPool = Executors.newCachedThreadPool();
        /**
         * Executors.newFixedThreadPool(100);
         *  最大线程数 100
         * 最多为100个线程，至少保证服务端不会被挂掉（服务器资源不会被耗尽）
         * newFixedThreadPool 这是就是多线程中的 伪异步IO
         * 保证了系统有限的资源空值
         *
         * 弊端：
         * 如果发生了大量的并发请求：
         *  有一些客户端的请求就会有延迟，甚至得不到处理
         *
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("服务端启动, 端口为：7777");
        while(true){
            // 获取socket套接字
            // accept()阻塞点
            final Socket socket = serverSocket.accept();
            System.out.println("有新客户端连接上来了...");
            threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println("有新客户端连接上来了...");
                        // 获取客户端输入流
                        InputStream is = socket.getInputStream();
                        byte[] bytes = new byte[1024];
                        while (true){
                            /**
                             * 循环读取数据  read() 阻塞点
                             */
                            int data = is.read(bytes);
                            if (data != -1){
                                String info = new String(bytes, 0, data, "GBK");
                                System.out.println(info);
                            }else{
                                System.out.println("data 数据读完了-------》");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }
}
