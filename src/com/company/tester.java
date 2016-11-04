package com.company;

/**
 * Created by evanpthompson on 11/2/2016.
 */
public class tester {

    public static void main(String[] args) {
        String padd = "12345 Main Street;Olathe;Kansas;66061";
        String[] parts = padd.split(";");

        for (int i = 0; i < parts.length ; i++) {
            System.out.println(parts[i]);
        }

        PostalAddress pa = new PostalAddress();
        PostalAddress pa1 = pa.buildPostalAddress(padd, ";");

        System.out.println(pa1);
    }

}


