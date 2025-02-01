package au.nagasonic.skripttranslate.util;

import au.nagasonic.skripttranslate.Skript_Translate;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ibm.icu.text.Transliterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslateUtils {
    public static String LANGUAGE_COMBINATION = "Any-Eng";

    public static String translate(String text, String language) throws IOException {
        return translateSpecific(text, "auto", language);
    }

    public static String translateSpecific(String text, String from, String to) throws IOException {
        String langFrom = from, langTo = to, word = text;
        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        String inputJson = response.toString();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(inputJson);
        JsonArray jsonArray2 = (JsonArray) jsonArray.get(0);
        JsonArray jsonArray3 = (JsonArray) jsonArray2.get(0);
        String out = jsonArray3.get(0).getAsString();
        out.replaceFirst("\"", "");
        return out;
    }

    public static String transliterate(String text) {
        Skript_Translate.info(text);
        Transliterator t = Transliterator.getInstance(LANGUAGE_COMBINATION);
        String out = t.transliterate(text);
        Skript_Translate.info(out);
        return out;
    }
}
