package info.bioinfweb.commons.graphics.vlcframegrabber;


import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;

import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;



public class FrameReaderRenderCallback extends RenderCallbackAdapter {
	public static final long NOTHING_TO_CAPTURE = -1;
	
	
	private DirectMediaPlayer mediaPlayer = null;
	private boolean sleeping = false;
	private int width;
	private int height;
	private BufferedImage image = null;
	private long captureTime = NOTHING_TO_CAPTURE;

	
	public FrameReaderRenderCallback(int width, int height) {
		super(new int[width * height]);
		
		this.width = width;
		this.height = height;
		image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
		    .getDefaultConfiguration().createCompatibleImage(width, height);      
		image.setAccelerationPriority(1.0f);
	}


	public void setMediaPlayer(DirectMediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}


	public boolean isSleeping() {
		return sleeping;
	}


	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}
	
	
	public boolean isIdle() {
		return captureTime == NOTHING_TO_CAPTURE;
	}
	
	
	public void sendImage(long time, String format) throws FrameGrabberBusyException {
		if (isIdle()) {
			captureTime = time;
			imageFormat = format;
		}
		else {
			throw new FrameGrabberBusyException("Frame " + captureTime + " has not yet been captured.");
		}
	}
	
	
	@Override
	protected void onDisplay(int[] rgbBuffer) {
		if (!isSleeping()) {
			if (captureTime == mediaPlayer.getTime()) {
				image.setRGB(0, 0, width, height, rgbBuffer, 0, width);
				try {
					if (imageFormat.equals(CHANNEL_MATRIX_FORMAT)) {
					  new SendingChannelMatrixResponse(new ChannelMatrix(image)).write(stream);
					}
					else {
						new SendingImageResponse(imageFormat, image).write(stream);
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				captureTime = NOTHING_TO_CAPTURE;
			}
		}
		else {
			try {
				Thread.sleep(SLEEP_INTERVAL);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
