package me.victoriest.photio.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author VictoriEST
 * @date 2018/7/25
 * photio
 * @ref https://github.com/souyunku/SnowFlake
 */
@Component
public class SnowFlakeIdGenerator {

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * 起始的时间戳
     */
    @Value("${photio.snow-flake.original-timestamp}")
    private long originalTimestamp;

    /**
     * 数据中心
     */
    @Value("${photio.snow-flake.data-center-id}")
    private long dataCenterId;

    /**
     * 机器标识
     */
    @Value("${photio.snow-flake.machine-id}")
    private long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currentStamp = getTimestamp();
        if (currentStamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currentStamp == lastTimestamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currentStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currentStamp;

        return (currentStamp - originalTimestamp) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getTimestamp();
        while (mill <= lastTimestamp) {
            mill = getTimestamp();
        }
        return mill;
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }

}
