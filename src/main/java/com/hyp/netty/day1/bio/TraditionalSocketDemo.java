package com.hyp.netty.day1.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @包 名: com.hyp.netty.day1.bio
 * @描 述: 传统Socket阻塞案例
 * @作 者: hyp
 * @邮 箱: henanyunpingearl@163.com
 * @创建日期: 2018/10/23 8:40
 * @修改日期: 2018/10/23 8:40
 *
 *
 *
 * 有两个阻塞点
 * Socket socket = serverSocket.accept();
 * int data = is.read(b);
 *
 * 特点即缺点：
 *  1个服务端只能为1个客户端服务（服务端没有优化的前提下）
 */
public class TraditionalSocketDemo {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("服务端启动, 端口为：7777");
        while(true){
            /**
             * // 获取socket套接字
             * // accept()阻塞点
             *  服务端只干一件事情 获取一个客户端返回一个socket
             */
            Socket socket = serverSocket.accept();
            System.out.println("有新客户端连接上来了...");
            // 获取客户端输入流
            InputStream is = socket.getInputStream();
            byte[] b = new byte[1024];
            while(true){
                // 循环读取数据
                // read() 阻塞点
                int data = is.read(b);
                if(data != -1){
                    String info = new String(b,0,data,"GBK");
                    System.out.println(info);
                }else{
                    break;
                }
            }
        }
    }
}
