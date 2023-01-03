package main;

public class LoggedLog {
    public String userId;
    public boolean isLoggedInOnWebsite;

    public LoggedLog(String userId, boolean isLoggedInOnWebsite){
        this.userId = userId;
        this.isLoggedInOnWebsite = isLoggedInOnWebsite;
    }
}