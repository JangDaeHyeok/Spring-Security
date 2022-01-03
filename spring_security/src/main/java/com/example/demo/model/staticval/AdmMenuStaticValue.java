package com.example.demo.model.staticval;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.vo.SecurityUrlMatcher;

import lombok.Getter;

@Getter
public class AdmMenuStaticValue {

	public static List<SecurityUrlMatcher> admMenuList = new ArrayList<SecurityUrlMatcher>();
	
}
