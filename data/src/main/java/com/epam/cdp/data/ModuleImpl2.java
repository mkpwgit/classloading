package com.epam.cdp.data;

import com.epam.cdp.loader.Module;

/**
 * @author kisel.nikolay@gmail.com
 * @since 04.10.2014
 */
public class ModuleImpl2 implements Module {

    private static int counter = 0;

    @Override
    public void greeting() {
        System.out.println("Greeting from ModuleImpl2 class.");
        System.out.println("Counter for ModuleImpl2 class is: " + counter);
    }

    @Override
    public void incrementCounter() {
        counter++;
    }
}
