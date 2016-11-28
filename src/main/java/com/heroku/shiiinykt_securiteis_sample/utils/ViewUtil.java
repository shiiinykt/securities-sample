package com.heroku.shiiinykt_securiteis_sample.utils;

import java.util.Map;

import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;

import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

public class ViewUtil {
	
	@SuppressWarnings("rawtypes")
	public static String render(final Map attributes, final String path) {
		PebbleTemplateEngine template = getTemplateEngine();

		return template.render(new ModelAndView(attributes, path));
	}

	private static PebbleTemplateEngine getTemplateEngine() {
		Loader loader = new ClasspathLoader();
		loader.setPrefix(null);
	
		return new PebbleTemplateEngine(loader);
	}
}
