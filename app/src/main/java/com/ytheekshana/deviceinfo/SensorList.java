package com.ytheekshana.deviceinfo;

class SensorList {

    private String SensorName;
    private String VenderName;
    private String SensorType;
    private String WakeUpType;
    private String SensorPower;

    SensorList(String SensorName, String VenderName, String SensorType,String WakeUpType, String SensorPower) {
        this.SensorName = SensorName;
        this.VenderName = VenderName;
        this.SensorType = SensorType;
        this.WakeUpType = WakeUpType;
        this.SensorPower = SensorPower;
    }

    String getSensorName() {
        return SensorName;
    }

    String getVenderName() {
        return VenderName;
    }

    String getSensorType() {
        return SensorType;
    }

    String getWakeUpType() {
        return WakeUpType;
    }

    String getSensorPower() {
        return SensorPower;
    }

}