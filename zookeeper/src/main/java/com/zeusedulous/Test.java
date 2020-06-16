package com.zeusedulous;


import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/15 15:00
 * @Desc :
 */
public class Test {
    public static void main(String[] args) throws Exception {
        ZookeeperHelper zkHelper = new ZookeeperHelper();
        zkHelper.getZooKeeper();

        //创建
        zkHelper.createNode("/test","test_value", CreateMode.EPHEMERAL);

        //获取节点值
        String data = zkHelper.getData("/test");
        System.out.println("获取节点值 data = " + data);
        //获取所有子节点
        zkHelper.createNode("/test2","test_value2", CreateMode.PERSISTENT);
        List<String> lists = zkHelper.getChildrenData("/");
        System.out.println("所有子节点：" + lists);

        //给节点赋值
        zkHelper.setData("/test","test_new".getBytes());
        data = zkHelper.getData("/test");
        System.out.println("获取节点值 data = " + data);

        //删除节点
        zkHelper.deleteNode("/test");
        lists = zkHelper.getChildrenData("/");
        System.out.println("所有子节点：" + lists);

        //删除带有子节点的node(因为含有子节点所以删除失败)
        zkHelper.createNode("/test2/test22","test22-value",CreateMode.EPHEMERAL);
        lists = zkHelper.getChildrenData("/");
        System.out.println("所有子节点：" + lists);
        lists = zkHelper.getChildrenData("/test2");
        System.out.println("所有子节点：" + lists);
        zkHelper.deleteNode("/test2");
    }


}
