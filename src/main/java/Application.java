import client.AbstractClient;
import client.impl.Client1;
import client.impl.Client2;
import client.impl.Client3;
import helper.Configuration;
import server.AbstractServer;
import server.impl.*;

public class Application {

    public static void main(String[] args) throws Exception {

        configureServers();

        AbstractClient client1 = new Client1();
        AbstractClient client2 = new Client2();
        AbstractClient client3 = new Client3();

        //Add Keys
        client1.add("Sachin", "Tendulkar");
        client1.add("Virender", "Sehwag");
        client2.add("Sourav", "Ganguly");
        client2.add("Rahul", "Dravid");
        client2.add("asfkafajdhjlahsfjhaslhlahlkhglashlaasfas", "Dravid");
        client3.add("VVS", "Lakshman");
        client3.add("MS", "Dhoni");
        client3.add("Yuvraj", "Singh");

        //GetKeys - Normal Situation
        client1.get("Sachin");
        client1.get("Sourav");
        client1.get("Yuvraj");
        client2.get("Rohit");

        //Delete Key
        client3.delete("Sachin");
        client1.get("Sachin");

        //Server down and get key
        Configuration.removeServer("127.0.0.1");
        client3.get("Virender");


    }

    public static void configureServers() {
        AbstractServer server1 = new Server1();
        AbstractServer server2 = new Server2();
        AbstractServer server3 = new Server3();
        AbstractServer server4 = new Server4();
        AbstractServer server5 = new Server5();
        AbstractServer server6 = new Server6();
        AbstractServer server7 = new Server7();
        AbstractServer server8 = new Server8();
        AbstractServer server9 = new Server9();
        AbstractServer server10 = new Server10();

        Configuration.addServer(server1);
        Configuration.addServer(server2);
        Configuration.addServer(server3);
        Configuration.addServer(server4);
        Configuration.addServer(server5);
        Configuration.addServer(server6);
        Configuration.addServer(server7);
        Configuration.addServer(server8);
        Configuration.addServer(server9);
        Configuration.addServer(server10);


    }
}
