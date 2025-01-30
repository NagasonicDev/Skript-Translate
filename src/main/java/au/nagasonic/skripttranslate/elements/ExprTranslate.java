package au.nagasonic.skripttranslate.elements;

import au.nagasonic.skripttranslate.util.Language;
import au.nagasonic.skripttranslate.util.TranslateUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Translated String")
@Description("Translates a string to the given language. Symbolic languages may have a warped appearance in-game.")
@Since("1.0")
@Examples("broadcast translated \"Welcome to the server\" as chinese")
public class ExprTranslate extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprTranslate.class, String.class, ExpressionType.COMBINED,
                "translated %string% as %language%");
    }
    private Expression<String> textExpr;
    private Expression<Language> languageExpr;
    @Override
    protected String @Nullable [] get(Event event) {
        String text = textExpr.getSingle(event);
        Language lang = languageExpr.getSingle(event);
        if (text == null || lang == null || lang == Language.NONE) {
            return null;
        }
        String translated;
        try {
            translated = TranslateUtils.translate(text, lang.getCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String[]{translated};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "translate " + textExpr.toString(event, debug) + " to " + languageExpr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.textExpr = (Expression<String>) exprs[0];
        this.languageExpr = (Expression<Language>) exprs[1];
        return true;
    }
}
