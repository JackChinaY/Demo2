package test;

import entity.PLU;

public class Test2 {
    public static void main(String[] args) {
        int a = 1;
        PLU plu = new PLU();
        plu.setNumber(String.valueOf(a++));
        System.out.println(plu.getNumber());
    }


}
