package model;

import java.io.*;

/**
 * The type Player.
 */
public class Player {

    private String nickname;
    private final String avatar;
    private Stats stats;

    /**
     * Instantiates a new Player.
     *
     * @param nickname the nickname
     * @param avatar   the avatar
     */
    public Player(String nickname, String avatar) {
        this.setNickname(nickname);
        this.avatar = avatar;
        stats = new Stats();
    }

    /**
     * Gets random avatar.
     *
     * @return the random avatar
     */
    public static String getRandomAvatar() {
        String[] avatarImages = {"avatar1.png", "avatar2.png", "avatar3.png", "avatar4.png", "avatar5.png", "avatar6.png", "avatar7.png", "avatar8.png", "avatar9.png"};
        int random = (int) (Math.random() * avatarImages.length);
        return avatarImages[random];
    }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets nickname.
     *
     * @param nickname the nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Read stats stats.
     *
     * @return the stats
     * @throws IOException the io exception
     */
    public Stats readStats() throws IOException {
        try {
            FileReader reader = new FileReader(getNickname() + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            int lineNum = 0;

            while ((line = bufferedReader.readLine()) != null) {
                lineNum++;

                if (lineNum == 1) {
                    stats.setGamesStarted(Integer.parseInt(line));
                } else if (lineNum == 2) {
                    stats.setFinishedGames(Integer.parseInt(line));
                } else if (lineNum == 3) {
                    stats.setGamesWon(Integer.parseInt(line));
                } else if (lineNum == 4) {
                    stats.setLostGames(Integer.parseInt(line));
                }
            }
            reader.close();

        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                FileWriter writer = new FileWriter(getNickname() + ".txt", false);
                writer.write("0\n");
                writer.write("0\n");
                writer.write("0\n");
                writer.write("0\n");
                writer.close();
            }
        }

        return stats;
    }

    /**
     * Gets stats.
     *
     * @return the stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Sets stats.
     *
     * @param stats the stats
     */
    public void setStats(Stats stats) {
        try {
            FileWriter writer = new FileWriter(getNickname() + ".txt", false);
            writer.write(stats.getGamesStarted() + "\n");
            writer.write(stats.getFinishedGames() + "\n");
            writer.write(stats.getGamesWon() + "\n");
            writer.write(stats.getLostGames() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.stats = stats;
    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

}
