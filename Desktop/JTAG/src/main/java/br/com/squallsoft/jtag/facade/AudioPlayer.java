/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.facade;

import java.net.URL;
import javax.sound.sampled.*;

public class AudioPlayer {
	
    private Clip clip;

    public AudioPlayer(String s) {
            try {
                final URL url = Thread.currentThread().getContextClassLoader().getResource(s);
                    AudioInputStream ais =
                            AudioSystem.getAudioInputStream(url);
                    AudioFormat baseFormat = ais.getFormat();
                    AudioFormat decodeFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            baseFormat.getSampleRate(),
                            16,
                            baseFormat.getChannels(),
                            baseFormat.getChannels() * 2,
                            baseFormat.getSampleRate(),
                            false
                    );
                    AudioInputStream dais =
                            AudioSystem.getAudioInputStream(
                                    decodeFormat, ais);
                    clip = AudioSystem.getClip();
                    clip.open(dais);
            }
            catch(Exception e) {
                    e.printStackTrace();
            }
    }

    public void play() {
            if(clip == null) return;
            stop();
            clip.setFramePosition(0);
            clip.start();
    }

    public void stop() {
            if(clip.isRunning()) clip.stop();
    }

    public void close() {
            stop();
            clip.close();
    }
	
}