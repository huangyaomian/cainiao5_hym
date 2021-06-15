package com.cainiao5.hym.common.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author: huangyaomian
 * created on: 2021/6/14 8:15 下午
 * description://todo
 */
@Entity(tableName = "tb_juser")
public class JUser {
    @PrimaryKey(autoGenerate = true)
    int jId;

    int age = 20;

    public String name = "哈哈哈哈";

    private JUser(){}

    public JUser(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return "JUser{" +
                "jId=" + jId +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
