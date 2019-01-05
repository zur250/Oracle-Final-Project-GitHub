package View;

import java.util.List;

public class DataTypeGenericForTable extends DataType {

	private final List<String> columnNames;
    private final List<List<Object>> data;

    public DataTypeGenericForTable(List<String> columnNames, List<List<Object>> data) {
        this.columnNames = columnNames ;
        this.data = data ;
    }

    public int getNumColumns() {
        return columnNames.size();
    }

    public String getColumnName(int index) {
        return columnNames.get(index);
    }

    public int getNumRows() {
        return data.size();
    }

    public Object getData(int column, int row) {
        return data.get(row).get(column);
    }

    public List<List<Object>> getData() {
        return data ;
    }
}
