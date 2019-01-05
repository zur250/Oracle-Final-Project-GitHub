package View;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public class GenericTablePane extends StackPane implements ViewInterface {

	private TableView<List<Object>> table = new TableView<>();
	private DataTypeGenericForTable data;// = dao.getAllData();

	public GenericTablePane(DataTypeGenericForTable data) {
		super();
		this.data=data;
		if (data!=null)
			createTable();
		showTable();
	}
	
	private void createTable() {
		for (int i = 0 ; i < data.getNumColumns() ; i++) {
			TableColumn<List<Object>, Object> column = new TableColumn<>(data.getColumnName(i));
		    int columnIndex = i ;
		    column.setCellValueFactory(cellData -> 
	        new SimpleObjectProperty<>(cellData.getValue().get(columnIndex)));
		    column.prefWidthProperty().bind(table.widthProperty().divide(data.getNumColumns()));
		    column.setResizable(false);
		    table.getColumns().add(column);
		}
		table.getItems().setAll(data.getData());
	}
	
	@Override
	public void updateData(DataType data) {
		getChildren().remove(table);
		clearData();
		this.data=(DataTypeGenericForTable)data;
		createTable();
		showTable();
	}

	private void showTable() {
		getChildren().add(table);		
	}

	@Override
	public void clearData() {
		table.getColumns().clear();
	}

}
