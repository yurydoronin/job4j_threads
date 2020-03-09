package ru.job4j.multithreading;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * Class Wget.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 28.11.2019
 */
@ThreadSafe
public class Wget {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Wget.class);

    /**
     * To download the file with the speed limit.
     *
     * @param url,        .
     * @param speedLimit, .
     */
    public void download(String url, int speedLimit) {
        URLConnection con = null;
        try {
            con = new URL(url).openConnection();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        try (InputStream in = Objects.requireNonNull(con).getInputStream();
             BufferedInputStream bis = new BufferedInputStream(in)) {
            int download;
            long time = System.currentTimeMillis();
            while ((download = bis.read()) != -1) {
                long currentTime = System.currentTimeMillis();
                if (download > speedLimit) {
                    Thread.sleep(currentTime - time);
                }
                time = System.currentTimeMillis();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
