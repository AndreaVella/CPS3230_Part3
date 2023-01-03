package main;

public class EventLog {
    public String id;
    public String timestamp;
    public int eventLogType;
    public String userId;
    public SystemState systemState;

    public EventLog(String id, String timestamp, int eventLogType, String userId, SystemState systemState){
        this.id = id;
        this.timestamp = timestamp;
        this.eventLogType = eventLogType;
        this.userId = userId;
        this.systemState = systemState;
    }
}