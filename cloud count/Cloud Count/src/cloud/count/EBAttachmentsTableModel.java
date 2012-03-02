package cloud.count;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;


public class EBAttachmentsTableModel extends AbstractTableModel
{
    private String[] columnNames = 
    {
        "Updated", 
        "By", 
        "Label", 
        "Document Name"

    };
    public EBAttachmentsTableModel(){
        attachmentsData = new ArrayList();
        populateData();
    }
    private ArrayList<String[]> attachmentsData;
//    = new ArrayList(); 
//    {
//        "24 / 12 / 2011",
//        "Bob",
//        "Estimate",
//        "JoeEstimate.doc"
//    };    
    
    private JackRabbitHandler jrh = new JackRabbitHandler("http://localhost:8080/rmi",
                "admin", "admin");//has admin because all it does is grab info

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() 
    {
        return attachmentsData.size();
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }
    
    private void populateData(){
        attachmentsData = jrh.getDocumentTable();
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
        return attachmentsData.get(row)[column];
    }
    
    public void refresh()
    {
        populateData();
        System.out.println("model refresh");
        this.fireTableDataChanged(); // Tells our table the data has changed
    }
}
