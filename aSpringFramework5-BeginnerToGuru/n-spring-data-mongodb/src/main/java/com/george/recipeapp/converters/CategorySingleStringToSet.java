package com.george.recipeapp.converters;

import com.george.recipeapp.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategorySingleStringToSet implements Converter<String, Set<CategoryCommand>> {

    @Override
    public Set<CategoryCommand> convert(String s) {
        Set<CategoryCommand> setToReturn = new HashSet<>();

        int indexOfEquals = s.indexOf('=');

        Long id = Long.parseLong(s.substring(0, indexOfEquals));
        String description = s.substring(indexOfEquals + 1);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(id);
        categoryCommand.setDescription(description);

        setToReturn.add(categoryCommand);

        return setToReturn;
    }
}
