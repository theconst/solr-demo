package org.demo.conversion;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService> {

    private final Collection<? extends Converter<?, ?>> converters;

    private DefaultConversionService conversionService = new DefaultConversionService();

    @Autowired
    private ConversionServiceFactoryBean(Collection<? extends Converter<?, ?>> converters) {
        this.converters = converters;
    }


    @Override
    public ConversionService getObject() throws Exception {
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return ConversionService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
