package test;

import entity.PLU;
import org.json.JSONObject;

public class Test2 {
    public static void main(String[] args) {
//        int a = 1;
//        PLU plu = new PLU();
//        plu.setNumber(String.valueOf(a++));
//        System.out.println(plu.getNumber());
        JSONObject jo = new JSONObject();
        jo.put("result", "ok");
        jo.put("id", "10001");
        System.out.println(jo.toString());

    }


}
