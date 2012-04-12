package cloud.count;

import badm.Budget;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.AbstractTableModel;

public class DashboardTableModel extends AbstractTableModel
{
   ArrayList<Budget> budgets;

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

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() 
    {
        if(budgets == null){
            budgets = Budget.all();
        }
        return budgets.size();
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
            System.out.println(budgets.size());
            Budget budget = budgets.get(row);    
            if (column == 0)
                return budget.getId();
            if(column == 3)
                return budget.getName();      
            return data1 [column];
    }
    
    public void refresh()
    {
        // Query for all budgets
        budgets.clear();
        budgets = Budget.all();
        // Update table
        this.fireTableDataChanged();
    }
    
    public Boolean isNew(int row) 
    {
        int id = -1;
        
        if(row == 0)
            id = Integer.parseInt(data1[0]);
        
        return id % 2 == 0;
    }
    
}

