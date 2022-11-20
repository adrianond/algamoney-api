package com.algamoney.api.utils;

import freemarker.template.*;

import java.util.List;
import java.util.TreeMap;

public class IndiceRomano implements TemplateMethodModelEx {

	private static final TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    private static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        final boolean upperCase = ((TemplateBooleanModel) arguments.get(0)).getAsBoolean();
        final Integer number = ((SimpleNumber) arguments.get(1)).getAsNumber().intValue();
        String roman = toRoman(number);
        return new SimpleScalar(upperCase ? roman : roman.toLowerCase());
    }
}
