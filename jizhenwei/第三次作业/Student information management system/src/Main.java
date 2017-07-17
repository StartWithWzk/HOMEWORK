import Controller.SIMScontroller;
import Model.SIMSmodel;
import Model.Student;
import View.SIMSview;
import com.mysql.jdbc.*;

import java.sql.DriverManager;
import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/14.
 */
public class Main {
    public static void main(String[] a) {
        SIMSmodel model = new SIMSmodel();
        SIMScontroller controller = new SIMScontroller(model);
        SIMSview view = new SIMSview(model, controller);
        controller.setView(view);
        controller.connect();


    }

}
