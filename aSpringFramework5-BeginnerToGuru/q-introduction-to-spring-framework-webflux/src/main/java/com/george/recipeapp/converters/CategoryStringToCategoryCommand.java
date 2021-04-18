package com.george.recipeapp.converters;

import com.george.recipeapp.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryStringToCategoryCommand implements Converter<String, CategoryCommand> {

    @Nullable
    @Override
    public CategoryCommand convert(String s) {
        int indexOfEquals = s.indexOf('=');

        String id = s.substring(0, indexOfEquals);
        String description = s.substring(indexOfEquals + 1);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(id);
        categoryCommand.setDescription(description);

        return categoryCommand;
    }
}
