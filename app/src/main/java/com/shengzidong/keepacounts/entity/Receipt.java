package com.shengzidong.keepacounts.entity;

/**
 * 保存每次消费的信息
 */
public class Receipt {
    /**
     * 消费类别
     */
    String category;
    /**
     * 消费日期
     */
    String Date;

    /**
     * 记账时间
     */
    long time;

    /**
     * 标签（消费品牌等）
     */
    String label;
    /**
     * 备注
     */
    String message;
    /**
     * 消费地点
     */
    String location;
    /**
     * 消费金额
     */
    float money;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }


    @Override
    public String toString() {
        return "Receipt{" +
                "category='" + category + '\'' +
                ", Date=" + Date +
                ", time=" + time +
                ", label='" + label + '\'' +
                ", message='" + message + '\'' +
                ", location='" + location + '\'' +
                ", money=" + money +
                '}';
    }
}
