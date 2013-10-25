package com.myPackage.server.database;

/**
La clase PersistenceManagerFactory
Todas las solicitudes que utilizan el almac�n de datos crean una nueva instancia de la clase PersistenceManager. 
Para ello, utilizan una instancia de la clase PersistenceManagerFactory.
Una instancia de PersistenceManagerFactory tarda alg�n tiempo en inicializarse. Afortunadamente, 
solo se necesita una instancia para cada aplicaci�n, y esta instancia se puede almacenar en una variable est�tica 
que pueden utilizar varias solicitudes y clases. Una forma sencilla de realizar este procedimiento es 
mediante la creaci�n de una clase envoltorio "singleton" para la instancia est�tica.
*/

import java.io.Serializable;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF implements Serializable{
 
    /**  
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");
    	 											
        private PMF() {}

        public static PersistenceManagerFactory get() {
            return pmfInstance;
        }
		 
} 
 