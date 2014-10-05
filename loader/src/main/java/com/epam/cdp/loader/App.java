package com.epam.cdp.loader;

import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * @author kisel.nikolay@gmail.com
 * @since 04.10.2014
 */
public class App {

    public static final Logger logger = Logger.getLogger(App.class);
    public static final String CLASS_NAME_1 = "com.epam.cdp.data.ModuleImpl";
    public static final String CLASS_NAME_2 = "com.epam.cdp.data.ModuleImpl2";

    public static JarClassLoader jarClassLoader = new JarClassLoader();

    public static void main(String[] args) throws Exception {

        while (processMenu()) ;

    }

    public static boolean processMenu() throws Exception {
        System.out.println("Menu:");
        System.out.println("Enter '1' to load ModuleImpl class.");
        System.out.println("Enter '2' to load ModuleImpl2 class.");
        System.out.println("Enter '3' to create new class loader.");
        System.out.println("Enter 'exit' to finish.");

        boolean isRepeated = true;
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        switch (answer) {
            case "1":
                loadClass(CLASS_NAME_1);
                break;
            case "2":
                loadClass(CLASS_NAME_2);
                break;
            case "3":
                jarClassLoader = new JarClassLoader();
                logger.debug("New class loader was created.");
                break;
            case "exit":
                isRepeated = false;
                break;
        }

        return isRepeated;
    }

    public static void loadClass(String className) throws Exception {
        Class clazz = jarClassLoader.loadClass(className);
        Module module = (Module) clazz.newInstance();
        logger.debug("Was loaded: " + module);
        module.incrementCounter();
        module.greeting();
    }
}
