package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import base.MusicManager;
import javazoom.jl.decoder.JavaLayerException;

public class TestMusic {

	public static void main(String[] args) throws FileNotFoundException, JavaLayerException, IOException {
		MusicManager.playBgm();
	}

}
