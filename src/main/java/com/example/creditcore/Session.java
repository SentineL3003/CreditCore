package com.example.creditcore;

public class Session {
    private static int employeeId;
    private static String fullName;
    private static String position;

    public static void start(int id, String name, String pos) {
        employeeId = id; fullName = name; position = pos;
    }

    public static int getEmployeeId() { return employeeId; }
    public static String getFullName() { return fullName; }
    public static String getPosition() { return position; }
}