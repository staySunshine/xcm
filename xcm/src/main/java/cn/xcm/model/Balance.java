package cn.xcm.model;

import java.math.BigDecimal;
import java.util.Date;

public class Balance {
    private Integer id;

    private Integer uid;

    private BigDecimal payments;

    private Integer kind;

    private Integer type;

    private String context;

    private Date date;

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", uid=" + uid +
                ", payments=" + payments +
                ", kind=" + kind +
                ", type=" + type +
                ", context='" + context + '\'' +
                ", date=" + date +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getPayments() {
        return payments;
    }

    public void setPayments(BigDecimal payments) {
        this.payments = payments;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}