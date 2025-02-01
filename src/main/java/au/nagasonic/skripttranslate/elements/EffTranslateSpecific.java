package au.nagasonic.skripttranslate.elements;

import au.nagasonic.skripttranslate.util.Language;
import au.nagasonic.skripttranslate.util.TranslateUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.function.Function;

@Name("Translate Specific")
@Description({"Different from the Translate effect in that it specifies the current language of the string", "This can help to reduce misunderstandings caused by the auto-detect language."})
@Examples({"set {_msg::*} to \"servidor\", \"Bienvenido\" and \"Hola\"", "translate {_msg::*} to english from spanish"})
@Since("1.0")
public class EffTranslateSpecific extends AsyncEffect {
    static {
        Skript.registerEffect(EffTranslateSpecific.class,
                "translate %strings% to %language% from %language%",
                "translate %strings% from %language% to %language%");
    }
    private Expression<String> texts;
    private Expression<Language> lang1;
    private Expression<Language> lang2;
    private int pattern;
    @Override
    protected void execute(Event event) {
        Language lang1 = this.lang1.getSingle(event);
        Language lang2 = this.lang2.getSingle(event);
        Language from;
        Language to;
        if (pattern == 0){
            from = lang2;
            to = lang1;
        }else{
            from = lang1;
            to = lang2;
        }
        Function<String, String> changeFunction;
        if (texts != null){
            changeFunction = text -> {
                try {
                    text = TranslateUtils.translateSpecific(text, from.getCode(), to.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return text;
            };
        }else{
            changeFunction = text -> text;
        }
        texts.changeInPlace(event, changeFunction);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        texts = (Expression<String>) exprs[0];
        if (!Changer.ChangerUtils.acceptsChange(texts, Changer.ChangeMode.SET, String.class)) {
            Skript.error(texts + " cannot be changed, thus it cannot be translated.");
            return false;
        }
        lang1 = (Expression<Language>) exprs[1];
        lang2 = (Expression<Language>) exprs[2];
        pattern = matchedPattern;
        return true;
    }
}
