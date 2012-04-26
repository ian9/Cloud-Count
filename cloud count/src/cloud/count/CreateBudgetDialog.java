package cloud.count;

import badm.Budget;
import badm.Line;
import cc.test.bridge.BridgeConstants;
import cc.test.bridge.BridgeConstants.Side;
import cc.test.bridge.LineInterface;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import cloud.count.DashboardFrame;
import java.util.ArrayList;

public final class CreateBudgetDialog extends javax.swing.JDialog 
{   
    Budget budget;
    
    public CreateBudgetDialog(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        budget = new Budget();
        budget.commit();
        System.out.println("New Budgets ID:"+budget.getId());
        initComponents();
        init();
    }
    
    protected void init()
    {

        initCreateBudgetIncomeTable();
        initCreateBudgetExpendituresTable();

        
    }
    
    protected void initCreateBudgetIncomeTable()
    {
        CBIncomeTableModel model = (CBIncomeTableModel) incomeTable.getModel();
        model.setBudget(budget);
        /**
         * This code will automate the width of the columns
         * on our dashboard table
         */
        final int[] WIDTHS = 
        {
            75,  // Line #
            75,  // Sublines
            75,  // Name
            75   // Budget
        };

        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = incomeTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
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

                    final int row = target.getSelectedRow();

                    SwingUtilities.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            editIncomeEntry(row);
                        }
                    });
                }
            }
        });
        
    }
    
    protected void initCreateBudgetExpendituresTable()
    {
       CBExpendituresTableModel model = (CBExpendituresTableModel) expendituresTable.getModel();
       model.setBudget(budget);
        /**
         * This code will automate the width of the columns
         * on our dashboard table
         */
        final int[] WIDTHS = 
        {
            75,  // Line #
            75,  // Sublines
            75,  // Name
            75   // Budget
        };

        TableColumnModel tableColumnModel = expendituresTable.getColumnModel();

        
        for (int i = 0; i < WIDTHS.length; i++) 
        {
            TableColumn col = expendituresTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(WIDTHS[i]);
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

                    final int row = target.getSelectedRow();

                    SwingUtilities.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            editExpendituresEntry(row);
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
    public void editExpendituresEntry(int row)
    {
        Line line = getLine(row, BridgeConstants.Side.EXPENDITURE);
        SublineUpdateDialog dialog = new SublineUpdateDialog(null, true, line);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
     /**
     * This method is the action performed when one of the Create Budget tables
     * is double clicked -> Bringing up the SublineUpdateDialog 
     * @param row that is being edited
     */
    public void editIncomeEntry(int row)
    {
        System.out.println("Editing an income entry line thing");
        Line line = getLine(row , BridgeConstants.Side.INCOME);
        SublineUpdateDialog dialog = new SublineUpdateDialog(null, true, line);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(1, 0), new java.awt.Dimension(1, 0), new java.awt.Dimension(1, 32767));
        titleTextField = new javax.swing.JTextField();
        descriptionScrollPanel = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        StartsLabel = new javax.swing.JLabel();
        endsLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        incomePanel = new javax.swing.JPanel();
        incomeScrollPane = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        incomeRemoveLineButton = new javax.swing.JButton();
        incomeAddLineButton = new javax.swing.JButton();
        incomeSublineUpButton = new javax.swing.JButton();
        incomeSublineDownButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        expendituresPanel = new javax.swing.JPanel();
        expendituresScrollPane = new javax.swing.JScrollPane();
        expendituresTable = new javax.swing.JTable();
        expendituresAddLineButton = new javax.swing.JButton();
        expendituresRemoveLineButton1 = new javax.swing.JButton();
        expendituresSublineUpButton = new javax.swing.JButton();
        expendituresSublineDownButton = new javax.swing.JButton();
        rollLabel = new javax.swing.JLabel();
        rollComboBox = new javax.swing.JComboBox();
        startsDateChooser = new datechooser.beans.DateChooserCombo();
        endsDateChooser = new datechooser.beans.DateChooserCombo();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cloud Count >> Create Budget");
        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(530, 575));

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        descriptionScrollPanel.setViewportView(descriptionTextArea);

        StartsLabel.setText("Starts:");

        endsLabel.setText("Ends:");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.setMaximumSize(new java.awt.Dimension(86, 29));
        saveButton.setMinimumSize(new java.awt.Dimension(86, 29));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        incomePanel.setBackground(new java.awt.Color(204, 204, 204));
        incomePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Income"));

        incomeTable.setModel(new cloud.count.CBIncomeTableModel());
        incomeScrollPane.setViewportView(incomeTable);

        incomeRemoveLineButton.setText("-");
        incomeRemoveLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeRemoveLineButtonActionPerformed(evt);
            }
        });

        incomeAddLineButton.setText("+");
        incomeAddLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeAddLineButtonActionPerformed(evt);
            }
        });

        incomeSublineUpButton.setText("Up");

        incomeSublineDownButton.setText("Down");

        org.jdesktop.layout.GroupLayout incomePanelLayout = new org.jdesktop.layout.GroupLayout(incomePanel);
        incomePanel.setLayout(incomePanelLayout);
        incomePanelLayout.setHorizontalGroup(
            incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(incomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(incomePanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(incomeScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, incomePanelLayout.createSequentialGroup()
                        .add(incomeSublineUpButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(incomeSublineDownButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(incomeAddLineButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(incomeRemoveLineButton)))
                .addContainerGap())
        );
        incomePanelLayout.setVerticalGroup(
            incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(incomePanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(incomeScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(incomePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(incomeRemoveLineButton)
                    .add(incomeAddLineButton)
                    .add(incomeSublineUpButton)
                    .add(incomeSublineDownButton))
                .addContainerGap())
        );

        titleLabel.setText("Title:");

        descriptionLabel.setText("Description:");

        expendituresPanel.setBackground(new java.awt.Color(204, 204, 204));
        expendituresPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Expenditures"));

        expendituresTable.setModel(new cloud.count.CBExpendituresTableModel());
        expendituresScrollPane.setViewportView(expendituresTable);

        expendituresAddLineButton.setText("+");
        expendituresAddLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expendituresAddLineButtonActionPerformed(evt);
            }
        });

        expendituresRemoveLineButton1.setText("-");

        expendituresSublineUpButton.setText("Up");

        expendituresSublineDownButton.setText("Down");

        org.jdesktop.layout.GroupLayout expendituresPanelLayout = new org.jdesktop.layout.GroupLayout(expendituresPanel);
        expendituresPanel.setLayout(expendituresPanelLayout);
        expendituresPanelLayout.setHorizontalGroup(
            expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(expendituresPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(expendituresPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(expendituresScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(expendituresPanelLayout.createSequentialGroup()
                        .add(expendituresSublineUpButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(expendituresSublineDownButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(expendituresAddLineButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(expendituresRemoveLineButton1)))
                .addContainerGap())
        );
        expendituresPanelLayout.setVerticalGroup(
            expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(expendituresPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(expendituresScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(expendituresPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(expendituresAddLineButton)
                    .add(expendituresRemoveLineButton1)
                    .add(expendituresSublineUpButton)
                    .add(expendituresSublineDownButton))
                .addContainerGap())
        );

        rollLabel.setText("Roll:");

        rollComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Weekly", "Bi Weekly", "Monthly" }));

        startsDateChooser.setCalendarPreferredSize(new java.awt.Dimension(350, 180));
        try {
            startsDateChooser.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }
        startsDateChooser.setLocale(new java.util.Locale("en", "GB", ""));

        try {
            endsDateChooser.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }
        endsDateChooser.setLocale(new java.util.Locale("en", "GB", ""));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(expendituresPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelButton)
                        .add(32, 32, 32))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(incomePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(titleLabel)
                                    .add(StartsLabel)
                                    .add(descriptionLabel)
                                    .add(endsLabel)
                                    .add(rollLabel))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(titleTextField)
                                    .add(descriptionScrollPanel)
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(rollComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(endsDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(startsDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(38, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(12, 12, 12)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(titleTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(titleLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(descriptionScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(descriptionLabel))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(StartsLabel))
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(startsDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(endsDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, endsLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(rollComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(rollLabel))
                .add(18, 18, 18)
                .add(incomePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(expendituresPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        budget.setDescription(descriptionTextArea.getText());
        budget.setName(titleTextField.getText());   
        System.out.println("Budget has been added: " + budget.commit());
        JOptionPane.showMessageDialog(null, "New Budget Saved!");
        budget = null;
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        budget.delete();
        dispose();
        System.out.println("CreateBudgetDialog has been closed.");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void incomeAddLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeAddLineButtonActionPerformed
        Line line = (Line) budget.createLine();
        line.setIncome(true);
        
        line.setName(
                JOptionPane.showInputDialog("Please enter line name."));
        line.setGoal(
                Double.parseDouble(JOptionPane.showInputDialog("Please enter total.")));
        line.setNumber(
                Integer.parseInt(JOptionPane.showInputDialog("Please enter lineNumber")));
        line.setTotal(0.0);
        line.commit();
        CBIncomeTableModel model = (CBIncomeTableModel) incomeTable.getModel();
        model.refresh();
    }//GEN-LAST:event_incomeAddLineButtonActionPerformed

    private void expendituresAddLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expendituresAddLineButtonActionPerformed
        Line line = (Line) budget.createLine();
        line.setIncome(false);
        
        line.setName(
                JOptionPane.showInputDialog("Please enter line name."));
        line.setGoal(
                Double.parseDouble(JOptionPane.showInputDialog("Please enter total.")));
        line.setNumber(
                Integer.parseInt(JOptionPane.showInputDialog("Please enter lineNumber")));
        line.setTotal(0.0);
        line.commit();
        CBExpendituresTableModel model = (CBExpendituresTableModel) expendituresTable.getModel();
        model.refresh();
    }//GEN-LAST:event_expendituresAddLineButtonActionPerformed

    private void incomeRemoveLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeRemoveLineButtonActionPerformed
        int row = incomeTable.getSelectedRow();
        getLine(row, BridgeConstants.Side.INCOME).delete();
        CBIncomeTableModel model = (CBIncomeTableModel) incomeTable.getModel();
        model.refresh();
    }//GEN-LAST:event_incomeRemoveLineButtonActionPerformed
    private Line getLine(int row, Side side){
        System.out.println("ROW:"+row);
        return (Line) budget.fetchLines(side).get(row); 
    }
    public static void main(String args[]) 
    {
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
            java.util.logging.Logger.getLogger(CreateBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateBudgetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final CreateBudgetDialog dialog = new CreateBudgetDialog(new javax.swing.JFrame(), true);
                
                dialog.addWindowListener(new java.awt.event.WindowAdapter() 
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) 
                    {
                        dialog.dispose();

                    }
                });
        
                // Center Dialog
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel StartsLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPanel;
    private javax.swing.JTextArea descriptionTextArea;
    private datechooser.beans.DateChooserCombo endsDateChooser;
    private javax.swing.JLabel endsLabel;
    private javax.swing.JButton expendituresAddLineButton;
    private javax.swing.JPanel expendituresPanel;
    private javax.swing.JButton expendituresRemoveLineButton1;
    private javax.swing.JScrollPane expendituresScrollPane;
    private javax.swing.JButton expendituresSublineDownButton;
    private javax.swing.JButton expendituresSublineUpButton;
    private javax.swing.JTable expendituresTable;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton incomeAddLineButton;
    private javax.swing.JPanel incomePanel;
    private javax.swing.JButton incomeRemoveLineButton;
    private javax.swing.JScrollPane incomeScrollPane;
    private javax.swing.JButton incomeSublineDownButton;
    private javax.swing.JButton incomeSublineUpButton;
    private javax.swing.JTable incomeTable;
    private javax.swing.JComboBox rollComboBox;
    private javax.swing.JLabel rollLabel;
    private javax.swing.JButton saveButton;
    private datechooser.beans.DateChooserCombo startsDateChooser;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    // End of variables declaration//GEN-END:variables
}
