package ru.stepkos.ybclubbot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    MEMO(EmojiParser.parseToUnicode(":memo:")),
    WHITE_CHECK_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    WAVE(EmojiParser.parseToUnicode(":wave:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
