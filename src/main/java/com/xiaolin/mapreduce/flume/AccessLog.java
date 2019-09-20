package com.xiaolin.mapreduce.flume;

public class AccessLog {

    private String domain;  //用户账号，可能为null
    private String time; // 操作系统
    private String flow;  //软件版本号
    private String ip;  // ip 可以解析出 省份/城市/运营商/构建标签/构建商圈
    private Long sizes; // 真正的流量
    private String provice; //省份
    private String city;//城市
    private String isp;//运营商
    private String year;//年份
    private String month;//月份
    private String day;//日


    public Long getSizes() {
        return this.sizes;
    }

    public void setSizes(final Long sizes) {
        this.sizes = sizes;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getFlow() {
        return this.flow;
    }

    public void setFlow(final String flow) {
        this.flow = flow;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public String getProvice() {
        return this.provice;
    }

    public void setProvice(final String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getIsp() {
        return this.isp;
    }

    public void setIsp(final String isp) {
        this.isp = isp;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(final String month) {
        this.month = month;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    @Override
public String toString() {
    return domain + "\t" +
             ip + "\t"+
            sizes + "\t" +
              time + "\t"+
            provice+ "\t" +
            city+ "\t" +
            isp+ "\t" +
            year+ "\t" +
            month+ "\t" +
            day;
}


}

