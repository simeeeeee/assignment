package com.lfin.assignment.common;


import org.testcontainers.containers.MySQLContainer;

public class TestMySQLContainer {

    private static final MySQLContainer<?> instance;
    private static String mysqlDockerImage = "mysql:8.0.32";
    private static String database = "assignment";
    private static String username = "assignment_user";
    private static String password = "password_for_test";
    private static String initScriptFile = "init.sql";


    static {

        instance = new MySQLContainer<>(mysqlDockerImage)
                .withDatabaseName(database)
                .withUsername(username)
                .withPassword(password)
                .withInitScript(initScriptFile)
        ;

        instance.start();   // start docker

        System.out.println("***********************************");
        System.out.printf(" Embedded Mysql Container \n");
        System.out.printf("  - image        :%s\n", mysqlDockerImage);
        System.out.printf("  - containerName:%s\n", instance.getContainerName());
        System.out.printf("  - jdbcUrl :%s\n", instance.getJdbcUrl());
        System.out.printf("  - username:%s\n", instance.getUsername());
        System.out.printf("  - password:%s\n", instance.getPassword());
        System.out.println("***********************************");

        // Shutdown hook to stop the container when JVM stops
        Runtime.getRuntime().addShutdownHook(new Thread(instance::stop));
    }

    // Private constructor to prevent instantiation
    private TestMySQLContainer() {}
    public static MySQLContainer<?> getInstance() {
        return instance;
    }

    static void printClasspath() {
        // Get the classpath
        String classpath = System.getProperty("java.class.path");

        System.out.println("***********************************");
        // Print the classpath
        System.out.println("Classpath:");
        System.out.println(classpath);
        System.out.println("***********************************");

    }

}
