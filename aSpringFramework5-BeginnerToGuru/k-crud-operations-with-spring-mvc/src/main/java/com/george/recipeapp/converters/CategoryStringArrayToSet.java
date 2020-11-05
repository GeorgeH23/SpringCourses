package com.george.recipeapp.converters;

import com.george.recipeapp.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategoryStringArrayToSet implements Converter<String[], Set<CategoryCommand>> {

    @Override
    public Set<CategoryCommand> convert(String[] source) {
        Set<CategoryCommand> setToReturn = new HashSet<>();
        for (String category : source) {
            CategoryCommand categoryCommand = new CategoryCommand();

            int indexOfEquals = category.indexOf('=');

            String id = category.substring(0, indexOfEquals);
            String description = category.substring(indexOfEquals + 1);

            categoryCommand.setId(Long.parseLong(id));
            categoryCommand.setDescription(description);

            setToReturn.add(categoryCommand);
        }
        return setToReturn;
    }
}
