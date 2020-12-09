/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.cliente.common;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 *
 * @author alirio
 */
public class Jaris {
    private JarFile jf;
    public Jaris(String jarFilename) throws Exception
    {
        try{
            jf = new JarFile(jarFilename);
        }catch(Exception e){
            jf = null;
            throw(e);
        }        
    }
    public InputStream getInputStream(String matchString) throws Exception
    {
        ZipEntry ze = null;
        try{
            Enumeration resources = jf.entries();
            while(resources.hasMoreElements()){
                JarEntry je = (JarEntry) resources.nextElement();
                if(je.getName().matches(".*\\"+matchString)){
                    String filename = je.getName();
                    ze = jf.getEntry(filename);
                }                
            }
        }catch(Exception e){
            throw(e);
        }
        InputStream is = jf.getInputStream(ze);
        return is;
    }
}