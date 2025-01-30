package au.nagasonic.skripttranslate.util;

import java.util.Arrays;

public enum Language {
    AMHARIC("am"),
    ARABIC("ar"),
    BASQUE("eu"),
    BENGALI("bn"),
    ENGLISH_UK("en-GB"),
    PORTUGESE_BRAZIL("pt-BR"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CHEROKEE("chr"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESTONIAN("et"),
    FILIPINO("fil"),
    FINNISH("fi"),
    FRENCH("fr"),
    GERMAN("de"),
    GREEK("el"),
    GUJARATI("gu"),
    HEBREW("iw"),
    HINDI("hi"),
    HUNGARIAN("hu"),
    ICELANDIC("is"),
    INDONESIAN("id"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KANNADA("kn"),
    KOREAN("ko"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MALAY("ms"),
    MALAYALAM("ml"),
    MARATHI("mr"),
    NORWEGIAN("no"),
    POLISH("pl"),
    PORTUGESE("pt-PT"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SERBIAN("sr"),
    CHINESE("zh_CN"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    TAMIL("ta"),
    TELUGU("te"),
    THAI("th"),
    CHINESE_TAIWAN("zh-TW"),
    TURKISH("tr"),
    URDU("ur"),
    UKRAINIAN("uk"),
    VIETNAMESE("vi"),
    WELSH("cy"),
    NONE("none")
    ;

    String code;

    public static Language fromCode(String code) {
        return Arrays.stream(Language.values())
                .filter(language -> language.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(NONE);
    }
    Language(String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}
