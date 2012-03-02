package cloud.count;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.swing.*;

import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;

public final class EditBudgetDialog extends javax.swing.JDialog {
    
    JackRabbitHandler jrh;
    int rightClickedRow;
    /**
     * Creates new form EditBudget
     */
    public EditBudgetDialog(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        jrh = new JackRabbitHandler("http://localhost:8080/rmi", "admin", "admin");
        rightClickedRow = 0;
        initComponents();
        init();
    }
    
    protected void init()
    {
        
        initEditBudgetIncomeTable();
        initEditBudgetExpendituresTable();
        initEditBudgetAttachmentsTable();
        initEditBudgetNotesTable();
        initEditBudgetAuditTrailTable();
    }
    
    protected void initEditBudgetIncomeTable()
    {
        /**
         * This code will automate the width of the columns
         * on our Income table
         */
        final int[] WIDTHS = 
        {
            30,  // ID
            90,  // Update date
            60,  // By
            70,  // Line #
            175, // Name
            70,  // Sub-Total

        };
        
        EBIncomeTableRenderer myTableRenderer = new EBIncomeTableRenderer();
        
        TableColumnModel tableColumnModel = incomeTable.getColumnModel();

        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = incomeTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
            
            tableColumnModel.getColumn(i).setCellRenderer(myTableRenderer);
        }
        
        incomeTable.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {        
                // Double Click
                if (e.getClickCount() == 2) 
                {
                    JTable target = (JTable) e.getSource();

                    final int row = target.getSelectedRow() + 1;

                    SwingUtilities.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            editEntry(row);
                        }
                    });
                }
            }
        });
    }
    
    protected void initEditBudgetExpendituresTable()
    {
        /**
         * This code will automate the width of the columns
         * on our expenditures table
         */
        final int[] WIDTHS = 
        {
            30,  // ID
            90,  // Update date
            60,  // By
            70,  // Line #
            175, // Name
            70,  // Sub-Total
        };

        EBExpendituresTableRenderer myTableRenderer = new EBExpendituresTableRenderer();
        TableColumnModel tableColumnModel = expendituresTable.getColumnModel();

        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = expendituresTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
            tableColumnModel.getColumn(i).setCellRenderer(myTableRenderer);
        }
        
        expendituresTable.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {        
                // Double Click
                if (e.getClickCount() == 2) 
                {
                    JTable target = (JTable) e.getSource();

                    final int row = target.getSelectedRow() + 1;

                    SwingUtilities.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            editEntry(row);
                        }
                    });
                }
            }
        });
    }
    
   /**
    * This method is the action performed when one of the Create Budget tables
    * is double clicked -> Bringing up the SublineUpdateDialog 
    * @param row that is being edited
    */
    public void editEntry(int row)
    {
        UpdateLineDialog dialog = new UpdateLineDialog(null, true);
        dialog.setVisible(true);
    }
    
    protected void initEditBudgetAttachmentsTable()
    {
        /**
         * This code will automate the width of the columns
         * on our attachment table
         */
        final int[] WIDTHS = 
        {
            70,  // Updated Date
            45,  // By
            75,  // Label
            175  // Document Name

        };

        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = attachmentsTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
        }
        
        attachmentsTable.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {        
                // Double Click Download
                if (e.getClickCount() == 2) 
                {
                    JTable target = (JTable) e.getSource();
                    final MouseEvent mouse = e;
                    final int row = target.getSelectedRow();

                    SwingUtilities.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            EBAttachmentsTableModel model = (EBAttachmentsTableModel) attachmentsTable.getModel();
                            DownloadAttachmentDialog dialog = new DownloadAttachmentDialog(null, true,
                            jrh,(String)model.getValueAt(attachmentsTable.rowAtPoint(mouse.getPoint()), 2));
                            dialog.setVisible(true);
                            model.refresh();      
                        }
                    });
                }
                
                // Right Click
                // I found this code from Stack overflow
                // http://stackoverflow.com/questions/766956/how-do-i-create-a-right-click-context-menu-in-java-swing
                if (e.getButton() == 3)
                {
                    rightClickedRow = attachmentsTable.rowAtPoint(e.getPoint());
                    rightClickPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    
                }
            }
        });
    }
    
    protected void initEditBudgetNotesTable()
    {
        /**
         * This code will automate the width of the columns
         * on our notes table
         */
        final int[] WIDTHS = 
        {
            50,  // Updated Date
            50,  // By
            200, // Note

        };

        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = notesTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
        }
    }
    
    protected void initEditBudgetAuditTrailTable()
    {
        /**
         * This code will automate the width of the columns
         * on our AuditTrail table
         */
        final int[] WIDTHS = 
        {
            50,  // Updated Date
            50,  // By
            200, // cCtion

        };

        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = auditTrailTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rightClickPopupMenu = new javax.swing.JPopupMenu();
        downloadDocumentMenuItem = new javax.swing.JMenuItem();
        deleteDocumentPopupMenu = new javax.swing.JMenuItem();
        editBudgetTabbedPane = new javax.swing.JTabbedPane();
        incomeExpendituresPane = new javax.swing.JPanel();
        incomePanel = new javax.swing.JPanel();
        incomeScrollPane = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        totalIncomeTextField = new java.awt.TextField();
        expendituresPanel = new javax.swing.JPanel();
        expendituresScrollPane = new javax.swing.JScrollPane();
        expendituresTable = new javax.swing.JTable();
        totalExpendituresTextField = new java.awt.TextField();
        descriptionPanel = new java.awt.Panel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        attachmentsPanel = new javax.swing.JPanel();
        attachmentsComboBox = new javax.swing.JComboBox();
        attachmentsScrollPane = new javax.swing.JScrollPane();
        attachmentsTable = new javax.swing.JTable();
        uploadButton = new java.awt.Button();
        notesPanel = new javax.swing.JPanel();
        enterTextField = new java.awt.TextField();
        addNoteButton = new java.awt.Button();
        notesScrollPane = new javax.swing.JScrollPane();
        notesTable = new javax.swing.JTable();
        auditTrailPanel = new javax.swing.JPanel();
        auditTrailPane = new javax.swing.JScrollPane();
        auditTrailTable = new javax.swing.JTable();
        cancelButton = new java.awt.Button();
        saveButton = new java.awt.Button();
        excessIncomeTextField = new javax.swing.JTextField();
        optionComboBox = new javax.swing.JComboBox();
        refreshButton = new java.awt.Button();

        downloadDocumentMenuItem.setText("Download Document");
        downloadDocumentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadDocumentMenuItemActionPerformed(evt);
            }
        });
        rightClickPopupMenu.add(downloadDocumentMenuItem);

        deleteDocumentPopupMenu.setText("Delete Document");
        deleteDocumentPopupMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDocumentPopupMenuActionPerformed(evt);
            }
        });
        rightClickPopupMenu.add(deleteDocumentPopupMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cloud Count >> Edit Budget");

        incomePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Income"));

        incomeTable.setModel(new cloud.count.EBIncomeTableModel());
        incomeScrollPane.setViewportView(incomeTable);

        totalIncomeTextField.setEditable(false);
        totalIncomeTextField.setText("Total: 5,123.95");

        org.jdesktop.layout.GroupLayout incomePanelLayout = new org.jdesktop.layout.GroupLayout(incomePanel);
        incomePanel.setLayout(incomePanelLayout);
        incomePanelLayout.setHorizontalGroup(
            incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(incomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, incomeScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, incomePanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(totalIncomeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        incomePanelLayout.setVerticalGroup(
            incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(incomePanelLayout.createSequentialGroup()
                .add(incomeScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(totalIncomeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        expendituresPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Expenditures"));

        expendituresTable.setModel(new cloud.count.EBExpendituresTableModel());
        expendituresScrollPane.setViewportView(expendituresTable);

        totalExpendituresTextField.setEditable(false);
        totalExpendituresTextField.setText("Total: 3,999.23");

        org.jdesktop.layout.GroupLayout expendituresPanelLayout = new org.jdesktop.layout.GroupLayout(expendituresPanel);
        expendituresPanel.setLayout(expendituresPanelLayout);
        expendituresPanelLayout.setHorizontalGroup(
            expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(expendituresPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, expendituresScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, expendituresPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(totalExpendituresTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        expendituresPanelLayout.setVerticalGroup(
            expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(expendituresPanelLayout.createSequentialGroup()
                .add(expendituresScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(totalExpendituresTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout incomeExpendituresPaneLayout = new org.jdesktop.layout.GroupLayout(incomeExpendituresPane);
        incomeExpendituresPane.setLayout(incomeExpendituresPaneLayout);
        incomeExpendituresPaneLayout.setHorizontalGroup(
            incomeExpendituresPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(incomeExpendituresPaneLayout.createSequentialGroup()
                .addContainerGap()
                .add(incomeExpendituresPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(incomePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(expendituresPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        incomeExpendituresPaneLayout.setVerticalGroup(
            incomeExpendituresPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(incomeExpendituresPaneLayout.createSequentialGroup()
                .add(incomePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(expendituresPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 62, Short.MAX_VALUE))
        );

        editBudgetTabbedPane.addTab("Income & Expenditures", incomeExpendituresPane);

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setText("This is a budget description.  To update just type in this text box.");
        descriptionScrollPane.setViewportView(descriptionTextArea);

        org.jdesktop.layout.GroupLayout descriptionPanelLayout = new org.jdesktop.layout.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(descriptionScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(descriptionPanelLayout.createSequentialGroup()
                .add(descriptionScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 325, Short.MAX_VALUE))
        );

        editBudgetTabbedPane.addTab("Description", descriptionPanel);

        attachmentsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select to Filter --", "Doc Files", "PDF Files" }));

        attachmentsTable.setModel(new cloud.count.EBAttachmentsTableModel());
        attachmentsScrollPane.setViewportView(attachmentsTable);

        uploadButton.setLabel("Upload New");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout attachmentsPanelLayout = new org.jdesktop.layout.GroupLayout(attachmentsPanel);
        attachmentsPanel.setLayout(attachmentsPanelLayout);
        attachmentsPanelLayout.setHorizontalGroup(
            attachmentsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(attachmentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(attachmentsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(attachmentsPanelLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(attachmentsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
                    .add(attachmentsPanelLayout.createSequentialGroup()
                        .add(attachmentsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, attachmentsPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(uploadButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        attachmentsPanelLayout.setVerticalGroup(
            attachmentsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(attachmentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(attachmentsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(attachmentsScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(uploadButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );

        editBudgetTabbedPane.addTab("Attachments", attachmentsPanel);

        enterTextField.setText("Enter Text");

        addNoteButton.setLabel("+");

        notesTable.setModel(new cloud.count.EBNotesTableModel());
        notesScrollPane.setViewportView(notesTable);

        org.jdesktop.layout.GroupLayout notesPanelLayout = new org.jdesktop.layout.GroupLayout(notesPanel);
        notesPanel.setLayout(notesPanelLayout);
        notesPanelLayout.setHorizontalGroup(
            notesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(notesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(notesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(notesPanelLayout.createSequentialGroup()
                        .add(enterTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addNoteButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(notesScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE))
                .addContainerGap())
        );
        notesPanelLayout.setVerticalGroup(
            notesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, notesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(notesScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(notesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(addNoteButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(enterTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        editBudgetTabbedPane.addTab("Notes", notesPanel);

        auditTrailTable.setModel(new cloud.count.EBAuditTrailTableModel());
        auditTrailPane.setViewportView(auditTrailTable);

        org.jdesktop.layout.GroupLayout auditTrailPanelLayout = new org.jdesktop.layout.GroupLayout(auditTrailPanel);
        auditTrailPanel.setLayout(auditTrailPanelLayout);
        auditTrailPanelLayout.setHorizontalGroup(
            auditTrailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(auditTrailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(auditTrailPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addContainerGap())
        );
        auditTrailPanelLayout.setVerticalGroup(
            auditTrailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(auditTrailPanelLayout.createSequentialGroup()
                .add(auditTrailPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addContainerGap())
        );

        editBudgetTabbedPane.addTab("Audit Trail", auditTrailPanel);

        cancelButton.setLabel("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setLabel("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        excessIncomeTextField.setEditable(false);
        excessIncomeTextField.setText("Excess of Income: 1, 124.72");

        optionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Draft", "Review", "Final" }));

        refreshButton.setLabel("Refesh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(editBudgetTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(refreshButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(optionComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(36, 36, 36)
                        .add(excessIncomeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 224, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(9, 9, 9)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(editBudgetTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(excessIncomeTextField)
                        .add(optionComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, refreshButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
        System.out.println("EditBudgetBudgetDialog has been closed.");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
       
        final JDialog parent = this;
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //startWaitCursor(null);
                UploadAttachmentDialog dialog = new UploadAttachmentDialog(parent, true, jrh);
                dialog.setVisible(true);
                EBAttachmentsTableModel model = (EBAttachmentsTableModel) attachmentsTable.getModel();
                model.refresh();
                //stopWaitCursor(null);
            }
        });
    }//GEN-LAST:event_uploadButtonActionPerformed

    private void deleteDocumentPopupMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDocumentPopupMenuActionPerformed
        
        //final JFrame parent = this;
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //startWaitCursor(null);
                EBAttachmentsTableModel model = (EBAttachmentsTableModel) attachmentsTable.getModel();
                DeleteDocumentConfirmationDialog dialog = new DeleteDocumentConfirmationDialog(null, true,
                        jrh,(String)model.getValueAt(rightClickedRow, 2));
                dialog.setVisible(true);
                model.refresh();
                //stopWaitCursor(null);
            }
        });
    }//GEN-LAST:event_deleteDocumentPopupMenuActionPerformed

    
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
       EBAttachmentsTableModel model = (EBAttachmentsTableModel) attachmentsTable.getModel();
       model.refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void downloadDocumentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadDocumentMenuItemActionPerformed
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                EBAttachmentsTableModel model = (EBAttachmentsTableModel) attachmentsTable.getModel();
                DownloadAttachmentDialog dialog = new DownloadAttachmentDialog(null, true,
                        jrh,(String)model.getValueAt(rightClickedRow, 2));
                dialog.setVisible(true);
                model.refresh();           
            }
        });
    }//GEN-LAST:event_downloadDocumentMenuItemActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                EditBudgetDialog dialog = new EditBudgetDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
        
  
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button addNoteButton;
    private javax.swing.JComboBox attachmentsComboBox;
    private javax.swing.JPanel attachmentsPanel;
    private javax.swing.JScrollPane attachmentsScrollPane;
    private javax.swing.JTable attachmentsTable;
    private javax.swing.JScrollPane auditTrailPane;
    private javax.swing.JPanel auditTrailPanel;
    private javax.swing.JTable auditTrailTable;
    private java.awt.Button cancelButton;
    private javax.swing.JMenuItem deleteDocumentPopupMenu;
    private java.awt.Panel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JMenuItem downloadDocumentMenuItem;
    private javax.swing.JTabbedPane editBudgetTabbedPane;
    private java.awt.TextField enterTextField;
    private javax.swing.JTextField excessIncomeTextField;
    private javax.swing.JPanel expendituresPanel;
    private javax.swing.JScrollPane expendituresScrollPane;
    private javax.swing.JTable expendituresTable;
    private javax.swing.JPanel incomeExpendituresPane;
    private javax.swing.JPanel incomePanel;
    private javax.swing.JScrollPane incomeScrollPane;
    private javax.swing.JTable incomeTable;
    private javax.swing.JPanel notesPanel;
    private javax.swing.JScrollPane notesScrollPane;
    private javax.swing.JTable notesTable;
    private javax.swing.JComboBox optionComboBox;
    private java.awt.Button refreshButton;
    private javax.swing.JPopupMenu rightClickPopupMenu;
    private java.awt.Button saveButton;
    private java.awt.TextField totalExpendituresTextField;
    private java.awt.TextField totalIncomeTextField;
    private java.awt.Button uploadButton;
    // End of variables declaration//GEN-END:variables
}
