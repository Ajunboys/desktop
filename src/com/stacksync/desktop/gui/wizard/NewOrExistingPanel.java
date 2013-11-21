/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ProfileBasicsPanel.java
 *
 * Created on Mar 27, 2011, 3:01:00 AM
 */

package com.stacksync.desktop.gui.wizard;

import javax.swing.ButtonGroup;
import com.stacksync.desktop.connection.plugins.Plugins;
import com.stacksync.desktop.gui.settings.SettingsPanel;

/**
 *
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class NewOrExistingPanel extends SettingsPanel {
    /** Creates new form ProfileBasicsPanel */
    public NewOrExistingPanel() {    	
        initComponents();

        ButtonGroup g = new ButtonGroup();

        g.add(rdNewRepo);
        g.add(rdExistingRepo);
        
        /// setting text ///
        rdNewRepo.setText(resourceBundle.getString("newp_setup_new_storage"));        
        rdExistingRepo.setText(resourceBundle.getString("newp_connect_to_existing_storage"));        
        
        jLabel1.setText(resourceBundle.getString("newp_create_or_connect"));       
        jLabel2.setText(resourceBundle.getString("newp_panel_wizard_message_part1"));
        jLabel3.setText(resourceBundle.getString("newp_panel_wizard_message_part2"));
        jLabel4.setText(resourceBundle.getString("newp_initialize_new_remote_option"));
        jLabel5.setText(resourceBundle.getString("newp_storage_details"));
        jLabel6.setText(resourceBundle.getString("newp_sync_files_message_part1"));
        jLabel7.setText(resourceBundle.getString("newp_sync_files_message_part2"));        
    }

    @Override
    public void load() {
        Plugins.loadAsync();
    }

    @Override
    public void save() {
        // Fressen
    }

    public boolean isNewRepository() {
        return rdNewRepo.isSelected();
    }

    public boolean isExistingRepository() {
        return rdExistingRepo.isSelected();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdNewRepo = new javax.swing.JRadioButton();
        rdExistingRepo = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        rdNewRepo.setFont(rdNewRepo.getFont().deriveFont(rdNewRepo.getFont().getStyle() | java.awt.Font.BOLD));
        rdNewRepo.setSelected(true);
        rdNewRepo.setText("Setup a new remote storage");
        rdNewRepo.setName("rdNewRepo"); // NOI18N
        rdNewRepo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNewRepoActionPerformed(evt);
            }
        });

        rdExistingRepo.setFont(rdExistingRepo.getFont().deriveFont(rdExistingRepo.getFont().getStyle() | java.awt.Font.BOLD));
        rdExistingRepo.setText("Connect to an existing remote storage");
        rdExistingRepo.setName("rdExistingRepo"); // NOI18N

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+2));
        jLabel1.setText("Create or connect?");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Use this wizard to create a new remote storage or connect to an ");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("existing one. Follow the instructions and click 'Next'.");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(jLabel4.getFont());
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Choose this option if you want to initialize a new remote storage.");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(jLabel5.getFont());
        jLabel5.setText("You can use any type of storage, e.g. FTP, Amazon S3 or IMAP.");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(jLabel6.getFont());
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Choose this option if you want to sync your files  with an");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setFont(jLabel7.getFont());
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("existing remote storage.");
        jLabel7.setName("jLabel7"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(rdExistingRepo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(rdNewRepo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addComponent(rdNewRepo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(40, 40, 40)
                .addComponent(rdExistingRepo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdNewRepoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNewRepoActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_rdNewRepoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton rdExistingRepo;
    private javax.swing.JRadioButton rdNewRepo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void clean() { }

    
    @Override
    public boolean check() {        
        return true;
    }
}