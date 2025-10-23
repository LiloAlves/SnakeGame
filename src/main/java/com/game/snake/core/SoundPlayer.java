package com.game.snake.core;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundPlayer {
    public static void play(String soundFileName) {
        try {
            URL url = SoundPlayer.class.getResource("/sounds/" + soundFileName);
            if (url == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return;
            }
            AudioInputStream in = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.addLineListener(ev -> {
                if (ev.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
