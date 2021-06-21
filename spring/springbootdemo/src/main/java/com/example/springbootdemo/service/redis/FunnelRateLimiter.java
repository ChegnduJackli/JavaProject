package com.example.springbootdemo.service.redis;

import java.util.HashMap;
import java.util.Map;

//漏斗限流
public class FunnelRateLimiter {
    static class Funnel {
        int capacity; //# 漏斗容量
        float leakingRate; //漏嘴流水速率
        int leftQuota; //漏斗剩余空间
        long leakingTs; //上一次漏水时间

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate; //每秒漏多少
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            long deltaTs = (nowTs - leakingTs)/1000; //距离上一次漏水过去了多久秒
            int deltaQuota = (int) (deltaTs * leakingRate); //又可以腾出不少空间了
            if (deltaQuota < 0) { // 间隔时间太长，整数数字过大溢出
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) { // 腾出空间太小，最小单位是1
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
            if (this.leftQuota > this.capacity) { //剩余空间不得高于容量
                this.leftQuota = this.capacity;
            }
        }

        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    //所有的漏斗
    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);

            funnels.put(key, funnel);
        }
        return funnel.watering(1); // 需要1个quota
    }

    public void TestMain(){
        for(int i=0;i<20;i++) {
            System.out.println(isActionAllowed("laoqian", "reply", 10, 2));
        }
    }
}
