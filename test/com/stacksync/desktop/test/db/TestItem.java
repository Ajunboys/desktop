package com.stacksync.desktop.test.db;

import com.stacksync.desktop.Constants;
import com.stacksync.desktop.config.ConfigNode;
import com.stacksync.desktop.db.DatabaseHelper;
import com.stacksync.desktop.db.models.CloneItem;
import com.stacksync.desktop.db.models.CloneItemVersion;
import com.stacksync.desktop.db.models.CloneWorkspace;
import com.stacksync.desktop.exceptions.ConfigException;
import com.stacksync.desktop.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 *
 * @author cotes
 */
public class TestItem {
    
    private static DatabaseHelper databaseHelper;
    private static EntityManager entityManager;
    
    @BeforeClass
    public static void setUpClass() throws ConfigException {
        
        databaseHelper = DatabaseHelper.getInstance();
        
        File configFolder = new File("database_test");
        // Create and copy config file
        File configFile = prepareConfigFile();
        databaseHelper.initializeDatabase(configFolder.getAbsolutePath(), getDBConfigNode(configFile));
        entityManager = databaseHelper.getEntityManager();
    }
    
    public static File prepareConfigFile() {
        File configFolder = new File("database_test");
        configFolder.mkdir();
        
        File configFile = new File(configFolder.getAbsoluteFile() + File.separator + Constants.CONFIG_FILENAME);
        
        InputStream is = null;
        try {
            is = TestItem.class.getResourceAsStream(Constants.CONFIG_DEFAULT_FILENAME);

            FileUtil.writeFile(is, configFile);
        } catch (IOException e) {
            assert false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                assert false;
            }
        }
        
        return configFile;
    }
    
    public static ConfigNode getDBConfigNode(File configFile){
        
        ConfigNode node = null;
        InputStream configStream = null;
        try {
            configStream = new FileInputStream(configFile);
            node = parseConfigFile(configStream);
        } catch (FileNotFoundException ex) {
            assert false;
        } finally {
            try {
                if (configStream != null) {
                    configStream.close();
                }
            } catch (IOException ex) { }
        }
        
        return node;
    }
    
    public static ConfigNode parseConfigFile(InputStream configStream) {
        
        ConfigNode node = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(configStream);
            node = new ConfigNode(doc.getDocumentElement());
            node = node.findChildByName("database");
        } catch (Exception e) {
            assert false;
        }
        
        return node;
    }
    
    @AfterClass
    public static void tearDownClass() {
        File configFolder = new File("database_test");
        try {
            FileUtils.deleteDirectory(configFolder);
        } catch (IOException ex) { }
    }
    
    @Before
    public void setUp() {
        CloneWorkspace workspace = new CloneWorkspace();
        workspace.setDefaultWorkspace(true);
        workspace.setId("wp1");
        workspace.setName("default");
        workspace.setLocalLastUpdate(1);
        workspace.setRemoteRevision(1);
        workspace.setOwner("me");
        workspace.setEncrypted(false);
        workspace.setPathWorkspace("/");
        persist(workspace);
        
        CloneItem item = new CloneItem();
        item.setId(1L);
        item.setFolder(true);
        item.setMimetype("folder");
        item.setStatus(CloneItem.Status.NEW);
        item.setSyncStatus(CloneItem.SyncStatus.UPTODATE);
        item.setUsingTempId(false);
        item.setWorkspace(workspace);
        ArrayList<CloneItemVersion> versions = new ArrayList<CloneItemVersion>();
        
        CloneItemVersion version1 = new CloneItemVersion();
        version1.setVersion(1);
        version1.setSize(1);
        version1.setChecksum(1);
        version1.setItem(item);
        versions.add(version1);
        
        CloneItemVersion version2 = new CloneItemVersion();
        version2.setVersion(2);
        version2.setSize(2);
        version2.setChecksum(2);
        version2.setItem(item);
        versions.add(version2);
        
        CloneItemVersion version3 = new CloneItemVersion();
        version3.setVersion(3);
        version3.setSize(3);
        version3.setChecksum(3);
        version3.setItem(item);
        versions.add(version3);
        
        item.setVersions(versions);
        persist(item);
        
    }
    
    @After
    public void tearDown() {
        CloneWorkspace workspace = entityManager.find(CloneWorkspace.class, "wp1");
        CloneItem item = entityManager.find(CloneItem.class, 1L);
        
        entityManager.getTransaction().begin();
        entityManager.remove(item);
        entityManager.remove(workspace);
        entityManager.getTransaction().commit();
    }
    
    @Test
    public void createItemWithVersions() {
        System.out.println("Hola");
    }
    
    @Test
    public void getItemById() {
        CloneItem item = databaseHelper.getFileOrFolder(1L);
        assert item.getId().equals(1L);
    }
    
    public void persist(Object o){
        entityManager.getTransaction().begin();
        entityManager.persist(o);
        entityManager.getTransaction().commit();
    }

}