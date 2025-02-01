package au.nagasonic.skripttranslate.elements;

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

import java.util.function.Function;

@Name("Romanize/Transliterate")
@Description("Transliterates the given Cyrillic text into english.")
@Examples({"set {text::*} to \"こんにちは\" and \"さようなら\"", "transliterate {text::*}", "broadcast {text::*}"})
@Since("1.1")
public class EffRomanize extends AsyncEffect {
    static {
        Skript.registerEffect(EffRomanize.class,
                "(romanize|transliterate) %strings%");
    }
    Expression<String> texts;
    @Override
    protected void execute(Event event) {
        Function<String, String> changeFunction;
        if (texts != null) {
            changeFunction = text -> {
                text = TranslateUtils.transliterate(text);
                return text;
            };
        }else{
            changeFunction = text -> text;
        }
        this.texts.changeInPlace(event, changeFunction);
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
        return true;
    }
}
