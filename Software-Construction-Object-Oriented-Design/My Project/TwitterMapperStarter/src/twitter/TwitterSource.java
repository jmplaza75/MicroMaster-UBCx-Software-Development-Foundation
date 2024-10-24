package twitter;

import twitter4j.Status;

import java.util.*;

/**
 * Abstract class representing a source of tweets from Twitter.
 * This class provides the functionality to filter tweets based on specific terms,
 * and notify observers when a new tweet is received.
 */
public abstract class TwitterSource extends Observable {

    // A set of terms to filter tweets. Only tweets containing these terms will be considered.
    protected Set<String> terms = new HashSet<>();

    /**
     * Abstract method that must be implemented by subclasses to synchronize
     * the filtering mechanism when the set of filter terms changes.
     */
    abstract protected void sync();

    /**
     * Sets the terms that will be used to filter tweets.
     * Clears any existing terms and adds the new terms.
     * Then, it synchronizes the filtering mechanism.
     *
     * @param newterms A collection of new terms to filter tweets.
     */
    public void setFilterTerms(Collection<String> newterms) {
        terms.clear();
        terms.addAll(newterms);
        sync();
    }

    /**
     * Handles an incoming tweet. This method is called whenever a new tweet is delivered
     * to the application. It marks this object as changed and notifies all observers
     * that a new tweet has been received.
     *
     * @param s The status object representing the incoming tweet.
     */
    protected void handleTweet(Status s) {
        setChanged();
        notifyObservers(s);
    }
}