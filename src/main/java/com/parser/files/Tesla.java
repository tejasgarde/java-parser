package com.parser.files;

import java.util.*;
import org.apache.log4j.Logger;

public class Tesla extends Car {

    private Logger logger =  Logger.getLogger(Tesla.class);

    private String batteryPower;

    public String getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(String batteryPower) {
        this.batteryPower = batteryPower;
    }
}
