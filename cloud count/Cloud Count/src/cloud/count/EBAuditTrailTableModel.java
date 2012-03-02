package cloud.count;

import javax.swing.table.AbstractTableModel;

public class EBAuditTrailTableModel extends AbstractTableModel
{
    private String[] columnNames = 
    {
        "Updated", 
        "By", 
        "Action"

    };

    private String[] auditTrailData = 
    {
        "24 / 12 / 2011",
        "Bob",
        "Uploaded attachment JoeEstimate.doc"
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
        return auditTrailData[column];
    }
    
    public void refresh()
    {
        this.fireTableDataChanged(); // Tells our table the data has changed
    }
}
