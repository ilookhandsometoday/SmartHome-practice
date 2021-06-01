package com.domru.smarthome;

//constants to identify different device types. Needed for device card construction, as
//enum didn't work
public class DeviceTypes{
    public static final String SOCKET_STRING = "Розетка";
    public static final int SOCKET_INT = 1;

    public static final String LIGHTBULB_STRING = "Лампочка";
    public static final int LIGHTBULB_INT = 2;

    public static final String SMOKE_DETECTOR_STRING = "Датчик задымления";
    public static final int SMOKE_DETECTOR_INT = 3;
}
