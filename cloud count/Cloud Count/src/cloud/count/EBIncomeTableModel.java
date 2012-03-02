package cloud.count;

import javax.swing.table.AbstractTableModel;

public class EBIncomeTableModel extends AbstractTableModel
{
    private String[] columnNames = 
    {
        "Id", 
        "Updated", 
        "By", 
        "Line #",
        "Name",
        "Sub-Total"
    };
    
    private String[] incomeData = 
    {
        "1",
        "24 / 12 / 2011",
        "Bob",
        "100",
        "Ordinary Collections",
        "1,000.00"
    };

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() 
    {
        return 10;
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
    
    public Boolean isNew(int row) 
    {
        // Apply Conditions for when a row is new
        int id = -1;
        
        id  = Integer.parseInt(incomeData[0]);
        
        return id % 2 == 0;
    }
}
