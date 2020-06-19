package com.zeusedulous.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/18 10:27
 * @Desc :
 */
public class Test {

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZookeeperHelper.getClient();


        //创建一个初始内容为空的节点
        ZookeeperHelper.createNode("/test1");
        //创建一个内容不为空的节点
        ZookeeperHelper.createNode("/test2","test2_value".getBytes());


        //创建一个内容为空的临时节点
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/test3");

        Thread.sleep(10000);

        //创建一个内容不为空的临时节点
        client.create().withMode(CreateMode.PERSISTENT).forPath("/test4","test4_value".getBytes());

        Thread.sleep(15000);

        String value = ZookeeperHelper.getData("/test2");
        System.out.println("value = " + value);

        Thread.sleep(15000);

        ZookeeperHelper.deleteNode("/test1");
        System.out.println("删除节点成功");


        //创建一个初始内容不为空的临时节点（子节点会失效，父节点一直存在），可以实现递归创建
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/test1/test2","test2".getBytes());
        System.out.println("==========递推创建节点成功========");
        Thread.sleep(10000);



        //获取节点内容
        ZookeeperHelper.createNode("/test7","test7_value".getBytes());
        ZookeeperHelper.createNode("/test7/test77" ,"test77_value".getBytes());
        System.out.println("创建节点成功");
        value = ZookeeperHelper.getData("/test7");
        System.out.println("value = " + value);
        value = ZookeeperHelper.getData("/test7/test77");
        System.out.println("value = " + value);

        Thread.sleep(20000);

        //删除节点
        ZookeeperHelper.deleteWithChildrenNode("/test7");


        //异步创建节点
        client.create().withMode(CreateMode.EPHEMERAL).inBackground((curatorFramework,curatorEvent)->{
            System.out.println("curatorFramework:" + curatorFramework);
            System.out.println("-----当前线程：" + Thread.currentThread().getName());
            System.out.println("-----getResultCode=:" + curatorEvent.getResultCode());
            System.out.println("-----getName=:" + curatorEvent.getName());
            System.out.println("-----getPath=:" + curatorEvent.getPath());
            System.out.println("-----getContext=:" + curatorEvent.getContext());
            System.out.println("-----getData=:" + curatorEvent.getData());
            System.out.println("-----getType=:" + curatorEvent.getType());
            System.out.println("-----getChildren=:" + curatorEvent.getChildren());
//            countDownLatch.countDown();

        }, Executors.newFixedThreadPool(10)).forPath("/syncNode","syncValue".getBytes());

        System.out.println(11111111);
//        countDownLatch.await();
//        System.out.println(22222222);

        Thread.sleep(Long.MAX_VALUE);

    }
}
