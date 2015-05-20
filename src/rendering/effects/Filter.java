package rendering.effects;

import java.awt.image.BufferedImage;

public interface Filter{
	  public abstract BufferedImage processImage(BufferedImage image);
	}
