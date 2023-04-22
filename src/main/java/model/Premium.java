package model;

/**
 * Premium interface
 */
public interface Premium {
    /**
     * Checks whether the object is premium.
     * @return true if the object is premium
     */
    public boolean isPremium();

    /**
     * Sets object as premium
     * @param premium boolean
     */
    public void setPremium(boolean premium);
}
