package com.epam.cdp.loader;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author kisel.nikolay@gmail.com
 * @since 04.10.2014
 */
public class JarClassLoader extends ClassLoader {

    public static final String JAR_PATH = "/home/mikalai/data-1.0-SNAPSHOT.jar";
    public static final Logger logger = Logger.getLogger(JarClassLoader.class);

    public JarClassLoader(ClassLoader parent) {
        super(parent);
    }

    public JarClassLoader() {
        this(JarClassLoader.class.getClassLoader());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //try to find class in jar and load it.
            JarFile jarFile = new JarFile(JAR_PATH);
            Enumeration entries = jarFile.entries();
            String classPath = transformToPath(name);
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                if (jarEntry.getName().equalsIgnoreCase(classPath)) {
                    byte[] classData = loadClassData(jarFile, jarEntry);
                    Class<?> clazz = defineClass(name, classData, 0, classData.length);
                    logger.debug("Class was load from jar.");
                    return clazz;
                }
            }

            //throw exception if class was not found in jar.
            logger.error("Class was not found!!!");
            throw new ClassNotFoundException();
        } catch (IOException ex) {
            logger.error("Exception was occurred during processing jar file!");
            throw new LoaderException("Can not read or find jar file.");
        }
    }

    private byte[] loadClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {
        long size = jarEntry.getSize();
        if (size == -1 || size == 0)
            return null;

        byte[] data = new byte[(int) size];
        InputStream in = jarFile.getInputStream(jarEntry);
        in.read(data);
        return data;
    }

    private String transformToPath(String className) {
        String path = className.replaceAll("\\.", "/");
        path += ".class";
        return path;
    }

}
