package src.Condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawThread extends Thread {
    private String name;                //操作人
    private MyCount myCount;        //账户
    private int x;                            //存款金额

   public DrawThread(String name, MyCount myCount, int x) {
        this.name = name;
        this.myCount = myCount;
        this.x = x;
    }

    public void run() {
        myCount.drawing(x, name);
        //Arrays.sort();
    }
}
