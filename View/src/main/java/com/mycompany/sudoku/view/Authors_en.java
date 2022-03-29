package com.mycompany.sudoku.view;

import java.util.ListResourceBundle;

/**
 * Class representing authors.
 */
public class Authors_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {{ "2", "Author Two"},{ "1", "Pawel Wieczorek"}};

}
