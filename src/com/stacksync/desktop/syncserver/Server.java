/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stacksync.desktop.syncserver;

/**
 *
 * @author gguerrero
 */
import com.stacksync.commons.models.Device;
import com.stacksync.desktop.Environment;
import com.stacksync.desktop.config.Config;
import com.stacksync.desktop.db.models.CloneWorkspace;
import com.stacksync.desktop.exceptions.ConfigException;
import com.stacksync.desktop.repository.Update;
import com.stacksync.commons.models.DeviceInfo;
import com.stacksync.commons.models.ItemMetadata;
import com.stacksync.commons.models.User;
import com.stacksync.commons.models.Workspace;
import com.stacksync.commons.omq.ISyncService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import omq.common.broker.Broker;
import omq.common.util.Serializers.JavaImp;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class Server {

    private final Logger logger = Logger.getLogger(Server.class.getName());
    private final Config config = Config.getInstance();
    private ISyncService syncServer;
    private Broker broker;
    private Map<Long, Workspace> rWorkspaces;
    private int i;

    public ISyncService getSyncServer() {
        return syncServer;
    }

    public Server(Broker broker) throws Exception {
        //env.setProperty(ParameterQueue.DEBUGFILE, "c:\\middlewareDebug");

        this.broker = broker;
        this.syncServer = this.broker.lookup(ISyncService.class.getSimpleName(), ISyncService.class);
        this.rWorkspaces = new HashMap<Long, Workspace>();
        this.i = 0;
    }

    public String getRequestId() {
        return config.getDeviceName() + "-" + (new Date()).getTime();
    }

    public List<Update> getChanges(String cloudId, CloneWorkspace workspace) {
        List<Update> updates = new ArrayList<Update>();

        String requestId = getRequestId();
        Workspace rWorkspace = rWorkspaces.get(workspace.getId());
        
        User user = new User();
        user.setCloudId(cloudId);

        List<ItemMetadata> items = syncServer.getChanges(requestId, user, rWorkspace);
        for (ItemMetadata item : items) {
            Update update = Update.parse(item, workspace);
            updates.add(update);
        }

        return updates;
    }

    public List<CloneWorkspace> getWorkspaces(String cloudId) throws IOException {
        String requestId = getRequestId();
        List<CloneWorkspace> workspaces = new ArrayList<CloneWorkspace>();

        List<Workspace> remoteWorkspaces = syncServer.getWorkspaces(cloudId, requestId);
        if (remoteWorkspaces.isEmpty()) {
            throw new IOException("get_workspaces hasn't workspaces");
        }

        for (Workspace rWorkspace : remoteWorkspaces) {
            CloneWorkspace workspace = new CloneWorkspace(rWorkspace);
            workspaces.add(workspace);
            rWorkspaces.put(workspace.getId(), rWorkspace);
        }

        return workspaces;
    }
    
    public void updateDevice(String cloudId) {
        
        String requestId = getRequestId();
        long deviceId;
        
        Environment env = Environment.getInstance();
        String osInfo = env.getOperatingSystem().toString() + "-" + env.getArchitecture();
        
        DeviceInfo device = new DeviceInfo(config.getDeviceId(), config.getDeviceName(),
                osInfo, null, null);
        
        User user = new User();
        user.setCloudId(cloudId);
        
        deviceId = syncServer.updateDevice(requestId, user, device);
        logger.debug("Obtained deviceId: "+deviceId);
        
        if (deviceId != -1) {
            try {
                // Set registerId in config
                config.setDeviceId(deviceId);
                config.save();
                // Something else?

                logger.info("Device registered");
            } catch (ConfigException ex) {
                
            }
        } else {
            // What to do here??
            logger.error("Device not registered!!");
        }
        
    }

    public void commit(String cloudId, CloneWorkspace workspace, List<ItemMetadata> commitItems) throws IOException {
        String requestId = getRequestId();
        Workspace rWorkspace = rWorkspaces.get(workspace.getId());

        User user = new User();
        user.setCloudId(cloudId);
        
        Device device = new Device(config.getDeviceId());
        
        syncServer.commit(requestId, user, rWorkspace, device, commitItems);
        logger.info(" [x] Sent '" + commitItems + "'");
    }
    
    public void createShareProposal(String cloudId, List<String> emails, String folderName) {
        
        String requestId = getRequestId();
        logger.info("Sending share proposal.");
        Long newWorkspaceId = syncServer.createShareProposal(cloudId, requestId, emails, cloudId);
        if (newWorkspaceId == -1) {
            logger.info("Proposal not accepted.");
            // Show message error.
        } else {
            logger.info("Proposal accepted. New workspace: "+newWorkspaceId);
            
        }
        
    }
    
    public void commit(String cloudId, String requestId, CloneWorkspace workspace, List<ItemMetadata> commitItems) throws IOException {
        Workspace rWorkspace = rWorkspaces.get(workspace.getId());

        User user = new User();
        user.setCloudId(cloudId);
        
        Device device = new Device(config.getDeviceId());
        
        syncServer.commit(requestId, user, rWorkspace, device, commitItems);
        saveLog(commitItems);
        logger.info(" [x] Sent '" + commitItems + "'");
    }

    private void saveLog(List<ItemMetadata> commitItems) {
        String debugPath = "test";
        if (debugPath.length() > 0) {
            try {

                File outputFolder = new File(debugPath + File.separator + "Client");
                outputFolder.mkdirs();

                JavaImp serializer = new JavaImp();
                byte[] bytes = serializer.serialize(commitItems);

                File outputFileContent = new File(outputFolder.getAbsoluteFile() + File.separator + "client-files-" + i);
                FileOutputStream outputStream = new FileOutputStream(outputFileContent);
                IOUtils.write(bytes, outputStream);
                outputStream.close();
                this.i++;
            } catch (Exception ex) {
                //java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
