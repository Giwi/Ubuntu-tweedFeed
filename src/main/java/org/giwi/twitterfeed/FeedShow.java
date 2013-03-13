package org.giwi.twitterfeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Gtk;
import org.gnome.notify.Notification;
import org.gnome.notify.Notify;

import com.google.gson.Gson;

public class FeedShow {

	private final static String twittAPIurl = "http://search.twitter.com/search.json";
	private final static String searchQuery = "?q=";
	private final static SimpleDateFormat SDF = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);

	/**
	 * sudo apt-get install libjava-gnome-java
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(final String[] args) throws InterruptedException, IOException, ParseException {
		if (args.length < 2) {
			displayUsage();
		}
		Gtk.init(null);
		Notify.init("Ubuntu TweetFeed");
		final String hashtag = args[0];
		if ("y".equals(args[1])) {
			Authenticator.setDefault(new Authenticator() {
				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(args[2], args[3].toCharArray());
				}
			});

			System.setProperty("http.proxyUser", args[2]);
			System.setProperty("http.proxyPassword", args[3]);
		}
		//
		FeedBean lastFeed = getFeed(twittAPIurl + searchQuery + URLEncoder.encode(hashtag, "UTF-8"));
		if (lastFeed != null) {

			while (true) {
				displayFeed(lastFeed);
				Thread.sleep(1000);
				lastFeed = getFeed(twittAPIurl + lastFeed.getRefresh_url());

			}
		}
	}

	private static void displayUsage() {
		System.out.println("Params : UbuntuTweetFeed.jar [search] [useProxyAuth] [proxyUser]* [proxyPass*]");
		System.out.println("search : #java or @toto");
		System.out.println("useProxyAuth : y or n");
		System.exit(0);
	}

	private static FeedBean getFeed(final String urlStr) throws IOException {
		FeedBean fb = null;
		final URL url = new URL(urlStr);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		final BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		while ((output = br.readLine()) != null) {
			fb = parseOutput(output);
		}
		conn.disconnect();
		return fb;
	}

	private static void displayFeed(final FeedBean twitts) throws InterruptedException, ParseException, UnsupportedEncodingException {
		// Wed, 13 Mar 2013 13:20:57 +0000
		Pixbuf p;
		for (final TwittBean twitt : twitts.getResults()) {
			// "dialog-information"
			final String imgurl = URLDecoder.decode(twitt.getProfile_image_url(), "UTF-8");
			final Notification Hello = new Notification(DateFormatUtils.format(SDF.parse(twitt.getCreated_at()), "EEE, d MMM yyyy HH:mm:ss", Locale.FRENCH), twitt.getFrom_user_name() + " (@"
					+ twitt.getFrom_user() + ") :\n" + WordUtils.wrap(twitt.getText(), 50), "");
			try {
				p = getIcon(imgurl);
				Hello.setIcon(p);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Hello.show();
			Thread.sleep(1000);
			Hello.close();
		}

	}

	private static Pixbuf getIcon(final String imgurl) throws IOException {
		final String fileName = FilenameUtils.getName(imgurl);
		final File local = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
		if (!local.exists()) {
			final URL url = new URL(imgurl);
			FileUtils.copyURLToFile(url, local);
		}
		final Pixbuf pic = new Pixbuf(local.getAbsolutePath());
		return pic;
	}

	private static FeedBean parseOutput(final String json) {
		final Gson gson = new Gson();
		final FeedBean tb = gson.fromJson(json, FeedBean.class);
		Collections.reverse(tb.getResults());
		return tb;
	}
}
