package com.zeusedulous.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author : Zeusedulous
 * @Date : 2020/6/15 14:44
 * @Desc :
 */
public class ZookeeperHelper implements Watcher{
    private  ZooKeeper zooKeeper;
    private  int sessionTimeOut = 3000;
    private  CountDownLatch countDownLatch = new CountDownLatch(1);
    private  String host = "192.168.81.72:2181";


    public ZooKeeper getZooKeeper() throws Exception {
        zooKeeper = new ZooKeeper(host, sessionTimeOut, this);
        countDownLatch.await();
        System.out.println("===========get zk success");
        return zooKeeper;
    }

    public void createNode(String path,String data,CreateMode mode) throws KeeperException, InterruptedException {
        zooKeeper.exists(path,true);
        String result = zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        System.out.println("创建节点成功！节点为："+path+",值为:"+data);
        System.out.println("创建结果为:"+result);
    }

    public Stat setData(String path,byte[] data) throws KeeperException, InterruptedException {
        zooKeeper.exists(path,true);
        Stat stat = zooKeeper.setData(path,data,-1);
        System.out.println("修改节点成功！");
        return stat;
    }

    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.exists(path,true);
        zooKeeper.delete(path,-1);
        System.out.println("删除节点成功！");
    }


    public void closeConnection() throws InterruptedException {
        if(null != zooKeeper) {
            zooKeeper.close();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("事件通知开始前");
        Event.KeeperState state = event.getState();
        //事件类型
        Event.EventType type = event.getType();
        System.out.println("事件状态:"+state+",事件类型:"+type);
        if(Event.KeeperState.SyncConnected == state){
            //事件类型  None表示是连接类型
            if(Event.EventType.None == type){
                //连接上zk服务器后放开信号量
                countDownLatch.countDown();
                System.out.println("=====ZK连接成功=====");
            }else if(Event.EventType.NodeCreated == type){
                System.out.println("监听到创建节点，路径为：" + event.getPath());
            }else if(Event.EventType.NodeDataChanged == type){
                System.out.println("监听到创建节点，路径为：" + event.getPath());
            }else if(Event.EventType.NodeDeleted == type){
                System.out.println("监听到删除节点，路径为：" + event.getPath());
            }
        }
    }
}
