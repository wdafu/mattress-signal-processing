package com.example.hospital.patient.wx.api.util;

/**
 * @author GaoXin
 * @description
 * @date 2022/10/21
 */
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
public class TDengineClientUtil {
    public static void installClient() {
        log.info("Start install tdengine client");
        try {
            Runtime runtime = Runtime.getRuntime();
            URL tdengineUrl = TDengineClientUtil.class.getClassLoader().getResource("tdengine/install_client.sh");
            if (tdengineUrl != null) {
                runCommand(runtime, "chmod 777 " + tdengineUrl.getPath());
                runCommand(runtime, "bash " + tdengineUrl.getPath());
            } else {
                log.error("installTDengineClient not find tdengine/install_client.sh tdengineUrl={}", tdengineUrl);
            }
        } catch (Throwable ex) {
            log.error("installTDengineClient error ", ex);
        }
        log.info("End install tdengine client");
    }

    private static void runCommand(Runtime runtime, String command) throws Exception {
        log.info("Run command:[{}]", command);
        Process process = runtime.exec(command);
        // 获取外部程序标准输出流
        new Thread(new OutputHandlerRunnable(process.getInputStream())).start();
        // 获取外部程序标准错误流
        new Thread(new OutputHandlerRunnable(process.getErrorStream())).start();
        int status = process.waitFor();
        if (status != 0) {
            throw new RuntimeException(
                    String.format("Failed to run command:[%s], process:%s", command, process.toString()));
        } else {
            log.info("Success to run command:[{}], process:{}", command, process.toString());
        }
    }

    private static class OutputHandlerRunnable implements Runnable {
        private final InputStream inputStream;

        public OutputHandlerRunnable(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream))) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    log.info(line);
                }
            } catch (IOException ex) {
                log.error("Read stream error: ", ex);
            }
        }
    }
}