package com.ytheekshana.deviceinfo;

class SensorInfo {

    private String sensorName;
    private String vendorName;
    private String sensorType;
    private String wakeUpType;
    private String sensorPower;

    SensorInfo(String sensorName, String vendorName, String sensorType, String wakeUpType, String sensorPower) {
        this.sensorName = sensorName;
        this.vendorName = vendorName;
        this.sensorType = sensorType;
        this.wakeUpType = wakeUpType;
        this.sensorPower = sensorPower;
    }

    String getSensorName() {
        return sensorName;
    }

    String getVendorName() { return vendorName; }

    String getSensorType() {
        return sensorType;
    }

    String getWakeUpType() {
        return wakeUpType;
    }

    String getSensorPower() {
        return sensorPower;
    }

}