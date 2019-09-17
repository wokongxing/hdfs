package com.xiaolin.mapreduce.flume;

/**
 * 讲师：PK哥   交流群：545916944
 */
    public class AccessLog {

        private String user;  //用户账号，可能为null
        private String platform; // 操作系统
        private String version;  //软件版本号
        private String ip;  // ==> 经纬度  省份/城市/运营商/构建标签/构建商圈
        private String traffic;
        private String time;
        private String duration;
        private String appId; // 一家公司可能有多个app
    private Long sizes; // 真正统计需要的流量字段

    private String provice;
    private String city;
    private String isp;
    private String year;
    private String month;
    private String day;


    @Override
    public String toString() {
        return user + "\t" +
                platform + "\t" +
                  version + "\t" +
                 ip + "\t"+
                sizes + "\t" +
                  time + "\t"+
                  duration + "\t" +
                  appId + "\t" +
                provice+ "\t" +
                city+ "\t" +
                isp+ "\t" +
                year+ "\t" +
                month+ "\t" +
                day;
    }

    public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPlatform() {
            return platform;
        }

    public Long getSizes() {
        return sizes;
    }

    public void setSizes(Long sizes) {
        this.sizes = sizes;
    }

    public void setPlatform(String platform) {
            this.platform = platform;
        }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }
    }

