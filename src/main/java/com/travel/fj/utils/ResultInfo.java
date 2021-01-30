package com.travel.fj.utils;

import com.travel.fj.domain.ValueObject;

public class ResultInfo extends ValueObject {

    private  Meta meta;
    private  Object data; //与这个操作相关的数据

    public ResultInfo(boolean isSuccess, String msg, Object data){
        this.setMeta(new Meta(isSuccess,msg));
        this.setData(data);
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //返回结果信息总述
    private class Meta{

        /** 操作是否成功 */
        private boolean success;
        /** 操作反馈消息 */
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Meta() {
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }



    }

}
