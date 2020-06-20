package client;


import helper.Configuration;
import server.AbstractServer;

public abstract class AbstractClient<T> {

    public T get(String key) throws Exception {
        AbstractServer server = Configuration.getServer(key);

        T t = null;
        Exception e = null;
        try {
            t = (T) server.get(key);
        } catch (Exception e1) {
            e = e1;
        } finally {
            if(t == null)
                System.out.println(e.getMessage());
        }

        if(t != null)
            System.out.println("Key found with value : " + t);
        return t;
    }

    public void delete(String key) throws Exception {
        AbstractServer server = Configuration.getServer(key);
        server.remove(key);
    }

    public void add(String key, T t) throws Exception {
        try {
            AbstractServer server = Configuration.getServer(key);
            server.add(key, t);
        } catch (Exception e) {
            System.out.println("Servers are down. Failed to add key");
        }
    }


}
