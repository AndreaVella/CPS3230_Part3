package main;

import java.util.List;
public class SystemState {
    public String userId;
    public boolean loggedIn;
    public List<Alert> alerts;

    public SystemState(String userId, boolean loggedIn, List<Alert> alerts){
        this.userId = userId;
        this.loggedIn = loggedIn;
        this.alerts = alerts;
    }
}
