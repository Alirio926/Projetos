/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.adapter.rxtx;


import gnu.io.*;
import java.util.TooManyListenersException;

import java.io.*;

import java.util.ArrayList;
import java.util.Enumeration;
/**
 *
 * @author Administrador
 */
public class SerialHelper {

    private SerialPort serialPort;
    private OutputStream outStream;
    private InputStream inStream;
    private DataInput dataInput;
    private DataOutput dataOutput;

    /**
     * \brief List the available serial ports
     *
     * \return Array of string for the available serial port names
     */
    public static String[] listSerialPorts() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        ArrayList portList = new ArrayList();
        String portArray[] = null;
        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            if (port.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portList.add(port.getName());
            }
        }
        portArray = (String[]) portList.toArray(new String[0]);
        return portArray;
    }

    /**
     * \brief Connect to the selected serial port with 57600bps-8N1 mode
     */
    public void connect(String portName) throws IOException {
        try {
            // Obtain a CommPortIdentifier object for the port you want to open
            CommPortIdentifier portId =
                    CommPortIdentifier.getPortIdentifier(portName);

            // Get the port's ownership
            serialPort = (SerialPort) portId.open("UsartSend", 5000);

            // Set the parameters of the connection.
            setSerialPortParameters();
            // Open the input and output streams for the connection.
            // If they won't open, close the port before throwing an
            // exception.
            
            //dataOutput = (DataOutput) serialPort.getOutputStream();
            outStream = serialPort.getOutputStream();
            //dataInput = (DataInput) serialPort.getInputStream();
            inStream = serialPort.getInputStream();
        } catch (NoSuchPortException e) {
            throw new IOException(e.getMessage());
        } catch (PortInUseException e) {
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            serialPort.close();
            throw e;
        }
    }

    /**
     * \brief Get the serial port input stream
     * \return The serial port input stream
     * @return 
     */
    public InputStream getSerialInputStream() {
        return inStream;
    }
    public DataInput getSerialDataInput(){
        return dataInput;
    }
    /**
     * \brief Get the serial port output stream
     * \return The serial port output stream
     * @return 
     */
    public OutputStream getSerialOutputStream() {
        return outStream;
    }
    public DataOutput getSerialDataOutput(){
        return dataOutput;
    }
    /**
     * \brief Sets the serial port parameters to 57600bps-8N1
     */
    protected void setSerialPortParameters() throws IOException {

        final int baudRate = 115200; // 9600bps

        try {
            // Set serial port to 9600bps-8N1..my favourite
            serialPort.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }

    /**
     * \brief Register listener for data available event
     *
     * @param dataAvailableListener The data available listener
     */
    public void addDataAvailableListener(SerialPortEventListener dataAvailableListener)
            throws TooManyListenersException {
        // Add the serial port event listener
        serialPort.addEventListener(dataAvailableListener);
        serialPort.notifyOnDataAvailable(true);
    }

    /**
     * \brief Disconnect the serial port
     */
    public void disconnect() {
        if (serialPort != null) {
            try {
                // close the i/o streams.
                
                outStream.close();
                inStream.close();
            } catch (IOException ex) {
                // don't care
            }
            // Close the port.
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * \brief Private inner class to test and debug the SerialHelper class
     */
  
}
