import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Load {
    /**
     * retrieve from https://stackoverflow.com/questions/8616781/how-to-get-a-web-pages-source-code-from-java
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
    public static Layout getLayoutFromJson(String inputJson){
        Gson gson  = new Gson();
        return gson.fromJson(inputJson, Layout.class);
    }
    /**
     * get the content in the file as string
     * @param filename the file name
     * @return the content aa s string
     */
    public static String getLoaclFileContent(String filename) {
        final Path path = FileSystems.getDefault().getPath("data", filename);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Couldn't find file: " + filename);
            System.exit(-1);
            return null;
        }
    }
    /**
     * get the content in the file as string
     * @param pathName the path of file
     * @param filename the file name
     * @return the string content
     */
    public static String getFileFromPath(String pathName, String filename) {
        final Path path = FileSystems.getDefault().getPath(pathName, filename);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Couldn't find file: " + filename);
            return null;
        }
    }
}
