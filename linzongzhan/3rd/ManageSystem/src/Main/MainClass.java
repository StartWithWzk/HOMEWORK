package Main;

import Controller.Protocol;
import View.InputAndOutput;

/**
 * Created by linzongzhan on 2017/7/15.
 */
public class MainClass {
    public static void main (String[] args) {
        InputAndOutput inputAndOutput = new InputAndOutput();
        Protocol protocol = new Protocol();
        System.out.println("输入help获取帮助");
        while (true) {
            String str = inputAndOutput.input();
            if (str.equals("exit")) {
                break;
            } else {
                protocol.cheak(str);
            }
        }
    }
}
