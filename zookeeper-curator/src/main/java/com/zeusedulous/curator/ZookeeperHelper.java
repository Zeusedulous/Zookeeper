package com.zeusedulous.curator;

import lombok.Data;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * @Author : Zeusedulous
 * @Date : 2020/6/17 15:19
 * @Desc :
 */
@Data
public class ZookeeperHelper {

    private static String host = "192.168.81.72:2181";
    private static int connectionTimeoutMs = 50000;
    private static int sessionTimeoutMs = 60000;
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(100000,3);
    private static CuratorFramework client = getConnection();


    public static CuratorFramework getConnection(){
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(host)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .build();

        client.start();
        System.out.println("========connect success=========");
        return client;
    }

    public static CuratorFramework getClient(){
        return client;
    }

    public static void createNode(String path) throws Exception {
        String str = client.create().forPath(path);
        System.out.println("创建node: path = " +path + ", str = " + str);
    }

    public static void createNode(String path,byte[] value) throws Exception {
        String str = client.create().forPath(path,value);
        System.out.println("创建node: path = " +path + ", value = " + value + ", str = " + str);
    }

    public static String getData(String path) throws Exception {
        byte[] value = client.getData().forPath(path);
        return new String(value);
    }

    public static void setData(String path, byte[] value) throws Exception {
        client.setData().forPath(path, value);
    }

    public static void deleteNode(String path) throws Exception {
        client.delete().forPath(path);
    }

    public static void deleteWithChildrenNode(String path) throws Exception{
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }
}
