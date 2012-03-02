package cloud.count;

import javax.swing.table.AbstractTableModel;

public class CBIncomeTableModel extends AbstractTableModel
{
    private String[] columnNames = 
    {
        "Line #", 
        "Sublines", 
        "Name", 
        "Budget"
    };
    
    private String[] incomeData = 
    {
        "100",
        "1",
        "N/A",
        "0.00"
    };

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() 
    {
        return 1;
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
            return incomeData[column];
    }
    
    public void refresh()
    {
        this.fireTableDataChanged(); // Tells our table the data has changed
    }
}
