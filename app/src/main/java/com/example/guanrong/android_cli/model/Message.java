package com.example.guanrong.android_cli.model;

import lombok.Data;

/**
 * 消息实体类
 *
 * @author ：Yang GuanRong
 * @date ：Created in 2019/9/11
 */
@Data
public class Message {

    private int type;   //0:表示一对一，1：表示群发
    private String sendId;
    private String receiverId;
    private String data;
}
