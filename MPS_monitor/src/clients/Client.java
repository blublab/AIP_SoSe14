package clients;

import java.net.InetAddress;
import java.util.Date;

public class Client {

    private InetAddress address;
    private Integer port;
    private Double load;
    private Status status;
    private Integer query;
    private Date signal;
    private Date uptime;
    private Date idletime;
    private Date downtime;

    public Client(InetAddress address, Integer port){
        this.address = address;
        this.port = port;
        this.load = 0d;
        this.query = 0;
        this.setStatus(Status.GREEN);
        this.refreshSignal();
    }

    public InetAddress getAddress() {
        return address;
    }

    public Integer getPort() {
        return port;
    }

    public Status getStatus() {
        return status;
    }

    public Double getLoad() {
        return load;
    }

    public void setLoad(Double load) {
        this.load = load;
    }

    public void setStatus(Status status) {

        switch (status){
            case GREEN:
                this.uptime = new Date();
                this.idletime = null;
                this.downtime = null;
                break;
            case YELLOW:
                this.uptime = null;
                this.idletime = new Date();
                this.downtime = null;
                break;
            case RED:
                this.load = null;
                this.uptime = null;
                this.idletime = null;
                this.downtime = new Date(this.signal.getTime());
                break;
        }

        this.status = status;
    }
    public void setStatus(String status) {
        this.setStatus(Status.valueOf(status));
    }


    public void refreshSignal(){
        this.signal = new Date();
    }

    public Date getSignal(){
        return this.signal;
    }

    public void incrementQuery(){
        this.query++;
    }

    @Override
    public String toString(){

        String signalS = this.signal == null ? "" : Long.toString(this.signal.getTime()),
               uptimeS = this.uptime == null ? "" : Long.toString(this.uptime.getTime()),
               idletimeS = this.idletime == null ? "" : Long.toString(this.idletime.getTime()),
               downtimeS = this.downtime == null ? "" : Long.toString(this.downtime.getTime());

        String str = "";
        str += "{";
        str += "\"host\":\"" + this.address.getHostAddress() + "\",";
        str += "\"port\":\"" + this.port + "\",";
        str += "\"load\":\"" + (this.load == null ? "" : this.load) + "\",";
        str += "\"query\":\"" + this.query + "\",";
        str += "\"status\":\"" + this.status + "\",";
        str += "\"signal\":\"" + signalS + "\",";
        str += "\"uptime\":\"" + uptimeS + "\",";
        str += "\"idletime\":\"" + idletimeS + "\",";
        str += "\"downtime\":\"" + downtimeS + "\"";
        str += "}";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client that = (Client) o;

        if (!address.equals(that.address)) return false;
        if (!port.equals(that.port)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + port.hashCode();
        return result;
    }
}