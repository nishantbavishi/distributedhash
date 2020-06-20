package server;

import helper.Configuration;

import java.util.List;

public abstract class AbstractServer<T> {


    public AbstractServer() {
        this.active = true;
    }

    private boolean active;
    public abstract String ip();
    public abstract T getFromReplica(String key) throws Exception;
    public abstract void addToReplica(String key, T value);
    public abstract void removeFromReplica(String key);


    public T get(String key) throws Exception {
        List<AbstractServer> serverList = Configuration.getServers(key);

        int count = 0;
        T value = null;
        for(AbstractServer server : serverList) {
            T valueCurrent = (T) server.getFromReplica(key);
            if(valueCurrent != null) {
                value = valueCurrent;
                count++;
            }

            if(count >= Configuration.numberOfReads)
                return value;
        }

        if(value == null) {
            throw new Exception("No value found for key : " + key);
        }

        return null;
    }

    public void add(String key, T value) {
        List<AbstractServer> serverList = Configuration.getServers(key);
        for(AbstractServer server : serverList) {
            server.addToReplica(key, value);
            server.persist(key, value);
        }

        System.out.println("Successfully added key : " + key);
    }

    /**
     * Not implementing remove from persisted file as it is only a file and not a database
     * @param key
     */
    public void remove(String key) {
        List<AbstractServer> serverList = Configuration.getServers(key);
        for(AbstractServer server : serverList) {
            server.removeFromReplica(key);
        }
        System.out.println("Successfully removed key : " + key);
    }

    public void persist(String key, T value) {
        Persist persist = new Persist(key, value, ip() + ".txt");
        persist.start();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
