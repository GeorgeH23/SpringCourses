package com.george.recipeapp.converters;

import com.george.recipeapp.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryStringArrayToSet implements Converter<String[], List<CategoryCommand>> {

    @Override
    public List<CategoryCommand> convert(String[] source) {
        List<CategoryCommand> setToReturn = new ArrayList<>();
        for (String category : source) {
            CategoryCommand categoryCommand = new CategoryCommand();

            int indexOfEquals = category.indexOf('=');

            String id = category.substring(0, indexOfEquals);
            String description = category.substring(indexOfEquals + 1);

            categoryCommand.setId(id);
            categoryCommand.setDescription(description);

            setToReturn.add(categoryCommand);
        }
        return setToReturn;
    }
}
