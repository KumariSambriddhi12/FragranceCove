package com.FragranceCove.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP session attributes.
 * Provides thread-safe methods for common session operations.
 */
public final class SessionUtil {

    // Private constructor to prevent instantiation
    private SessionUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Sets an attribute in the session.
     *
     * @param request the HttpServletRequest containing the session
     * @param key     the attribute key (non-null)
     * @param value   the attribute value
     * @throws IllegalArgumentException if key is null
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Session attribute key cannot be null");
        }
        
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session.
     *
     * @param request the HttpServletRequest containing the session
     * @param key     the key of the attribute to retrieve
     * @return the attribute value, or null if the attribute does not exist or the
     *         session is invalid
     * @throws IllegalArgumentException if key is null
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        if (key == null) {
            throw new IllegalArgumentException("Session attribute key cannot be null");
        }
        
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getAttribute(key) : null;
    }

    /**
     * Removes an attribute from the session.
     *
     * @param request the HttpServletRequest containing the session
     * @param key     the key of the attribute to remove
     * @throws IllegalArgumentException if key is null
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        if (key == null) {
            throw new IllegalArgumentException("Session attribute key cannot be null");
        }
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the current session and all its attributes.
     *
     * @param request the HttpServletRequest containing the session
     * @return true if session was invalidated, false if no session existed
     */
    public static boolean invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return true;
        }
        return false;
    }

    /**
     * Checks if a session exists and contains the specified attribute.
     *
     * @param request the HttpServletRequest containing the session
     * @param key     the attribute key to check
     * @return true if the attribute exists, false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public static boolean containsAttribute(HttpServletRequest request, String key) {
        if (key == null) {
            throw new IllegalArgumentException("Session attribute key cannot be null");
        }
        
        HttpSession session = request.getSession(false);
        return (session != null) && (session.getAttribute(key) != null);
    }
}