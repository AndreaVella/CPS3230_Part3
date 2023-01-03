package main.api.requests;

import main.EventLog;
import main.LoggedLog;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequests {

    public int postAlert(String jsonAlert){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonAlert))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAlerts(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert?userId=afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public EventLog[] getEventLog(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/EventsLog/afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4"))
                .header("accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            EventLog[] eventLogs = new Gson().fromJson(response.body(), EventLog[].class);

            return eventLogs;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoggedLog getLog(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/EventsLog/afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4/login-status"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LoggedLog log = new Gson().fromJson(response.body(), LoggedLog.class);

            return log;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
