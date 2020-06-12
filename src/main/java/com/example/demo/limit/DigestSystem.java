package com.example.demo.limit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author ：jyk
 * @date ：Created in 2019/11/14 20:31
 * @description： 人体消化系统
 */
@Slf4j
public class DigestSystem {

    //最多吃三碗饭
    private static Semaphore semaphore = new Semaphore(3);

    public static void eatFood() {
        try {
            synchronized (semaphore) {
                semaphore.acquire(1);
            }
            log.info(Thread.currentThread().getName() + " eat food");
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
        } catch (Exception e) {
            log.error("eat food", e);
        } finally {
            //release表示消化了
            semaphore.release();
            log.info(Thread.currentThread().getName() + ": food had release");
        }
    }

    public static void goToEat() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 5).forEach((i) -> service.submit(DigestSystem::eatFood));
    }

    public static void main(String[] args) {
        goToEat();
    }
}
