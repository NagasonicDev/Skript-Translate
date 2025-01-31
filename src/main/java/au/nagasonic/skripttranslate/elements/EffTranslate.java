package au.nagasonic.skripttranslate.elements;

import au.nagasonic.skripttranslate.util.Language;
import au.nagasonic.skripttranslate.util.TranslateUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.function.Function;


public class EffTranslate extends AsyncEffect {
    static {
        Skript.registerEffect(EffTranslate.class,
                "translate %strings% to %language%");
    }
    private Expression<String> texts;
    private Expression<Language> langExpr;

    @Override
    protected void execute(Event event) {
        Function<String, String> changeFunction;
        if (texts != null) {
            Language lang = langExpr.getSingle(event);
            changeFunction = text -> {
                try {
                    text = TranslateUtils.translate(text, lang.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return text;
            };
        }else{
            changeFunction = text -> text;
        }
        this.texts.changeInPlace(event, changeFunction);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Translate " + texts.toString(event, debug) + " to " + langExpr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        texts = (Expression<String>) exprs[0];
        if (!Changer.ChangerUtils.acceptsChange(texts, Changer.ChangeMode.SET, String.class)) {
            Skript.error(texts + " cannot be changed, thus it cannot be translated.");
            return false;
        }
        langExpr = (Expression<Language>) exprs[1];
        return true;
    }
}
