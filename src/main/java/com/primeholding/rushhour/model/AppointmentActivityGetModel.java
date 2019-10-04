package com.primeholding.rushhour.model;

public class AppointmentActivityGetModel {
    private Integer id;
    private ActivityGetModel activity;
    private Integer orderNum;

    public AppointmentActivityGetModel() {

    }

    public AppointmentActivityGetModel(Integer id, ActivityGetModel activity, Integer orderNum) {
        this.setId(id);
        this.setActivity(activity);
        this.setOrderNum(orderNum);
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public ActivityGetModel getActivity() {
        return activity;
    }

    public void setActivity(ActivityGetModel activity) {
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
