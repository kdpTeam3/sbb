package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Hellolombok {
	private final String hello;
	private final int lombok;
	
	public static void main(String[] args) {
		Hellolombok helloLombok = new Hellolombok("헬로",5);
		
		
		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());
	}

}
