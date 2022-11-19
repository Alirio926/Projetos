//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2021.10.15 �s 02:52:13 PM BRT 
//


package com.squallsoft.api.nesdb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="game" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="peripherals" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="device" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="type" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;choice>
 *                     &lt;element name="cartridge" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="board" type="{}board"/>
 *                               &lt;element name="properties" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="property" maxOccurs="unbounded">
 *                                           &lt;complexType>
 *                                             &lt;complexContent>
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                                                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                                               &lt;/restriction>
 *                                             &lt;/complexContent>
 *                                           &lt;/complexType>
 *                                         &lt;/element>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                             &lt;attribute name="system">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="Famicom"/>
 *                                   &lt;enumeration value="NES-NTSC"/>
 *                                   &lt;enumeration value="NES-PAL"/>
 *                                   &lt;enumeration value="NES-PAL-A"/>
 *                                   &lt;enumeration value="NES-PAL-B"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/attribute>
 *                             &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                             &lt;attribute name="prototype" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                             &lt;attribute name="dumper" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                             &lt;attribute name="datedumped" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                             &lt;attribute name="dump" default="ok">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="ok"/>
 *                                   &lt;enumeration value="bad"/>
 *                                   &lt;enumeration value="unknown"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/attribute>
 *                             &lt;attribute name="crc" type="{}crc" />
 *                             &lt;attribute name="sha1" type="{}sha1" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="arcade" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="board" type="{}board"/>
 *                               &lt;element name="properties" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="property" maxOccurs="unbounded">
 *                                           &lt;complexType>
 *                                             &lt;complexContent>
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                                                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                                               &lt;/restriction>
 *                                             &lt;/complexContent>
 *                                           &lt;/complexType>
 *                                         &lt;/element>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                             &lt;attribute name="system" use="required">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="VS-Unisystem"/>
 *                                   &lt;enumeration value="VS-Dualsystem"/>
 *                                   &lt;enumeration value="Playchoice-10"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/attribute>
 *                             &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                             &lt;attribute name="ppu">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="RP2C03B"/>
 *                                   &lt;enumeration value="RP2C03G"/>
 *                                   &lt;enumeration value="RP2C04-0001"/>
 *                                   &lt;enumeration value="RP2C04-0002"/>
 *                                   &lt;enumeration value="RP2C04-0003"/>
 *                                   &lt;enumeration value="RP2C04-0004"/>
 *                                   &lt;enumeration value="RC2C03B"/>
 *                                   &lt;enumeration value="RC2C03C"/>
 *                                   &lt;enumeration value="RC2C05-01"/>
 *                                   &lt;enumeration value="RC2C05-02"/>
 *                                   &lt;enumeration value="RC2C05-03"/>
 *                                   &lt;enumeration value="RC2C05-04"/>
 *                                   &lt;enumeration value="RC2C05-05"/>
 *                                   &lt;enumeration value="RP2C07"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/attribute>
 *                             &lt;attribute name="dumper" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                             &lt;attribute name="datedumped" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                             &lt;attribute name="dump" default="ok">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                                   &lt;enumeration value="ok"/>
 *                                   &lt;enumeration value="bad"/>
 *                                   &lt;enumeration value="unknown"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/attribute>
 *                             &lt;attribute name="crc" type="{}crc" />
 *                             &lt;attribute name="sha1" type="{}sha1" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="altname" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="subclass" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="catalog" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="publisher" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="developer" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="portdeveloper" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="region" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="players" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                 &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}token" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}token" fixed="1.0" />
 *       &lt;attribute name="conformance" default="strict">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="strict"/>
 *             &lt;enumeration value="loose"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="author" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="agent" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "game"
})
@XmlRootElement(name = "database")
public class Database {

    protected List<Database.Game> game;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String version;
    @XmlAttribute(name = "conformance")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String conformance;
    @XmlAttribute(name = "author")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String author;
    @XmlAttribute(name = "agent")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String agent;
    @XmlAttribute(name = "timestamp")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String timestamp;

    /**
     * Gets the value of the game property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the game property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGame().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Database.Game }
     * 
     * 
     */
    public List<Database.Game> getGame() {
        if (game == null) {
            game = new ArrayList<Database.Game>();
        }
        return this.game;
    }

    /**
     * Obt�m o valor da propriedade version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    /**
     * Define o valor da propriedade version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obt�m o valor da propriedade conformance.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConformance() {
        if (conformance == null) {
            return "strict";
        } else {
            return conformance;
        }
    }

    /**
     * Define o valor da propriedade conformance.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConformance(String value) {
        this.conformance = value;
    }

    /**
     * Obt�m o valor da propriedade author.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Define o valor da propriedade author.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Obt�m o valor da propriedade agent.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgent() {
        return agent;
    }

    /**
     * Define o valor da propriedade agent.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgent(String value) {
        this.agent = value;
    }

    /**
     * Obt�m o valor da propriedade timestamp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Define o valor da propriedade timestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }


    /**
     * <p>Classe Java de anonymous complex type.
     * 
     * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="peripherals" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="device" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="type" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;choice>
     *           &lt;element name="cartridge" maxOccurs="unbounded">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="board" type="{}board"/>
     *                     &lt;element name="properties" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="property" maxOccurs="unbounded">
     *                                 &lt;complexType>
     *                                   &lt;complexContent>
     *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                                       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                                     &lt;/restriction>
     *                                   &lt;/complexContent>
     *                                 &lt;/complexType>
     *                               &lt;/element>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/sequence>
     *                   &lt;attribute name="system">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="Famicom"/>
     *                         &lt;enumeration value="NES-NTSC"/>
     *                         &lt;enumeration value="NES-PAL"/>
     *                         &lt;enumeration value="NES-PAL-A"/>
     *                         &lt;enumeration value="NES-PAL-B"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/attribute>
     *                   &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                   &lt;attribute name="prototype" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                   &lt;attribute name="dumper" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                   &lt;attribute name="datedumped" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                   &lt;attribute name="dump" default="ok">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="ok"/>
     *                         &lt;enumeration value="bad"/>
     *                         &lt;enumeration value="unknown"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/attribute>
     *                   &lt;attribute name="crc" type="{}crc" />
     *                   &lt;attribute name="sha1" type="{}sha1" />
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="arcade" maxOccurs="unbounded">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="board" type="{}board"/>
     *                     &lt;element name="properties" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="property" maxOccurs="unbounded">
     *                                 &lt;complexType>
     *                                   &lt;complexContent>
     *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                                       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                                     &lt;/restriction>
     *                                   &lt;/complexContent>
     *                                 &lt;/complexType>
     *                               &lt;/element>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/sequence>
     *                   &lt;attribute name="system" use="required">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="VS-Unisystem"/>
     *                         &lt;enumeration value="VS-Dualsystem"/>
     *                         &lt;enumeration value="Playchoice-10"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/attribute>
     *                   &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                   &lt;attribute name="ppu">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="RP2C03B"/>
     *                         &lt;enumeration value="RP2C03G"/>
     *                         &lt;enumeration value="RP2C04-0001"/>
     *                         &lt;enumeration value="RP2C04-0002"/>
     *                         &lt;enumeration value="RP2C04-0003"/>
     *                         &lt;enumeration value="RP2C04-0004"/>
     *                         &lt;enumeration value="RC2C03B"/>
     *                         &lt;enumeration value="RC2C03C"/>
     *                         &lt;enumeration value="RC2C05-01"/>
     *                         &lt;enumeration value="RC2C05-02"/>
     *                         &lt;enumeration value="RC2C05-03"/>
     *                         &lt;enumeration value="RC2C05-04"/>
     *                         &lt;enumeration value="RC2C05-05"/>
     *                         &lt;enumeration value="RP2C07"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/attribute>
     *                   &lt;attribute name="dumper" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                   &lt;attribute name="datedumped" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                   &lt;attribute name="dump" default="ok">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                         &lt;enumeration value="ok"/>
     *                         &lt;enumeration value="bad"/>
     *                         &lt;enumeration value="unknown"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/attribute>
     *                   &lt;attribute name="crc" type="{}crc" />
     *                   &lt;attribute name="sha1" type="{}sha1" />
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *         &lt;/choice>
     *       &lt;/sequence>
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="altname" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="subclass" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="catalog" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="publisher" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="developer" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="portdeveloper" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="region" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="players" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}token" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "peripherals",
        "cartridge",
        "arcade"
    })
    public static class Game {

        protected Database.Game.Peripherals peripherals;
        protected List<Database.Game.Cartridge> cartridge;
        protected List<Database.Game.Arcade> arcade;
        @XmlAttribute(name = "name")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String name;
        @XmlAttribute(name = "altname")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String altname;
        @XmlAttribute(name = "class")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String clazz;
        @XmlAttribute(name = "subclass")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String subclass;
        @XmlAttribute(name = "catalog")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String catalog;
        @XmlAttribute(name = "publisher")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String publisher;
        @XmlAttribute(name = "developer")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String developer;
        @XmlAttribute(name = "portdeveloper")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String portdeveloper;
        @XmlAttribute(name = "region")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String region;
        @XmlAttribute(name = "players")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger players;
        @XmlAttribute(name = "date")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String date;

        /**
         * Obt�m o valor da propriedade peripherals.
         * 
         * @return
         *     possible object is
         *     {@link Database.Game.Peripherals }
         *     
         */
        public Database.Game.Peripherals getPeripherals() {
            return peripherals;
        }

        /**
         * Define o valor da propriedade peripherals.
         * 
         * @param value
         *     allowed object is
         *     {@link Database.Game.Peripherals }
         *     
         */
        public void setPeripherals(Database.Game.Peripherals value) {
            this.peripherals = value;
        }

        /**
         * Gets the value of the cartridge property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cartridge property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCartridge().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Database.Game.Cartridge }
         * 
         * 
         */
        public List<Database.Game.Cartridge> getCartridge() {
            if (cartridge == null) {
                cartridge = new ArrayList<Database.Game.Cartridge>();
            }
            return this.cartridge;
        }

        /**
         * Gets the value of the arcade property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the arcade property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getArcade().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Database.Game.Arcade }
         * 
         * 
         */
        public List<Database.Game.Arcade> getArcade() {
            if (arcade == null) {
                arcade = new ArrayList<Database.Game.Arcade>();
            }
            return this.arcade;
        }

        /**
         * Obt�m o valor da propriedade name.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Define o valor da propriedade name.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Obt�m o valor da propriedade altname.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAltname() {
            return altname;
        }

        /**
         * Define o valor da propriedade altname.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAltname(String value) {
            this.altname = value;
        }

        /**
         * Obt�m o valor da propriedade clazz.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getClazz() {
            return clazz;
        }

        /**
         * Define o valor da propriedade clazz.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setClazz(String value) {
            this.clazz = value;
        }

        /**
         * Obt�m o valor da propriedade subclass.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubclass() {
            return subclass;
        }

        /**
         * Define o valor da propriedade subclass.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubclass(String value) {
            this.subclass = value;
        }

        /**
         * Obt�m o valor da propriedade catalog.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCatalog() {
            return catalog;
        }

        /**
         * Define o valor da propriedade catalog.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCatalog(String value) {
            this.catalog = value;
        }

        /**
         * Obt�m o valor da propriedade publisher.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPublisher() {
            return publisher;
        }

        /**
         * Define o valor da propriedade publisher.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPublisher(String value) {
            this.publisher = value;
        }

        /**
         * Obt�m o valor da propriedade developer.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDeveloper() {
            return developer;
        }

        /**
         * Define o valor da propriedade developer.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDeveloper(String value) {
            this.developer = value;
        }

        /**
         * Obt�m o valor da propriedade portdeveloper.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPortdeveloper() {
            return portdeveloper;
        }

        /**
         * Define o valor da propriedade portdeveloper.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPortdeveloper(String value) {
            this.portdeveloper = value;
        }

        /**
         * Obt�m o valor da propriedade region.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegion() {
            return region;
        }

        /**
         * Define o valor da propriedade region.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegion(String value) {
            this.region = value;
        }

        /**
         * Obt�m o valor da propriedade players.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPlayers() {
            return players;
        }

        /**
         * Define o valor da propriedade players.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPlayers(BigInteger value) {
            this.players = value;
        }

        /**
         * Obt�m o valor da propriedade date.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDate() {
            return date;
        }

        /**
         * Define o valor da propriedade date.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDate(String value) {
            this.date = value;
        }


        /**
         * <p>Classe Java de anonymous complex type.
         * 
         * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="board" type="{}board"/>
         *         &lt;element name="properties" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="property" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
         *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="system" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *             &lt;enumeration value="VS-Unisystem"/>
         *             &lt;enumeration value="VS-Dualsystem"/>
         *             &lt;enumeration value="Playchoice-10"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="ppu">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *             &lt;enumeration value="RP2C03B"/>
         *             &lt;enumeration value="RP2C03G"/>
         *             &lt;enumeration value="RP2C04-0001"/>
         *             &lt;enumeration value="RP2C04-0002"/>
         *             &lt;enumeration value="RP2C04-0003"/>
         *             &lt;enumeration value="RP2C04-0004"/>
         *             &lt;enumeration value="RC2C03B"/>
         *             &lt;enumeration value="RC2C03C"/>
         *             &lt;enumeration value="RC2C05-01"/>
         *             &lt;enumeration value="RC2C05-02"/>
         *             &lt;enumeration value="RC2C05-03"/>
         *             &lt;enumeration value="RC2C05-04"/>
         *             &lt;enumeration value="RC2C05-05"/>
         *             &lt;enumeration value="RP2C07"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="dumper" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="datedumped" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="dump" default="ok">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *             &lt;enumeration value="ok"/>
         *             &lt;enumeration value="bad"/>
         *             &lt;enumeration value="unknown"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="crc" type="{}crc" />
         *       &lt;attribute name="sha1" type="{}sha1" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "board",
            "properties"
        })
        public static class Arcade {

            @XmlElement(required = true)
            protected Board board;
            protected Database.Game.Arcade.Properties properties;
            @XmlAttribute(name = "system", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String system;
            @XmlAttribute(name = "revision")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String revision;
            @XmlAttribute(name = "ppu")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String ppu;
            @XmlAttribute(name = "dumper")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String dumper;
            @XmlAttribute(name = "datedumped")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String datedumped;
            @XmlAttribute(name = "dump")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String dump;
            @XmlAttribute(name = "crc")
            @XmlJavaTypeAdapter(HexBinaryAdapter.class)
            protected byte[] crc;
            @XmlAttribute(name = "sha1")
            @XmlJavaTypeAdapter(HexBinaryAdapter.class)
            protected byte[] sha1;

            /**
             * Obt�m o valor da propriedade board.
             * 
             * @return
             *     possible object is
             *     {@link Board }
             *     
             */
            public Board getBoard() {
                return board;
            }

            /**
             * Define o valor da propriedade board.
             * 
             * @param value
             *     allowed object is
             *     {@link Board }
             *     
             */
            public void setBoard(Board value) {
                this.board = value;
            }

            /**
             * Obt�m o valor da propriedade properties.
             * 
             * @return
             *     possible object is
             *     {@link Database.Game.Arcade.Properties }
             *     
             */
            public Database.Game.Arcade.Properties getProperties() {
                return properties;
            }

            /**
             * Define o valor da propriedade properties.
             * 
             * @param value
             *     allowed object is
             *     {@link Database.Game.Arcade.Properties }
             *     
             */
            public void setProperties(Database.Game.Arcade.Properties value) {
                this.properties = value;
            }

            /**
             * Obt�m o valor da propriedade system.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSystem() {
                return system;
            }

            /**
             * Define o valor da propriedade system.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSystem(String value) {
                this.system = value;
            }

            /**
             * Obt�m o valor da propriedade revision.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRevision() {
                return revision;
            }

            /**
             * Define o valor da propriedade revision.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRevision(String value) {
                this.revision = value;
            }

            /**
             * Obt�m o valor da propriedade ppu.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPpu() {
                return ppu;
            }

            /**
             * Define o valor da propriedade ppu.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPpu(String value) {
                this.ppu = value;
            }

            /**
             * Obt�m o valor da propriedade dumper.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDumper() {
                return dumper;
            }

            /**
             * Define o valor da propriedade dumper.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDumper(String value) {
                this.dumper = value;
            }

            /**
             * Obt�m o valor da propriedade datedumped.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDatedumped() {
                return datedumped;
            }

            /**
             * Define o valor da propriedade datedumped.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDatedumped(String value) {
                this.datedumped = value;
            }

            /**
             * Obt�m o valor da propriedade dump.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDump() {
                if (dump == null) {
                    return "ok";
                } else {
                    return dump;
                }
            }

            /**
             * Define o valor da propriedade dump.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDump(String value) {
                this.dump = value;
            }

            /**
             * Obt�m o valor da propriedade crc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public byte[] getCrc() {
                return crc;
            }

            /**
             * Define o valor da propriedade crc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCrc(byte[] value) {
                this.crc = value;
            }

            /**
             * Obt�m o valor da propriedade sha1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public byte[] getSha1() {
                return sha1;
            }

            /**
             * Define o valor da propriedade sha1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSha1(byte[] value) {
                this.sha1 = value;
            }


            /**
             * <p>Classe Java de anonymous complex type.
             * 
             * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="property" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
             *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "property"
            })
            public static class Properties {

                @XmlElement(required = true)
                protected List<Database.Game.Arcade.Properties.Property> property;

                /**
                 * Gets the value of the property property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the property property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getProperty().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Database.Game.Arcade.Properties.Property }
                 * 
                 * 
                 */
                public List<Database.Game.Arcade.Properties.Property> getProperty() {
                    if (property == null) {
                        property = new ArrayList<Database.Game.Arcade.Properties.Property>();
                    }
                    return this.property;
                }


                /**
                 * <p>Classe Java de anonymous complex type.
                 * 
                 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
                 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Property {

                    @XmlAttribute(name = "name", required = true)
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    @XmlSchemaType(name = "token")
                    protected String name;
                    @XmlAttribute(name = "value", required = true)
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    @XmlSchemaType(name = "token")
                    protected String value;

                    /**
                     * Obt�m o valor da propriedade name.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName() {
                        return name;
                    }

                    /**
                     * Define o valor da propriedade name.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName(String value) {
                        this.name = value;
                    }

                    /**
                     * Obt�m o valor da propriedade value.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Define o valor da propriedade value.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                }

            }

        }


        /**
         * <p>Classe Java de anonymous complex type.
         * 
         * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="board" type="{}board"/>
         *         &lt;element name="properties" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="property" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
         *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="system">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *             &lt;enumeration value="Famicom"/>
         *             &lt;enumeration value="NES-NTSC"/>
         *             &lt;enumeration value="NES-PAL"/>
         *             &lt;enumeration value="NES-PAL-A"/>
         *             &lt;enumeration value="NES-PAL-B"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="revision" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="prototype" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *       &lt;attribute name="dumper" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="datedumped" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="dump" default="ok">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *             &lt;enumeration value="ok"/>
         *             &lt;enumeration value="bad"/>
         *             &lt;enumeration value="unknown"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="crc" type="{}crc" />
         *       &lt;attribute name="sha1" type="{}sha1" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "board",
            "properties"
        })
        public static class Cartridge {

            @XmlElement(required = true)
            protected Board board;
            protected Database.Game.Cartridge.Properties properties;
            @XmlAttribute(name = "system")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String system;
            @XmlAttribute(name = "revision")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String revision;
            @XmlAttribute(name = "prototype")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger prototype;
            @XmlAttribute(name = "dumper")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String dumper;
            @XmlAttribute(name = "datedumped")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String datedumped;
            @XmlAttribute(name = "dump")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String dump;
            @XmlAttribute(name = "crc")
            @XmlJavaTypeAdapter(HexBinaryAdapter.class)
            protected byte[] crc;
            @XmlAttribute(name = "sha1")
            @XmlJavaTypeAdapter(HexBinaryAdapter.class)
            protected byte[] sha1;

            /**
             * Obt�m o valor da propriedade board.
             * 
             * @return
             *     possible object is
             *     {@link Board }
             *     
             */
            public Board getBoard() {
                return board;
            }

            /**
             * Define o valor da propriedade board.
             * 
             * @param value
             *     allowed object is
             *     {@link Board }
             *     
             */
            public void setBoard(Board value) {
                this.board = value;
            }

            /**
             * Obt�m o valor da propriedade properties.
             * 
             * @return
             *     possible object is
             *     {@link Database.Game.Cartridge.Properties }
             *     
             */
            public Database.Game.Cartridge.Properties getProperties() {
                return properties;
            }

            /**
             * Define o valor da propriedade properties.
             * 
             * @param value
             *     allowed object is
             *     {@link Database.Game.Cartridge.Properties }
             *     
             */
            public void setProperties(Database.Game.Cartridge.Properties value) {
                this.properties = value;
            }

            /**
             * Obt�m o valor da propriedade system.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSystem() {
                return system;
            }

            /**
             * Define o valor da propriedade system.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSystem(String value) {
                this.system = value;
            }

            /**
             * Obt�m o valor da propriedade revision.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRevision() {
                return revision;
            }

            /**
             * Define o valor da propriedade revision.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRevision(String value) {
                this.revision = value;
            }

            /**
             * Obt�m o valor da propriedade prototype.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getPrototype() {
                return prototype;
            }

            /**
             * Define o valor da propriedade prototype.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setPrototype(BigInteger value) {
                this.prototype = value;
            }

            /**
             * Obt�m o valor da propriedade dumper.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDumper() {
                return dumper;
            }

            /**
             * Define o valor da propriedade dumper.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDumper(String value) {
                this.dumper = value;
            }

            /**
             * Obt�m o valor da propriedade datedumped.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDatedumped() {
                return datedumped;
            }

            /**
             * Define o valor da propriedade datedumped.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDatedumped(String value) {
                this.datedumped = value;
            }

            /**
             * Obt�m o valor da propriedade dump.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDump() {
                if (dump == null) {
                    return "ok";
                } else {
                    return dump;
                }
            }

            /**
             * Define o valor da propriedade dump.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDump(String value) {
                this.dump = value;
            }

            /**
             * Obt�m o valor da propriedade crc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public byte[] getCrc() {
                return crc;
            }

            /**
             * Define o valor da propriedade crc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCrc(byte[] value) {
                this.crc = value;
            }

            /**
             * Obt�m o valor da propriedade sha1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public byte[] getSha1() {
                return sha1;
            }

            /**
             * Define o valor da propriedade sha1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSha1(byte[] value) {
                this.sha1 = value;
            }


            /**
             * <p>Classe Java de anonymous complex type.
             * 
             * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="property" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
             *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "property"
            })
            public static class Properties {

                @XmlElement(required = true)
                protected List<Database.Game.Cartridge.Properties.Property> property;

                /**
                 * Gets the value of the property property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the property property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getProperty().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Database.Game.Cartridge.Properties.Property }
                 * 
                 * 
                 */
                public List<Database.Game.Cartridge.Properties.Property> getProperty() {
                    if (property == null) {
                        property = new ArrayList<Database.Game.Cartridge.Properties.Property>();
                    }
                    return this.property;
                }


                /**
                 * <p>Classe Java de anonymous complex type.
                 * 
                 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
                 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Property {

                    @XmlAttribute(name = "name", required = true)
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    @XmlSchemaType(name = "token")
                    protected String name;
                    @XmlAttribute(name = "value", required = true)
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    @XmlSchemaType(name = "token")
                    protected String value;

                    /**
                     * Obt�m o valor da propriedade name.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName() {
                        return name;
                    }

                    /**
                     * Define o valor da propriedade name.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName(String value) {
                        this.name = value;
                    }

                    /**
                     * Obt�m o valor da propriedade value.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Define o valor da propriedade value.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                }

            }

        }


        /**
         * <p>Classe Java de anonymous complex type.
         * 
         * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="device" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="type" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "device"
        })
        public static class Peripherals {

            @XmlElement(required = true)
            protected List<Database.Game.Peripherals.Device> device;

            /**
             * Gets the value of the device property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the device property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDevice().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Database.Game.Peripherals.Device }
             * 
             * 
             */
            public List<Database.Game.Peripherals.Device> getDevice() {
                if (device == null) {
                    device = new ArrayList<Database.Game.Peripherals.Device>();
                }
                return this.device;
            }


            /**
             * <p>Classe Java de anonymous complex type.
             * 
             * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="type" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Device {

                @XmlAttribute(name = "type", required = true)
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String type;
                @XmlAttribute(name = "name")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String name;

                /**
                 * Obt�m o valor da propriedade type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define o valor da propriedade type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obt�m o valor da propriedade name.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Define o valor da propriedade name.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

            }

        }

    }

}
