# Skript-Translate

Skript-Translate has the ability to translate text to the range of supported languages shown below.

# Supported Languages:
- Amharic
- Arabic
- Basque
- Bengali
- Bulgarian
- Catalan
- Cherokee
- Chinese
- Chinese (Taiwan)
- Croatian
- Czech
- Danish
- Dutch
- English
- English (UK)
- Estonian
- Filipino
- Finnish
- French
- German
- Greek
- Gujarati
- Hebrew
- Hindi
- Hungarian
- Icelandic
- Indonesian
- Italian
- Japanese
- Kannada
- Korean
- Latvian
- Lithuanian
- Malay
- Malayalam
- Marathi
- Norwegian
- Polish
- Portugese
- Portugese (Brazil)
- Romanian
- Russian
- Serbian
- Slovak
- Slovenian
- Spanish
- Swahili
- Swedish
- Tamil
- Telugu
- Thai
- Turkish
- Urdu
- Ukrainian
- Vietnamese
- Welsh


# Usage

### Translate
```
translate %strings% to %language%
```

For Example:
```
set {_test::*} to "hello", "welcome", "to", "the" and "server"
translate {_test::*} to french
```

### Translate Specific
```
translate %strings% to %language% from %language%
translate %strings% from %language% to %language%
```
This can help to fix any misunderstandings when auto-detecting languages like in the Translate effect. This should only be used if it is actually the language.

For Example:
```
set {_test::*} to "hello", "welcome", "to", "the" and "server"
translate {_test::*} to french from english
```
