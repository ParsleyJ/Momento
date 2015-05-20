package rendering.effects;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

public class PosterizeFilter implements Filter {

	public BufferedImage processImage(BufferedImage image) {
		short[] invertArray = new short[256];

		for (int counter = 0; counter < 256; counter++)
			invertArray[counter] = (short) ((counter-(counter%32)));

		BufferedImageOp invertFilter = new LookupOp(new ShortLookupTable(0, invertArray), null);
		return invertFilter.filter(image, null);

	}

}
