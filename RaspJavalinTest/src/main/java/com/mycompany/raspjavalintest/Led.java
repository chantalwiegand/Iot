/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspjavalintest;

/**
 *
 * @author chantalwiegand
 */
public class Led {

    public void turnOn() {
        try {
            Runtime runTime = Runtime.getRuntime();
            runTime.exec("gpio mode 3 out");
            runTime.exec("gpio write 3 0");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Exception occured: ");

        }
    }

    public void turnOff() {
        try {
            Runtime runTime = Runtime.getRuntime();
            runTime.exec("gpio mode 3 out");
            runTime.exec("gpio write 3 1");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Exception occured: ");

        }
    }

    public void blink() {
        try {
            Runtime runTime = Runtime.getRuntime();
            
            runTime.exec("gpio mode 3 out");
            
            for (int i = 0; i < 10; i++) {
                runTime.exec("gpio write 3 1");
                Thread.sleep(500);
                runTime.exec("gpio write 3 0");
                Thread.sleep(500);
            }
            
                
        } catch (Exception e) {
            System.out.println("Exception occured: ");

        }
    }

}
