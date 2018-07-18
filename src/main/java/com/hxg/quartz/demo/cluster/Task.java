package com.hxg.quartz.demo.cluster;

import com.hxg.quartz.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Configuration
@EnableScheduling
public class Task {

    @Autowired
    private TestService testService;

    private static ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

    @Value("#{'${monitor.url}'.split(',')}")
    private List<String> monitorUrlList;

    @Scheduled(cron = "${cron}")
    @SchedulerLock(name = "scheduledTaskName", lockAtMostFor = 4000, lockAtLeastFor = 4000)
    private void processItem() throws InterruptedException {
        log.warn("@Scheduled service, date = {}", new Date());

        Map<String, Object> map = new HashMap<>();

        int clientTotal = monitorUrlList.size();
        // 同时并发执行的线程数
        int threadTotal = 10;

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    // 判断是否正常
                    add(monitorUrlList.get(finalI));
                    semaphore.release();
                } catch (Exception e) {
                    //
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    public void add(String monitorUrl) {
        Socket socket = new Socket();
        boolean flag = true;
        try {
            // monitorUrl里面的内容为 ip:port
            // 5s超时
            String[] arr = monitorUrl.split(":");
            socket.connect(new InetSocketAddress(arr[0], Integer.parseInt(arr[1])), 5000);
        } catch (Exception e) {
            flag = false;
        } finally {
            Integer count = MapUtils.getInteger(map, monitorUrl, 0);
            if (!flag) {
                count++;
            }

            System.out.println("testService = " + testService);
            if (count % 10 == 3) {
                System.out.println("告警");
                count = 0;
            }
            map.put(monitorUrl, count);
        }
    }
}
