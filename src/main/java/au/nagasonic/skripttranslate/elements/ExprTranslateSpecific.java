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

@Name("Translate Specific")
@Description("Translates a text to a language while specifying the language translating from.")
@Since("1.0")
@Examples("")
public class ExprTranslateSpecific extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprTranslateSpecific.class, String.class, ExpressionType.COMBINED,
                "translate %string% as %language% from %language%",
                "translate %string% from %language% to %language%");
    }
    private Expression<String> textExpr;
    private Expression<Language> lang1Expr;
    private Expression<Language> lang2Expr;
    private int pattern;
    @Override
    protected String @Nullable [] get(Event event) {
        String text = textExpr.getSingle(event);
        Language lang1 = lang1Expr.getSingle(event);
        Language lang2 = lang2Expr.getSingle(event);
        if (text == null || lang1 == null || lang1 == Language.NONE || lang2 == null || lang2 == Language.NONE){ return null; }
        String translated;
        try {
            if (pattern == 0) translated = TranslateUtils.translateSpecific(text, lang2.getCode(), lang1.getCode());
            else translated = TranslateUtils.translateSpecific(text, lang1.getCode(), lang2.getCode());
        }catch (Exception e){
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
        if (pattern == 0) return "translate " + textExpr.toString(event, debug) + " to " + lang1Expr.toString(event, debug) + " from " + lang2Expr.toString(event, debug);
        else return "translate " + textExpr.toString(event, debug) + " from " + lang1Expr.toString(event, debug) + " to " + lang2Expr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.textExpr = (Expression<String>) exprs[0];
        this.lang1Expr = (Expression<Language>) exprs[1];
        this.lang2Expr = (Expression<Language>) exprs[2];
        this.pattern = matchedPattern;
        return true;
    }
}
