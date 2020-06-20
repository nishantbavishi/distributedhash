package server;

import helper.FileUtils;

import java.io.IOException;

public class Persist implements Runnable {

    private Thread t;
    private String key;
    private Object value;
    private String fileName;

    public Persist(String key, Object value, String fileName) {
        this.key = key;
        this.value = value;
        this.fileName = fileName;
    }

    public void run() {

        StringBuilder data = new StringBuilder();
        data.append(key)
                .append(":")
                .append(value.toString())
                .append("\n");

        try {
            Thread.sleep(5000);
            FileUtils.write(fileName, data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully persisted key : " + key + " to file : " + fileName);
    }

    public void start() {
        if(t == null) {
            t = new Thread(this);
            t.start();
            //t.run();
        }
    }
}
