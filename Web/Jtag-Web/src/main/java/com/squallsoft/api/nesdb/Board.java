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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java de board complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="board">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prg" type="{}rom" maxOccurs="unbounded"/>
 *         &lt;element name="chr" type="{}rom" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="wram" type="{}ram" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vram" type="{}ram" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="chip" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ic">
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="battery" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="cic" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pad" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="h" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                       &lt;minInclusive value="0"/>
 *                       &lt;maxInclusive value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="v" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                       &lt;minInclusive value="0"/>
 *                       &lt;maxInclusive value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="pcb" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="mapper">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *             &lt;minInclusive value="0"/>
 *             &lt;maxInclusive value="255"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "board", propOrder = {
    "prg",
    "chr",
    "wram",
    "vram",
    "chip",
    "cic",
    "pad"
})
public class Board {

    @XmlElement(required = true)
    protected List<Rom> prg;
    protected List<Rom> chr;
    protected List<Ram> wram;
    protected List<Ram> vram;
    protected List<Board.Chip> chip;
    protected List<Board.Cic> cic;
    protected List<Board.Pad> pad;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String type;
    @XmlAttribute(name = "pcb")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String pcb;
    @XmlAttribute(name = "mapper")
    protected Integer mapper;

    /**
     * Gets the value of the prg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rom }
     * 
     * 
     */
    public List<Rom> getPrg() {
        if (prg == null) {
            prg = new ArrayList<Rom>();
        }
        return this.prg;
    }

    /**
     * Gets the value of the chr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rom }
     * 
     * 
     */
    public List<Rom> getChr() {
        if (chr == null) {
            chr = new ArrayList<Rom>();
        }
        return this.chr;
    }

    /**
     * Gets the value of the wram property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wram property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWram().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ram }
     * 
     * 
     */
    public List<Ram> getWram() {
        if (wram == null) {
            wram = new ArrayList<Ram>();
        }
        return this.wram;
    }

    /**
     * Gets the value of the vram property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vram property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVram().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ram }
     * 
     * 
     */
    public List<Ram> getVram() {
        if (vram == null) {
            vram = new ArrayList<Ram>();
        }
        return this.vram;
    }

    /**
     * Gets the value of the chip property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chip property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChip().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Board.Chip }
     * 
     * 
     */
    public List<Board.Chip> getChip() {
        if (chip == null) {
            chip = new ArrayList<Board.Chip>();
        }
        return this.chip;
    }

    /**
     * Gets the value of the cic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Board.Cic }
     * 
     * 
     */
    public List<Board.Cic> getCic() {
        if (cic == null) {
            cic = new ArrayList<Board.Cic>();
        }
        return this.cic;
    }

    /**
     * Gets the value of the pad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Board.Pad }
     * 
     * 
     */
    public List<Board.Pad> getPad() {
        if (pad == null) {
            pad = new ArrayList<Board.Pad>();
        }
        return this.pad;
    }

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
     * Obt�m o valor da propriedade pcb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcb() {
        return pcb;
    }

    /**
     * Define o valor da propriedade pcb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcb(String value) {
        this.pcb = value;
    }

    /**
     * Obt�m o valor da propriedade mapper.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMapper() {
        return mapper;
    }

    /**
     * Define o valor da propriedade mapper.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMapper(Integer value) {
        this.mapper = value;
    }


    /**
     * <p>Classe Java de anonymous complex type.
     * 
     * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}ic">
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="battery" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Chip
        extends Ic
    {

        @XmlAttribute(name = "type", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String type;
        @XmlAttribute(name = "battery")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger battery;

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
         * Obt�m o valor da propriedade battery.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getBattery() {
            return battery;
        }

        /**
         * Define o valor da propriedade battery.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setBattery(BigInteger value) {
            this.battery = value;
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
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Cic {

        @XmlAttribute(name = "type", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String type;

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
     *       &lt;attribute name="h" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *             &lt;minInclusive value="0"/>
     *             &lt;maxInclusive value="1"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="v" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *             &lt;minInclusive value="0"/>
     *             &lt;maxInclusive value="1"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Pad {

        @XmlAttribute(name = "h", required = true)
        protected int h;
        @XmlAttribute(name = "v", required = true)
        protected int v;

        /**
         * Obt�m o valor da propriedade h.
         * 
         */
        public int getH() {
            return h;
        }

        /**
         * Define o valor da propriedade h.
         * 
         */
        public void setH(int value) {
            this.h = value;
        }

        /**
         * Obt�m o valor da propriedade v.
         * 
         */
        public int getV() {
            return v;
        }

        /**
         * Define o valor da propriedade v.
         * 
         */
        public void setV(int value) {
            this.v = value;
        }

    }

}
