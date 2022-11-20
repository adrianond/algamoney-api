package com.algamoney.api.utils;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;
import java.util.TreeMap;

public class IndiceAlfabetico implements TemplateMethodModelEx {

	private static final TreeMap<Integer, String> map = new TreeMap<>();

    static {
    	map.put(26,"Z");
    	map.put(25,"Y");
    	map.put(24,"X");
    	map.put(23,"W");
    	map.put(22,"V");
    	map.put(21,"U");
    	map.put(20,"T");
    	map.put(19,"S");
    	map.put(18,"R");
    	map.put(17,"Q");
    	map.put(16,"P");
    	map.put(15,"O");
    	map.put(14,"N");
    	map.put(13,"M");
    	map.put(12,"L");
    	map.put(11,"K");
    	map.put(10,"J");
    	map.put(9, "I");
    	map.put(8, "H");
    	map.put(7, "G");
    	map.put(6, "F");
        map.put(5, "E");
        map.put(4, "D");
        map.put(3, "C");
        map.put(2, "B");
        map.put(1, "A");
    }

    private static String toAlfa(int number) {
        if (number < 1 || number > 26) {
            return "";
        }
        return map.get(number);
    }

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        final Integer number = ((SimpleNumber) arguments.get(0)).getAsNumber().intValue();
        return new SimpleScalar(toAlfa(number));
    }
}
