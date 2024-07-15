package model;

/**
 * The type Stats.
 */
public class Stats {

    /**
     * The Games started.
     */
    int gamesStarted;
    /**
     * The Finished games.
     */
    int finishedGames;
    /**
     * The Games won.
     */
    int gamesWon;
    /**
     * The Lost games.
     */
    int lostGames;

    /**
     * Instantiates a new Stats.
     */
    public Stats() {
        gamesStarted = 0;
        finishedGames = 0;
        gamesWon = 0;
        lostGames = 0;
    }

    /**
     * Gets games started.
     *
     * @return the games started
     */
    public int getGamesStarted() {
        return gamesStarted;
    }

    /**
     * Sets games started.
     *
     * @param gamesStarted the games started
     */
    public void setGamesStarted(int gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    /**
     * Gets finished games.
     *
     * @return the finished games
     */
    public int getFinishedGames() {
        return finishedGames;
    }

    /**
     * Sets finished games.
     *
     * @param finishedGames the finished games
     */
    public void setFinishedGames(int finishedGames) {
        this.finishedGames = finishedGames;
    }

    /**
     * Gets games won.
     *
     * @return the games won
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Sets games won.
     *
     * @param gamesWon the games won
     */
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    /**
     * Gets lost games.
     *
     * @return the lost games
     */
    public int getLostGames() {
        return lostGames;
    }

    /**
     * Sets lost games.
     *
     * @param lostGames the lost games
     */
    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    /**
     * Add one games started.
     */
    public void addGamesStarted() {
        gamesStarted++;
    }

    /**
     * Add one games won.
     */
    public void addGamesWon() {
        gamesWon++;
    }

    /**
     * Add one finished games.
     */
    public void addfinishedGames() {
        finishedGames++;
    }

    /**
     * Add one lost games.
     */
    public void addlostGames() {
        lostGames++;
    }
}
