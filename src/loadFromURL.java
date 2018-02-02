import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class loadFromURL {
    /**
     * load the text of the source code from the url web page by a url string input
     * @param urlString the string of the url input
     * @return the text of source code
     * @throws IOException
     */
    public static String loadSourceCode(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder textFromURL = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            textFromURL.append(inputLine + "\n");
        }
        in.close();
        return textFromURL.toString();
    }
    /**
     * a method that loads the Layout Object from the Json file
     * @param inputJson input Json string
     * @return the layout of the game
     */
    public static Layout loadFromJson(String inputJson){
        Gson gson  = new Gson();
        Layout layoutOfGame = gson.fromJson(inputJson, Layout.class);
        return layoutOfGame;
    }
}
