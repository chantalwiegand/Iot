/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspjavalintest;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;


/**
 *
 * @author chantalwiegand
 */
public class DHT11 {

    private final int pin;
    private static final int MAXTIMINGS = 85;
    private final int[] dht11_dat =
    {
        0, 0, 0, 0, 0
    };

    private float temperature;
    private float humidity;
    
    // constructor
    public DHT11(final int pin)
    {
        this.pin = pin;
        // setup wiringPi
        if (Gpio.wiringPiSetup() == -1)
        {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }
        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
    }

    // activate this to get data from sensor
    public void getSensorData()
    {
        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

        Gpio.pinMode(pin, Gpio.OUTPUT);
        
        // send the start signal
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);
        Gpio.digitalWrite(pin, Gpio.HIGH);
        
        // turn to input mode
        Gpio.pinMode(pin, Gpio.INPUT);

        // read the data, (26-28uS HIGH is 0 bit, 70uS is 1 bit)
        for (int i = 0; i < MAXTIMINGS; i++)
        {
            int timeuS = 0;
            
            //read transistions
            while (Gpio.digitalRead(pin) == laststate)
            {
                timeuS++;
                Gpio.delayMicroseconds(1);
                if (timeuS == 255)
                {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pin);

            if (timeuS == 255)
            {
                break;
            }

            /* ignore first 3 transitions */
            if (i >= 4 && i % 2 == 0)
            {
                /* shove each bit into the storage bytes */
                dht11_dat[j / 8] <<= 1;
                // countertime checks difference between 0 and 1 
                // on Raspberry Pi 3B it seams a reas
                if (timeuS > 24)
                {
                    dht11_dat[j / 8] |= 1;
                }
                j++;
            }
        }
        // check we read 40 bits (8bit x 5 ) + verify checksum in the last
        // byte
        if (j >= 40 && checkParity())
        {
            float hum = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (hum > 100)
            {
                hum = dht11_dat[0]; // for DHT11
            }
            float temp = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (temp > 125)
            {
                temp = dht11_dat[2]; // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0)
            {
                temp = -temp;
            }
            // if you want fahenheit
            final float fah = temp * 1.8f + 32;
            this.temperature = temp;
            this.humidity = hum;
        } else
        {
            System.out.println("Data not good, skip");
        }

    }

    public float getTemperature()
    {
        return temperature;
    }
    
    public float getHumidity()
    {
        return humidity;
    }
    
    
    private boolean checkParity()
    {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }


}


