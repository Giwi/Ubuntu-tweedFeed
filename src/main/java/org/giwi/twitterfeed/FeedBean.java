package org.giwi.twitterfeed;

import java.util.List;

public class FeedBean {
	private List<TwittBean> results;
	private String refresh_url;

	/**
	 * @return the results
	 */
	public List<TwittBean> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(final List<TwittBean> results) {
		this.results = results;
	}

	/**
	 * @return the refresh_url
	 */
	public String getRefresh_url() {
		return refresh_url;
	}

	/**
	 * @param refresh_url
	 *            the refresh_url to set
	 */
	public void setRefresh_url(final String refresh_url) {
		this.refresh_url = refresh_url;
	}

}
