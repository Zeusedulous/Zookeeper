package com.zeusedulous;

import java.io.IOException;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/15 15:00
 * @Desc :
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        ZookeeperHelper zookeeperHelper = new ZookeeperHelper();
        zookeeperHelper.connectZookeeper();
    }
}
