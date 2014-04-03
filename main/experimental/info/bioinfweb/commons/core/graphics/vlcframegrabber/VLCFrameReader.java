package info.bioinfweb.commons.core.graphics.vlcframegrabber;


import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;



public class VLCFrameReader {
	private DirectMediaPlayer mediaPlayer = null;

	public VLCFrameReader(MediaPlayerFactory factory, int width, int height) {
		super();
		mediaPlayer = factory.newMediaPlayer(width, height, renderCallback)
	}
	
	
	
}
