package com.ytheekshana.deviceinfo;


class ThermalInfo {

    private String thermalName;
    private String thermalValue;

    ThermalInfo(String thermalName, String thermalValue) {
        this.thermalName = thermalName;
        this.thermalValue = thermalValue;
    }

    String getThermalName() {
        return thermalName;
    }

    String getThermalValue() {
        return thermalValue;
    }
}