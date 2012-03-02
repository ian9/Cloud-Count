package cloud.count;

import java.util.Random;
import javax.swing.table.AbstractTableModel;

public class DashboardTableModel extends AbstractTableModel
{
    /**
     * This will be information from the database eventually
     */
    
    private Random rand = new Random();
    
    private String[] columnNames = 
    {
        "Id", 
        "Updated", 
        "Status", 
        "Title", 
        "Budget", 
        "Actual", 
        "Var", 
        "%"
    };
    
    private String[] data1 = 
    {
        "6",
        "24/01/2012",
        "DRAFT",
        "Budget for 2012",
        "1,000.00",
        "N / A",
        "N / A",
        "N / A"
    };
    
    private String[] data2 = 
    {
        "11",
        "24/12/2011",
        "APPROVED",
        "Budget for 2011",
        "950.00",
        "948.00",
        "+ 2.00",
        "+ 0.21"
    };    

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() 
    {
        return 2;
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
        if(row == 0)
            return data1[column];
        else
            return data2[column];
    }
    
    public void refresh()
    {
        data1[0] = rand.nextInt(10000) + "";
        this.fireTableDataChanged(); // Tells our table the data has changed
    }
    
    public Boolean isNew(int row) 
    {
        int id = -1;
        
        if(row == 0)
            id = Integer.parseInt(data1[0]);
        else
            id  = Integer.parseInt(data2[0]);
        
        return id % 2 == 0;
    }
    
}

