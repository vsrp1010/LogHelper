package io.vsrp.domain;

public class LogEvent {

    private long timestamp = 0;
    private String serverHost = "";
    private String driverHost = "";
    private String message = "";
    private String status = "";
    private String slaStatus = "";
    private String job = "";
    private String runnable = "";
    private String iteration = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public void setSlaStatus(String slaStatus) {
        this.slaStatus = slaStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getDriverHost() {
        return driverHost;
    }

    public void setDriverHost(String driverHost) {
        this.driverHost = driverHost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getRunnable() {
        return runnable;
    }

    public void setRunnable(String runnable) {
        this.runnable = runnable;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    @Override
    public String toString() {
        return  "job=" + job +
                ", runnable=" + runnable +
                ", iteration=" + iteration +
                ", timestamp=" + timestamp +
                ", serverHost=" + serverHost +
                ", driverHost=" + driverHost +
                ", status=" + status +
                ", sla=" + slaStatus +
                ", message=" + message;
    }
//    @Override
//    public String toString() {
//        return "Time=" + timestamp +
//                ", ITR=" + job +
//                "::" + runnable +
//                "::" + iteration +
//                ", serverHost=" + serverHost +
////                ", driverHost='" + driverHost + '\'' +
//                ", message='" + message + '\'' +
//                ", status=" + status +
//                '}';
//    }
}
