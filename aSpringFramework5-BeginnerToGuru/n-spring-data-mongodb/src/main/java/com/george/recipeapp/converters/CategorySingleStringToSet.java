package com.george.recipeapp.converters;

import com.george.recipeapp.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategorySingleStringToSet implements Converter<String, List<CategoryCommand>> {

    @Override
    public List<CategoryCommand> convert(String s) {
        List<CategoryCommand> setToReturn = new ArrayList<>();

        int indexOfEquals = s.indexOf('=');

        String id = s.substring(0, indexOfEquals);
        String description = s.substring(indexOfEquals + 1);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(id);
        categoryCommand.setDescription(description);

        setToReturn.add(categoryCommand);

        return setToReturn;
    }
}
