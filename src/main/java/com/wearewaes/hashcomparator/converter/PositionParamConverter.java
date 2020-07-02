package com.wearewaes.hashcomparator.converter;

import com.wearewaes.hashcomparator.domain.Position;
import org.springframework.core.convert.converter.Converter;

public class PositionParamConverter implements Converter<String, Position> {
    @Override
    public Position convert(String source) {
        return Position.valueOf(source.toUpperCase());
    }
}

