package com.xiaolin.mapreduce.TopN;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;

public class ServerInfo implements WritableComparable {
    private String userid;//用户id
    private String servername;//服务器名称
    private BigDecimal price;//价格

    public ServerInfo() {
    }

    public ServerInfo(String userid, String servername, BigDecimal price) {
        this.userid = userid;
        this.servername = servername;
        this.price = price;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(userid);
        dataOutput.writeUTF(servername);
        dataOutput.writeUTF(price.toString());

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        userid = dataInput.readUTF();
        servername = dataInput.readUTF();
        price = new BigDecimal(dataInput.readUTF());
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "userid='" + userid + '\'' +
                ", servername='" + servername + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        ServerInfo serverInfo = (ServerInfo) o;
        return serverInfo.price.compareTo(this.price);
    }
}
