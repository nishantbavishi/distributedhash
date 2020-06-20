package helper;

import server.AbstractServer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Configuration {

    private static SortedMap<Integer, AbstractServer> servers = new TreeMap<Integer, AbstractServer>();
    private static Map<String, AbstractServer> ipToServerMap = new ConcurrentHashMap<>();
    private static Integer numberOfReplicas = 4;
    public static Integer numberOfWrites = 3;
    public static Integer numberOfReads = 3;

    public static AbstractServer getServer(String key) throws Exception {

        Exception e1 = null;
        try {
            List<AbstractServer> serverList = getServers(key);
            if (serverList.size() > 0)
                return serverList.get(0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public static void addServer(AbstractServer server) {
        servers.put(HashFunction.hash(server.ip()), server);
        ipToServerMap.put(server.ip(), server);
    }

    public static void removeServer(String ip) {

        AbstractServer server  = ipToServerMap.get(ip);
        if(server != null) {
            server.setActive(false);
            System.out.println(server.ip() + " Server is down");
        }

    }


    public static List<AbstractServer> getServers(String key) {

        int hash = HashFunction.hash(key);
        List<AbstractServer> serverList = new ArrayList<AbstractServer>();
        Map<Integer, AbstractServer> tailMapForHash = servers.tailMap(hash);

        int count = 0;
        for(Map.Entry<Integer, AbstractServer> entry : tailMapForHash.entrySet()) {
            if(entry.getValue().isActive()) {
                serverList.add(entry.getValue());
                count++;
            }

            if(count == numberOfReplicas)
                break;
        }

        if(count < numberOfReplicas) {
            for(Map.Entry<Integer, AbstractServer> entry : servers.entrySet()) {
                if(entry.getValue().isActive()) {
                    serverList.add(entry.getValue());
                    count++;
                }

                if(count == numberOfReplicas)
                    break;
            }
        }
        return serverList;
    }
}
