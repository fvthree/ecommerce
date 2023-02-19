package events;

import org.springframework.context.ApplicationEvent;

import com.fvthree.ecommerce.user.model.User;

public class UserEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7990978807125085004L;
	
	private String eventType;
    private User user;
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public UserEvent(String eventType, User user) {
        super(user);
        this.eventType = eventType;
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserEvent [eventType=" + eventType + ", user=" + user + "]";
    }
}
