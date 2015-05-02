package controller;

public class Notification {
	private int notificationID;
	private int toUserID;
	private char notificationType;
	private String notificationMessage;
	private int aboutUserID;
	private int typeID;
	private String datetime;
	
	public Notification(
			int notificationID,
			int toUserID,
			char notificationType,
			String notificationMessage,
			int aboutUserID,
			int typeID,
			String datetime
			){
		this.setNotificationID(notificationID);
		this.setToUserID(toUserID);
		this.setNotificationType(notificationType);
		this.setNotificationMessage(notificationMessage);
		this.setAboutUserID(aboutUserID);
		this.setTypeID(typeID);
		this.setDatetime(datetime);
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public int getToUserID() {
		return toUserID;
	}

	public void setToUserID(int toUserID) {
		this.toUserID = toUserID;
	}

	public char getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(char notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public int getAboutUserID() {
		return aboutUserID;
	}

	public void setAboutUserID(int aboutUserID) {
		this.aboutUserID = aboutUserID;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
