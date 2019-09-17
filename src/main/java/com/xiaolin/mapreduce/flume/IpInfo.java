package com.xiaolin.mapreduce.flume;

public class IpInfo {
        private Long start;//开始的十进制数
        private Long end;//结尾的十进制数
        private String province;//省
        private String city;//市
        private String isp;//运营商

        public Long getStart() {
            return start;
        }

        public void setStart(Long start) {
            this.start = start;
        }

        public Long getEnd() {
            return end;
        }

        public void setEnd(Long end) {
            this.end = end;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
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
}
