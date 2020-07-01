package SA50.T6.WadCA.LAPS.model;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, LType> {
    @Override
    public LType convert(String source) {
        return LType.valueOf(source);
    }
}
