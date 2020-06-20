package server.impl;

import server.AbstractServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server1 extends AbstractServer<Object> {


    private Map<String, Object> map = new ConcurrentHashMap<>();

    public String ip() {
        return "127.0.0.1";
    }

    @Override
    public Object getFromReplica(String key) throws Exception {
        return map.getOrDefault(key, null);
    }

    @Override
    public void addToReplica(String key, Object value) {
        map.put(key, value);
        System.out.println("Successfully added key : " + key + " to server : " + ip());
    }


    @Override
    public void removeFromReplica(String key) {
        if(map.containsKey(key)) {
            map.remove(key);
            System.out.println("Successfully removed key : " + key + " from server : " + ip());
        }
    }

}
