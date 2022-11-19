//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2021.10.15 �s 02:52:13 PM BRT 
//


package com.squallsoft.api.nesdb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.squallsoft.jtag.nesdb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.squallsoft.jtag.nesdb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Database }
     * 
     */
    public Database createDatabase() {
        return new Database();
    }

    /**
     * Create an instance of {@link Board }
     * 
     */
    public Board createBoard() {
        return new Board();
    }

    /**
     * Create an instance of {@link Database.Game }
     * 
     */
    public Database.Game createDatabaseGame() {
        return new Database.Game();
    }

    /**
     * Create an instance of {@link Database.Game.Arcade }
     * 
     */
    public Database.Game.Arcade createDatabaseGameArcade() {
        return new Database.Game.Arcade();
    }

    /**
     * Create an instance of {@link Database.Game.Arcade.Properties }
     * 
     */
    public Database.Game.Arcade.Properties createDatabaseGameArcadeProperties() {
        return new Database.Game.Arcade.Properties();
    }

    /**
     * Create an instance of {@link Database.Game.Cartridge }
     * 
     */
    public Database.Game.Cartridge createDatabaseGameCartridge() {
        return new Database.Game.Cartridge();
    }

    /**
     * Create an instance of {@link Database.Game.Cartridge.Properties }
     * 
     */
    public Database.Game.Cartridge.Properties createDatabaseGameCartridgeProperties() {
        return new Database.Game.Cartridge.Properties();
    }

    /**
     * Create an instance of {@link Database.Game.Peripherals }
     * 
     */
    public Database.Game.Peripherals createDatabaseGamePeripherals() {
        return new Database.Game.Peripherals();
    }

    /**
     * Create an instance of {@link Rom }
     * 
     */
    public Rom createRom() {
        return new Rom();
    }

    /**
     * Create an instance of {@link Ram }
     * 
     */
    public Ram createRam() {
        return new Ram();
    }

    /**
     * Create an instance of {@link br.com.squallsoft.jtag.nesdb.Ic.Pin }
     * 
     */
    public com.squallsoft.api.nesdb.Ic.Pin createIcPin() {
        return new com.squallsoft.api.nesdb.Ic.Pin();
    }

    /**
     * Create an instance of {@link Board.Chip }
     * 
     */
    public Board.Chip createBoardChip() {
        return new Board.Chip();
    }

    /**
     * Create an instance of {@link Board.Cic }
     * 
     */
    public Board.Cic createBoardCic() {
        return new Board.Cic();
    }

    /**
     * Create an instance of {@link Board.Pad }
     * 
     */
    public Board.Pad createBoardPad() {
        return new Board.Pad();
    }

    /**
     * Create an instance of {@link Database.Game.Arcade.Properties.Property }
     * 
     */
    public Database.Game.Arcade.Properties.Property createDatabaseGameArcadePropertiesProperty() {
        return new Database.Game.Arcade.Properties.Property();
    }

    /**
     * Create an instance of {@link Database.Game.Cartridge.Properties.Property }
     * 
     */
    public Database.Game.Cartridge.Properties.Property createDatabaseGameCartridgePropertiesProperty() {
        return new Database.Game.Cartridge.Properties.Property();
    }

    /**
     * Create an instance of {@link Database.Game.Peripherals.Device }
     * 
     */
    public Database.Game.Peripherals.Device createDatabaseGamePeripheralsDevice() {
        return new Database.Game.Peripherals.Device();
    }

}
