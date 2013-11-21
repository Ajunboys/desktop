/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RepositoryTestPanel2.java
 *
 * Created on May 4, 2011, 6:27:37 PM
 */
package com.stacksync.desktop.gui.wizard;

import org.apache.log4j.Logger;
import com.stacksync.desktop.config.profile.Profile;
import com.stacksync.desktop.config.Repository;
import com.stacksync.desktop.connection.plugins.TransferManager;
import com.stacksync.desktop.db.models.Workspace;
import com.stacksync.desktop.exceptions.CacheException;
import com.stacksync.desktop.exceptions.InitializationException;
import com.stacksync.desktop.exceptions.NoRepositoryFoundException;
import com.stacksync.desktop.exceptions.StorageConnectException;
import com.stacksync.desktop.exceptions.StorageException;
import com.stacksync.desktop.gui.settings.SettingsPanel;
import com.stacksync.desktop.util.StringUtil;

/**
 *
 * @author pheckel
 */
public class RepositoryTestPanel extends SettingsPanel {    
    
    private class TestWorker extends Thread{

        private TestListener callbackListener;
        private Repository repository;
        
        public TestWorker(String name, Repository repository, TestListener callbackListener){
            super(name);
            
            this.repository = repository;
            this.callbackListener = callbackListener;
        }

        private void doProcess() throws CacheException, StorageConnectException, NoRepositoryFoundException, StorageException, InitializationException{
            TransferManager transfer = repository.getConnection().createTransferManager();
            
            transfer.initStorage(); 

            progress.setValue(4);                            
            setStatus(resourceBundle.getString("reptest_created_status_ok"));

            setStatus("Initializing workspaces...");
            profile.setFactory();
            Workspace.InitializeWorkspaces(profile, callbackListener);
            progress.setValue(progress.getMaximum());            
        }
        
        
        @Override
        public void run() {
                        
            try{
                doProcess();
            } catch (InitializationException ex) {
                logger.error(config.getMachineName() + "#Exception: ", ex);
                setStatus(ex.getMessage());
                setError(ex);

                callbackListener.actionCompleted(false);
            } catch (NoRepositoryFoundException ex) {
                logger.error(config.getMachineName() + "#Exception: ", ex);
                setStatus(resourceBundle.getString("reptest_nothing_found") + ex.getMessage());
                setError(ex);

                callbackListener.actionCompleted(false);
            } catch (StorageException ex) { 
                logger.error(config.getMachineName() + "#Exception: ", ex);

                setStatus(resourceBundle.getString("reptest_connection_status_fail")+ex.getMessage());
                setError(ex);

                callbackListener.actionCompleted(false);
            } catch (Exception ex) {
                logger.error(config.getMachineName() + "#Exception: ", ex);
                setStatus(ex.getMessage());
                setError(ex);

                callbackListener.actionCompleted(false);
            }            
        }
    
    }
    
    private final Logger logger = Logger.getLogger(RepositoryTestPanel.class.getName());
    
    /** Creates new form RepositoryTestPanel2 */
    public RepositoryTestPanel(Profile profile) {    	
        initComponents();
	
        this.profile = profile;	
        this.scrDetails.setVisible(config.isExtendedMode());
        this.txtDetails.setVisible(config.isExtendedMode());
        this.txtDetails.setText("");
        this.txtDetails.setVisible(config.isExtendedMode());
        this.chkToggleDetails.setVisible(config.isExtendedMode());
        this.lblProgress.setVisible(config.isExtendedMode());
        
        
        /// setting text ///
        lblProgress.setText(resourceBundle.getString("reptest_progress"));
        
        lblTitle.setText(resourceBundle.getString("reptest_init_remote_storage"));        
        lblIntro1.setText(resourceBundle.getString("reptest_message_part1"));
        lblIntro2.setText(resourceBundle.getString("reptest_message_part2"));        
        
        chkToggleDetails.setText(resourceBundle.getString("reptest_details"));  
    }
    
    public void doRepoAction(final TestListener callbackListener) {
        progress.setMinimum(0);
        progress.setMaximum(5);

        progress.setValue(1);      
        
        Repository repository = profile.getRepository();                

        TestWorker repositoryTest = new TestWorker("RepositoryTest", repository, callbackListener);
        repositoryTest.start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProgress = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();
        lblTitle = new javax.swing.JLabel();
        lblIntro1 = new javax.swing.JLabel();
        lblIntro2 = new javax.swing.JLabel();
        scrDetails = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        chkToggleDetails = new javax.swing.JCheckBox();

        lblProgress.setText("__<<progress>>");
        lblProgress.setName("lblProgress"); // NOI18N

        progress.setName("progress"); // NOI18N

        lblTitle.setFont(lblTitle.getFont().deriveFont(lblTitle.getFont().getStyle() | java.awt.Font.BOLD, lblTitle.getFont().getSize()+2));
        lblTitle.setText("__Initializing Remote Storage");
        lblTitle.setName("lblTitle"); // NOI18N

        lblIntro1.setText("__Stacksync now connects to the remote storage and create a new");
        lblIntro1.setName("lblIntro1"); // NOI18N

        lblIntro2.setText("__repository for your files. This should normally take only a few seconds.");
        lblIntro2.setName("lblIntro2"); // NOI18N

        scrDetails.setName("scrDetails"); // NOI18N

        txtDetails.setColumns(20);
        txtDetails.setRows(5);
        txtDetails.setName("txtDetails"); // NOI18N
        scrDetails.setViewportView(txtDetails);

        chkToggleDetails.setSelected(true);
        chkToggleDetails.setText("__Details");
        chkToggleDetails.setName("chkToggleDetails"); // NOI18N
        chkToggleDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkToggleDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIntro1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIntro2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProgress, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkToggleDetails, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrDetails, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addGap(6, 6, 6)
                .addComponent(lblIntro1)
                .addGap(4, 4, 4)
                .addComponent(lblIntro2)
                .addGap(32, 32, 32)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProgress)
                .addGap(18, 18, 18)
                .addComponent(chkToggleDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkToggleDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkToggleDetailsActionPerformed
        boolean isSelected = chkToggleDetails.isSelected();
        scrDetails.setVisible(isSelected);
}//GEN-LAST:event_chkToggleDetailsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkToggleDetails;
    private javax.swing.JLabel lblIntro1;
    private javax.swing.JLabel lblIntro2;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JProgressBar progress;
    private javax.swing.JScrollPane scrDetails;
    private javax.swing.JTextArea txtDetails;
    // End of variables declaration//GEN-END:variables

    @Override
    public void load() {
        // ..
    }

    @Override
    public void save() {
        // ..
    }
    
    public void setStatus(String s) {        
        txtDetails.append(s + "\n");
        lblProgress.setText(s);
    }
    
    public void setError(Throwable e) {
        progress.setValue(progress.getMaximum());
        txtDetails.append(StringUtil.getStackTrace(e)+"\n");
        
        chkToggleDetails.setSelected(true);
        scrDetails.setVisible(true);
        
        logger.error(config.getMachineName() + "#Exception: ", e);
    }

    @Override
    public void clean() {
        txtDetails.setText("");
    }
    
    @Override
    public boolean check() {        
        return true;
    }
    
    public interface TestListener {
        public void actionCompleted(boolean success);
        public void setError(Throwable e);
        public void setStatus(String s);
    }
}