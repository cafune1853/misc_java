package com.doggy.loadbalance;

import java.util.*;

/**
 * Created by huangzhw on 2016/9/8.
 */
public class Balance {
    private Map<String,Integer> serverMap = new HashMap<>();
    private int pos;
    private final Object lock = new Object();
    
    //构建一个简单的服务器列表
    private List<String> transferServerToList() {
        Map<String,Integer> copyMap = new HashMap<>(serverMap);
        return new ArrayList<>(copyMap.keySet());
    }
    
    //构建一个加权的服务器列表
    private List<String> transferServerToValuedList() {
        Map<String,Integer> copyMap = new HashMap<>(serverMap);
        List<String> serverList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry:copyMap.entrySet()){
            //权值越大则被加到轮询列表中的次数越大
            for(int cur = 0;cur < entry.getValue();++cur){
                serverList.add(entry.getKey());
            }
        }
        return serverList;
    }
    
    //随机取服务器
    private String random(List<String> serverList) {
        int size = serverList.size();
        Random random = new Random();
        int index = random.nextInt(size);
        return serverList.get(index);
    }
    
    //轮询取服务器
    private String roundRobin(List<String> serverList) {
        int size = serverList.size();
        synchronized (lock){
            if(pos >= size){
                pos = 0;
            }
            return serverList.get(pos++);
        }
    }
    
    // 轮询的负载均衡算法
    public String roundRobinChoice(){
        List<String> serverList = transferServerToList();
        return roundRobin(serverList);
    }
    
    //带权轮询的负载均衡算法，权越大则被轮询的次数越多
    public String valueRoundRobinChoice(){
        List<String> serverList = transferServerToValuedList();
        return roundRobin(serverList);
    }
    
    // 随机的负载均衡算法,在访问量大时，由统计学概率来说与轮询算法效果差不多
    public String randomChoice(){
        List<String> serverList = transferServerToList();
        return random(serverList);
    }
    
    //带权随机的负载均衡算法
    public String valuedRandomChoice(){
        List<String> serverList = transferServerToValuedList();
        int size = serverList.size();
        Random random = new Random();
        int index = random.nextInt(size);
        return serverList.get(index);
    }
    
    //hash的负载均衡：对调用者的ip进行hash,映射到固定的一台服务器上,在提供者数量不变的情况下，可以提供有状态的会话。
    public String hashChoice(String consumerIp){
        List<String> serverList = transferServerToList();
        int size = serverList.size();
        int index = consumerIp.hashCode() % size;
        return serverList.get(index);
    }
    
    //TODO: 最小连接数
}

class MC extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
    
}
