package work.daqian.myai.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import work.daqian.myai.exception.BadRequestException;

@Getter
@AllArgsConstructor
public enum Provider {
    OLLAMA(1, "本地 Ollama 模型"),
    RESTRICT(2, "限制模型"),
    ALIBABA(3, "阿里云模型 api"),
    GOOGLE(4, "谷歌模型 api"),
    OPENAI(5, "OpenAI api"),
    ;

    @EnumValue
    private final int value;
    @JsonValue
    private final String desc;

    public static Provider fromValue(int value) {
        for (Provider provider : Provider.values()) {
            if (provider.getValue() == value)
                return provider;
        }
        throw new BadRequestException("未知模型提供商");
    }
}
