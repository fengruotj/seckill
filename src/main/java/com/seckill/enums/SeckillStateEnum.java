package com.seckill.enums;

/**
 * Created by dello on 2016/7/5.
 * 使用枚举表常述常量数据字段
 */
public enum  SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_KILL(-2,"系统异常"),
    DATA_REWRITE(-3,"数据串改");

    private int state;
    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum statOf(int index){
        for(SeckillStateEnum state:values()){
            if(state.getState()==index){
                return state;
            }
        }
        return null;
    }
}
