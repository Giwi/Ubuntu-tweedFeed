package org.giwi.twitterfeed;


public class TwittBean {
	private String created_at;
	private String from_user;
	private String from_user_name;
	private String profile_image_url;
	private String text;

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TwittBean [created_at=");
		builder.append(created_at);
		builder.append(", from_user=");
		builder.append(from_user);
		builder.append(", from_user_name=");
		builder.append(from_user_name);
		builder.append(", profile_image_url=");
		builder.append(profile_image_url);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(final String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the from_user
	 */
	public String getFrom_user() {
		return from_user;
	}

	/**
	 * @param from_user
	 *            the from_user to set
	 */
	public void setFrom_user(final String from_user) {
		this.from_user = from_user;
	}

	/**
	 * @return the from_user_name
	 */
	public String getFrom_user_name() {
		return from_user_name;
	}

	/**
	 * @param from_user_name
	 *            the from_user_name to set
	 */
	public void setFrom_user_name(final String from_user_name) {
		this.from_user_name = from_user_name;
	}

	/**
	 * @return the profile_image_url
	 */
	public String getProfile_image_url() {
		return profile_image_url;
	}

	/**
	 * @param profile_image_url
	 *            the profile_image_url to set
	 */
	public void setProfile_image_url(final String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}
}
