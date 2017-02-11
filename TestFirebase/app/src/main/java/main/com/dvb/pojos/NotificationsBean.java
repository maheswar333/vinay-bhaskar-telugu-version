package main.com.dvb.pojos;

/**
 * Created by AIA on 12/9/16.
 */

public class NotificationsBean {
    public String subject_notification;
    public String message_notification;
    public String date_notification;
    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject_notification() {
        return subject_notification;
    }

    public void setSubject_notification(String subject_notification) {
        this.subject_notification = subject_notification;
    }

    public String getMessage_notification() {
        return message_notification;
    }

    public void setMessage_notification(String message_notification) {
        this.message_notification = message_notification;
    }

    public String getDate_notification() {
        return date_notification;
    }

    public void setDate_notification(String date_notification) {
        this.date_notification = date_notification;
    }
}
