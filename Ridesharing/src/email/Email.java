package email;

import domain.Client;

import java.io.Serializable;
import java.util.ArrayList;

public class Email implements Serializable {
    private static final long serialVersionUID = -3686472195559526951L;
    private Client from;
    private ArrayList<Client> to, copy;
    private String title, body;

    public Client getFrom() {
        return from;
    }

    public Email setFrom(Client from) {
        this.from = from;
        return this;
    }

    public ArrayList<Client> getTo() {
        return to;
    }

    public Email setTo(ArrayList<Client> to) {
        this.to = to;
        return this;
    }

    public Email setTo(Client to) {
        ArrayList<Client> toList = new ArrayList<Client>();
        toList.add(to);
        setTo(toList);
        return this;
    }

    public ArrayList<Client> getCopy() {
        return copy;
    }

    public Email setCopy(ArrayList<Client> copy) {
        this.copy = copy;
        return this;
    }

    public Email setCopy(Client copy) {
        ArrayList<Client> copyList = new ArrayList<Client>();
        copyList.add(copy);
        setCopy(copyList);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Email setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Email setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        ArrayList<Client> clients = getTo();
        StringBuilder clientsTo = new StringBuilder();
        clients.stream().forEach(client -> clientsTo.append(client.getName()));


        clients = getCopy();
        StringBuilder clientsCopy = new StringBuilder();
        clients.stream().forEach(client -> clientsCopy.append(client.getName()));

        return "SEND EMAIL:" + "\n" +
                "From: " + getFrom().getName() +
                " To: " + clientsTo +
                " Copy: " + clientsCopy +
                " Title: " + getTitle() + "\n" +
                "Body: " + getBody() + "\n";
    }
}
