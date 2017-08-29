package com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center;

/**
 * Created by Administrator on 2017/3/8.
 */

public class RechargeBean {

    /**
     * id : ch_LazTWTKSm9KO0av90KKmXfDK
     * object : charge
     * created : 1488951174
     * livemode : true
     * paid : false
     * refunded : false
     * app : app_TeLCmDLqDi9Oavbv
     * channel : wx
     * order_no : 014889511744446747789
     * client_ip : 115.205.219.82
     * amount : 12310
     * amount_settle : 12310
     * currency : cny
     * subject : 用户18374964761充值123.1元014889
     * body : 014889511744446747789用户18374964761充值123.1元
     * time_paid : null
     * time_expire : 1488958374
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_LazTWTKSm9KO0av90KKmXfDK/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * metadata : {}
     * credential : {"object":"credential","wx":{"appId":"wxfd6710a5c2e59cd7","partnerId":"1265355101","prepayId":"wx201703081332545d53773c070520417234","nonceStr":"790f11df89012b04e5e9d958ca995702","timeStamp":"1488951174","packageValue":"Sign=WXPay","sign":"41878B1FD0949F4D49C514042C879BB1"}}
     * extra : {}
     * description : null
     */

    public String id;
    public String object;
    public int created;
    public boolean livemode;
    public boolean paid;
    public boolean refunded;
    public String app;
    public String channel;
    public String order_no;
    public String client_ip;
    public int amount;
    public int amount_settle;
    public String currency;
    public String subject;
    public String body;
    public Object time_paid;
    public int time_expire;
    public Object time_settle;
    public Object transaction_no;
    public RefundsBean refunds;
    public int amount_refunded;
    public Object failure_code;
    public Object failure_msg;
    public MetadataBean metadata;
    public CredentialBean credential;
    public ExtraBean extra;
    public Object description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_settle() {
        return amount_settle;
    }

    public void setAmount_settle(int amount_settle) {
        this.amount_settle = amount_settle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getTime_paid() {
        return time_paid;
    }

    public void setTime_paid(Object time_paid) {
        this.time_paid = time_paid;
    }

    public int getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(int time_expire) {
        this.time_expire = time_expire;
    }

    public Object getTime_settle() {
        return time_settle;
    }

    public void setTime_settle(Object time_settle) {
        this.time_settle = time_settle;
    }

    public Object getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(Object transaction_no) {
        this.transaction_no = transaction_no;
    }

    public RefundsBean getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsBean refunds) {
        this.refunds = refunds;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public Object getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(Object failure_code) {
        this.failure_code = failure_code;
    }

    public Object getFailure_msg() {
        return failure_msg;
    }

    public void setFailure_msg(Object failure_msg) {
        this.failure_msg = failure_msg;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public CredentialBean getCredential() {
        return credential;
    }

    public void setCredential(CredentialBean credential) {
        this.credential = credential;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public static class RefundsBean {
    }

    public static class MetadataBean {
    }

    public static class CredentialBean {
        public static class WxBean {
        }
    }

    public static class ExtraBean {
    }
}
