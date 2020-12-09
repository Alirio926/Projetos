/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.cliente.common;

/**
 *
 * @author Alirio
 */
public interface ProgressBarInterface {
    public void setPercent(int p, int f, String process);
    public void setPercent(int p, int f);
    public void setStateButton(int b, boolean s);
}
