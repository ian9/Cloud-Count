/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud.count;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.jcr.*;
import javax.swing.JOptionPane;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;

/**
 *
 * @author Thomas
 * A set of functions for use with JackRabbit
 * This class is built to be called all over the project
 * for varied reasons. It will mainly be used for 
 * initilizing the Jackrabbit connection and the dealing
 * with the server. 
 */
public class JackRabbitHandler {
    
    private static Session session; //the login session
    private static Repository repo;//the repo
    private static SimpleCredentials credientials;//the credientials
    private static GregorianCalendar cal;//a calendar for getting dates
    private static String list;//a list of all of the data in the repo 
    ArrayList<String[]> table;
    
    /**
     * Makes the first connection and intilizes all of the 
     * other methods.
     * @param host The host server for the repo
     * @param user The user who is making the request
     * @param password The password of the user making the request
     */
    public JackRabbitHandler(String host, String user, String password){
        cal = new GregorianCalendar(); 
       try{
           initConnection(host,user,password);
       }
       catch(Exception e){
           JOptionPane.showMessageDialog(null,"There has been an error in connecting to the "
                   + "server");
           System.out.println("There has been an error in connecting to the "
                   + "server");
       }
       list = "";
       table = new ArrayList();
    }
    
    public void initConnection(String host, String user, String password)
    throws Exception{
        repo = new URLRemoteRepository(host);
        
        credientials = new SimpleCredentials(user, 
                password.toCharArray());
        
        session = repo.login(credientials);
        
        
    }
    /**
     * This version searches for documents starting at the root.
     * @return A matrix of documents that contains the values of 
     * the table. The returned ArrayList is a CSV.
     * The properties are sorted in the same order the table
     * expects them to be in. For clarities sake the order is:
     * updateDate, user, label(path), name. They come back sorted by updateDate.
     * Note: no actual data is returned just a table representation of the documents
     * in the repo.
     * 
     * @author Thomas
     */
    public ArrayList<String[]> getDocumentTable(){
            list = "";
            try{
                iterate(session.getRootNode());
            }catch(Exception e){
                System.out.println("Error in populating table in handler");
            }
            System.out.println(list);
            String[] rows = list.split("\n");
            System.out.println(rows.length);
            table = new ArrayList();
            int realRows =0;
            for(int i=0; i<rows.length; i++){
                String[] cols = rows[i].split(",");
                System.out.println("clos:"+cols.length);
                if(cols.length==4){
                    table.add(new String[4]);
                    table.get(realRows)[0]= cols[3];//date
                    table.get(realRows)[1]= cols[0];//user
                    table.get(realRows)[2]= cols[2];//path(label)
                    table.get(realRows)[3]= cols[1];//name
                    realRows++;
                }
            }
            for(String[] s : table){
                for(String ss : s){
                    System.out.print(ss+" ");
                }
                System.out.println();
                
            }
                return table;
    } 
     /**
     * This version searches for files from n.
     * @return A matrix of documents that contains the values of 
     * the table. The properties are sorted in the same order the table
     * expects them to be in. For clarities sake the order is:
     * updateDate, user, label(path), name. They come back sorted by updateDate.
     * Note: no actual data is returned just a table representation of the documents
     * in the repo.
     * @param n The node with respect to generate the table from
     * @author Thomas
     */
    public  ArrayList<String[]> getDocumentTable(Node n){
       list = "";
            try{
                iterate(n);
            }catch(Exception e){
                System.out.println("Error in populating table in handler");
            }
            System.out.println(list);
            String[] rows = list.split("\n");
            System.out.println(rows.length);
            table = new ArrayList();
            int realRows =0;
            for(int i=0; i<rows.length; i++){
                String[] cols = rows[i].split(",");
                System.out.println("clos:"+cols.length);
                if(cols.length==4){
                    table.add(new String[4]);
                    table.get(realRows)[0]= cols[3];//date
                    table.get(realRows)[1]= cols[0];//user
                    table.get(realRows)[2]= cols[2];//path(label)
                    table.get(realRows)[3]= cols[1];//name
                    realRows++;
                }
            }
           return table;
    } 
    /**
     * 
     * Downloads the selected file to the selected location.
     * Works regardless of the system it is run on.
     * @param path The path to the node that you are downloading
     * @param downloadLocation The location to which you are downloading
     * @author Thomas
     */
    public  void download(String path, String downloadLocation){
        System.out.println(path);
        try{
            Node item = getItem(path);
            System.out.println(item.getName());
            PropertyIterator pi = item.getProperties();
            while(pi.hasNext()){
                Property p = pi.nextProperty();

                String name = p.getName();
                if(!name.equals("jcr:data"))
                    continue;
                Binary bin = p.getValue().getBinary();
                InputStream inp = bin.getStream();
                
                FileOutputStream fop;
                if(System.getProperty("os.name").contains("Windows"))
                    fop = new FileOutputStream(downloadLocation+"\\\\" + item.getName());
                else
                    fop = new FileOutputStream(downloadLocation+"/" + item.getName());

                final int BUFSIZ = 512;

                byte[] buf = new byte[BUFSIZ];

                int len = 0;

                while ((len = inp.read(buf)) > 0) {
                    fop.write(buf, 0, len);
                }

                inp.close();

                fop.flush();
                fop.close();
                JOptionPane.showMessageDialog(null, "Download has finised");
            }
        }catch(Exception e){
            System.out.println("error in dl "+e);
            JOptionPane.showMessageDialog(null, "Download has failed");
        }
       

            
        }
    
    private  Node getItem(String path){
        path = path.substring(1);
        try{
            Node root = session.getRootNode();
            return root.getNode(path);
        }catch (Exception e){
            System.out.println("The item does not exist");
        }
        return null;
    }
    
    /**
     * Deletes a node in the repo
     * @param path The path to the node to be deleted
     * @return 
     */
    public boolean deleteItem(String path){
        if(path.charAt(0)=='/')
        path = path.substring(1);
        try{
            Node root = session.getRootNode();
            root.getNode(path).remove();
            session.save();
        }catch (Exception e){
            System.out.println("The item does not exist");
            return false;
        }
        return true;
    }
    
    //tests to see if a document is a duplicate
    private int duplicate(String duplicatePath,Node root)throws Exception{
            if(root.hasNode(duplicatePath)){
              int choice = JOptionPane.showConfirmDialog(null,"The file already exists are you sure"
                      + "you wish to procede with upload as it will be overwritten?",
                      "confirm upload",
                JOptionPane.YES_NO_OPTION);
              if(choice == 0){
                  deleteItem(duplicatePath);
                  System.out.println(duplicatePath+" deleted for update");
                  return 0;//they said yes
              }
              else
                return 1;//they said no
            }
            
            return 2;//not a duplicate
    }
    
    private void iterate(Node node) throws Exception{
        //System.out.println("node:"+node.getPath());
        if(node.getName().equals("jcr:system"))
            return;
           
        PropertyIterator ps = node.getProperties();
        while(ps.hasNext()){
            Property p = ps.nextProperty();
            String name = p.getName();
            if(name.equals("jcr:data"))
                list= list + (node.getName()+ ","+ node.getPath()+",");
            else if(name.equals("jcr:updateDate"))
                list= list + (p.getValue());
            else if(name.equals("jcr:user"))
                list= list + (p.getValue()+ ",");
        }
        list = list + ("\n");//seperator signifying a new node
        NodeIterator nodes = node.getNodes();
        while(nodes.hasNext())
            iterate(nodes.nextNode());           
    }
    
    /**
     * A way to add items that uses a dynamic path creation.
     * The path does not have to exist in any form before the item is added
     * and it will exist in its entierty once the item has been 
     * added succesfuly. The contense of the document are bound to the
     * property 'jcr:data' in a child node to the path with the name equal to the
     * document name. In addition the 'jcr:updateDate' and 'jcr:user' properties
     * are set to the date of the update and the user who made the update
     * respectively. 
     * @param path the path of the item to add
     * @param documentName the item to add
     * @return ?has it been added
     * @author Thomas
     */
    public boolean upload(String path, String documentName){
        String user = credientials.getUserID();
        cal = new GregorianCalendar();//reset the date info
        String date = ""+cal.get(Calendar.DAY_OF_WEEK)+"\\"+
                cal.get(Calendar.MONTH)+"\\"+cal.get(Calendar.YEAR);
        System.out.println(user);
        
        try {//make the path and add the item
            // Get the root node and construct the initial xpath: course/docs/2011
            Node root = session.getRootNode();
            Node newNode = root;
            String[] docPath;
            
            //split on the dirs
            if(System.getProperty("os.name").contains("Windows"))
                 docPath = documentName.split("\\\\");//damn windows and its diffrent slash
            else
                docPath = documentName.split("/");
            
            //make sure that the item doesn't already exist and if it does confirm deletion
            String duplicatePath = "";//the path to test if it is a duplicate
            if(path.length()==1 || path.charAt(path.length() -1 ) == '/')
                duplicatePath = path.substring(1)+docPath[docPath.length-1];
            else
                duplicatePath = path.substring(1)+"/"+docPath[docPath.length-1];
            System.out.println(duplicatePath);
            if(duplicate(duplicatePath,root)==1)//check for and deal with a duplicate
                return false;
            
            //tease out the path
            try{//does the path exist?
                if(!path.equals("/")){
                    path = path.substring(1);
                    newNode = root.getNode(path);//test to see if the node on the path already exists
                } 
            }catch(Exception e){//catch a nonexistant path and build that path
                String[] pathArray = path.split("/");//break the path up
                int i = 0;//a counter
                String testPath = pathArray[i];//the first position
                System.out.println(root.hasNode(testPath));
                while(root.hasNode(testPath)){//while the test path exists
                    newNode = root.getNode(testPath);//get the node furthest down the path
                    System.out.println(testPath);
                    i++;//move to next piece of the path
                    testPath+= ("/"+pathArray[i]);//add the next node to the path
                }

                for(; i < pathArray.length; i++){//build up the rest of the path
                    newNode = newNode.addNode(pathArray[i]);
                }
                newNode = root.getNode(path);//the path exists now so get it
           }//path as been created and newNode is at the bottom of the path
            
           //add the new node
           Node item = newNode.addNode(docPath[docPath.length-1]);
           //add the item
           FileInputStream fip = new FileInputStream(documentName); 
           // Convert this to a binary document
           ValueFactory vf = session.getValueFactory();
           Binary doc = vf.createBinary(fip);
           // Bind the document to the item node with attribute jcr:data
           item.setProperty("jcr:data", doc);
           
           //Set the other attrobutes
           item.setProperty("jcr:updateDate", date);
           item.setProperty("jcr:user", user);
           
           fip.close();//close the stream and hope that everything worked
           session.save();
           JOptionPane.showMessageDialog(null, "Upload has been succesful");
        }catch(Exception e){
            System.out.println("There has been an error "+e);
            JOptionPane.showMessageDialog(null, "Upload has failed "+e);
            return false;
        }
        return true;
    }
    
    public void logOff(){
        session.logout();
    }
    
}