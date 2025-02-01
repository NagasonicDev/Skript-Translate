package au.nagasonic.skripttranslate.classes;

import au.nagasonic.skripttranslate.util.Language;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unused", "deprecation"})
public class Types {
    static {
        EnumUtils<Language> LANGUAGE_ENUM = new EnumUtils<>(Language.class, "languages");
        Classes.registerClass(new ClassInfo<>(Language.class, "language")
                .user("language")
                .name("Language")
                .description("Represents a Language for Translation")
                .usage(LANGUAGE_ENUM.getAllNames())
                .examples("english", "vietnamese")
                .since("1.0")
                .parser(new Parser<Language>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable Language parse(String s, ParseContext context) {
                        return LANGUAGE_ENUM.parse(s);
                    }
                    @Override
                    public String toString(Language o, int flags) {
                        return LANGUAGE_ENUM.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(Language o) {
                        return toString(o, 0);
                    }
                })
        );
    }
}
