/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud.count;

import java.awt.Cursor;
import java.awt.Frame;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author Joey
 */
public class UploadAttachmentDialog extends javax.swing.JDialog {

    static JackRabbitHandler jrh;

    /**
     * Creates new form UploadAttachment
     */
    public UploadAttachmentDialog(JDialog parent, boolean modal, JackRabbitHandler jrh) {
        super(parent, modal);
        this.jrh = jrh;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileNameLabel = new javax.swing.JLabel();
        fileNameTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        fileLabel = new javax.swing.JLabel();
        labelTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        uploadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CloudCount >> Upload Attachment");
        setName("UploadAttachment");

        fileNameLabel.setText("File Name:");

        fileNameTextField.setEditable(false);

        browseButton.setLabel("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        fileLabel.setText("Label:");

        labelTextField.setText("/");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        uploadButton.setText("Upload");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(25, 25, 25)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(fileLabel)
                            .add(fileNameLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(fileNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 337, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(browseButton))
                            .add(labelTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(uploadButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileNameLabel)
                    .add(fileNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(browseButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileLabel)
                    .add(labelTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(uploadButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
        System.out.println("UploadAttachmentDialog has been closed.");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        final JDialog parent = this;
        
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select Document to Upload");
        try{
            RestoreWindow.restoreOptions(fc);
        }catch(Exception e){
            System.out.println("your window properties have not been saved");
        }
        //In response to a button click:
        int returnVal = fc.showOpenDialog(parent);
        
        // The Selected File
        File file = fc.getSelectedFile();
        
        // Fill in the file name Label with this the files name        
        fileNameTextField.setText(file.getPath());
        try{
            RestoreWindow.storeOptions(fc);
        }catch(Exception e){
            System.out.println("your window properties have not been saved");
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed

        startWaitCursor(uploadButton);
        if (testLabel()) {
            String path = labelTextField.getText();
            if (path.charAt(0) != '/') {
                path = "/" + path;
            }
            if (jrh.upload(path, fileNameTextField.getText()))//;
            {
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "There are one or more errors"
                    + " in your label fields. Make sure it is only alphanumeric");
        }
        stopWaitCursor(uploadButton);

    }//GEN-LAST:event_uploadButtonActionPerformed

    private boolean testLabel() {
        char[] label = labelTextField.getText().toCharArray();
        for (char c : label) {
            if (c != '/' && !Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static void startWaitCursor(JComponent component) {
        Cursor WAIT_CURSOR =
                Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        RootPaneContainer root =
                (RootPaneContainer) component.getTopLevelAncestor();
        root.getGlassPane().setCursor(WAIT_CURSOR);
        root.getGlassPane().setVisible(true);
    }

    /**
     * Sets cursor for specified component to normal cursor
     */
    public static void stopWaitCursor(JComponent component) {
        Cursor DEFAULT_CURSOR =
                Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        RootPaneContainer root =
                (RootPaneContainer) component.getTopLevelAncestor();
        root.getGlassPane().setCursor(DEFAULT_CURSOR);
        root.getGlassPane().setVisible(false);
    }

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
            java.util.logging.Logger.getLogger(UploadAttachmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UploadAttachmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UploadAttachmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UploadAttachmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                UploadAttachmentDialog dialog = new UploadAttachmentDialog(null, true, jrh);
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
    private javax.swing.JButton browseButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JTextField labelTextField;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
