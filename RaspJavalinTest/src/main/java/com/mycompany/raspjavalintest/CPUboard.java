package com.mycompany.raspjavalintest;

import java.io.*;

public class CPUboard
{

    // read temperature from system
    public float getCPUtemperature()
    {

        String fileName = "/sys/class/thermal/thermal_zone0/temp";
        String line = null;

        float tempC = -1F;
        float celsius = 0;
        
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null)
            {
                tempC = (Integer.parseInt(line) / 1000F);
            }
            bufferedReader.close();
            
        } catch (FileNotFoundException ex)
        {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }
       
        
        return tempC;
    }
    
}
