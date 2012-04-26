package cloud.count;

import badm.Budget;
import badm.Line;
import cc.test.bridge.BridgeConstants;
import cc.test.bridge.LineInterface;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class CBExpendituresTableModel extends AbstractTableModel
{
    ArrayList<LineInterface> lines;
    Budget budget;
    
    private String[] columnNames = 
    {
        "Line #", 
        "Sublines", 
        "Name", 
        "Budget",
        "Actual"
    };

    private String[] expenditureData = 
    {
        "100",
        "0",
        "N/A",
        "0.00",
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
        if(lines == null && budget != null)
        {
            lines = budget.fetchLines(BridgeConstants.Side.EXPENDITURE);
        }
        else if(budget == null)
        {
            return 0;
        }
        return lines.size();
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
        
        System.out.println(lines.size());
        
        Line line = (Line)lines.get(row);    
        if(column == 0)
            return line.getNumber();
        if(column == 2)
            return line.getName();
        if(column == 3)
            return line.getGoal();
        if(column == 4)
            return line.getTotal();
        
        return expenditureData [column];
    }
    
    public void refresh()
    {

        lines.clear();
        lines = budget.fetchLines(BridgeConstants.Side.EXPENDITURE);
        this.fireTableDataChanged(); // Tells our table the data has changed
    }
    
    public void setBudget(Budget budget)
    {
        this.budget = budget;
    }
}
