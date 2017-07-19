import control.Controller;
import view.Console;

import java.util.Scanner;

/**
 * Created by 黄伟烽 on 2017/7/16.
 */
public class SystemManager {

    private static Controller controller = new Controller();

    public static void main(String[] args){
        Console console = new Console();
        console.illustrate();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            controller.dealWithInput(line);
        }
    }
}
