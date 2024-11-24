package omsu.softwareengineering;

import omsu.softwareengineering.client.simple.SimpleClient;

public class Main {
    public static void main(String[] args) {
        new SimpleClient();
        new Application().run();
    }
}