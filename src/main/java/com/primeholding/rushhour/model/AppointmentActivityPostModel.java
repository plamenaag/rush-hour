package com.primeholding.rushhour.model;

public class AppointmentActivityPostModel {
    private Integer activityId;
    private Integer orderNum;

    public AppointmentActivityPostModel() {

    }

    public AppointmentActivityPostModel(Integer activityId, Integer orderNum) {
        this.setActivityId(activityId);
        this.setOrderNum(orderNum);
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
