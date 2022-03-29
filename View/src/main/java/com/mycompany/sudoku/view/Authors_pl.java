package com.mycompany.sudoku.view;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {{ "2", "Author Two"},{ "1", "Pawel Wieczorek"}};
}
