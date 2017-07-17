package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by linzongzhan on 2017/7/14.
 */
public class InputAndOutput {

    //将字符串在控制台上输出
    public void output (String out) {
        System.out.println(out);
    }

    //获得控制台输入的字符串
    public String input () {
        String readLine = "";
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            readLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readLine;
    }
}
