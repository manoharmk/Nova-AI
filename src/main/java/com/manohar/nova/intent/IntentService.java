package com.manohar.nova.intent;

/**
 * Service interface for analyzing user messages and detecting intent.
 */
public interface IntentService {

    /**
     * Resolves the target intent from a raw user prompt.
     *
     * @param message the raw input query from the user
     * @return the resolved {@link Intent} object
     */
    Intent detectIntent(String message);
}
