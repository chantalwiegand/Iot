/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspjavalintest;

import com.google.gson.JsonArray;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author chantalwiegand
 */
public class ApiStart {

    String cpuTekst;

    public static void main(String[] args) throws InterruptedException {

        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7000);
        app.get("rest/devices", ctx -> {
            ctx.html("These are all the sensors<br>"
                    + "<a href=devices/cpu>CPU temperature </a> <br>"
                    + "<a href=devices/dht>DHT data </a> <br>"
                    + "<a href=devices/dht/all>Old DHT data</a> <br>"
                    + "<a href=devices/cpu/all>Old CPU data </a> <br>"
                    + "<a href=devices/led/on>Turn led on </a> <br>"
                    + "<a href=devices/led/off>Turn led off </a> <br>"
                    + "<a href=devices/led/blink>let led blink </a> <br>");
        });
        app.get("rest/devices/cpu", ctx -> {
            JSONObject obj = null;

            CPUboard cpu = new CPUboard();
            Float temperature = null;

            for (int i = 0; i < 10; i++) {
                obj = new JSONObject();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                temperature = cpu.getCPUtemperature();
                String cpuTekst = Float.toString(temperature);
                obj.put("temperature", cpuTekst);
                obj.put("date", formatter.format(date));

            }

            String fileName = "";
            for (int i = 0; i < 10000; i++) {
                File checkFile = new File("/home/pi/Documents/Java/eindproject/CpuData/CpuData" + i + ".json");
                if (!checkFile.exists()) {
                    fileName = "/home/pi/Documents/Java/eindproject/CpuData/CpuData" + i + ".json";
                    i = 10001;
                }
            }

            try (FileWriter file = new FileWriter(fileName)) {
                file.write(obj.toJSONString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ctx.json(obj);

        });

        app.get("rest/devices/dht", ctx -> {

            JSONObject obj = null;
            String temperature = null, humidity = null, checkString = "0.0";
            DHT11 dht = new DHT11(21);

            for (int i = 0; i < 100; i++) {

                obj = new JSONObject();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());

                dht.getSensorData();
                humidity = Float.toString(dht.getHumidity());
                temperature = Float.toString(dht.getTemperature());

                obj.put("humidity", humidity);
                obj.put("temperature", temperature);
                obj.put("date", formatter.format(date));
                System.out.println(temperature);

            }

            String fileName = "";
            for (int i = 0; i < 1000; i++) {
                File checkFile = new File("/home/pi/Documents/Java/eindproject/DhtData/DhtData" + i + ".json");
                if (!checkFile.exists()) {
                    fileName = "/home/pi/Documents/Java/eindproject/DhtData/DhtData" + i + ".json";
                    i = 10001;
                }
            }

            try (FileWriter file = new FileWriter(fileName)) {
                file.write(obj.toJSONString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ctx.json(obj);
        });

        app.get("rest/devices/cpu/all", ctx -> {
            JSONArray employeeList = new JSONArray();
            for (int i = 0; i < 1000; i++) {
                JSONParser jsonParser = new JSONParser();

                try (FileReader reader = new FileReader("/home/pi/Documents/Java/eindproject/CpuData/CpuData" + i + ".json")) {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);
                    employeeList.add(obj);
                    System.out.println(employeeList);
                    ctx.json(employeeList);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    i = 1001;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        app.get("rest/devices/dht/all", ctx -> {
            JSONArray employeeList = new JSONArray();
            for (int i = 0; i < 1000; i++) {
                JSONParser jsonParser = new JSONParser();

                try (FileReader reader = new FileReader("/home/pi/Documents/Java/eindproject/DhtData/DhtData" + i + ".json")) {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);
                    employeeList.add(obj);
                    System.out.println(employeeList);
                    ctx.json(employeeList);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    i = 1001;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        app.get("rest/devices/led/on", ctx -> {
            Led led = new Led();
            led.turnOn();
            ctx.result("The led is on");
        });

        app.get("rest/devices/led/off", ctx -> {
            Led led = new Led();
            led.turnOff();
            ctx.result("The led is off");
        });

        app.get("rest/devices/led/blink", ctx -> {
            Led led = new Led();
            led.blink();
            ctx.result("The led is blinking 10 times, please wait for it to be done, before submitting another request!");
        });

    }

}
