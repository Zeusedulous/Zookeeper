package com.zeusedulous;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/15 14:44
 * @Desc :
 */
public class ZookeeperHelper {
    private  ZooKeeper zooKeeper;
    private  int sessionTimeOut = 3000;
    private  CountDownLatch countDownLatch = new CountDownLatch(1);
    private  String host = "192.168.81.72:2181";


    public ZooKeeper getZooKeeper() throws Exception {
        zooKeeper = new ZooKeeper(host, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }else{
                    System.out.println("connect failed");
                }
            }
        });
        countDownLatch.await();
        System.out.println("===========get zk success");
        return zooKeeper;
    }

    public void createNode(String path,String data,CreateMode mode) throws KeeperException, InterruptedException {
       zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
       System.out.println("=========创建节点成功");
    }

    public String getData(String path) throws KeeperException, InterruptedException {
        byte [] value = zooKeeper.getData(path,false,null);
        if(value == null){
            return "";
        }
        return new String(value);
    }

    public List<String> getChildrenData(String path) throws KeeperException, InterruptedException {
        List<String> lists = zooKeeper.getChildren(path,false);
        return lists;
    }

    public Stat setData(String path,byte[] data) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.setData(path,data,-1);
        return stat;
    }

    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path,-1);
    }

    public boolean exist(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path,false);
        if(null == stat){
            return false;
        }
        return true;
    }

    public void closeConnection() throws InterruptedException {
        if(null != zooKeeper) {
            zooKeeper.close();
        }
    }
}
