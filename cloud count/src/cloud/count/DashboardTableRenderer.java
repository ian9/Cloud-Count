package cloud.count;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DashboardTableRenderer extends DefaultTableCellRenderer
{
    private final static Color COLOR_SELECTED = Color.CYAN;
    private final static Color COLOR_NEW = Color.GREEN;
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        DashboardTableModel model = (DashboardTableModel) table.getModel();
        
        Boolean isNew = model.isNew(row);

        if(isSelected)
            setBackground(COLOR_SELECTED);
        else if(isNew)
            setBackground(COLOR_NEW);
        else
            setBackground(Color.white);
            
        setForeground(Color.black);
        
        return this;
    }
}
