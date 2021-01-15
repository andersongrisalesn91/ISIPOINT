/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 *   Contributors:
 *     James Sutton - Updating in new Sample App
 */
package co.com.mirecarga.vendedor.mqtt;


import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>MQTTConnections</code> is a singleton class which stores all the connection objects
 * in one central place so they can be passed between activities using a client handle.
 */
public class MQTTConnections {

    /** Singleton instance of <code>MQTTConnections</code>**/
    private static MQTTConnections instance = null;

    /** List of {@link MQTTConnection} object **/
    private HashMap<String, MQTTConnection> connections = null;

    /** {@link Persistence} object used to save, delete and restore connections**/
    private Persistence persistence = null;

    /**
     * Create a MQTTConnections object
     * @param context Applications context
     */
    private MQTTConnections(Context context){
        connections = new HashMap<String, MQTTConnection>();

        // If there is state, attempt to restore it
        persistence = new Persistence(context);
        try {
            List<MQTTConnection> connectionList = persistence.restoreConnections(context);
            for(MQTTConnection connection : connectionList) {
                System.out.println("MQTTConnection was persisted.." + connection.handle());
                connections.put(connection.handle(), connection);
            }
        } catch (PersistenceException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns an already initialised instance of <code>MQTTConnections</code>, if MQTTConnections has yet to be created, it will
     * create and return that instance.
     * @param context The applications context used to create the <code>MQTTConnections</code> object if it is not already initialised
     * @return <code>MQTTConnections</code> instance
     */
    public synchronized static MQTTConnections getInstance(Context context){
        if(instance ==  null){
            instance = new MQTTConnections(context);
        }
        return instance;
    }

    /**
     * Finds and returns a {@link MQTTConnection} object that the given client handle points to
     * @param handle The handle to the {@link MQTTConnection} to return
     * @return a connection associated with the client handle, <code>null</code> if one is not found
     */
    public MQTTConnection getConnection(String handle){
        return connections.get(handle);
    }

    /**
     * Adds a {@link MQTTConnection} object to the collection of connections associated with this object
     * @param connection {@link MQTTConnection} to add
     */
    public void addConnection(MQTTConnection connection){
        connections.put(connection.handle(), connection);
        try{
            persistence.persistConnection(connection);
        } catch (PersistenceException e){
            // @todo Handle this error more appropriately
            //error persisting well lets just swallow this
            e.printStackTrace();
        }

    }

// --Commented out by Inspection START (12/10/2016, 10:21):
//    /**
//     * Create a fully initialised <code>MqttAndroidClient</code> for the parameters given
//     * @param context The Applications context
//     * @param serverURI The ServerURI to connect to
//     * @param clientId The clientId for this client
//     * @return new instance of MqttAndroidClient
//     */
//    public MqttAndroidClient createClient(Context context, String serverURI, String clientId){
//        return new MqttAndroidClient(context, serverURI, clientId);
//    }
// --Commented out by Inspection STOP (12/10/2016, 10:21)

    /**
     * Get all the connections associated with this <code>MQTTConnections</code> object.
     * @return <code>Map</code> of connections
     */
    public Map<String, MQTTConnection> getConnections(){
        return connections;
    }

    /**
     * Removes a connection from the map of connections
     * @param connection connection to be removed
     */
    public void removeConnection(MQTTConnection connection){
        connections.remove(connection.handle());
        persistence.deleteConnection(connection);
    }


    /**
     * Updates an existing connection within the map of
     * connections as well as in the persisted model
     * @param connection connection to be updated.
     */
    public void updateConnection(MQTTConnection connection){
        connections.put(connection.handle(), connection);
        persistence.updateConnection(connection);
    }


}
