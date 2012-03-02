package cloud.count;

import javax.swing.table.AbstractTableModel;

public class EBNotesTableModel extends AbstractTableModel
{
    private String[] columnNames = 
    {
        "Updated", 
        "By", 
        "Note"

    };

    private String[] expenditureData = 
    {
        "24 / 12 / 2011",
        "Bob",
        "Updated the budget for 2012"
    };    

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() 
    {
        return 4;
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
        return expenditureData[column];
    }
    
    public void refresh()
    {
        this.fireTableDataChanged(); // Tells our table the data has changed
    }
}
