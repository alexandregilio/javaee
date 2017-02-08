package org.sticker.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

@Named("stickerSheet")
@ApplicationScoped
public class StickerSheet implements Serializable {

	public List<String> getAllStickers() {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		final String stickerPath = "/resources/stickers/";
		List<String> allStickers = new ArrayList<>(ctx.getResourcePaths(stickerPath));

		for (ListIterator<String> listIterator = allStickers.listIterator(); listIterator.hasNext();) {
			String value = listIterator.next();
			value = value.substring(stickerPath.length(), value.length());
			listIterator.set(value);
		}

		return allStickers;
	}
}